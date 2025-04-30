import java.util.Arrays;

public enum GeoPoliticalZones {
    North_Central("Benue", "FCT", "Kogi", "Kwara", "Nasarawa", "Niger", "Plateu"),
    North_East("Adamawa", "Bauchi", "Borno"),
    North_West("Kaduna", "Katsina", "Kano", "Kebbi", "Sokoto", "Jigawa", "Zamfara"),
    South_East("Abia", "Anambra", "Ebonyi", "Enugu", "Imo"),
    South_South("Akwa_Ibom", "Bayelsa", "Cross-River", "Delta", "Edo", "Rivers"),
    South_West("Ekiti", "Lagos", "Osun", "Ondo", "Ogun", "Oyo")
    ;
    private String[] politicalZones;

    GeoPoliticalZones(String... politicalZones) {
        this.politicalZones = politicalZones;
    }


    public String[] getPoliticalZones() {
        return this.politicalZones;
    }
}
