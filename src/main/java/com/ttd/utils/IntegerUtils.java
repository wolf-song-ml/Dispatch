package com.ttd.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class IntegerUtils {

    public static boolean equals(Integer num1, Integer num2){
        if(num1 != null && num2 != null && num1.intValue() == num2.intValue()) {
            return true;
        }
        return false;
    }
    
    public static boolean notEquals(Integer num1, Integer num2){
        return !equals(num1, num2);
    }
    
    public static boolean greatThan(Integer num1, Integer num2){
        if(num1 != null && num2 != null && num1.intValue() > num2.intValue()) {
            return true;
        }
        return false;
    }
    
    public static boolean greatThanEquals(Integer num1, Integer num2){
        if(num1 != null && num2 != null && num1.intValue() >= num2.intValue()) {
            return true;
        }
        return false;
    }
    
    public static boolean lessThan(Integer num1, Integer num2){
        if(num1 != null && num2 != null && num1.intValue() < num2.intValue()) {
            return true;
        }
        return false;
    }
    
    public static boolean lessThanEquals(Integer num1,Integer num2){
        if(num1 != null && num2 != null && num1.intValue() <= num2.intValue()) {
            return true;
        }
        return false;
    }
    
    public static int intValue(Integer num){
        if(num == null) {
            return 0;
        }
        return num.intValue();
    }
    
    public static int parseInt(String num){
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static Integer parseInt(Object num){
        try {
            return Integer.parseInt(num.toString());
        } catch (Exception e) {
            return 0;
        }
    }
    public static int parseIntValue(Object num){
        try {
            return Integer.parseInt(num.toString());
        } catch (Exception e) {
            return 0;
        }
    }
    public static Long parseLong(Object num){
        try {
            return Long.parseLong(num.toString());
        } catch (Exception e) {
            return 0L;
        }
    }
    
    public static boolean munInArray(Integer num, Integer...numArray){
        if(num != null && numArray != null) {
        	return Sets.newHashSet(numArray).contains(num);
        }
        return false;
    }
    
    public static Integer[] split(String str, String separatorChars) {
        String[] taskIdList = StringUtils.split(str, separatorChars);
        if(taskIdList != null && taskIdList.length > 0) {
            Integer[] arr = new Integer[taskIdList.length];
            for(int i = 0; i < taskIdList.length; i++) {
                arr[i] = parseInt(taskIdList[i]);
            }
            return arr;
        }
        return null;
    }
    
    /**
	 * String to List<Integer>
	 * @param ids
	 * @return
	 */
	public static List<Integer> strToList(String ids){
		if(StringUtils.isEmpty(ids))
			return Lists.newArrayList();
		List<Integer> list = Lists.newArrayList();
		for (String str : ids.split(",")) {
			list.add(IntegerUtils.parseInt(str));
		}
		return list;
	}
	
	public static boolean greatThanZero(final Integer num1){
        if(num1 != null && num1>0) {
            return true;
        }
        return false;
    }
}
