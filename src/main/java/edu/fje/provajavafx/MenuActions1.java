package edu.fje.provajavafx;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.*;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MenuActions1 {
    public static String platName;
    public static String platPrice;
    public static String platIngredients;
    public static String platDescription;
    public static void addPlatAction() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Añadir plat");

        Label nameLabel = new Label("Nombre del plat:");
        TextField nameField = new TextField();

        Label priceLabel = new Label("Precio del plat:");
        TextField priceField = new TextField();

        // ...

        Label ingredientsLabel = new Label("Ingredientes:");
        TextField ingredientsField = new TextField();

        Label descriptionLabel = new Label("Descripción:");
        TextField descriptionField = new TextField();

// ...

        Button addButton = new Button("Añadir");
        addButton.setOnAction(event -> {
            platName = nameField.getText();
            platPrice = priceField.getText();
            platIngredients = ingredientsField.getText();
            platDescription = descriptionField.getText();

            // Lógica para guardar los datos en la base de datos
            savePlat(platName, platPrice, platIngredients, platDescription);

            dialog.close();
        });

        vbox.getChildren().addAll(nameLabel, nameField, priceLabel, priceField, ingredientsLabel, ingredientsField, descriptionLabel, descriptionField, addButton);

        Scene scene = new Scene(vbox, 400, 300);

        scene.getStylesheets().add(MenuActions1.class.getResource("/estils1.css").toExternalForm()); // Modify this line to link the CSS file

        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private static void savePlat(String name, String price, String ingredients, String description) {
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

        // Consulta SQL para insertar el plat en la base de datos
        String insertQuery = "INSERT INTO plats (nombre, precio, ingredientes, descripcion) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Crear la tabla si no existe
            createTableIfNotExists(connection);

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, name);
                statement.setString(2, price);
                statement.setString(3, ingredients);
                statement.setString(4, description);
                statement.executeUpdate();
                System.out.println("Plat añadido correctamente a la base de datos.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar el plat: " + e.getMessage());
        }
    }

    private static void createTableIfNotExists(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS plats ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(100),"
                + "precio DECIMAL(10, 2),"
                + "ingredientes VARCHAR(500),"
                + "descripcion VARCHAR(500)"
                + ")";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        }
    }
}
