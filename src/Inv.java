// Inv klassen håndterer spillerens inventory (rygsæk) system
// Her opbevares alle spillerens items, og klassen styrer hvordan items kan tilføjes, fjernes, søges efter og sorteres
public class Inv {
    // Maksimal vægt som inventory kan bære. Dette simulerer hvor meget spilleren kan bære
    double maxWeight = 50;

    // Array der indeholder alle items i inventory. Størrelsen 100 bestemmer hvor mange forskellige items der kan være
    private Item[] items = new Item[100];

    // Bruges til at gemme brugerens valg i nogle operationer, fx ved sortering
    private String valg;

    // Denne metode fjerner et specifikt item fra inventory baseret på dets ID
    // Den leder gennem hele inventory efter et item med det givne ID og fjerner det når det findes
    public void deleteItemFromInventory(int itemid) {
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getId() == itemid) {
                // Speciel håndtering for Consumables
                if (items[i] instanceof Consumable) {
                    Consumable consumable = (Consumable) items[i];

                    // Fjern en fra stack
                    if (consumable.removeFromStack(1)) {
                        System.out.println("1 item fjernet fra stakken");

                        // Hvis stakken er tom, fjern hele items[i]
                        if (consumable.getStackSize() == 0) {
                            items[i] = null;
                            System.out.println("Consumable stakken er tom og fjernet");
                        }
                    }
                } else {
                    // For ikke-Consumables, fjern hele items[i]
                    items[i] = null;
                    System.out.println("Item Blev Fjernet");
                }
                break;
            }
        }
    }

    // Returnerer hele items arrayet så andre dele af programmet kan se inventorys indhold
    public Item[] getItems() {
        return items;
    }

    // Viser alle items der er i inventory lige nu
    // Hvis inventory er tomt gives der besked om dette
    // Ellers vises hvert item med deres detaljer
    public void showInventory() {
        // Vi tjekker kun første plads - hvis den er tom, antager vi inventory er tomt
        if (items[0] == null) {
            System.out.println("Du har ingen items i dit inventory.");
        }else {
            // Gennemgår hele inventory og viser hvert item der ikke er null
            for (int i = 0; i < items.length; i++) {
                if (items[i] != null) {
                    items[i].displayItem();
                }
            }
        }
    }

    // Tilføjer et nyt item til inventory hvis der er plads og vægten tillader det
    // Metoden sikrer at den totale vægt ikke overstiger maxWeight
    // Og at der er en ledig plads i inventory arrayet
    public void addItem(Item item) {
        // Holder styr på den samlede vægt
        double totalVægt = 0;

        // Beregner den nuværende totalvægt ved at lægge alle items sammen
        for (Item i : items) {
            if (i != null) {
                totalVægt += i.getWeight();
            }
        }

        // Tjekker om det nye item vil gøre inventory for tungt
        if (totalVægt + item.getWeight() > maxWeight) {
            System.out.println("Item kan ikke tilføjes. Den samlede vægt overskrider grænsen");
        }else {
            // Leder efter første ledige plads i inventory
            for (int i = 0; i < items.length; i++) {
                if (items[i] == null) {
                    items[i] = item;
                    System.out.println("Item blev tilføjet til dit inventory.");
                    break;
                }
            }
        }
    }

    // Søgefunktion der kan finde items baseret på navn eller ID
    // Søgningen er ikke case-sensitive, så "Sword" og "sword" vil give samme resultat
    // Viser alle items der matcher søgningen
    public void searchItem(String searchTerm) {
        boolean found = false;

        // Gennemgår alle items i inventory og leder efter matches
        for (Item item : items) {
            if (item != null && (item.getName().toLowerCase().contains(searchTerm.toLowerCase())
                    || String.valueOf(item.getId()).equals(searchTerm))) {
                System.out.println("Fundet genstand: ");
                item.displayItem();
                found = true;
            }
        }

        // Giver besked hvis ingen items blev fundet
        if (!found) {
            System.out.println("Der er ingen genstande som matcher søgningen: " + searchTerm);
        }
    }

    // Hovedsorteringsfunktion der styrer hvilken type sortering der skal bruges
    // Parameter 'ii' bestemmer sorteringstypen:
    // 1 = Sorter efter ID
    // 2 = Sorter efter navn
    // 3 = Sorter efter vægt
    public void sort(int ii) {
        switch (ii){
            case 1:
                sortItemsById();
                showInventory();
                break;
            case 2:
                sortItemsByName();
                showInventory();
                break;
            case 3:
                sortItemsByWeight();
                showInventory();
                break;
            default:
                System.out.println("Det indtastede er ikke en mulighed");
        }
    }

    // Sorterer items alfabetisk efter deres navne ved hjælp af Selection Sort algoritmen
    // Finder det "mindste" navn (alfabetisk først) og bytter det med det første item
    // Gentager processen for resten af listen
    public void sortItemsByName() {
        int n = items.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (items[j] != null && items[minIndex] != null &&
                        items[j].getName().compareToIgnoreCase(items[minIndex].getName()) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i && items[minIndex] != null && items[i] != null) {
                Item temp = items[minIndex];
                items[minIndex] = items[i];
                items[i] = temp;
            }
        }
    }

    // Sorterer items efter deres vægt, fra lettest til tungest
    // Bruger samme sorteringsalgoritme som ved navnesortering
    public void sortItemsByWeight() {
        int n = items.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            // Springer over null værdier
            while (minIndex < n && items[minIndex] == null) {
                minIndex++;
            }
            if (minIndex >= n) break;

            for (int j = i + 1; j < n; j++) {
                if (items[j] != null && items[minIndex] != null &&
                        items[j].getWeight() < items[minIndex].getWeight()) {
                    minIndex = j;
                }
            }

            if (minIndex != i && items[minIndex] != null && items[i] != null) {
                Item temp = items[minIndex];
                items[minIndex] = items[i];
                items[i] = temp;
            }
        }
    }

    // Sorterer items efter deres ID-nummer, fra lavest til højest
    // Bruger samme sorteringsalgoritme som de andre sorteringsfunktioner
    public void sortItemsById() {
        int n = items.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            while (minIndex < n && items[minIndex] == null) {
                minIndex++;
            }
            if (minIndex >= n) break;

            for (int j = i + 1; j < n; j++) {
                if (items[j] != null && items[minIndex] != null &&
                        items[j].getId() < items[minIndex].getId()) {
                    minIndex = j;
                }
            }

            if (minIndex != i && items[minIndex] != null && items[i] != null) {
                Item temp = items[minIndex];
                items[minIndex] = items[i];
                items[i] = temp;
            }
        }
    }
}