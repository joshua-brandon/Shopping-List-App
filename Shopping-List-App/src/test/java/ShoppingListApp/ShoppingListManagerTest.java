package ShoppingListApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingListManagerTest {

    private ShoppingListManager manager;

    @BeforeEach
    void setUp() {
        manager = new ShoppingListManager();  // Create a fresh instance before each test
    }

    @Test
    void addItem() {
        manager.addItem("Apple");
        assertTrue(manager.getItems().stream().anyMatch(item -> item.getName().equals("Apple")));
    }

    @Test
    void removeItem() {
        manager.addItem("Apple");
        manager.removeItem("Apple");
        assertFalse(manager.getItems().stream().anyMatch(item -> item.getName().equals("Apple")));
    }

    @Test
    void updateQuantity() {
        manager.addItem("Apple");
        manager.updateQuantity("Apple", 5);
        assertEquals(5, manager.getItems().stream()
            .filter(item -> item.getName().equals("Apple"))
            .findFirst()
            .get()
            .getQuantity());
    }

    @Test
    void invalidItem() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> manager.addItem(""));
        assertEquals("Item name must be valid.", exception.getMessage());
    }
}
