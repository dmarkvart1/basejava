import com.sun.deploy.util.ArrayUtil;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
            size = 0;
        }

    }

    void save(Resume r) {
        for (int i = 0; i <= size; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                size++;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid)
                return storage[i];
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                System.arraycopy(storage, i + 1, storage, i, storage.length - 1 - i);
                size--;
            }
        }
    }


    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] withoutNull = new Resume[size];
        for (int i = 0; i < size; i++) {
                for (int w = 0; w < size; w++) {
                    if (withoutNull[w] == null) {
                        withoutNull[w] = storage[i];
                        break;
                    }
                }
        }

        return withoutNull;
    }

    int size() {
        return size;
    }
}
