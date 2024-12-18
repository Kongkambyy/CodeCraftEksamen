public class Login {

    // Autentifikationsmetode der verificerer brugerens login-oplysninger
    // Metoden modtager brugernavn og password som parametre
    // Returnerer true hvis login er succesfuldt, false hvis det fejler
    public static boolean auth(String username, String password) {
        // Gennemløb alle brugere i systemets bruger-array
        for (Player account : Lager.users) {
            // Tjek om det nuværende account matcher de indtastede oplysninger
            // Sammenligner både brugernavn og password
            if (account.getUsername().equals(username) && account.getPassword().equals(password)) {
                // Hvis login er succesfuldt, gem den nuværende bruger som den aktive bruger
                Lager.loggedInPlayer = account;
                // Returner true for at indikere vellykket login
                return true;
            }
        }
        // Hvis ingen bruger matcher, returner false
        return false;
    }
}