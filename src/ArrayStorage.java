/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            storage[i] = null;
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
        Resume r = null;
        for (int i = 0; i < size (); i++) {
            if (storage[i].uuid.equals(uuid)) {
                r = storage[i];
                break;
            }
        }
        return r ;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                storage[i] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume [] resumeNotNull = new Resume[size()];
        int count = 0;
        for (int i = 0; i < storage.length; i++) {
            try {
                if (storage[i].uuid != null) resumeNotNull[count++] = storage[i];
            } catch (NullPointerException e) {
                //NOP
            }
        }
        return resumeNotNull;
    }

    int size() {
        int count = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) count++;
        }
        return count;
    }
}
