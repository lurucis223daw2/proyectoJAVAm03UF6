package edu.fje.provajavafx;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class MenuActions3 {

    public static void addPostreAction() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Añadir postre");

        Label nameLabel = new Label("Nombre del postre:");
        TextField nameField = new TextField();

        Label priceLabel = new Label("Precio del postre:");
        TextField priceField = new TextField();

        Label ingredientsLabel = new Label("Ingredientes:");
        TextField ingredientsField = new TextField();

        Label descriptionLabel = new Label("Descripción:");
        TextField descriptionField = new TextField();

        Label typeLabel = new Label("Tipo de postre:");
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Postre Normal", "Postre Congelado");

        Button addButton = new Button("Añadir");
        addButton.setOnAction(event -> {
            String postreName = nameField.getText();
            String postrePrice = priceField.getText();
            String postreIngredients = ingredientsField.getText();
            String postreDescription = descriptionField.getText();
            String postreType = typeComboBox.getValue();

            // Lógica para guardar los datos en la base de datos
            savePostre(postreName, postrePrice, postreIngredients, postreDescription, postreType);

            dialog.close();
        });

        vbox.getChildren().addAll(
                nameLabel, nameField, priceLabel, priceField, ingredientsLabel, ingredientsField,
                descriptionLabel, descriptionField, typeLabel, typeComboBox, addButton
        );

        Scene scene = new Scene(vbox, 400, 350);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private static void savePostre(String name, String price, String ingredients, String description, String type) {
        // Cargar la configuración de la base de datos desde el archivo properties
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/database.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de configuración: " + e.getMessage());
            return;
        }

        // Obtener los valores de configuración de la base de datos
        String driver = properties.getProperty("jdbc.drivers");
        String url = properties.getProperty("jdbc.url");
        String user = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        // Configurar el driver de la base de datos
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver de la base de datos: " + e.getMessage());
            return;
        }

        // Consulta SQL para insertar el postre en la base de datos
        String insertQuery = "INSERT INTO postres (nombre, precio, ingredientes, descripcion, tipo) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Crear la tabla si no existe
            createTableIfNotExists(connection);

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, name);
                statement.setString(2, price);
                statement.setString(3, ingredients);
                statement.setString(4, description);
                statement.setString(5, type);
                statement.executeUpdate();
                System.out.println("Postre añadido correctamente a la base de datos.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar el postre: " + e.getMessage());
        }
    }

    private static void createTableIfNotExists(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS postres ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(100),"
                + "precio DECIMAL(10, 2),"
                + "ingredientes VARCHAR(500),"
                + "descripcion VARCHAR(500),"
                + "tipo VARCHAR(100)"
                + ")";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        }
    }
}
