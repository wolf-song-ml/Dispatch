package com.ttd.utils;

import org.apache.commons.lang.StringUtils;

/**
 * Long型比较,防止引用类型报错
 * @author wolf
 * @since 2016-03-10
 */
public final class LongUtils {
    private static Long ZERO = new Long(0);

    public static boolean equals(final Long num1, final Long num2){
        if(num1 != null && num2 != null && num1.longValue() == num2.longValue()) {
            return true;
        }
        return false;
    }
    
    public static boolean notEquals(final Long num1, final Long num2){
        return !equals(num1, num2);
    }
    
    public static boolean greatThan(final Long num1, final Long num2){
        if(num1 != null && num2 != null && num1.longValue() > num2.longValue()) {
            return true;
        }
        return false;
    }

    public static boolean greatThanZero(final Long num1){
        if(num1 != null && num1.longValue() > ZERO.longValue()) {
            return true;
        }
        return false;
    }
    
    public static boolean greatThanEquals(final Long num1, final Long num2){
        if(num1 != null && num2 != null && num1.longValue() >= num2.longValue()) {
            return true;
        }
        return false;
    }
    
    public static boolean lessThan(final Long num1, final Long num2){
        if(num1 != null && num2 != null && num1.longValue() < num2.longValue()) {
            return true;
        }
        return false;
    }
    
    public static boolean lessThanEquals(final Long num1, final Long num2){
        if(num1 != null && num2 != null && num1.longValue() <= num2.longValue()) {
            return true;
        }
        return false;
    }
    
    public static int intValue(final Long num){
        if(num == null) {
            return 0;
        }
        return num.intValue();
    }
    
    public static Long parseLong(final String num){
        try {
            return Long.parseLong(num);
        } catch (Exception e) {
            return 0L;
        }
    }
    
    public static Long parseLong(final Object num){
        try {
            return Long.parseLong(num.toString());
        } catch (Exception e) {
            return 0L;
        }
    }

    public static long longValue(final Long num){
        if(num == null) {
            return 0;
        }
        return num.longValue();
    }
    
    public static Long[] split(String str, String separatorChars) {
        String[] taskIdList = StringUtils.split(str, separatorChars);
        if(taskIdList != null && taskIdList.length > 0) {
            Long[] arr = new Long[taskIdList.length];
            for(int i=0; i < taskIdList.length; i++) {
                arr[i] = parseLong(taskIdList[i]);
            }
            return arr;
        }
        return null;
    }
    
    // 限制数字
    public static Long limitNumber(Long number, int maxNumberLength) {
		if (number == null){ return 0L; }
			
		long maxNumber = 0;
		for (int i = 0; i < maxNumberLength; i++) {
			int a = 1;
			for (int j = 0; j < i; j++) {
				a = a * 10;
			}
			maxNumber += a * 9;
		}
		if (number > maxNumber) { return maxNumber; }
		
		return number;
	}
}
