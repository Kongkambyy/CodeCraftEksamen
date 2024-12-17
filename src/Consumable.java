public class Consumable extends Item {
    static final Consumable HEALING_POTION = new Consumable(9, "Healing Potion", 1.0, 20);
    static final Consumable DAMAGE_POTION = new Consumable(10, "Damage Potion", 1.0, 10);

    private int stackSize;
    private final int maxStackSize;

    public Consumable(int id, String name, double weight, int maxStackSize) {
        super(id, name, weight, 0);
        this.stackSize = 1;
        this.maxStackSize = maxStackSize;
    }

    public int getStackSize() {
        return stackSize;
    }

    public int getMaxStackSize() {
        return maxStackSize;
    }

    public boolean addToStack(int amount) {
        if (stackSize + amount <= maxStackSize) {
            stackSize += amount;
            return true;
        }
        return false;
    }

    public boolean removeFromStack(int amount) {
        if (stackSize - amount >= 0) {
            stackSize -= amount;
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return stackSize >= maxStackSize;
    }

    @Override
    public double getWeight() {
        return weight * stackSize;
    }

    @Override
    public void displayItem() {
        System.out.println(" _________________");
        System.out.println(" | Name  : " + this.name);
        System.out.println(" | ItemID: " + this.id);
        System.out.println(" | Weight: " + this.weight + " KG");
        System.out.println(" | Antal : " + this.stackSize + "/" + this.maxStackSize);
        System.out.println(" _________________");
    }

    public Consumable createWithStackSize(int newStackSize) {
        if (newStackSize <= 0 || newStackSize > maxStackSize) {
            throw new IllegalArgumentException("Ugyldig stack-størrelse");
        }
        Consumable newStack = new Consumable(this.id, this.name, this.weight, this.maxStackSize);
        newStack.stackSize = newStackSize;
        return newStack;
    }

}