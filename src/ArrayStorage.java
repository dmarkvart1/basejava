/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[4];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                break;
            } else storage[i] = null;
        }

    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                return null;
            } else return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                continue;
            } else if (storage[i].uuid == uuid) {
                storage[i] = null;
            }
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] withoutNull = new Resume[0];
        int count = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                count++;
            }
        }
        withoutNull = new Resume[count];
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                for (int w = 0; w < withoutNull.length; w++) {
                    if (withoutNull[w] == null) {
                        withoutNull[w] = storage[i];
                        break;
                    }
                }
            }
        }

        return withoutNull;
    }

    int size() {
        int count = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) {
                count++;
            }
        }
        return count;
    }
}
