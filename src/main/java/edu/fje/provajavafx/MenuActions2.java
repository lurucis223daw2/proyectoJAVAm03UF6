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

public class MenuActions2 {

    public static void addBebidaAction() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Añadir bebida");

        Label nameLabel = new Label("Nombre de la bebida:");
        TextField nameField = new TextField();

        Label priceLabel = new Label("Precio de la bebida:");
        TextField priceField = new TextField();

        Label brandLabel = new Label("Marca de la bebida:");
        TextField brandField = new TextField();

        Button addButton = new Button("Añadir");
        addButton.setOnAction(event -> {
            String bebidaName = nameField.getText();
            String bebidaPrice = priceField.getText();
            String bebidaBrand = brandField.getText();

            // Lógica para guardar los datos en la base de datos
            saveBebida(bebidaName, bebidaPrice, bebidaBrand);

            dialog.close();
        });

        vbox.getChildren().addAll(nameLabel, nameField, priceLabel, priceField, brandLabel, brandField, addButton);

        Scene scene = new Scene(vbox, 400, 300);

        //scene.getStylesheets().add(MenuActions2.class.getResource("/estils2.css").toExternalForm()); // Agrega esta línea si deseas aplicar estilos CSS

        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private static void saveBebida(String name, String price, String brand) {
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

        // Consulta SQL para insertar la bebida en la base de datos
        String insertQuery = "INSERT INTO bebidas (nombre, precio, marca) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            // Crear la tabla si no existe
            createTableIfNotExists(connection);

            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setString(1, name);
                statement.setString(2, price);
                statement.setString(3, brand);
                statement.executeUpdate();
                System.out.println("Bebida añadida correctamente a la base de datos.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar la bebida: " + e.getMessage());
        }
    }

    private static void createTableIfNotExists(Connection connection) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS bebidas ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "nombre VARCHAR(100),"
                + "precio DECIMAL(10, 2),"
                + "marca VARCHAR(100)"
                + ")";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        }
    }
}
