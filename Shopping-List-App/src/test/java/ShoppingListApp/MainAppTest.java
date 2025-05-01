package ShoppingListApp;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class MainAppTest {

    @Start
    public void start(Stage stage) {
        MainApp mainApp = new MainApp();
        mainApp.start(stage);
    }

    @Test
    public void testAddItem(FxRobot robot) {
        robot.clickOn("#nameField").write("Apple");
        robot.clickOn("#quantityField").write("5");
        robot.clickOn("#addButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(robot.lookup("#listView").queryListView().getItems().contains("Apple (x5)"));
        assertEquals("Item added.", robot.lookup("#messageLabel").queryLabeled().getText());
    }

    @Test
    public void testRemoveItem(FxRobot robot) {
        robot.clickOn("#nameField").write("Banana");
        robot.clickOn("#quantityField").write("2");
        robot.clickOn("#addButton");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("Banana (x2)");
        robot.clickOn("#removeButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(robot.lookup("#listView").queryListView().getItems().contains("Banana (x2)"));
        assertEquals("Item removed.", robot.lookup("#messageLabel").queryLabeled().getText());
    }

    @Test
    public void testUpdateQuantity(FxRobot robot) {
        robot.clickOn("#nameField").write("Orange");
        robot.clickOn("#quantityField").write("3");
        robot.clickOn("#addButton");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("Orange (x3)");
        robot.clickOn("#quantityField").eraseText(10).write("7");
        robot.clickOn("#updateButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(robot.lookup("#listView").queryListView().getItems().contains("Orange (x7)"));
        assertEquals("Quantity updated.", robot.lookup("#messageLabel").queryLabeled().getText());
    }

    @Test
    public void testInvalidQuantity(FxRobot robot) {
        robot.clickOn("#nameField").write("Grape");
        robot.clickOn("#quantityField").write("2");
        robot.clickOn("#addButton");
        WaitForAsyncUtils.waitForFxEvents();

        robot.clickOn("Grape (x2)");
        robot.clickOn("#quantityField").eraseText(10).write("invalid");
        robot.clickOn("#updateButton");
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("Quantity must be a number.", robot.lookup("#messageLabel").queryLabeled().getText());
    }
}
