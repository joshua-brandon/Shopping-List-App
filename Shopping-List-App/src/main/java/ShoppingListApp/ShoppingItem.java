package ShoppingListApp;

import java.util.Objects;

public class ShoppingItem {
    private final String name;
    private int quantity;

    public ShoppingItem(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty.");
        }
        this.name = name.trim();
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero.");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " (x" + quantity + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingItem)) return false;
        ShoppingItem that = (ShoppingItem) o;
        return name.equalsIgnoreCase(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name.toLowerCase());
    }
}
