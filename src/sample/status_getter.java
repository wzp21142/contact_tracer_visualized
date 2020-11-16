package sample;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class status_getter {
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历输出所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set("错误");
            alert.headerTextProperty().set("无网络连接或API已失效,请联系开发者!");
            alert.showAndWait();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String[] get_Province_status(String province) {
        String[] result = new String[5];//result[0]:现有确诊数 result[1]:总确诊数 result[2]:疑似病例数 result[3]:治愈数 result[4]:死亡数
        String temp = sendGet("http://111.231.75.86:8000/api/provinces/CHN/?provinceNames=" + province);
        String[] ContentList = temp.split(",");
        for (int i = 3; i < 8; i++) {
            String[] t = ContentList[i].split(":");
            result[i - 3] = t[1];
        }
        return result;
    }

}
