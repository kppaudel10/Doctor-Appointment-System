package com.oda.component;

import java.lang.reflect.Array;

/**
 * @author kulPaudel
 * @project OnlineDoctorAppointMent
 * @Date 4/18/22
 */
public class GetRating {
    public static Integer getRating(Integer valueOne,Integer valueTwo,
                                    Integer valueThree,Integer valueFour
    ,Integer valueFive){
        Integer rating = 0;
//        Integer[] array = new Integer[]{valueOne,valueTwo,valueThree,valueFour,valueFive};
//        for(Integer i : array){
//            if (i !=null){
//                rating +=1;
//            }
//        }
        if (valueFive != null && valueFive == 1){
            rating = 5;
        }else if (valueFour != null && valueFour == 1){
            rating = 4;
        } else if (valueThree !=null && valueThree == 1) {
            rating = 3;
        } else if (valueTwo != null && valueTwo == 1) {
            rating = 2;
        }else {
            rating = 1;
        }
        return rating;
    }
}
