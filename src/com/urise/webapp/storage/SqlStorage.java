package com.urise.webapp.storage;

import com.urise.webapp.exeption.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;

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
        return sqlHelper.transactionalExecute(conn -> {
            Resume resume;
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, rs.getString("full_name"));
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM contact WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addContact(rs, resume);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section WHERE resume_uuid =?")) {
                ps.setString(1, uuid);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    addSection(rs, resume);
                }
            }

            return resume;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                if (ps.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContact(conn, resume);
            insertContact(conn, resume);
            deleteSection(conn, resume);
            insertSections(conn, resume);
            return null;
        });
    }


    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    insertContact(conn, resume);
                    insertSections(conn, resume);
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
                    addContact(rs, resume);
                }
            }

            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM section ORDER BY resume_uuid")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("resume_uuid");
                    Resume resume = resumesMap.get(uuid);
                    addSection(rs, resume);
//                    if (answerSectionType(type)){
//                        resume.addSection(type, new TextContentSection(value));
//                    }else {
//                        resume.addSection(type, new ListStringSection(value));
//                    }
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

    private void insertContact(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, String.valueOf(e.getValue()).replaceAll("\\[|\\]", ""));
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContact(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE  FROM contact WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void deleteSection(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE  FROM section WHERE resume_uuid=?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("type"));
                                if (answerSectionType(type)){
                        resume.addSection(type, new TextContentSection(value));
                    }else {
                        resume.addSection(type, new ListStringSection(value));
                    }
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
}
//
//    private Map<String, Resume> getResumeTitle(ResultSet rs) throws SQLException {
//        Resume resume = null;
//        while (rs.next()) {
//            String uuid = rs.getString("uuid");
//            resume = resumesMap.get(uuid);
//            if (resume == null) {
//                resume = new Resume(uuid, rs.getString("full_name"));
//                resumesMap.put(uuid, resume);
//            }
//        }
//        return resumesMap;
//    }
//
//    private Resume getResumeContact(ResultSet rs) throws SQLException {
//        Resume resume = null;
//        while (rs.next()) {
//            String uuid = rs.getString("resume_uuid");
//            resume = resumesMap.get(uuid);
//            ContactType type = (ContactType.valueOf(rs.getString("type")));
//            String value = rs.getString("value");
//            resume.addContact(type, value);
//        }
//        return resume;
//    }
//
//    private Resume getResumeSection(ResultSet rs) throws SQLException {
//        Resume resume = null;
//        while (rs.next()) {
//            String uuid = rs.getString("resume_uuid");
//            resume = resumesMap.get(uuid);
//            SectionType type = (SectionType.valueOf(rs.getString("type")));
//            String value = rs.getString("value");
//            if (answerSectionType(type)) {
//                resume.addSection(type, new TextContentSection(value));
//            } else {
//                resume.addSection(type, new ListStringSection(value));
//            }
//        }
//        return resume;
//    }



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


//    String sample = gson.toJson(e.getValue());
//                ListStringSection fromJsonSection = gson.fromJson(sample, ListStringSection.class);
//                int i = 0;
//                for (String elem : fromJsonSection.getLists()) {
//                    i++;
//                    section = Arrays.asList(elem.substring(1, elem.length() - 1).split(","));
//                    section.set(i, elem + "/n");
//                }
//                ps.setString(3, String.valueOf(section));


//    private void deleteAttributes(Connection conn, Resume resume, String sql) throws SQLException {
//        try (PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, resume.getUuid());
//            ps.execute();
//        }
//    }