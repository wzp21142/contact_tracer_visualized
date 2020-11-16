package sample;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.text.ParseException;

public class Chart {
    private static String[] places = "公寓1,公寓2,餐厅,写字楼1,写字楼2,地铁,教学楼,电影院".split(",");
    @FXML
    private static LineChart chart;

    public static XYChart.Series<Integer, Integer> createSeries() {
        int[] nums = new int[24];//感染人数
        XYChart.Series series = new LineChart.Series<>();
        SkipList[] list = tracer_logic.getSL();
        String PlaceName = Main.getButtonName();
        try {
            for (int i = 0; i < places.length; i++) {
                if (places[i].equals(PlaceName)) {
                    String[] Persons = InfoController.getHumanPlaceInfo(list, i).toString().split("\n");
                    for (String aPerson : Persons) {
                        String[] Infos = aPerson.split(" ");
                        if (Integer.parseInt(Infos[5]) > 0)
                            nums[Integer.parseInt(Infos[6].split(":")[0])]++;
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
