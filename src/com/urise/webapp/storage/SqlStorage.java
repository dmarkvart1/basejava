package com.urise.webapp.storage;

import com.google.gson.Gson;
import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

import static com.urise.webapp.model.SectionType.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "SELECT * FROM resume r " +
                        "  LEFT JOIN contact c " +
                        "    ON r.uuid = c.resume_uuid " +
                        "        LEFT JOIN section s " +
                        "    ON r.uuid = s.resume_uuid " +
                        "  WHERE r.uuid = ?",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String contactValue = rs.getString(5);
                        ContactType contactType = ContactType.valueOf(rs.getString(4));
                        resume.addContact(contactType, contactValue);

                        String sectionValue = rs.getString(9);
                        SectionType sectionType = SectionType.valueOf(rs.getString(8));
                        switch (sectionType) {
                            case OBJECTIVE:
                            case PERSONAL:
                                resume.addSection(sectionType, new TextContentSection(sectionValue));
                                break;
                            default:
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
//                                test(new ListStringSection(Collections.singletonList(sectionValue)));
                                resume.addSection(sectionType, new ListStringSection(sectionValue));
                                break;
                        }

                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(r.getUuid());
                }
            }
            deleteContact(conn, r);
            insertContact(conn, r);
            deleteSection(conn, r);
            insertSections(conn, r);
            return null;
        });
    }


    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContact(conn, r);
                    insertSections(conn, r);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid=?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumesMap = new LinkedHashMap<>();
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = resumesMap.get(uuid);
                    if (resume == null) {
                        resume = new Resume(uuid, rs.getString("full_name"));
                        resumesMap.put(uuid, resume);
                    }
                }
            }
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume resume = resumesMap.get(uuid);
                    ContactType type = (ContactType.valueOf(rs.getString("type")));
                    String value = rs.getString("value");
                    resume.addContact(type, value);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume resume = resumesMap.get(uuid);
                    SectionType type = (SectionType.valueOf(rs.getString("type")));
                    String value = rs.getString("value");
                    if (answerSectionType(type)){
                        resume.addSection(type, new TextContentSection(value));
                    }else {
                        resume.addSection(type, new ListStringSection(value));
                    }
                }
            }
            return null;
        });
        return new ArrayList<>(resumesMap.values());
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume", st -> {
            ResultSet rs = st.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void insertContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, String.valueOf(e.getValue()).replaceAll("\\[|\\]",""));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContact(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE  FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void deleteSection(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE  FROM section WHERE resume_uuid=?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private boolean answerSectionType(SectionType sectionType) {
        switch (sectionType) {
            case OBJECTIVE:
            case PERSONAL:
                return true;
            default:
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                return false;
        }
    }

//    private void test(ListStringSection listStringSection){
//        Gson gson = new Gson();
//        String sample = gson.toJson(listStringSection);
//        ListStringSection fromJsonSection = gson.fromJson(sample, ListStringSection.class);
//        int i=0;
//        for(String name : fromJsonSection.getLists()){
//            i++;
//            String[] array = name.substring(1, name.length() - 1).split(",");
//            System.out.println(array[i]);
//            }
//        }
}

//    String sample = gson.toJson(e.getValue());
//                ListStringSection fromJsonSection = gson.fromJson(sample, ListStringSection.class);
//                int i = 0;
//                for (String elem : fromJsonSection.getLists()) {
//                    i++;
//                    section = Arrays.asList(elem.substring(1, elem.length() - 1).split(","));
//                    section.set(i, elem + "/n");
//                }
//                ps.setString(3, String.valueOf(section));