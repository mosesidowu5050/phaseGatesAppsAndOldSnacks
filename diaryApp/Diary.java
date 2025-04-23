import java.util.ArrayList;
import java.util.List;

public class Diary {
    private String username;
    private String password;
    private boolean isLocked;
    private List<Entry> entries;
    private int idCounter;

    public Diary(String username, String password) {
        if (!isValidUsernameAndPassword(username, password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        this.username = username;
        this.password = password;
        this.isLocked = true;
        this.idCounter = 400;
        this.entries = new ArrayList<>();
    }

    public void unlockDiary(String password) {
        if (!password.equals(this.password)) {
            throw new IllegalArgumentException("Invalid password");
        }
        this.isLocked = false;
    }

    public void lockDiary() {
        this.isLocked = true;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void createEntry(String title, String body) {
        if (title == null || body == null) {
            throw new IllegalArgumentException("Title and body cannot be null.");
        }
        Entry newEntry = new Entry(idCounter++, title, body);
        entries.add(newEntry);
    }

    public int getId() {
        return idCounter - 1;
    }

    public int getSize() {
        return entries.size();
    }

    public void deleteEntry(int id) {
        if (isLocked) throw new IllegalArgumentException("Diary is locked...");

        Entry entryToRemove = findEntryById(id);
        if (entryToRemove == null) {
            throw new IllegalArgumentException("Entry with given ID does not exist");
        }
        entries.remove(entryToRemove);
    }

    public Entry findEntryById(int id) {
        if (isLocked) throw new IllegalArgumentException("Diary is locked...");
        for (Entry entry : entries) {
            if (entry.getId() == id) {
                return entry;
            }
        }
        return null;
    }


    public void updateEntry(int id, String newTitle, String newBody) {
        if (isLocked) throw new IllegalStateException("Diary is locked...");
        Entry entryToUpdate = findEntryById(id);
        if (entryToUpdate == null) {
            throw new IllegalArgumentException("ID number not found.");
        }
        if (newTitle == null || newBody == null ) {
            throw new IllegalArgumentException("Title and body cannot be null or empty.");
        }
        entries.remove(entryToUpdate);
        entries.add(new Entry(id, newTitle, newBody));
    }



    public String getUsername() {
        return this.username;
    }

    public boolean checkPassword(String password) {
            return this.password.equals(password);
    }

    private boolean isValidUsernameAndPassword(String username, String password) {
        boolean isValidUsername = username != null && !username.trim().isEmpty();
        boolean isValidPassword = password != null && password.length() >= 8;
        return isValidUsername && isValidPassword;
    }
}
