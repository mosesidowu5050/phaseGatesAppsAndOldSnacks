import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Diaries implements Serializable {
    private static final long serialVersionUID = 3L;
    private List<Diary> diaries = new ArrayList<>();

    public void add(String username, String password) {
        if (findByUsername(username) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        Diary diary = new Diary(username, password);
        diaries.add(diary);
    }

    public Diary findByUsername(String username) {
        if (username == null) {
            return null;
        }
        for (Diary diary : diaries) {
            if (username.equals(diary.getUsername())) {
                return diary;
            }
        }
        return null;
    }

    public void delete(String username, String password) {
        Diary removeDiary = null;
        for (Diary diary : diaries) {
            if (diary.getUsername().equals(username) && diary.checkPassword(password)) {
                removeDiary = diary;
                break;
            }
        }
        if (removeDiary != null) {
            diaries.remove(removeDiary);
        } else {
            throw new IllegalArgumentException("Diary not found!");
        }
    }

    public void saveHistoryToFile(String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
            System.out.println("Diaries saved to " + fileName);
        }
    }

    public static Diaries loadHistoryFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Diaries) in.readObject();
        }
    }
}