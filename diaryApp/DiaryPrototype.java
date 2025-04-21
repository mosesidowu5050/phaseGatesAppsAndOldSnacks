import java.util.Scanner;

public class DiaryPrototype {
    public static Diaries newDiaries = new Diaries();
    public static Diary personalDiary;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {
            printMenu();
            System.out.print("Choose option from 1 - 7: ");
            String option = scanner.nextLine();

            while (!option.matches("[1-7]")) {
                System.out.print("Choose option from 1 - 7");
                option = scanner.nextLine();
            }

            if (option.equals("1")) {
                loginEntry();
            }
            if (option.equals("2")) {
                createEntry();
            }
            if (option.equals("3")) {
                deleteEntry();
            } else if (option.equals("4")) {
                findEntryById();
            } else if (option.equals("5")) {
                updateEntry();
            }
            else if (option.equals("6")) {
                findByUsername();
            } else if (option.equals("7")) {
                System.out.println("Exiting...");
                break;
            }
            System.out.println();
        }
    }


    public static void printMenu(){
        System.out.println("""
                1. Login Entry.
                2. Create a new Entry.
                3. Delete Entry.
                4. Find Entry by ID
                5. Update Entry.
                6. Find Dairy by Username.
                7. Exit.
                """);
    }

    public static void loginEntry() {
        String username;
        String password;

        while (true) {
            System.out.print("Enter Username: ");
            username = scanner.nextLine();
            if (username.matches("[a-zA-Z]+")) break;
            System.out.println("Invalid username. Only letters are allowed.");
        }

        while (true) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (password.matches("[a-zA-Z0-9]{8}")) break;
            System.out.println("Invalid password. 8 characters only.");
        }

        try {
            personalDiary = new Diary(username, password);
            System.out.println("New diary created successfully for: " + username);

            newDiaries.add(username, password);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to create diary: " + e.getMessage());
        }
    }



    public static void createEntry() {
        if (personalDiary == null) {
            System.out.println("Please log in first using option 1.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try {
            personalDiary.unlockDiary(password);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to unlock diary: " + e.getMessage());
            return;
        }

        System.out.print("Enter the title: ");
        String title = scanner.nextLine();

        System.out.print("Enter the body: ");
        String body = scanner.nextLine();

        try {
            personalDiary.createEntry(title, body);
            System.out.println("Entry created successfully! Your ID is: " + personalDiary.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to create entry: " + e.getMessage());
        }

        personalDiary.lockDiary();
    }

    public static void deleteEntry() {
        if (personalDiary == null) {
            System.out.println("Please log in first using option 1.");
            return;
        }

        System.out.println("Enter password to unlock diary: ");
        String password = scanner.nextLine();

        try {
            personalDiary.unlockDiary(password);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to unlock diary: " + e.getMessage());
            return;
        }

        System.out.println("Enter the ID to delete entry: ");
        String idNumber = scanner.nextLine();

        int id = 0;
        try {
            id = Integer.parseInt(idNumber);
        } catch (NumberFormatException e) {
            System.out.println("Failed to find entry with ID: " + idNumber);
        }

        try {
            personalDiary.deleteEntry(id);
            System.out.println("Entry deleted successfully! Your ID is: " + id);
        } catch (IllegalArgumentException e){
            System.out.println("Failed to delete entry: " + e.getMessage());
        }
        personalDiary.lockDiary();
    }

    public static void findEntryById() {
        if (personalDiary == null) {
            System.out.println("Please log in first using option 1.");
            return;
        }

        System.out.println("Enter password to unlock diary: ");
        String password = scanner.nextLine();

        try {
            personalDiary.unlockDiary(password);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to unlock diary: " + e.getMessage());
            return;
        }

        System.out.println("Enter the ID to find entry: ");
        String idInput = scanner.nextLine();
        int id;

        try {
            id = Integer.parseInt(idInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
            return;
        }

        try {
            Entry entry = personalDiary.findEntryById(id);
            if (entry != null) {
                System.out.println("ID Entry:\n" + entry);
            } else {
                System.out.println("No entry found with ID: " + id);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to find entry: " + e.getMessage());
        }
            personalDiary.lockDiary();
        }


        public static void updateEntry() {
            if (personalDiary == null) {
                System.out.println("Please log in first using option 1.");
            }
            System.out.println("Enter password to unlock diary: ");
            String password = scanner.nextLine();
            try {
                personalDiary.unlockDiary(password);
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to unlock diary: " + e.getMessage());
            }

            System.out.println("Enter your ID: ");
            String idInput = scanner.nextLine();

            System.out.println("Enter the title: ");
            String titleInput = scanner.nextLine();

            System.out.println("Enter the body: ");
            String bodyInput = scanner.nextLine();
            try {
                personalDiary.updateEntry(Integer.parseInt(idInput), titleInput, bodyInput);
                System.out.println("Entry updated successfully!");
            } catch (IllegalArgumentException e) {
                System.out.println("Failed to update entry: " + e.getMessage());
            }
            personalDiary.lockDiary();
        }

    private static void findByUsername() {
        if (personalDiary == null){
            System.out.println("Please log in first using option 1.");
        }
        System.out.println("Enter password to unlock diary: ");
        String password = scanner.nextLine();

        try {
            personalDiary.unlockDiary(password);
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to unlock diary: " + e.getMessage());
        }
        System.out.println("Enter your username: ");
        String username = scanner.nextLine();

        try {
            Diary foundEntry = newDiaries.findByUsername(username);
            System.out.println("Found entry with username: " + foundEntry);
        } catch (IllegalArgumentException e){
            System.out.println("Failed to find entry with username: " + e.getMessage());
        }
        personalDiary.lockDiary();
    }
}
