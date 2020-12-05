package sample;

import javafx.scene.control.Alert;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class status_getter {
    static String[] province_name = "安徽,澳门,北京,重庆,福建,广东,甘肃,广西,贵州,湖北,河北,黑龙江,湖南,河南,海南,吉林,江苏,江西,辽宁,内蒙古,宁夏,青海,四川,山东,上海,陕西,山西,天津,台湾,香港,新疆,西藏,云南,浙江".split(",");
    static String[] province_id = "AH,AM,BJ,CQ,FJ,GD,GS,GX,GZ,HB,HB-1,HLJ,HN,HN-1,HN-2,JL,JS,JX,LN,NMG,NX,QH,SC,SD,SH,SX,SX-1,TJ,TW,XG,XJ,XZ,YN,ZJ".split(",");

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

            System.out.println(connection.getContentLength());
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历输出所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // 定义 BufferedReader输入流来读取URL的响应
            InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
            in = new BufferedReader(isr);

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
        int pos = 0;
        for (int i = 0; i < province_name.length; i++)
            if (province_name[i].equals(province))
                pos = i;
        String temp = sendGet("http://111.231.75.86:8000/api/provinces/CHN/" + province_id[pos]);
        System.out.println(temp);
        String[] ContentList = temp.split(",");
        for (int i = 3; i < 8; i++) {
            String[] t = ContentList[i].split(":");
            result[i - 3] = t[1];
        }
        return result;
    }

}
