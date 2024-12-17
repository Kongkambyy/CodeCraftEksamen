import java.io.*;

public class FileHandler {

    public static void writeFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/Users.txt"))) {
            // Gennemgå alle brugere i systemet
            for (Player user : Lager.users) {
                // Spring tomme brugerpladser over
                if (user == null) continue;

                // Skriv brugerens login-oplysninger
                bw.write(user.getUsername());
                bw.newLine();
                bw.write(user.getPassword());
                bw.newLine();

                // Forbered en streng til at gemme item-ID'er
                String itemIds = "";
                Item[] userItems = user.getInv().getItems();

                // Gennemgå alle items i brugerens inventory
                for (Item item : userItems) {
                    if (item != null) {
                        // Tilføj item-ID til strengen
                        itemIds += item.getId();
                    }
                }

                // Skriv item-ID'er
                bw.write(itemIds);
                bw.newLine();

                // Markør til at adskille brugere
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
                // Check om vi er nået til en bruger-separator
                if (line.equals("#")) {
                    tempPlayer = null;
                    lineCounter = 0;
                    continue;
                }

                lineCounter++;

                // Første linje er brugernavn
                if (lineCounter == 1) {
                    tempPlayer = new Player(line, "");
                }
                // Anden linje er password
                else if (lineCounter == 2) {
                    if (tempPlayer != null) {
                        tempPlayer.setPassword(line);
                    }
                }
                // Tredje linje er item-ID'er
                else if (lineCounter == 3) {
                    if (tempPlayer != null) {
                        Inv inv = new Inv();

                        // Tilføj items baseret på ID'er
                        for (char c : line.toCharArray()) {
                            // Konverter char til et tal
                            int itemId = Character.getNumericValue(c);

                            // Hent item fra den centrale itemliste
                            if (itemId >= 0 && itemId < Lager.itemListe.length) {
                                Item item = Lager.itemListe[itemId];
                                if (item != null) {
                                    inv.addItem(item);
                                }
                            }
                        }

                        // Sæt inventoryet for spilleren
                        tempPlayer.setUserInventory(inv);

                        // Tilføj spilleren til systemet
                        Lager.addPlayerToArray(tempPlayer);
                    }
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