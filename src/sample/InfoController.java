package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class InfoController implements Initializable {
    private static String[] places = "公寓1,公寓2,餐厅,写字楼1,写字楼2,地铁,教学楼,电影院".split(",");
    @FXML
    private TableColumn<Human_SinglePlace, String> nameCol;
    @FXML
    private TableColumn<Human_SinglePlace, String> phoneCol;
    @FXML
    private TableColumn<Human_SinglePlace, String> workspaceCol;
    @FXML
    private TableColumn<Human_SinglePlace, String> homeCol;
    @FXML
    private TableColumn<Human_SinglePlace, String> arrivetimeCol;
    @FXML
    private TableColumn<Human_SinglePlace, String> leavetimeCol;
    @FXML
    private TableColumn<Human_SinglePlace, String> MaskCol;
    @FXML
    private TableColumn<Human_SinglePlace, String> LevelCol;
    @FXML
    private TableView<Human_SinglePlace> information;

    public void setTableColumns(SkipList[] list, String PlaceName) throws ParseException {
        ObservableList<Human_SinglePlace> datalist = FXCollections.observableArrayList();
        for (int i = 0; i < places.length; i++) {
            if (places[i].equals(PlaceName)) {
                String[] Persons = getHumanPlaceInfo(list, i).toString().split("\n");
                for (String aPerson : Persons) {
                    String[] Infos = aPerson.split(" ");
                    String Mask = Infos[4].equals(true) ? "是" : "否";
                    String Level = (Integer.parseInt(Infos[5]) == 0) ? "未感染" : (Integer.parseInt(Infos[5]) == 1) ? "有防护密接" : (Integer.parseInt(Infos[5]) == 2) ? "无防护密接" : "感染者";
                    Human_SinglePlace tempHuman = new Human_SinglePlace(Infos[0], Infos[1], Infos[2], Infos[3], Mask, Level, Infos[6], Infos[7]);
                    //System.out.println(tempHuman);
                    datalist.add(tempHuman);
                }
            }
        }
        information.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        nameCol.setCellValueFactory(new PropertyValueFactory<Human_SinglePlace, String>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Human_SinglePlace, String>("phonenumber"));
        workspaceCol.setCellValueFactory(new PropertyValueFactory<Human_SinglePlace, String>("workspace"));
        homeCol.setCellValueFactory(new PropertyValueFactory<Human_SinglePlace, String>("home"));
        arrivetimeCol.setCellValueFactory(new PropertyValueFactory<Human_SinglePlace, String>("arrivetime"));
        leavetimeCol.setCellValueFactory(new PropertyValueFactory<Human_SinglePlace, String>("leavetime"));
        MaskCol.setCellValueFactory(new PropertyValueFactory<Human_SinglePlace, String>("isMaskOn"));
        LevelCol.setCellValueFactory(new PropertyValueFactory<Human_SinglePlace, String>("IllLevel"));
        information.setItems(datalist);
    }

    public StringBuilder getHumanPlaceInfo(SkipList[] list, int index) throws ParseException {
        StringBuilder temp = new StringBuilder();
        String[] PersonList = list[index].toString().split("\n");//分离出每个人的信息
        String[] PersonList_leave = list[index + 8].toString().split("\n");
        for (int i = 0; i < PersonList.length; i++) {
            String[] tempStringArrive = PersonList[i].split("-");//以-为界,分开时间与个人信息
            String[] tempString2 = tempStringArrive[1].split(" ");
            //tempString2[3] = tempString2[3].substring(0, tempString2[3].length() - 2);
            String tempLeavetime = list[index + 8].findPersonbyPhone(tempString2[3]).toString().split("-")[0];
            temp.append(tempString2[0] + " " + tempString2[1] + " " + tempString2[2] + " " + tempString2[3] + " " + tempString2[4] + " " + tempString2[5] + " " + tempStringArrive[0] + " " + tempLeavetime + "\n");
        }
        //System.out.println(temp);
        return temp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            //information.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//设置可多选
            setTableColumns(tracer_logic.getSL(), Main.getButtonName());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
