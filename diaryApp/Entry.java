import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Entry {
    private int id;
    private String title;
    private String contentBody;
    private LocalDateTime dateCreated;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Entry(int id, String title, String body) {
        if (id < 0) {
            throw new IllegalArgumentException("Invalid id entry...");
        }

        this.id = id;
        this.title = title;
        this.contentBody = body;
        this.dateCreated = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContentBody() {
        return contentBody;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }
    public String toString() {
        return "Entry ID: "+ id + "\nTitle: " + title + " \nBody: " + contentBody + "\nCreated: " + dateCreated.format(FORMATTER);
    }
}
