import java.io.*;

public class FileHandler {

    public static void writeFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/Users.txt"))) {
            for (Player user : Lager.users) {
                if (user == null) continue;

                // Skriv brugeroplysninger
                bw.write(user.getUsername());
                bw.newLine();
                bw.write(user.getPassword());
                bw.newLine();

                // Forbered strenge til at gemme item-ID'er og stack-størrelser
                StringBuilder itemDetails = new StringBuilder();

                Item[] userItems = user.getInv().getItems();
                for (Item item : userItems) {
                    if (item != null) {
                        // Tilføj item-ID
                        itemDetails.append(item.getId());

                        // Tilføj stack-størrelse for consumables
                        if (item instanceof Consumable) {
                            Consumable consumable = (Consumable) item;
                            itemDetails.append(":").append(consumable.getStackSize());
                        } else {
                            // Standard stack-størrelse for ikke-consumables
                            itemDetails.append(":1");
                        }
                        itemDetails.append(",");
                    }
                }

                // Fjern sidste komma
                if (itemDetails.length() > 0) {
                    itemDetails.setLength(itemDetails.length() - 1);
                }

                // Skriv item-detaljer
                bw.write(itemDetails.toString());
                bw.newLine();
                bw.write("#");
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fejl ved skrivning til fil: " + e.getMessage());
        }
    }

    public static void getDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Users.txt"))) {
            String line;
            Player tempPlayer = null;
            int lineCounter = 0;

            while ((line = reader.readLine()) != null) {
                if (line.equals("#")) {
                    tempPlayer = null;
                    lineCounter = 0;
                    continue;
                }

                lineCounter++;

                switch (lineCounter) {
                    case 1:
                        tempPlayer = new Player(line, "");
                        break;
                    case 2:
                        if (tempPlayer != null) {
                            tempPlayer.setPassword(line);
                        }
                        break;
                    case 3:
                        if (tempPlayer != null) {
                            Inv inv = new Inv();

                            // Split item-detaljer
                            String[] itemDetails = line.split(",");

                            for (String itemDetail : itemDetails) {
                                // Split hver item-detalje i ID og stack-størrelse
                                String[] parts = itemDetail.split(":");

                                int itemId = Integer.parseInt(parts[0]);
                                int stackSize = parts.length > 1 ? Integer.parseInt(parts[1]) : 1;

                                // Hent original item
                                Item originalItem = Lager.itemListe[itemId];

                                if (originalItem instanceof Consumable) {
                                    // Opret consumable med specifik stack-størrelse
                                    Consumable consumable = ((Consumable) originalItem).createWithStackSize(stackSize);

                                    for (int i = 0; i < inv.getItems().length; i++) {
                                        if (inv.getItems()[i] == null) {
                                            inv.getItems()[i] = consumable;
                                            break;
                                        }
                                    }
                                } else {

                                    for (int i = 0; i < inv.getItems().length; i++) {
                                        if (inv.getItems()[i] == null) {
                                            inv.getItems()[i] = originalItem;
                                            break;
                                        }
                                    }
                                }
                            }

                            tempPlayer.setUserInventory(inv);
                            Lager.addPlayerToArray(tempPlayer);
                        }
                        lineCounter = 0;
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Fejl ved læsning fra fil: " + e.getMessage());
        }
    }

    public static void save() {
        if (Lager.loggedInPlayer != null) {
            for (int i = 0; i < Lager.users.length; i++) {
                if (Lager.users[i] != null && Lager.users[i].getUsername().equals(Lager.loggedInPlayer.getUsername())) {
                    Lager.users[i] = Lager.loggedInPlayer;
                    break;
                }
            }
        }
        writeFile();
    }

    public static void load() {
        getDataFromFile();
    }
}