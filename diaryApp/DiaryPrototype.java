import java.util.Scanner;
import java.io.IOException;
import java.io.FileNotFoundException;

public class DiaryPrototype {
    private static Diaries newDiaries;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String DIARIES_FILE = "diaries.ser";

    public static void main(String[] args) {

        try {
            newDiaries = Diaries.loadHistoryFromFile(DIARIES_FILE);
            System.out.println("Loaded existing diaries from " + DIARIES_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("No existing diaries found. Starting with a new Diaries collection.");
            newDiaries = new Diaries();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading diaries: " + e.getMessage());
            System.out.println("Starting with a new Diaries collection.");
            newDiaries = new Diaries();
        }

        printDiariesMenu();
        scanner.close();
    }


    private static void printDiariesMenu() {
        while (true) {
            displayDiariesMenu();
            String option = scanner.nextLine();
            if (!option.matches("[1-6]")) {
                System.out.println("Invalid option. Please choose 1-6.");
                continue;
            }

            switch (option) {
                case "1" -> createDiary();
                case "2" -> findDiaryByUsername();
                case "3" -> deleteDiary();
                case "4" -> saveDiariesHistory();
                case "5" -> loadDiariesHistory();
                case "6" -> {
                    System.out.println("Exiting...");
                    try {
                        newDiaries.saveHistoryToFile(DIARIES_FILE);
                        System.out.println("Diaries saved successfully.");
                    } catch (IOException e) {
                        System.err.println("Error saving diaries: " + e.getMessage());
                    }
                    return;
                }
            }
            System.out.println();
        }
    }

    private static void displayDiariesMenu() {
        System.out.println("""
                    Diaries Menu:
                        1. Create / Add Diary
                        2. Find Diary By Username
                        3. Delete Diary
                        4. Save Diaries
                        5. Load Diaries
                        6. Exit
                """);
        System.out.print("Choose option (1-6): ");
    }

    private static void createDiary() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();

        System.out.print("Enter Password (at least 8 characters): ");
        String password = scanner.nextLine();

        try {
            newDiaries.add(username, password);
            System.out.println("Diary created successfully for: " + username);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to create diary: " + e.getMessage());
        }
    }

    private static void findDiaryByUsername() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        Diary diary = newDiaries.findByUsername(username);

        if (diary == null) {
            System.out.println("Diary not found for username: " + username);
            return;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        try {
            diary.unlockDiary(password);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to unlock diary: " + e.getMessage());
            return;
        }

        handleDiaryMenu(diary, username);
    }

    private static void handleDiaryMenu(Diary diary, String username) {
        while (true) {
            displayDiaryMenu(username);
            String option = scanner.nextLine();
            if (!option.matches("[1-5]")) {
                System.out.println("Invalid option. Please choose 1-5.");
                continue;
            }

            switch (option) {
                case "1" -> addEntry(diary);
                case "2" -> findEntryById(diary);
                case "3" -> updateEntry(diary);
                case "4" -> deleteEntry(diary);
                case "5" -> {
                    diary.lockDiary();
                    return;
                }
            }
            System.out.println();
        }
    }

    private static void displayDiaryMenu(String username) {
        System.out.println("Welcome, " + username.toUpperCase());
        System.out.println("""
            1. Create an Entry
            2. Find Entry By ID
            3. Update Entry
            4. Delete Entry
            5. Return to Diaries Menu
        """);
        System.out.print("Choose option (1-5): ");
    }

    private static void addEntry(Diary diary) {
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Body: ");
        String body = scanner.nextLine();
        try {
            diary.createEntry(title, body);
            System.out.println("Entry created successfully! ID: " + (diary.getId()));
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to create entry: " + e.getMessage());
        }
    }

    private static void findEntryById(Diary diary) {
        System.out.print("Enter Entry ID: ");
        String idInput = scanner.nextLine();
        try {
            int id = Integer.parseInt(idInput);
            Entry entry = diary.findEntryById(id);
            if (entry != null) {
                System.out.println("Entry:\n" + entry);
            } else {
                System.out.println("No entry found with ID: " + id);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to view entry: Invalid ID or " + e.getMessage());
        }
    }

    private static void updateEntry(Diary diary) {
        System.out.print("Enter Entry ID: ");
        String idInput = scanner.nextLine();
        System.out.print("Enter New Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter New Body: ");
        String body = scanner.nextLine();
        try {
            diary.updateEntry(Integer.parseInt(idInput), title, body);
            System.out.println("Entry updated successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to update entry: Invalid ID or " + e.getMessage());
        }
    }

    private static void deleteEntry(Diary diary) {
        System.out.print("Enter Entry ID: ");
        String idInput = scanner.nextLine();
        try {
            int id = Integer.parseInt(idInput);
            diary.deleteEntry(id);
            System.out.println("Entry deleted successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to delete entry: Invalid ID or " + e.getMessage());
        }
    }

    private static void deleteDiary() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try {
            newDiaries.delete(username, password);
            System.out.println("Diary deleted successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to delete diary: " + e.getMessage());
        }
    }

    private static void saveDiariesHistory() {
        try {
            newDiaries.saveHistoryToFile(DIARIES_FILE);
            System.out.println("Diaries saved successfully.");
        } catch (IOException e) {
            System.out.println("Failed to save diaries: " + e.getMessage());
        }
    }

    private static void loadDiariesHistory() {
        try {
            newDiaries = Diaries.loadHistoryFromFile(DIARIES_FILE);
            System.out.println("Diaries loaded successfully from " + DIARIES_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("No saved diaries found at " + DIARIES_FILE);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load diaries: " + e.getMessage());
        }
    }
}