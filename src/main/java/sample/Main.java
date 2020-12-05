package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("密接追踪器");
        //FXMLLoader loader = new FXMLLoader();
        //System.out.println(getClass().getResource(""));
        FXMLLoader loader = new FXMLLoader();    // 创建对象
        loader.setLocation(getFxmlPath(this, "Main"));//获得fxml的路径
        InputStream inputStream = getFxmlFile(this, "Main");
        Object o = null;
        try {
            o = loader.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = (Parent) o;
        //Parent root = loader.getRoot();
        //System.out.println("111!!!"+root);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        //loader.setRoot(getClass().getResource("/sample/"));
        /*loader.setLocation(getClass().getResource("/sample/Main.fxml"));
        try {
            rootLayout = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // Show the scene containing the root layout.
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/COVID-19.jpg")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(null);
    }

    public static URL getFxmlPath(Object o, String fileName) {
        return o.getClass().getResource("/resources/" + fileName + ".fxml");
    }

    public static InputStream getFxmlFile(Object o, String fileName) {
        return o.getClass().getResourceAsStream("/resources/" + fileName + ".fxml");
    }
}
