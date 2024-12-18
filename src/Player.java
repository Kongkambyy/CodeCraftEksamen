public class Player {
    // Private variabler til at gemme brugerens grundlæggende oplysninger
    // private sikrer at disse kun kan tilgås inden for Player-klassen
    private String username;      // Brugerens brugernavn
    private String password;      // Brugerens adgangskode

    // Opretter et standard inventory for brugeren
    // Hver bruger får sit eget inventory ved oprettelse
    private Inv userInventory = new Inv();

    // Konstruktør der initialiserer en ny bruger
    // Modtager brugernavn og password som parametre
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Setter-metode til at ændre brugerens brugernavn
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter-metode til at hente brugerens brugernavn
    public String getUsername() {
        return username;
    }

    // Setter-metode til at ændre brugerens password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter-metode til at hente brugerens password
    public String getPassword() {
        return password;
    }

    // Getter-metode til at hente brugerens fulde inventory
    public Inv getUserInventory() {
        return userInventory;
    }

    // Setter-metode til at opdatere hele brugerens inventory
    public void setUserInventory(Inv userInventory) {
        this.userInventory = userInventory;
    }

    // Alternativ getter-metode til at hente inventory
    public Inv getInv() {
        return userInventory;
    }
}