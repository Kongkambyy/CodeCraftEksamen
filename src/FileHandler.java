import java.io.*;

public class FileHandler {

    // Metode til at skrive brugerdata og inventory til en tekstfil
    // Gemmer alle brugeroplysninger, herunder brugernavn, password og items
    public static void writeFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/Users.txt"))) {
            // Gennemløb alle brugere i systemet
            for (Player user : Lager.users) {
                // Spring tomme brugerpladser over
                if (user == null) continue;

                // Skriv brugerens brugernavn
                bw.write(user.getUsername());
                bw.newLine();
                // Skriv brugerens password
                bw.write(user.getPassword());
                bw.newLine();

                // Opret en StringBuilder til at samle item-detaljer
                StringBuilder itemDetails = new StringBuilder();

                // Hent brugerens inventory items
                Item[] userItems = user.getInv().getItems();
                for (Item item : userItems) {
                    if (item != null) {
                        // Tilføj item-ID til detaljerne
                        itemDetails.append(item.getId());

                        // Håndter stack-størrelse specielt for Consumable items
                        if (item instanceof Consumable) {
                            Consumable consumable = (Consumable) item;
                            // Tilføj stack-størrelse for consumables
                            itemDetails.append(":").append(consumable.getStackSize());
                        } else {
                            // Standard stack-størrelse for ikke-consumable items
                            itemDetails.append(":1");
                        }
                        itemDetails.append(",");
                    }
                }

                // Fjern det sidste komma fra strengen
                if (itemDetails.length() > 0) {
                    itemDetails.setLength(itemDetails.length() - 1);
                }

                // Skriv item-detaljer til filen
                bw.write(itemDetails.toString());
                bw.newLine();
                // Tilføj separator mellem brugere
                bw.write("#");
                bw.newLine();
            }
        } catch (IOException e) {
            // Håndter eventuelle fejl ved filskrivning
            System.out.println("Fejl ved skrivning til fil: " + e.getMessage());
        }
    }

    // Metode til at indlæse brugerdata fra en tekstfil
    // Genopbygger brugere, passwords og inventories fra gemt fil
    public static void getDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Users.txt"))) {
            String line;
            // Midlertidig variabel til at opbygge bruger
            Player tempPlayer = null;
            // Tæller til at holde styr på linjer for hver bruger
            int lineCounter = 0;

            // Læs filen linje for linje
            while ((line = reader.readLine()) != null) {
                // Separator mellem brugere
                if (line.equals("#")) {
                    tempPlayer = null;
                    lineCounter = 0;
                    continue;
                }

                lineCounter++;

                switch (lineCounter) {
                    case 1:
                        // Første linje er brugernavn
                        tempPlayer = new Player(line, "");
                        break;
                    case 2:
                        // Anden linje er password
                        if (tempPlayer != null) {
                            tempPlayer.setPassword(line);
                        }
                        break;
                    case 3:
                        // Tredje linje er inventory-items
                        if (tempPlayer != null) {
                            // Opret nyt inventory for brugeren
                            Inv inv = new Inv();

                            // Tjek om linjen ikke er tom
                            if (!line.trim().isEmpty()) {
                                // Del item-detaljer op
                                String[] itemDetails = line.split(",");

                                for (String itemDetail : itemDetails) {
                                    // Opdel hver item-detalje i ID og stack-størrelse
                                    String[] parts = itemDetail.split(":");

                                    // Konverter item-ID
                                    int itemId = Integer.parseInt(parts[0]);
                                    // Hent stack-størrelse (standard 1 hvis ikke angivet)
                                    int stackSize = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;

                                    // Hent original item fra systemets item-liste
                                    Item originalItem = Lager.itemListe[itemId];

                                    // Speciel håndtering for Consumable items
                                    if (originalItem instanceof Consumable) {
                                        // Opret consumable med specifik stack-størrelse
                                        Consumable consumable = ((Consumable) originalItem).createWithStackSize(stackSize);
                                        inv.addItem(consumable);
                                    } else {
                                        // Tilføj standard items
                                        inv.addItem(originalItem);
                                    }
                                }
                            }

                            // Tildel inventory til brugeren
                            tempPlayer.setUserInventory(inv);
                            // Tilføj brugeren til systemet
                            Lager.addPlayerToArray(tempPlayer);
                        }
                        // Nulstil linjetæller
                        lineCounter = 0;
                        break;
                }
            }
        } catch (IOException e) {
            // Håndter eventuelle fejl ved filindlæsning
            System.out.println("Fejl ved læsning fra fil: " + e.getMessage());
        }
    }

    // Metode til at gemme den nuværende brugers data
    // Opdaterer brugeroplysninger inden filskrivning
    public static void save() {
        // Tjek om der er en logget ind bruger
        if (Lager.loggedInPlayer != null) {
            // Gennemløb alle brugere
            for (int i = 0; i < Lager.users.length; i++) {
                // Find og opdater den eksisterende bruger
                if (Lager.users[i] != null && Lager.users[i].getUsername().equals(Lager.loggedInPlayer.getUsername())) {
                    Lager.users[i] = Lager.loggedInPlayer;
                    break;
                }
            }
        }
        // Gem data til fil
        writeFile();
    }

    // Metode til at indlæse brugerdata ved programstart
    // Kalder metoden til at hente data fra fil
    public static void load() {
        getDataFromFile();
    }
}