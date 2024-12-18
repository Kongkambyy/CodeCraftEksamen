public class Consumable extends Item {
    // Statiske konstanter der definerer standard consumable items
    // Disse kan bruges direkte i hele programmet uden at skulle oprette nye objekter
    static final Consumable HEALING_POTION = new Consumable(9, "Healing Potion", 1.0, 20);
    static final Consumable DAMAGE_POTION = new Consumable(10, "Damage Potion", 1.0, 10);

    // Private variabler til at styre stack-funktionalitet
    private int stackSize;        // Nuværende antal items i stakken
    private final int maxStackSize; // Maksimalt antal items tilladt i stakken

    // Konstruktør der initialiserer et consumable item
    // Arver grundlæggende item-egenskaber fra parent-klassen
    public Consumable(int id, String name, double weight, int maxStackSize) {
        // Kalder parent-konstruktør med id, navn, vægt
        // Sætter value til 0 da consumables har special funktionalitet
        super(id, name, weight, 0);

        // Sæt standard stack-størrelse til 1
        this.stackSize = 1;

        // Gem den maksimale stack-størrelse
        this.maxStackSize = maxStackSize;
    }

    // Getter metode til at hente nuværende stack-størrelse
    public int getStackSize() {
        return stackSize;
    }

    // Getter metode til at hente maksimal stack-størrelse
    public int getMaxStackSize() {
        return maxStackSize;
    }

    // Metode til at tilføje flere items til stakken
    public boolean addToStack(int amount) {
        // Tjek om tilføjelse overskrider maks stack-størrelse
        if (stackSize + amount <= maxStackSize) {
            stackSize += amount;
            return true;
        }
        return false;
    }

    // Metode til at fjerne items fra stakken
    public boolean removeFromStack(int amount) {
        // Tjek om fjernelse vil give negativ stack-størrelse
        if (stackSize - amount >= 0) {
            stackSize -= amount;
            return true;
        }
        return false;
    }

    // Tjek om stakken er fuld
    public boolean isFull() {
        return stackSize >= maxStackSize;
    }

    // Overskriver vægt-metode så den tager stack-størrelse i betragtning
    @Override
    public double getWeight() {
        return weight * stackSize;
    }

    // Metode til at vise item-detaljer
    @Override
    public void displayItem() {
        System.out.println(" _________________");
        System.out.println(" | Name  : " + this.name);
        System.out.println(" | ItemID: " + this.id);
        System.out.println(" | Weight: " + this.weight + " KG");
        System.out.println(" | Antal : " + this.stackSize + "/" + this.maxStackSize);
        System.out.println(" _________________");
    }

    // Metode til at oprette en ny consumable med specifik stack-størrelse
    public Consumable createWithStackSize(int newStackSize) {
        // Validér stack-størrelse
        if (newStackSize <= 0 || newStackSize > maxStackSize) {
            throw new IllegalArgumentException("Ugyldig stack-størrelse");
        }

        // Opret nyt consumable item med samme egenskaber
        Consumable newStack = new Consumable(this.id, this.name, this.weight, this.maxStackSize);

        // Sæt den nye stack-størrelse
        newStack.stackSize = newStackSize;

        return newStack;
    }
}