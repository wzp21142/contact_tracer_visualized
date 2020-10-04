package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class tracer_logic {

    private static String[] places = "公寓1,公寓2,餐厅,写字楼1,写字楼2,地铁,教学楼,电影院".split(",");
    private static SkipList[] SL_Places = new SkipList[places.length * 2];//0~7:公寓1,公寓2,餐厅,写字楼1,写字楼2,地铁,教学楼,电影院,8开始存离开时间

    public static int Time_String_to_Int(String time) {
        String[] HourAndMin = time.split(":");
        return Integer.parseInt(HourAndMin[0]) * 60 + Integer.parseInt(HourAndMin[1]);
    }

    public static SkipList[] readInfo(String filename) {
        File file = new File(filename + ".txt");
        BufferedReader reader = null;
        //SkipList[] SL_Places = new SkipList[places.length*2];//0~7:公寓1,公寓2,餐厅,写字楼1,写字楼2,地铁,教学楼,电影院,8开始存离开时间
        for (int i = 0; i < SL_Places.length; i++) {
            SL_Places[i] = new SkipList();
        }
        try {
            reader = new BufferedReader(new FileReader(file));//
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                String[] personInfo = tempString.split(" ");//0:人名,1:工作地点,2:居住地点,3:电话号码,4:是否戴口罩,5:感染情况,6:去留信息
                //System.out.println(tempString);
                Human aPerson=new Human(personInfo[0],personInfo[1],personInfo[2],personInfo[3],Boolean.parseBoolean(personInfo[4]),Integer.parseInt(personInfo[5]));
                String[] placeInfo = personInfo[6].split(",");
                for(String aInfo:placeInfo) {
                    String[] tempS1=aInfo.split("-");//此时0存起止时间,1存地点
                    int i=-1;
                    for(String aPlace:places) {
                        i++;
                        if (aPlace.equals(tempS1[1])) {//在对应的跳表中存入手机号以及起止时间
                            String[] tempTime = tempS1[0].split("~");
                            //System.out.println(aPerson.name+" "+tempTime[0]+","+tempTime[1]);
                            SL_Places[i].put(Time_String_to_Int(tempTime[0]), aPerson);//存到达时间
                            SL_Places[i + 8].put(Time_String_to_Int(tempTime[1]), aPerson);//存离开时间
                        }
                    }
                }
            }
           /* for(int i=0;i<SL_Places.length/2;i++)
                System.out.println(places[i]+"到达时间:\n"+SL_Places[i]);
            for(int i=SL_Places.length/2;i<SL_Places.length;i++)
                System.out.println(places[i-SL_Places.length/2]+"离开时间:\n"+SL_Places[i]);*/
            reader.close();
            for (int i = 0; i < 8; i++)
                calIllLevel(i);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SL_Places;
    }

    //对第x个表中存储的人进行感染等级计算
    public static void calIllLevel(int x) {
        SkipListNode<Human> pArrive = SL_Places[x].findIlledPerson(null);
        while (pArrive != null) {
            SkipListNode<Human>[] Contacted_Human = SL_Places[x].FindCommonNodes(SL_Places[x].getComparedNode(pArrive.time, true), SL_Places[x + 8].getComparedNode(SL_Places[x + 8].findPersonbyPhone(pArrive.person_info.phonenumber).time, false), SL_Places[x], SL_Places[x + 8]);
            for (SkipListNode<Human> t : Contacted_Human) {
                if (t == null) break;
                SkipListNode<Human> a = SL_Places[x].findPersonbyPhone(t.person_info.phonenumber);
                SkipListNode<Human> b = SL_Places[x + 8].findPersonbyPhone(t.person_info.phonenumber);
                a.person_info.IllLevel = (a.person_info.IllLevel == 3) ? 3 : (a.person_info.isMaskOn == true) ? 1 : 2;
                b.person_info.IllLevel = a.person_info.IllLevel;
            }
            pArrive = SL_Places[x].findIlledPerson(pArrive);
        }
    }

    public static SkipList[] getSL() {
        return SL_Places;
    }

    public static int getNumOfContacted(String place) {
        int num = 0, index;
        for (index = 0; index < places.length; index++) {
            if (places[index].equals(place))
                break;
        }
        SkipListNode p = null;
        do {
            p = SL_Places[index].findSuspectedPerson(p);
            num++;
        }
        while (p != null);
        return num;
    }
}
