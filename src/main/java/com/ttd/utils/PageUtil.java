package com.ttd.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;

/**
 * Page工具类
 */
public class PageUtil {
    
    public static String dateFormat(Date date) {
        return dateFormat(date,"yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期格式化
     * @param date
     * @param format
     * @return
     */
    public static String dateFormat(Date date,String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 字符串转换成时间
     * @param date
     * @param format
     * @return
     */
    public static Date stringToDate(String date ,String format){
    	 try {
    		 if(StringUtils.isBlank(format)){
    			 format = "yyyy-MM-dd HH:mm:ss";
    		 }
             SimpleDateFormat sdf = new SimpleDateFormat(format);
             return sdf.parse(date);
         } catch (Exception e) {
             return null;
         }
    }
    
    /**
     * 格式化显示当前日期
     * @param format
     * @return
     */
    public static String dateFormat(String format) {
        if (format != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(getServerDate());
        }
        return null;
    }
    
    /**
     * 计算两个时间段之间的所耗时间
     * @param date 开始时间
     * @param date2 结束时间
     * @return 所消耗时间段的字符串
     */
    public static String getRunningTime(Date date,Date date2) {
        if(date == null || date2 == null) {
            return null;
        }
        long mss = date2.getTime() - date.getTime();
        return getRunnigtTime(mss);
    }
    
    public static String getRunnigtTime(Long mss){
        try {
            long days = mss / (1000 * 60 * 60 * 24);
            long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (mss % (1000 * 60)) / 1000;
            StringBuffer str = new StringBuffer();
            if (days > 0) {
                str.append(days).append("天,");
            }
            if (hours < 10) {
                str.append("0");
            }
            str.append(hours).append(":");
            if (minutes < 10) {
                str.append("0");
            }
            str.append(minutes).append(":");
            if (seconds < 10) {
                str.append("0");
            }
            return str.append(seconds).toString();
        } catch (Exception e) {
            return "";
        }
    }
    
    /**
     * 服务器当前日期
     * @return
     */
    public static Date getServerDate() {
        return new Date();
    }
    
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 时间格式化， 传入毫秒
     * @param time
     * @return
     */
    public static String dateFormat(long time) {
    	if(time <= 0){return null;}
        return dateFormat(new Date(time), "yyyy-MM-dd HH:mm:ss");
    }
    
    public static String jsonFormat(Object json) {
    	try {
    		if(json != null) {
    			return JSON.toJSONString(json, true);
    		}
		} catch (Exception e) {
			
		}
    	return "";
    }
    
    /**
     * 获得文件大小
     * @param size
     * @return B/KB/MB/GB/TB
     */
    public static String fileSize(long size) {
        return StringUtils.join(fileSizeArray(size));
    }

    /**
     * [整数大小,单位]
     * @param size
     * @return
     */
    public static Object[] fileSizeArray(long size) {
        try {
        	BigDecimal longs = new BigDecimal(String.valueOf(size));
            if (size >= 0 && size < 1024L) {
                return new Object[]{size,"B"};
            } else if (size >= 1024L && size < 1024L * 1024L) {
            	BigDecimal sizeB = new BigDecimal(1024L);
            	return new Object[]{longs.divide(sizeB, 2, BigDecimal.ROUND_HALF_UP).toString(),"KB"};
            } else if (size >= 1024L * 1024L && size < 1024L * 1024L * 1024L) {
            	BigDecimal sizeKB = new BigDecimal(1024L * 1024L);
            	return new Object[]{longs.divide(sizeKB, 2, BigDecimal.ROUND_HALF_UP).toString(),"MB"};
            } else if (size >= 1024L * 1024L * 1024L && size < 1024L * 1024L * 1024L * 1024L) {
                BigDecimal sizeMB = new BigDecimal(1024L * 1024L * 1024L);
                return new Object[]{longs.divide(sizeMB, 2, BigDecimal.ROUND_HALF_UP).toString(),"GB"};
            } else {
                BigDecimal sizeGB = new BigDecimal(1024L * 1024L * 1024L * 1024L);
                return new Object[]{longs.divide(sizeGB, 2, BigDecimal.ROUND_HALF_UP).toString(),"TB"};
            }
        } catch (Exception e) {
            return new Object[]{0,"B"};
        }
    }
    
    /**
     * 获取文件MD5值
     * @param path 文件的绝对路径
     * @return
     */
    public static String fileMD5(String path) {
        try {
            InputStream fis = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return new BigInteger(1, md5.digest()).toString(16).toUpperCase();
        } catch (Exception e) {
            return null;
        }
    }
    
    private static String[] WEEK_DAY = {"日","一","二","三","四","五","六"};
    public static String getWeekDay(String version){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Calendar cal = Calendar.getInstance();
            cal.setTime(sdf.parse(version));//版本创建时间
            if(cal.getTimeInMillis() <= 0) {return "";}//无效日期
            int weekYear = cal.get(Calendar.WEEK_OF_YEAR);//周数
            int weekDay = cal.get(Calendar.DAY_OF_WEEK);//星期几
            StringBuffer str = new StringBuffer();
            str.append(",[第").append(weekYear).append("周,星期").append(WEEK_DAY[weekDay-1]).append("]");
            return str.toString();
        } catch (Exception e) {
            return "";
        }
    }
   
    public static String divide(int x,int y){
        String res = "0";
        try {
            if(x <= 0 || y <=0) {
                return res;
            }
            BigDecimal i = new BigDecimal(x);
            BigDecimal j = new BigDecimal(y);
            res = i.divide(j, 1, BigDecimal.ROUND_FLOOR).toString();
        } catch (Exception e) {
            return res;
        }
        return res.replace(".0", "");
    }
    
    public static long divideSpeed(long fileSize,long cost){
    	long second = cost / 1000 == 0 ? 1 : cost / 1000;//秒
        return divideLong(fileSize,second);
    }
    
    public static long divideLong(long x,long y){
    	if(x <= 0 || y <=0) {return 0;}
    	BigDecimal i = new BigDecimal(x);
        BigDecimal j = new BigDecimal(y);
        return i.divide(j, 1, BigDecimal.ROUND_FLOOR).longValue();
    }
    
    public static String dividePercent(int x,int y){
        String res = "0";
        try {
            if(x <= 0 || y <=0) {
                return res;
            }
            BigDecimal i = new BigDecimal(x*100);
            BigDecimal j = new BigDecimal(y);
            res = i.divide(j, 1, BigDecimal.ROUND_FLOOR).toString();
        } catch (Exception e) {
            return res;
        }
        return res.replace(".0", "");
    }
    
    public static String dividePercent(Integer x, Integer y){
        String res = "0";
        if(x == null || y == null){
        	return res;
        }
        return dividePercent(x.intValue(), y.intValue());
    }
    
    public static int dividePercent2(int x,int y){
        int res = 0;
        try {
            if(x <= 0 || y <=0) {
                return res;
            }
            BigDecimal i = new BigDecimal(x*100);
            BigDecimal j = new BigDecimal(y);
            res = i.divide(j, 1, BigDecimal.ROUND_FLOOR).intValue();
        } catch (Exception e) {
            return res;
        }
        return res;
    }
    
    public static boolean taskOutDate(Date date) {
        boolean res = false;
        try {
            long ct = date.getTime();
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);//1月以前
            return cal.getTimeInMillis() > ct;
        } catch (Exception e) {
            ;
        }
        return res;
    }
    
    /**
     * 判断文件类型
     * @param fileName
     * @return
     */
    public static String textType(String fileName){
        String type ="text/plain";
        try{
            String suffix = StringUtils.substring(fileName, fileName.lastIndexOf("."));
            if(Sets.newHashSet(".properties",".ini",".conf").contains(suffix)) {
                type ="text/x-properties";
            } else if(Sets.newHashSet(".html",".xml",".json",".vm").contains(suffix)) {
                type ="application/xml";
            } else if(Sets.newHashSet(".sh",".bat").contains(suffix)) {
                type ="text/x-sh";
            } else if(Sets.newHashSet(".java").contains(suffix)) {
                type ="text/x-java";
            } else if(Sets.newHashSet(".php").contains(suffix)) {
                type ="application/x-httpd-php";
            } else if(Sets.newHashSet(".py").contains(suffix)) {
                type ="text/x-python";
            } else if(Sets.newHashSet(".c",".cpp").contains(suffix)) {
                type ="text/x-csrc";
            }
        } catch(Exception ex){
            
        }
        return type;
    }
    
    //0:删除后的字符串,1:被删除的字符串
    public static String[] strDelete(String str,String start,String end) {
    	String[] newStr = new String[2];
    	newStr[1] = StringUtils.substringBetween(str, start, end);
    	newStr[0] = StringUtils.remove(str, start+newStr[1]+end);
    	return newStr;
    }

    /**
     * 转换为html
     * @param text
     * @return
     */
    public static String escapeHtml(Object text) {
        if (!(text instanceof String)) {
            return text.toString();
        }
        StringBuffer str = new StringBuffer();
        char[] cs = text.toString().toCharArray();
        for (char c : cs) {
            if (c == '>') {
                str.append("&gt;");
            } else if (c == '<') {
                str.append("&lt;");
            } else if (c == '&') {
                str.append("&amp;");
            } else {
                str.append(c);
            }
        }
        return str.toString();
    }
}
