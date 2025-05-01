package ShoppingListApp;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListManager {
    private final List<ShoppingItem> items = new ArrayList<>();

    public List<ShoppingItem> getItems() {
        return new ArrayList<>(items); // return a copy for safety
    }

    public void addItem(String name) {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Item name must be valid.");
		}
        if (items.stream().anyMatch(i -> i.getName().equalsIgnoreCase(name))) {
            throw new IllegalArgumentException("Item already exists.");
        }
        items.add(new ShoppingItem(name));
    }

    public void removeItem(String name) {
        ShoppingItem item = findItem(name);
        if (item == null) {
            throw new IllegalArgumentException("Item not found.");
        }
        items.remove(item);
    }

    public void updateQuantity(String name, int quantity) {
        ShoppingItem item = findItem(name);
        if (item == null) {
            throw new IllegalArgumentException("Item not found.");
        }
        item.setQuantity(quantity);
    }

    private ShoppingItem findItem(String name) {
        return items.stream()
            .filter(i -> i.getName().equalsIgnoreCase(name))
            .findFirst()
            .orElse(null);
    }
}
