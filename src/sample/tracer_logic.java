package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class tracer_logic {

    private static String[] places = "公寓1,公寓2,餐厅,写字楼1,写字楼2,地铁,教学楼,电影院".split(",");
    private static SkipList[] SL_Places = new SkipList[places.length*2];//0~7:公寓1,公寓2,餐厅,写字楼1,写字楼2,地铁,教学楼,电影院,8开始存离开时间

    public static int Time_String_to_Int(String time){
        String[] HourAndMin=time.split(":");
        return Integer.parseInt(HourAndMin[0])*60+Integer.parseInt(HourAndMin[1]);
    }
    public static SkipList[] readInfo(){
        File file=new File("1日.txt");
        BufferedReader reader = null;
        //SkipList[] SL_Places = new SkipList[places.length*2];//0~7:公寓1,公寓2,餐厅,写字楼1,写字楼2,地铁,教学楼,电影院,8开始存离开时间
        for(int i=0;i<SL_Places.length;i++){
            SL_Places[i]=new SkipList();
        }
        try {
            reader = new BufferedReader(new FileReader(file));//
            String tempString = null;
            while((tempString = reader.readLine())!=null) {
                String[] personInfo = tempString.split(" ");//0:人名,1:工作地点,2:居住地点,3:电话号码,4:是否戴口罩,5:感染情况,6:去留信息
                Human aPerson=new Human(personInfo[0],personInfo[1],personInfo[2],personInfo[3],Boolean.parseBoolean(personInfo[4]),Integer.parseInt(personInfo[5]));
                String[] placeInfo = personInfo[6].split(",");
                for(String aInfo:placeInfo) {
                    String[] tempS1=aInfo.split("-");//此时0存起止时间,1存地点
                    int i=-1;
                    for(String aPlace:places) {
                        i++;
                        if (aPlace.equals(tempS1[1])) {//在对应的跳表中存入手机号以及起止时间
                            String[] tempTime = tempS1[0].split("~");
                            SL_Places[i].put(Time_String_to_Int(tempTime[0]), aPerson);//存到达时间
                            SL_Places[i+8].put(Time_String_to_Int(tempTime[1]), aPerson);//存离开时间
                        }
                    }
                }
            }
            for(int i=0;i<SL_Places.length/2;i++)
                System.out.println(places[i]+"到达时间:\n"+SL_Places[i]);
            for(int i=SL_Places.length/2;i<SL_Places.length;i++)
                System.out.println(places[i-SL_Places.length/2]+"离开时间:\n"+SL_Places[i]);
            reader.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return SL_Places;
    }

    public static SkipList[] getSL(){
        return SL_Places;
    }
}
