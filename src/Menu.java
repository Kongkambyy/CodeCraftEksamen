import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Menu {
    // Scanner-objektet er vores primære værktøj til at modtage brugerinput
    // Static sikrer, at vi kun opretter ét Scanner-objekt for hele programmet
    private static final Scanner scanner = new Scanner(System.in);

    // Start-metoden er indgangsporten til hele vores inventory management system
    // Den styrer den indledende brugerinteraktion med login, registrering og systemafslutning
    public static void start() {
        // 'authenticated' holder styr på om brugeren er succesfuldt logget ind
        boolean authenticated = false;

        // Denne løkke fortsætter indtil en bruger korrekt autentificeres
        while (!authenticated) {
            try {
                // Udskriv velkomst-header med farvet tekst for at skabe en indbydende brugergrænseflade
                printHeader("\u001B[34m--- Velkommen til CodeCraft Inventory System ---");

                // Vis menuvalg med farvekodet tekst for bedre læsbarhed
                System.out.println("\u001B[33m1. Log ind\u001B[0m");
                System.out.println("\u001B[33m2. Registrer\u001B[0m");
                System.out.println("\u001B[33m3. Afslut\u001B[0m");
                System.out.print("\u001B[36mVælg en af ovenstående muligheder: \u001B[0m");

                // Modtag brugerens valg som et heltal
                int choice = scanner.nextInt();
                scanner.nextLine(); // Rens inputbufferen efter tal-input

                // Switch-statement til at håndtere forskellige brugervalg
                switch (choice) {
                    case 1 -> {
                        // Login-proces
                        try {
                            printProgressBar("");
                            printHeader("Log ind");

                            // Indsaml login-oplysninger
                            System.out.print("Brugernavn: ");
                            String username = scanner.nextLine();
                            System.out.print("Adgangskode: ");
                            String password = scanner.nextLine();

                            // Find bruger i systemet
                            Player loggedInPlayer = null;
                            for (Player user : Lager.users) {
                                if (user != null && user.getUsername().equals(username) && user.getPassword().equals(password)) {
                                    loggedInPlayer = user;
                                    break;
                                }
                            }

                            // Håndtere login-resultat
                            // Tilføjet et klokkeslæt så brugeren kan se tidspunktet han har logget ind på
                            if (loggedInPlayer != null) {
                                Lager.loggedInPlayer = loggedInPlayer;
                                authenticated = true;
                                System.out.println("\u001B[32mLogin succesfuldt! Velkommen, " + loggedInPlayer.getUsername() + ".\u001B[0m");

                                // Her udskriver vi dato og klokkeslæt
                                LocalDateTime now = LocalDateTime.now();
                                DateTimeFormatter datoFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                                System.out.println("\u001B[36mDato og tidspunkt: " + now.format(datoFormat) + "\u001B[0m");

                                // Tilføjer metoden promptEnterKey, så brugeren når at se login har været successfuldt
                                promptEnterKey();
                                printProgressBar("Logger ind");
                                mainMenu(); // Går til hovedmenuen
                            } else {
                                System.out.println("\u001B[31mForkert brugernavn eller adgangskode. Prøv igen.\u001B[0m");
                                promptEnterKey();
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("\u001B[31mUgyldig input for login. Prøv igen.\u001B[0m");
                            promptEnterKey();
                        }
                    }
                    case 2 -> {
                        // Registreringsproces
                        try {
                            printProgressBar("");
                            printHeader("Registrer ny bruger");

                            System.out.print("Nyt brugernavn: ");
                            String newUsername = scanner.nextLine();
                            System.out.print("Ny adgangskode: ");
                            String newPassword = scanner.nextLine();

                            // Validere brugerinputtet
                            // Tjekker også om brugeren har indtastet korrekte informationer
                            if (newUsername.trim().isEmpty() || newPassword.trim().isEmpty()) {
                                throw new IllegalArgumentException("Brugernavn og adgangskode kan ikke være tomme");
                            }

                            // Opretter en ny bruger til systemet
                            // Exception handling fanger hvis der er ugyldige inputs fra brugeren
                            Player thisNewPLayer = new Player(newUsername, newPassword);
                            Lager.addPlayerToArray(thisNewPLayer);
                            FileHandler.save();
                            promptEnterKey();
                        } catch (IllegalArgumentException e) {
                            System.out.println("\u001B[31mUgyldig input for registrering: " + e.getMessage() + "\u001B[0m");
                            promptEnterKey();
                        }
                    }
                    case 3 -> {
                        // Afslutter programmet
                        try {
                            FileHandler.save();
                            printProgressBar("Afslutter");
                            System.exit(0);
                        } catch (Exception e) {
                            System.out.println("\u001B[31mFejl ved gemning af data. Afslutter alligevel.\u001B[0m");
                            System.exit(1);
                        }
                    }
                    default -> System.out.println("\u001B[31mUgyldigt valg. Prøv igen.\u001B[0m");
                }
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31mUgyldigt input. Indtast venligst et tal.\u001B[0m");
                scanner.nextLine(); // Rens inputbufferen
                promptEnterKey();
            }
        }
    }

    // Hovedmenu efter succesfuldt login
    public static void mainMenu() {
        // Styrer om brugeren fortsat er i hovedmenuen
        boolean continueRunning = true;

        // Løkke der holder menuen åben indtil brugeren vælger at logge ud
        while (continueRunning) {
            try {
                // Udskriv menuoverskrift
                printHeader("\u001B[34m--- Main Menu ---");

                // Vis de mulige handlinger brugeren har, efter login er fuldført
                System.out.println("\u001B[33m1. Se dit nuværende inventory\u001B[0m");
                System.out.println("\u001B[33m2. Tilføj item til dit inventory\u001B[0m");
                System.out.println("\u001B[33m3. Fjern item fra dit inventory\u001B[0m");
                System.out.println("\u001B[33m4. Søg efter et item\u001B[0m");
                System.out.println("\u001B[33m5. Sorter inventory\u001B[0m");
                System.out.println("\u001B[33m6. Log ud\u001B[0m");
                System.out.print("\u001B[36mIndtast dit valg: \u001B[0m");

                // Modtag brugerens valg
                int choice = scanner.nextInt();
                scanner.nextLine(); // Rens inputbufferen

                // Switch-statement til at håndtere de forskellige valg i hovedmenuen
                switch (choice) {
                    case 1 -> {
                        // Viser brugerens nuværende inventory
                        try {
                            printProgressBar("Henter inventory");
                            Lager.loggedInPlayer.getInv().showInventory();
                            System.out.println("Inventory er hentet!");
                            promptEnterKey();
                        } catch (NullPointerException e) {
                            System.out.println("\u001B[31mFejl ved visning af inventory.\u001B[0m");
                        }
                    }
                    case 2 -> {
                        // Tilføjer et item til inventory
                        // Udskriver først alle items oprettet i systemet, så brugeren kan se hvad han vil have
                        try {
                            printProgressBar("Åbner tilføjelsesmenu");
                            System.out.println("Tilføjelsesmenu er klar.");
                            udskrivAlleItems();
                            System.out.println("Indtast ID på item: ");
                            int id = scanner.nextInt();

                            // Validér det valgte item-ID
                            if (id < 0 || id >= Lager.itemListe.length || Lager.itemListe[id] == null) {
                                throw new IllegalArgumentException("Ugyldigt item ID");
                            }

                            Item selectedItem = Lager.itemListe[id];

                            // Special håndtering for consumable items
                            // Eftersom consumables er specielle, da de kan stackes
                            if (selectedItem instanceof Consumable) {
                                Consumable consumable = (Consumable) selectedItem;
                                System.out.println("Dette er et consumable item.");
                                System.out.printf("Maks stack-størrelse er %d%n", consumable.getMaxStackSize());
                                System.out.println("Hvor mange vil du tilføje?");

                                int stackSize = scanner.nextInt();

                                // Validere stack-størrelsen
                                if (stackSize <= 0 || stackSize > consumable.getMaxStackSize()) {
                                    throw new IllegalArgumentException("Ugyldig stack-størrelse");
                                }

                                // Opret en ny consumable med specifik stack-størrelse
                                Consumable newConsumable = consumable.createWithStackSize(stackSize);
                                Lager.loggedInPlayer.getInv().addItem(newConsumable);
                            } else {
                                // Tilføj ikke-consumable items
                                Lager.loggedInPlayer.getInv().addItem(selectedItem);
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("\u001B[31mUgyldigt ID format. Indtast venligst et tal.\u001B[0m");
                            scanner.nextLine(); // Rens inputbufferen
                        } catch (IllegalArgumentException e) {
                            System.out.println("\u001B[31m" + e.getMessage() + "\u001B[0m");
                        }
                    }
                    case 3 -> {
                        // Fjerner et item fra inventory
                        try {
                            printProgressBar("Åbner fjernelsesmenu");
                            System.out.println("Fjernelsesmenu er klar.");
                            Lager.loggedInPlayer.getInv().showInventory();
                            System.out.println("Indtast ID på item du vil fjerne: ");
                            int thisUserInputId = scanner.nextInt();
                            Lager.loggedInPlayer.getInv().deleteItemFromInventory(thisUserInputId);
                            promptEnterKey();
                        } catch (InputMismatchException e) {
                            System.out.println("\u001B[31mUgyldigt ID format. Indtast venligst et tal.\u001B[0m");
                            scanner.nextLine();
                        }
                    }
                    case 4 -> {
                        // Søger efter et item
                        try {
                            printProgressBar("Åbner søgemenu");
                            System.out.println("Søgemenu er klar.");
                            Lager.loggedInPlayer.getInv().showInventory();
                            System.out.println("Indtast navn eller ID på den item, du vil søge efter.");
                            String search = scanner.nextLine();
                            Lager.loggedInPlayer.getInv().searchItem(search);
                            promptEnterKey();
                        } catch (Exception e) {
                            System.out.println("\u001B[31mFejl under søgning.\u001B[0m");
                            promptEnterKey();
                        }
                    }
                    case 5 -> {
                        // Sorter inventory
                        try {
                            printProgressBar("Åbner sorteringsmenu");
                            System.out.println("Sorteringsmenu er klar.");
                            promptEnterKey();
                            System.out.println("Hvordan vil du sortere dit inventory.");
                            System.out.println("1. ID");
                            System.out.println("2. Navn");
                            System.out.println("3. Antal");
                            System.out.println();

                            int sorteringsValgMenu = scanner.nextInt();
                            Lager.loggedInPlayer.getInv().sort(sorteringsValgMenu);
                        } catch (InputMismatchException e) {
                            System.out.println("\u001B[31mUgyldigt valg for sortering. Indtast venligst et tal.\u001B[0m");
                            scanner.nextLine();
                        }
                    }
                    case 6 -> {
                        // Logger ud af systemet, og gemmer data til FileHandler
                        try {
                            FileHandler.save();
                            printProgressBar("Logger ud");
                            start();
                        } catch (Exception e) {
                            System.out.println("\u001B[31mFejl ved logout, men logger ud alligevel.\u001B[0m");
                            start();
                        }
                    }
                    default -> System.out.println("\u001B[31mUgyldigt valg. Prøv igen.\u001B[0m");
                }
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31mUgyldigt input. Indtast venligst et tal.\u001B[0m");
                scanner.nextLine(); // Rens inputbufferen
                promptEnterKey();
            } catch (Exception e) {
                System.out.println("\u001B[31mDer opstod en uventet fejl. Prøv igen.\u001B[0m");
                promptEnterKey();
            }
        }
    }

    // Udskriver en formateret header
    // Frem for at lave et pænt UI design samtlige steder, har vi oprettet
    // en metoder som er nem at kalde når den er nødvendig
    private static void printHeader(String header) {
        System.out.println();
        System.out.println("\u001B[34m=== " + header + " ===\u001B[0m\n");
    }

    // Vent på brugerens Enter-tast
    public static void promptEnterKey() {
        System.out.println("\nTryk Enter for at fortsætte...");
        scanner.nextLine();
    }

    // Vis en progressbar med animation
    private static void printProgressBar(String message) {
        System.out.print(message + " [");
        // Her printer den "=", 8 gange for at simulere en progressbar
        for (int i = 0; i < 8; i++) {
            System.out.print("=");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println("] Fuldført!");
        System.out.println("");
        rensTerminal();
    }

    // Rens konsollen ved at udskrive 100 tomme linjer
    // Implementeret for at fjerne meget af det fyld der kan komme
    private static void rensTerminal() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
    }

    // Udskriver alle tilgængelige items der er oprettet i spillet
    private static void udskrivAlleItems() {
        try {
            for (int i = 0; i < Lager.itemListe.length; i++) {
                if (Lager.itemListe[i] != null) {
                    Lager.itemListe[i].displayItem();
                }
            }
        } catch (Exception e) {
            System.out.println("\u001B[31mFejl ved visning af items.\u001B[0m");
        }
    }
}