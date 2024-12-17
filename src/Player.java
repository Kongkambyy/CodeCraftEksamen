public class Player {

    private String username;
    private String password;
    private Inv userInventory = new Inv();
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public void convertInventoryToString() {

    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public Inv getUserInventory() {
        return userInventory;
    }
    public void setUserInventory(Inv userInventory) {
        this.userInventory = userInventory;
    }
    public Inv getInv() {
        return userInventory;
    }

}
