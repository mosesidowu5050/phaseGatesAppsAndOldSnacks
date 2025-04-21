import java.time.LocalDate;

public class Entry {
    private int id;
    private String title;
    private String contentBody;
    private LocalDate dateCreated;

    public Entry(int id, String title, String body) {
        if (title == null || title.trim().isEmpty() || body == null || body.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid entry details...");
        }
        if (id < 0) {
            throw new IllegalArgumentException("Invalid id entry...");
        }

        this.id = id;
        this.title = title;
        this.contentBody = body;
        this.dateCreated = LocalDate.now();
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

    public LocalDate getDateCreated() {
        return dateCreated;
    }
}
