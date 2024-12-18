public class Armor extends Item {

    // Statiske konstanter definerer standard armor-items i spillet
    // Hver armor har unik ID, navn, vægt og beskyttelsesværdi
    static final Armor SHIELD = new Armor(4, "Shield", 17, 0);   // Armor-only item
    static final Armor HELMET = new Armor(5, "Helmet", 15, 5);
    static final Armor CHEST_PLATE = new Armor(6, "Chest Plate", 24, 25);
    static final Armor PANTS = new Armor(7, "Pants", 17, 10);
    static final Armor BOOTS = new Armor(8, "Boots", 20, 4);

    // Konstruktør der nedarver fra Item-klassen
    // Initialiserer en armor med ID, navn, vægt og beskyttelsesværdi
    public Armor(int id, String name, int weight, int value) {
        // Kalder parent-klassens konstruktør med de givne parametre
        super(id, name, weight, value);
    }

    // Overrider displayItem() metoden fra Item-klassen
    // Viser armor-delens detaljer i et formateret output
    @Override
    public void displayItem() {
        // Tegner en ramme omkring armor-delens detaljer
        System.out.println(" _________________");
        // Udskriver armor-delens navn
        System.out.println(" | Name  : " + this.name);
        // Udskriver armor-delens ID
        System.out.println(" | ItemID: " + this.id);
        // Udskriver armor-delens vægt
        System.out.println(" | Weight: " + this.weight + " KG");
        // Udskriver armor-delens livspoint
        System.out.println(" | Health : " + this.value);
        // Færddigøre rammen
        System.out.println(" _________________");
    }
}

