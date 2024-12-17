// Denne abstrakte klasse danner grundlag for alle items i spillet
// Fra denne klasse nedarver vi til Weapon, Armor og Consumable
abstract class Item {
    // Basis variabler som alle items skal bruge
    int id;        // Unikt ID til at identificere hvert item
    String name;   // Itemets navn som vises til spilleren
    double weight;    // Itemets vægt som påvirker hvor meget man kan bære
    int value;     // Itemets værdi (bruges forskelligt af de forskellige items)


    // Hoved-konstruktør der opretter et nyt item med alle nødvendige værdier
    public Item(int id, String name, double weight, int value) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.value = value;
    }

    // Getter metoder til at hente item information
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public int getValue() {
        return value;
    }

    // Setter metoder til at ændre item information hvis nødvendigt
    public void setID(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // ToString metode der bruges hvis vi vil have item info som en tekststreng
    @Override
    public String toString() {
        return "Items [id=" + id + ", name=" + name + ", weight=" + weight + ", value=" + value + "]";
    }


    // Standard metode til at vise item information i konsollen
    // Denne metode kan overskrives af de forskellige item typer
    // for at vise deres specifikke egenskaber
    public void displayItem() {
        System.out.println(" _________________");
        System.out.println(" | Name  : " + this.name);
        System.out.println(" | ItemID: " + this.id);
        System.out.println(" | Weight: " + this.weight + " KG");
        System.out.println(" | Value : " + this.value);
        System.out.println(" _________________");
    }
}