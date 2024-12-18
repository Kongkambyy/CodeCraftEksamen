public class Weapon extends Item{

    static final Weapon Sword = new Weapon(0, "Sword", 17,25);
    static final Weapon AXE = new Weapon(1, "Axe", 21, 12);
    static final Weapon SPEAR = new Weapon(2, "Spear", 15, 10);
    static final Weapon DAGGER = new Weapon(3, "Dagger", 10, 10);

    public Weapon(int id, String name, int weight, int value) {
        super(id, name, weight, value);
    }

    public void displayItem() {
        System.out.println(" _________________");
        System.out.println(" | Name  : " + this.name);
        System.out.println(" | ItemID: " + this.id);
        System.out.println(" | Weight: " + this.weight + " KG");
        System.out.println(" | Damage : " + this.value);
        System.out.println(" _________________");
    }
}
