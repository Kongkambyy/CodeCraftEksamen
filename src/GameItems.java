// Denne klasse har ansvar for at holde styr på og initialisere alle items i spillet
public class GameItems {
    // Alle items i spillet
    static final Weapon Sword = new Weapon(0, "Sword", 30,25);
    static final Weapon AXE = new Weapon(1, "Axe", 25, 12);
    static final Weapon SPEAR = new Weapon(2, "Spear", 15, 10);
    static final Weapon DAGGER = new Weapon(3, "Dagger", 10, 10);

    static final Armor SHIELD = new Armor(4, "Shield", 23, 0);
    static final Armor HELMET = new Armor(5, "Helmet", 16, 5);
    static final Armor CHEST_PLATE = new Armor(6, "Chest Plate", 27, 25);
    static final Armor PANTS = new Armor(7, "Pants", 17, 10);
    static final Armor BOOTS = new Armor(8, "Boots", 20, 4);

    static final Consumable HEALING_POTION = new Consumable(9, "Healing Potion", 5, 20);
    static final Consumable DAMAGE_POTION = new Consumable(10, "Damage Potion", 5, 10);

    // Metode der tilføjer test-items til en spiller
    public static void addTestItems(Player player) {
        Inv inv = new Inv();
        inv.addItem(Sword);
        inv.addItem(AXE);
        inv.addItem(SPEAR);
        inv.addItem(DAGGER);
        inv.addItem(BOOTS);
        inv.addItem(CHEST_PLATE);
        inv.addItem(HELMET);
        inv.addItem(PANTS);
        inv.addItem(HEALING_POTION);
        inv.addItem(DAMAGE_POTION);
        player.setUserInventory(inv);
    }
}