package sample;

import java.io.*;
import java.sql.Time;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;
    private static String ButtonName;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("密接追踪器");
        initRootLayout();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Main.fxml"));
            rootLayout = loader.load();
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview(String Place_Name) {
        try {
            // Load person overview.
            AnchorPane location = FXMLLoader.load(getClass().getResource("Floor_Status.fxml"));
            Stage infoStage = new Stage();
            infoStage.setTitle(Place_Name+"信息");
            infoStage.setScene(new Scene(location));
            //new InfoController().setTableColumns(tracer_logic.readInfo(),Place_Name);
            infoStage.setResizable(false);
            infoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        //tracer_logic.readInfo();
        launch(args);
    }

    public void FloorClicked(MouseEvent mouseEvent) {
        Button EventSource = (Button)mouseEvent.getSource();
        ButtonName=EventSource.getText();
        showPersonOverview(ButtonName);
    }

    public static String getButtonName(){
        return ButtonName;
    }
}