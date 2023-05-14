package edu.fje.provajavafx;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class MenuActions4 {
    public static void reviewPlatsAction() {
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

        // Consulta SQL para obtener la información de la tabla de bebidas
        String selectQuery = "SELECT * FROM bebidas";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("id");
                        String nombre = resultSet.getString("nombre");
                        double precio = resultSet.getDouble("precio");
                        String marca = resultSet.getString("marca");

                        // Haz algo con los datos obtenidos, por ejemplo, imprimirlos en la consola
                        System.out.println("ID: " + id);
                        System.out.println("Nombre: " + nombre);
                        System.out.println("Precio: " + precio);
                        System.out.println("Marca: " + marca);
                        System.out.println("---------------------------");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al consultar la información de la base de datos: " + e.getMessage());
        }
    }
}
