package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Main extends Application implements Initializable {
    private static ChoiceBox dayBoxSelected, ProvinceBoxSelected;
    private static String ButtonName;
    private static String[] Provinces = "北京,天津,上海,重庆,河北,山西,辽宁,吉林,黑龙江,江苏,浙江,安徽,福建,江西,山东,河南,湖北,湖南,广东,海南,四川,贵州,云南,陕西,甘肃,青海,台湾,内蒙古,广西,西藏,宁夏,新疆,香港,澳门".split(",");
    @FXML
    private ChoiceBox dayBox, ProvinceBox;
    @FXML
    private Button Button0, Button1, Button2, Button3, Button4, Button5, Button6, Button7;
    @FXML
    private Text Text0, Text1, Text2, Text3, Text4;
    private Stage primaryStage;
    private AnchorPane rootLayout;

    public static void main(String[] args) {
        //tracer_logic.readInfo();
        launch(args);
    }

    public static String getButtonName() {
        return ButtonName;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("密接追踪器");
        initRootLayout();
    }

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

    public void showPersonOverview(String Place_Name) {
        try {
            // Load person overview.
            AnchorPane location = FXMLLoader.load(getClass().getResource("Floor_Status.fxml"));
            Stage infoStage = new Stage();
            infoStage.setTitle(Place_Name + "信息");
            infoStage.setScene(new Scene(location));
            infoStage.setResizable(false);
            infoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void GetterChanged(MouseEvent mouseEvent) {
    }

    public void FloorClicked(MouseEvent mouseEvent) {
        Button EventSource = (Button) mouseEvent.getSource();
        ButtonName = EventSource.getText();
        showPersonOverview(ButtonName);
    }

    public void setButtonColor(Button x) {
        String placename = x.getText();
        int personnum = tracer_logic.getNumOfContacted(placename);
        if (personnum > 50) x.setStyle("-fx-background-color:RED");
        else if (personnum > 30) x.setStyle("-fx-background-color:ORANGE");
        else if (personnum > 0) x.setStyle("-fx-background-color:YELLOW");
        else if (personnum == 0) x.setStyle("-fx-background-color:#4A87A1");
    }

    //Box选择框以及风险等级初始化
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList list = new ArrayList();
        for (int i = 1; i <= 31; i++) {
            File file = new File(i + "日.txt");
            if (file.exists()) {
                list.add(file.getName().subSequence(0, 2));
                dayBox.getItems().addAll(file.getName().subSequence(0, 2));
            }
        }
        String[] days = (String[]) list.toArray(new String[0]);
        dayBox.getSelectionModel().select(0);
        tracer_logic.readInfo(days[0]);
        setButtonColor(Button0);
        setButtonColor(Button1);
        setButtonColor(Button2);
        setButtonColor(Button3);
        setButtonColor(Button4);
        setButtonColor(Button5);
        setButtonColor(Button6);
        setButtonColor(Button7);
        dayBoxSelected = dayBox;
        dayBoxSelected.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                tracer_logic.readInfo(days[newValue.intValue()]);
                setButtonColor(Button0);
                setButtonColor(Button1);
                setButtonColor(Button2);
                setButtonColor(Button3);
                setButtonColor(Button4);
                setButtonColor(Button5);
                setButtonColor(Button6);
                setButtonColor(Button7);
            }
        });
        Text0.setText("0");
        Text1.setText("0");
        Text2.setText("0");
        Text3.setText("0");
        Text4.setText("0");
        ProvinceBox.getItems().addAll(Provinces);
        ProvinceBoxSelected = ProvinceBox;
        ProvinceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String[] result = status_getter.get_Province_status(Provinces[newValue.intValue()]);
                Text0.setText(result[0]);
                Text1.setText(result[1]);
                Text2.setText(result[2]);
                Text3.setText(result[3]);
                Text4.setText(result[4]);
            }
        });
    }
}