package sample;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class Chart {
    private static String[] places = "公寓1,公寓2,餐厅,写字楼1,写字楼2,地铁,教学楼,电影院".split(",");
    private static SkipList[] SL_Places = tracer_logic.getSL();
    @FXML
    private static LineChart chart;

    //对第x个表中存储的密接人数进行计数
    public static int[] calIllNum(int x) {
        SkipListNode<Human> pArrive = SL_Places[x].findIlledPerson(null);
        int[] nums = new int[24];//各小时感染人数
        while (pArrive != null) {
            SkipListNode<Human>[] Contacted_Human = SL_Places[x].FindCommonNodes(SL_Places[x].getComparedNode(pArrive.time, true), SL_Places[x + 8].getComparedNode(SL_Places[x + 8].findPersonbyPhone(pArrive.person_info.phonenumber).time, false), SL_Places[x], SL_Places[x + 8]);
            for (SkipListNode<Human> t : Contacted_Human) {
                if (t == null) break;
                nums[t.time / 60]++;
            }
            pArrive = SL_Places[x].findIlledPerson(pArrive);//从该节点开始继续寻找
        }
        return nums;
    }

    public static XYChart.Series<Integer, Integer> createSeries() {
        XYChart.Series series = new LineChart.Series<>();
        SkipList[] list = tracer_logic.getSL();
        int[] nums = new int[24];
        String PlaceName = MainInterface.getButtonName();
        for (int i = 0; i < places.length; i++) {
            if (places[i].equals(PlaceName)) {
                nums = calIllNum(i);
            }
        }
       /*try {
            for (int i = 0; i < places.length; i++) {
                if (places[i].equals(PlaceName)) {
                    String[] Persons = InfoController.getHumanPlaceInfo(list, i).toString().split("\n");
                    for (String aPerson : Persons) {
                        String[] Infos = aPerson.split(" ");
                        if (Integer.parseInt(Infos[5]) > 0)
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }*/
        for (int i = 0; i < 24; i++) {
            int j = i - 1;
            if (j >= 0)
                nums[i] += nums[j];
            series.setName("时间-人数");
            series.getData().add(new XYChart.Data(i, nums[i]));
        }
        return series;
    }

    public static Parent createContent() {
        NumberAxis xAxis = new NumberAxis(0, 24, 2);
        xAxis.setLabel("时间");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("密接总人数");

        chart = new LineChart(xAxis, yAxis);
        chart.getData().add(createSeries());
        chart.setTitle("密接情况折线图");
        chart.setCreateSymbols(false);
        return chart;
    }
}
