public class Lager {

    public static Player [] users = new Player[200];
    public static Inv [] invS = new Inv[64];
    public static Player loggedInPlayer = null;
    public static Item [] itemListe = new Item[11];
    public static void lavItemListen(){
        itemListe[0] = Weapon.Sword;
        itemListe[3] = Weapon.DAGGER;
        itemListe[2] = Weapon.SPEAR;
        itemListe[1] = Weapon.AXE;
        itemListe[9] = Consumable.HEALING_POTION;
        itemListe[10] = Consumable.DAMAGE_POTION;
        itemListe[4] = Armor.SHIELD;
        itemListe[7] = Armor.PANTS;
        itemListe[6] = Armor.CHEST_PLATE;
        itemListe[5] = Armor.HELMET;
        itemListe[8] = Armor.BOOTS;
    }

    // Metoden tilføjer brugere der registrere sig, til arrayet af users.
    // Hvis registrering er successfuld
    public static void addPlayerToArray(Player player) {
        // Tjek om brugernavnet allerede eksisterer
        for (Player eksisterendeBruger : users) {
            if (eksisterendeBruger != null &&
                    eksisterendeBruger.getUsername().equalsIgnoreCase(player.getUsername())) {
                System.out.println("\u001B[31mBrugernavnet er allerede i brug!\u001B[0m");
                return; // Afbryd tilføjelsen
            }
        }

        // Tilføj brugeren hvis brugernavnet er unikt
        for (int i = 0 ; i < users.length ; i++){
            if (users[i] == null) {
                users[i] = player;
                System.out.println("\u001B[32mBruger registreret med succes!\u001B[0m");
                break;
            }
        }
    }



}