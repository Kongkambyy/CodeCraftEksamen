public class Armor extends Item {
    static final Armor SHIELD = new Armor(4, "Shield", 17, 0);   // Armor-only item
    static final Armor HELMET = new Armor(5, "Helmet", 15, 5);
    static final Armor CHEST_PLATE = new Armor(6, "Chest Plate", 24, 25);
    static final Armor PANTS = new Armor(7, "Pants", 17, 10);
    static final Armor BOOTS = new Armor(8, "Boots", 20, 4);

    public Armor(int id, String name, int weight, int value) {
        super(id, name, weight, value);
    }
    @Override
    public void displayItem() {
        System.out.println(" _________________");
        System.out.println(" | Name  : " + this.name);
        System.out.println(" | ItemID: " + this.id);
        System.out.println(" | Weight: " + this.weight + " KG");
        System.out.println(" | Health : " + this.value);
        System.out.println(" _________________");
    }
}

