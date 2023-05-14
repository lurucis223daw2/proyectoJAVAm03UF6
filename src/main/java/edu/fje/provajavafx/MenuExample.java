package edu.fje.provajavafx;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear los botones del menú
        Button addPlatItem = new Button("Añadir plat");
        Button addBebidaItem = new Button("Añadir bebida");
        Button addPostreItem = new Button("Añadir postre");
        Button reviewPlatsItem = new Button("Revisar plats");
        Button modifyPlatItem = new Button("Modificar datos");
        Button deletePlatItem = new Button("Eliminar plats");
        Button exitItem = new Button("Salir");

        // Agregar eventos a los botones del menú
        addPlatItem.setOnAction(event -> MenuActions1.addPlatAction());
        addBebidaItem.setOnAction(event -> MenuActions2.addBebidaAction());
        addPostreItem.setOnAction(event -> MenuActions3.addPostreAction());
        reviewPlatsItem.setOnAction(event -> MenuActions4.reviewPlatsAction());
        modifyPlatItem.setOnAction(event -> MenuActions5.modifyPlatAction());
        deletePlatItem.setOnAction(event -> MenuActions6.deletePlatAction());
        exitItem.setOnAction(event -> primaryStage.close());

        // Crear el contenedor para los botones del menú
        VBox menuBox = new VBox(10);
        menuBox.setAlignment(Pos.CENTER);
        menuBox.getChildren().addAll(addPlatItem, addBebidaItem, addPostreItem, reviewPlatsItem, modifyPlatItem, deletePlatItem, exitItem);

        // Crear la escena
        Scene scene = new Scene(menuBox, 400, 300);

        // Vincular el archivo CSS a la escena
        scene.getStylesheets().add(getClass().getResource("/estils.css").toExternalForm());

        // Configurar el escenario principal
        primaryStage.setTitle("Ejemplo de Menú");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
