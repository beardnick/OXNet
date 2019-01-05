package com.example.demo.controller;
import com.example.demo.dao.OxDataRepository;
import com.example.demo.dao.ResultDataRepository;
import com.example.demo.model.OxData;
import com.example.demo.model.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.xml.soap.SAAJResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class OxDataController {
    private int start=0;
    @Autowired
    private OxDataRepository oxDataRepository;
    private ResultDataRepository resultDataRepository;

    @RequestMapping("/getAll")
    public List<OxData> getAll(){
        return oxDataRepository.findNext(0,3000);
    }

    public List<Object> position_judge(float a, float distance, HashMap<String,Object> flag){
        //HashMap<String, String> flag = new HashMap<>();
        List<Object> stand_lie = new ArrayList<Object>();
        System.out.println("position judge!\n");
        Float dis = distance;
            if(a > 9.8){
                flag.put("stand_up","1");
                flag.put("lie_down", "0");
                stand_lie.add(1);   stand_lie.add(0);
            }else if(a < 9.8){
                flag.put("stand_up", "0");
                flag.put("lie_down", "1");
                stand_lie.add(0);   stand_lie.add(1);
            }else {
                flag.put("stand_up", "-1");
                flag.put("lie_down", "-1");
                stand_lie.add(-1);   stand_lie.add(-1);
            }
            stand_lie.add(distance);
            flag.put("move_distance",dis.toString());

        return stand_lie;
    }

//    public void breath_judge(float a, int start,int offset, HashMap<String ,String >flag){
//        int start_tmp = start;
//        List<OxData> oneSet = new ArrayList<>();
//        oneSet = oxDataRepository.findNext(start,3000);
//        int j = 0;
//        boolean end_flag = false;
//        for (int i = 0; i < 60; i++, start_tmp+=50){  //get the first index where ox only breath
//            oneSet = oxDataRepository.findNext(start_tmp,offset);
//            int z = -1, y=-1;
//            for( ; j < 50; j+=5){
//                OxData sample = oneSet.get(j);
//                if(sample.getZ() == 0){ z = 0; }
//                if(sample.getY() < 10 && sample.getY() > 9.5){  y = 0; }
//                if(z == 0 && y ==0){
//                    end_flag = true;
//                    break;
//                }
//            }
//            if(end_flag){ break; }
//        }
//        oneSet = oxDataRepository.findNext(j,3*offset);
//        float a_sum = 0;
//        for (int i =0; i < 50; i++){
//            a_sum += Math.abs(oneSet.get(i).getY() - 9.8);
//        }
//        breath_a = a_sum/100;  //breath acceleration
//        /* analysis breath frequency and depth */
//        int y_count = 0;
//        int t_count = 0;
//        float y_start = oneSet.get(0).getY();
//        float y_tmp = 0;
//        for(int i = 0; i < 3*offset; i++){
//            t_count++;
//            if(Math.abs(oneSet.get(i).getY()-y_start) < 0.2 ){
//                y_count ++;
//                i += 10;
//            }
//
//
//        }
//    }

    @CrossOrigin("*")
    @RequestMapping("/analysis_pers")
    public HashMap<String, Object> analysis_pers(){
        HashMap<String,Object> flag = new HashMap<>();
        //flag = { head_up, head_down, stand_up, lie_down,move_distance, breath_frequency, breath_depth,breath_a }
        int offset = 50;
//        int start_old = oxDataRepository.query_start();
        int start_old = start;
        //float breath_a = 0;
        int head_up = -1, head_down = -1;
        int stand_up = -1, lie_down = -1;
        float move_distance = 0, breath_a = 0;
        System.out.println("start: "+ start);
        //ResultData objects = resultDataRepository.select_breath(start);
//        if ( (objects=resultDataRepository.select_breath(start++)) == null){
//
//        }

//        float breath_frequency = objects.getBreath_frequency();
//        float breath_depth = objects.getBreath_depth();
        //float breath_a = objects.getBreath_a();

        List<OxData> oneSet = new ArrayList<>();

        /* analysis breath: breath_frequency and breath_depth */
//        if( start%3000 == 0){
//            int start_tmp = start;
//            //oneSet = oxDataRepository.findNext(start,3000);
//            int j = 0;
//            boolean end_flag = false;
//            for (int i = 0; i < 60; i++, start_tmp+=50){  //get the first index where ox only breath
//                oneSet = oxDataRepository.findNext(start_tmp,offset);
//                int z = -1, y=-1;
//                for( ; j < 50; j+=5){
//                    OxData sample = oneSet.get(j);
//                    if(sample.getZ() == 0){ z = 0; }
//                    if(sample.getY() < 10 && sample.getY() > 9.5){  y = 0; }
//                    if(z == 0 && y ==0){
//                        end_flag = true;
//                        break;
//                    }
//                }
//                if(end_flag){ break; }
//            }
//            oneSet = oxDataRepository.findNext(j,3*offset);
//            float a_sum = 0;
//            for (int i =0; i < 50; i++){
//                a_sum += Math.abs(oneSet.get(i).getY() - 9.8);
//            }
//            breath_a = a_sum/100;  //breath acceleration
//            /* analysis breath frequency and depth */
//            int y_count = 0;
//            int t_count = 0;
//            float y_start = oneSet.get(0).getY();
//            float y_tmp = 0;
//            for(int i = 0; i < 3*offset; i++){
//                t_count++;
//                if(Math.abs(oneSet.get(i).getY()-y_start) < 0.2 ){
//                    y_count ++;
//                    i += 10;
//                }
//
//
//            }
//        }
        try {
            /* analysis head action: head_up/head_down */
            int z_count = 0;
            if ((oneSet = oxDataRepository.findNext(start,offset)) != null){
                float ox_data[][] = new float[5][3];
                for (int i = 0; i < 50; i += 10 ){
                    int index = i/10;
                    System.out.println("index:"+index+"\n");
                    ox_data[index][0] = oneSet.get(i).getX();
                    ox_data[index][1] = oneSet.get(i).getY();
                    ox_data[index][2] = oneSet.get(i).getZ();
                }
                flag.put("data",ox_data);
                System.out.println("oneSet:"+oneSet+"\n");
                for (OxData next: oneSet) {
                    if(next.getZ() > 0 ){
                        z_count ++;
                    }else if(next.getZ() < 0){
                        z_count--;
                    }
                }
                if(z_count > 0){  //head_up
                    flag.put("head_up","1");
                    flag.put("head_down", "0");
                    head_up = 1;    head_down = 0;
                }else if(z_count < 0){ //head_down
                    flag.put("head_up", "0");
                    flag.put("head_down", "1");
                    head_up = 0;    head_down = 1;
                }else {  //not move head
                    flag.put("head_up", "-1");
                    flag.put("head_down", "-1");
                    head_up = -1;   head_down = -1;
                }
            }else {
                System.out.println("none data!!!\n");  //********
                flag.put("head_up","-1");
                flag.put("head_down","-1");
            }

            /*analysis body action: stand_up/lie_down and move_distance*/
            float a1 = 0, a2=0, a3=0;
            float distance_lower= 0.8f, distance_upper=1.2f;
            float heigth1=0, heigth2=0, heigth3=0;
            List<OxData> firstSet = new ArrayList<>();
            List<OxData> SecondSet = new ArrayList<>();
            List<OxData> ThirdSet = new ArrayList<>();
            if(start == 0 || start == offset){
                System.out.println("start1\n");
                firstSet = oxDataRepository.findNext(start,150);
                SecondSet.addAll(firstSet);
                ThirdSet.addAll(firstSet);
            }else if(start == (oxDataRepository.getLastId()-offset) || start == (oxDataRepository.getLastId()-2*offset)){
                System.out.println("start2\n");
                firstSet = oxDataRepository.findNext(start-100, 150);
                SecondSet.addAll(firstSet);
                ThirdSet.addAll(firstSet);
            }else {
                System.out.println("start3\n");
                firstSet = oxDataRepository.findNext(start - 100, 150);
                SecondSet = oxDataRepository.findNext(start - 50, 150);
                ThirdSet = oxDataRepository.findNext(start, 150);
            }
            System.out.println("firstSet:"+firstSet+"\n");
            System.out.println("secondSet:"+SecondSet+"\n");
            System.out.println("thirdSet:"+ThirdSet+"\n");
            for(int i = 0; i < 150; i++){
                a1 += firstSet.get(i).getY();
                a2 += SecondSet.get(i).getY();
                a3 += ThirdSet.get(i).getY();
            }
            a1 = a1/150;    heigth1= Math.abs((float) (1.0/2*(a1-9.8)*9));
            a2 = a2/150;    heigth2= Math.abs((float) (1.0/2*(a2-9.8)*9));
            a3 = a3/150;    heigth3= Math.abs((float) (1.0/2*(a3-9.8)*9));
            System.out.println("heigth: "+heigth1+":"+heigth2+":"+heigth3+"\n");
            System.out.println("a:"+a1+":"+a2+":"+a3+"\n");

            float body_a = 0;
            List<Object> stand_lie ;
            if(heigth1 > distance_lower && heigth1 < distance_upper){  //stand_up or lie_down and move_distance

                stand_lie = position_judge(a1,heigth1,flag);
                body_a =Math.abs( (float) (a1 - 9.8));

            }else if(heigth2 > distance_lower && heigth2 < distance_upper){
                stand_lie = position_judge(a2,heigth2,flag);
                body_a =Math.abs( (float) (a2 - 9.8));
            }else if (heigth3 > distance_lower && heigth3 < distance_upper){
                stand_lie = position_judge(a3,heigth3,flag);
                body_a =Math.abs( (float) (a3 - 9.8));
            }else {
                body_a = 0f;
                stand_lie = position_judge(9.8f,0,flag);
                System.out.println("ox don't move\n");
            }
            stand_up = (int)stand_lie.get(0);
            lie_down = (int)stand_lie.get(1);
            move_distance = (float)stand_lie.get(2);
            breath_a = (float)(SecondSet.get(50).getY() - body_a - 9.8);
//            breath_a = (float) Math.abs(oneSet.get(start).getY() - body_a);
            flag.put("breath_a",String.valueOf(breath_a));
//            resultDataRepository.saveAnalysisResult();
            oxDataRepository.saveAnalysisResult(start,head_up,head_down,stand_up,lie_down,move_distance,breath_a);
            Integer standUp = oxDataRepository.count_standUp();
            Integer lieDown = oxDataRepository.count_lieDown();
            Integer headUp = oxDataRepository.count_headUp();
            Integer headDown = oxDataRepository.count_headDown();
            flag.put("standUp",standUp.toString());
            flag.put("lieDown",lieDown.toString());
            flag.put("headUp", headUp.toString());
            flag.put("headDown", headDown.toString());
        }catch (Exception e){
            System.out.println("query error!\n");
            e.printStackTrace();
        }
        start = start_old + offset;  /* next start data index */
        if((start+50) > oxDataRepository.getLastId()){
            System.out.println("ox_data last id:"+oxDataRepository.getLastId()+"\n");
            start = 0;
        }

//        oxDataRepository.update_start(start+50);

        return flag;
    }
}
