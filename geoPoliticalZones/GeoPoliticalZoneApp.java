import java.util.Scanner;

public class GeoPoliticalZoneApp {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of your state: ");
        String stateName = scanner.nextLine();

        int counter = 0;

        for (GeoPoliticalZones allZones : GeoPoliticalZones.values()) {
            for(String state : allZones.getPoliticalZones())
                if (stateName.equalsIgnoreCase(state)) {
                    counter++;
                    System.out.println(allZones);
            }
        }
        if(counter != 1) System.out.println("No political zone found");
    }
}
