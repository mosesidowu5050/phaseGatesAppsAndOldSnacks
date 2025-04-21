import java.util.List;

public class Diaries {
   private List<Diary> diaries;

    public void add(String title, String body) {
        diaries.add(new Diary(title, body));
    }

    public Diary findByUserName(String userName) {
        for (Diary diary : diaries) {
            if (diary.getUsername().equals(userName)) {
                return diary;
            }
        }
        return null;
    }

        public void delete(String username, String body) {
        diaries.remove(new Diary(username, body));
    }
}
