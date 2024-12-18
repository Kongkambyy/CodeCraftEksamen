import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHandler {

    // Metode til at gemme brugerdata til en fil
    public static void writeFile() {
        // Opret en BufferedWriter til at skrive til filen
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
            // Udskriv fejlmeddelelse hvis der opstår en IO-fejl
            System.out.println("Fejl ved skrivning til fil: " + e.getMessage());
        }
    }

    // Metode til at nedbryde en streng af item-ID'er til et inventory
    public static Inv stringNedBryder(String s) {
        // Opret et nyt inventory
        Inv inv = new Inv();

        // Gennemløb hver karakter i strengen
        for (int i = 0; i < s.length(); i++) {
            // Konverter char til et numerisk ID
            int ii = s.charAt(i) - '0';

            // Tilføj item fra den centrale itemliste til inventoryet
            inv.addItem(Lager.itemListe[ii]);
        }

        // Returner det udfyldte inventory
        return inv;
    }

    // Metode til at indlæse brugerdata fra fil
    public static void getDataFromFile() {
        // Opret et midlertidigt array til at gemme brugere
        Player [] pa = new Player[200];

        // Brug try-with-resources til sikker fil-læsning
        try (BufferedReader reader = new BufferedReader(new FileReader("src/Users.txt"))) {
            String line;
            // Opret en midlertidig bruger
            Player tempPlayer = new Player("", "");
            // Tæller til at holde styr på linjer
            int lineCounter = 0;

            // Læs filen linje for linje
            while ((line = reader.readLine()) != null) {
                // Hvis linjen er "#", reset til næste bruger
                if (line.equals("#")) {
                    tempPlayer = new Player("", "");
                    lineCounter = 0;
                    continue;
                }

                // Øg linjetælleren
                lineCounter++;

                // Første linje er brugernavn
                if (lineCounter == 1) {
                    tempPlayer.setUsername(line);
                }
                // Anden linje er password
                else if (lineCounter == 2) {
                    tempPlayer.setPassword(line);
                }
                // Tredje linje er item-ID'er
                else if (lineCounter == 3) {
                    // Konverter item-ID'er til et inventory
                    tempPlayer.setUserInventory(stringNedBryder(line));
                    // Tilføj spilleren til systemet
                    Lager.addPlayerToArray(tempPlayer);
                }
            }
        } catch (IOException e) {
            // Udskriv fejlmeddelelse ved indlæsningsfejl
            System.err.println("Fejl ved læsning af fil: " + e.getMessage());
        }
    }

    // Metode til at gemme den nuværende brugers data
    public static void save() {
        // Tjek om der er en logget ind bruger
        if (Lager.loggedInPlayer != null) {
            // Gennemløb alle brugere
            for (int i = 0; i < Lager.users.length; i++) {
                // Find den eksisterende bruger og opdater
                if (Lager.users[i] != null && Lager.users[i].getUsername().equals(Lager.loggedInPlayer.getUsername())) {
                    Lager.users[i] = Lager.loggedInPlayer;
                    break;
                }
            }
        }
        // Gem data til fil
        writeFile();
    }

    // Metode til at indlæse data fra fil
    public static void load() {
        // Kald metoden til at hente data fra filen
        getDataFromFile();
    }
}