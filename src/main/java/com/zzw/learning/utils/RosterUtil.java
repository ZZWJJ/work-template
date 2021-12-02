package com.zzw.learning.utils;

import org.apache.commons.collections4.ListUtils;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 排班方法
 * @projectName:produce
 * @see:com.kunyue.produce.util
 * @author:zzw
 * @createTime:2021/6/15 14:06
 * @version:1.0
 */
public class RosterUtil {

    /**
     * description
     * param [teamMap, shift]
     * return void
     * author zzw
     * createTime 2021/6/15 14:24
     **/
    public static LinkedHashMap<LocalDate,List<Long>> RoundRobinPattern(Long[] teamArr, String date , int shift, int repeat, int index) throws ParseException {
        Map<LocalDate,LocalDate> dateMap = DateUtil.getEndDay(date, 48L);
        int days = dateMap.size();
        List<Long> list = sort(shift,days,repeat,teamArr,index);
        LinkedHashMap<LocalDate,List<Long>> map = new LinkedHashMap<>();
        List<List<Long>> teamList = ListUtils.partition(list,shift);
        List<LocalDate> dates = new ArrayList<>(dateMap.values());
        for (int i = 0; i < days; i ++) {
            map.put(dates.get(i),teamList.get(i));
        }
        return map;
    }

    private static List<Long> sort(int shift, int days, int repeat, Long[] arr,int index){
        List<Long> list = new ArrayList<>();
        for (int i = 0; i < days; i++) {
            for (int j = 0; j < shift; j++){
                list.add(arr[(index + j) % arr.length]);
            }
            if (repeat > 1){
                if (i % repeat > 0){
                    index = index +1;
                }
            }else {
                index = index +1;
            }
        }
        return list;

    }




    public static void main(String[] args) throws ParseException {
        Long[] teamArr = {1L,2L,3L,4L};
        LinkedHashMap<LocalDate,List<Long>> map = RosterUtil.RoundRobinPattern(teamArr,"2021-06-15",1,1,0);
        System.out.println(map.toString());
        System.out.println(map.size());
    }


}
