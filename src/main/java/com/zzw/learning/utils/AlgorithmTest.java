package com.zzw.learning.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @description:
 * @projectName:work-template
 * @see:com.zzw.learning.utils
 * @author:zzw
 * @createTime:2022/6/22 14:53
 * @version:1.0
 */
public class AlgorithmTest {

    /**
     * description 两数求和
     * param []
     * return int[]
     * author zzw
     * createTime 2022/6/22 14:54
     **/
    public static int[] twoSum() {
        int[] nums = {4, 3, 2};
        int target = 6;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("No two sum solution");


    }


    /**
     * description 截取字符串某几位
     * param []
     * return java.lang.String
     * author zzw
     * createTime 2022/6/22 14:54
     **/
    public static String truncateSentence() {
        String s = "chopper is not a tanuki";
        int k = 5;
        String[] strings = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) {
            sb.append(strings[i]);
            if (i < k - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    /**
     * description 括号是否合法
     * param
     * return
     * author zzw
     * createTime 2022/6/22 14:56
     **/
    public static Boolean legalBrackets(){
//        给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
//        有效字符串需满足：
//        左括号必须用相同类型的右括号闭合。
//        左括号必须以正确的顺序闭合
        String s = "[(()]";
        Stack<String> stack = new Stack<>();
        Map<String, String> map = new HashMap<>();
        map.put(")","(");
        map.put("]","[");
        map.put("}","{");
        char[] chars = s.toCharArray();
        for (char c : chars) {
            String tempC = String.valueOf(c);
            if (!map.containsKey(tempC)){
                stack.push(tempC);
            }else if (stack.isEmpty() || !map.get(tempC).equals(stack.pop())){
                return false;
            }
        }
        if (!stack.isEmpty()){
            return false;
        }
        return true;
    }


    public static void main(String[] args) {
        System.out.println(legalBrackets());
    }
}
