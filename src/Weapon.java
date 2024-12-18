public class Weapon extends Item{

    // Statiske konstanter definerer standard våben i spillet
    // Hver våben har unik ID, navn, vægt og skadesværdi
    static final Weapon Sword = new Weapon(0, "Sword", 17,25);
    static final Weapon AXE = new Weapon(1, "Axe", 21, 12);
    static final Weapon SPEAR = new Weapon(2, "Spear", 15, 10);
    static final Weapon DAGGER = new Weapon(3, "Dagger", 10, 10);

    // Konstruktør der nedarver fra Item-klassen
    // Initialiserer et våben med ID, navn, vægt og skadesværdi
    public Weapon(int id, String name, int weight, int value) {
        // Kalder parent-klassens konstruktør med de givne parametre
        super(id, name, weight, value);
    }

    // Overrider displayItem() metoden fra Item-klassen
    // Viser våbnets detaljer i et formateret output
    public void displayItem() {
        // Tegner en ramme omkring våbnets detaljer
        System.out.println(" _________________");
        // Udskriver våbnets navn
        System.out.println(" | Name  : " + this.name);
        // Udskriver våbnets unikke ID
        System.out.println(" | ItemID: " + this.id);
        // Udskriver våbnets vægt i kilogram
        System.out.println(" | Weight: " + this.weight + " KG");
        // Udskriver våbnets skadesværdi
        System.out.println(" | Damage : " + this.value);
        // Afslutter rammen
        System.out.println(" _________________");
    }
}
