package ShoppingListApp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final ShoppingListManager manager = new ShoppingListManager();
    private TextField nameField;
    private TextField quantityField;
    private ListView<String> listView;
    private Label messageLabel;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Shopping List App – Joshua Brandon");
        scene = buildUI(); // Build and store scene
        stage.setScene(scene);
        stage.show();
    }

    private Scene buildUI() {
        nameField = new TextField();
        nameField.setId("nameField");
        quantityField = new TextField();
        quantityField.setId("quantityField");
        listView = new ListView<>();
        listView.setId("listView");
        messageLabel = new Label();
        messageLabel.setId("messageLabel");
        Button addButton = new Button("Add Item");
        addButton.setId("addButton");
        Button removeButton = new Button("Remove Item");
        removeButton.setId("removeButton");
        Button updateButton = new Button("Update Quantity");
        updateButton.setId("updateButton");

        HBox inputBox = new HBox(10, new Label("Item:"), nameField, new Label("Qty:"), quantityField);
        HBox buttonBox = new HBox(10, addButton, removeButton, updateButton);
        VBox root = new VBox(10, inputBox, buttonBox, listView, messageLabel);
        root.setPadding(new javafx.geometry.Insets(10));

        addButton.setOnAction(e -> handleAdd());
        removeButton.setOnAction(e -> handleRemove());
        updateButton.setOnAction(e -> handleUpdate());

        return new Scene(root, 600, 400);
    }

    private void handleAdd() {
        String name = nameField.getText();
        String qtyText = quantityField.getText();
        try {
            manager.addItem(name);
            if (!qtyText.isBlank()) {
                int qty = Integer.parseInt(qtyText);
                manager.updateQuantity(name, qty);
            }
            refreshList();
            messageLabel.setText("Item added.");
        } catch (NumberFormatException e) {
            messageLabel.setText("Quantity must be a number.");
        } catch (IllegalArgumentException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    private void handleRemove() {
        String selected = listView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("An item must be selected.");
            return;
        }
        String name = selected.split(" \\(")[0];
        try {
            manager.removeItem(name);
            refreshList();
            messageLabel.setText("Item removed.");
        } catch (IllegalArgumentException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    private void handleUpdate() {
        String selected = listView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            messageLabel.setText("An item must be selected.");
            return;
        }
        String name = selected.split(" \\(")[0];
        try {
            int qty = Integer.parseInt(quantityField.getText());
            manager.updateQuantity(name, qty);
            refreshList();
            messageLabel.setText("Quantity updated.");
        } catch (NumberFormatException e) {
            messageLabel.setText("Quantity must be a number.");
        } catch (IllegalArgumentException e) {
            messageLabel.setText(e.getMessage());
        }
    }

    private void refreshList() {
        listView.getItems().setAll(
            manager.getItems().stream()
                .map(ShoppingItem::toString)
                .toList()
        );
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Scene getScene() {
        return scene;
    }
}
