package com.ttd.utils;

import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * Excel
 * @author wolf
 * @since 2016-03-10
 */
public class ExcelUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelUtils.class);
    
    private static Object getValue(HSSFCell cell) {
        Object value = null;
        try {
            if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
                value = cell.getBooleanCellValue();
            } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                value = cell.getNumericCellValue();
                if(HSSFDateUtil.isCellDateFormatted(cell)) {//日期类型
                    value = HSSFDateUtil.getJavaDate((Double)value); 
                }
            } else {
                value = cell.getStringCellValue();
                if(value != null) {
                    value = value.toString().trim();//去空格
                }
            }
        } catch (Exception e) {
            LOG.warn("获取cell值失败",e.getMessage());
        }
        return value;
    }

    /**
     * 读取Excel,Object[boolean,Date,double,String]
     * @param path
     * @return
     */
    public static List<List<Object>> readExcel(String path) {
        File file = new File(path);
        if (file.exists() && file.getName().toLowerCase().endsWith(".xls")) {
            try {
                return readExcel(new FileInputStream(file));
            } catch (Exception e) {
                LOG.error("读取excel文件异常", e);
                return null;
            }
        }
        return null;
    }
    
    /**
     * 读取Excel,Object[boolean,Date,double,String]
     * @param in
     * @return
     */
    public static List<List<Object>> readExcel(InputStream in) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            int sheetCount = workbook.getNumberOfSheets();
            HSSFSheet firstSheet = null;
            if (sheetCount <= 0 || (firstSheet = workbook.getSheetAt(0)) == null) {
                return null;
            }
            int rowCount = firstSheet.getLastRowNum();
            int firstCellCount = 0;
            List<List<Object>> rowValue = Lists.newArrayList();
            for (int i = 0; i <= rowCount; i++) {
                HSSFRow row = firstSheet.getRow(i);
                if (row == null) {
                    continue;
                }
                int cellCount = row.getLastCellNum();
                if (i == 0) {// 第一行列数,默认为表头
                    firstCellCount = cellCount;
                }
                if (cellCount != firstCellCount) {
                    continue;// 与表头列数不对应跳过该行
                }
                List<Object> cellValue = Lists.newArrayList();
                for (int j = 0; j < cellCount; j++) {
                    HSSFCell cell = row.getCell(j);
                    if (cell == null) {
                        continue;
                    }
                    Object value = getValue(cell);
                    if (value == null || "".equals(value)) {
                        continue;
                    }
                    cellValue.add(value);
                }
                if (cellCount == cellValue.size()) {
                    rowValue.add(cellValue);
                }
            }
            return rowValue;
        } catch (Exception e) {
            LOG.error("读取excel异常", e);
        }
        return null;
    }
    
    /**
     * 除去表头 读取Excel,Object[boolean,Date,double,String]
     * @param in
     * @param accessNull 是否允许行中有空的单元格,允许有空格时单元格的值返回""
     * @return
     */
    public static List<List<Object>> readExcelExceptHead(InputStream in, boolean accessNull) {
        return readExcelExceptHead(in, accessNull, 0);
    }

    /**
     * 除去表头， 并指定除去表头后跳过行数
     * @param in
     * @param accessNull
     * @param beginRow
     * @return
     */
    public static List<List<Object>> readExcelExceptHead(InputStream in, boolean accessNull, int beginRow){
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            int sheetCount = workbook.getNumberOfSheets();
            HSSFSheet firstSheet = null;
            if (sheetCount <= 0 || (firstSheet = workbook.getSheetAt(0)) == null) {
                return null;
            }
            int rowCount = firstSheet.getLastRowNum();
            int firstCellCount = 0;
            List<List<Object>> rowValue = Lists.newArrayList();
            for (int i = beginRow; i <= rowCount; i++) {
                HSSFRow row = firstSheet.getRow(i);
                if (row == null) {
                    continue;
                }
                int cellCount = row.getLastCellNum();
                if (i == beginRow) {
                    firstCellCount = cellCount;
                    continue;
                }
                List<Object> cellValue = Lists.newArrayList();
                boolean flag = false;
                for (int j = 0; j < firstCellCount; j++) {
                    HSSFCell cell = row.getCell(j);
                    Object value = null;
                    if (cell == null) {
                        if(!accessNull){
                            continue;
                        }else{
                            value = "";
                        }
                    }else{
                        value = changeCellToString(cell);
                        if (value == null || "".equals(value)) {
                            if(!accessNull){
                                continue;
                            }else{
                                value = "";
                            }
                        }else{
                            flag = true;
                        }
                    }
                    cellValue.add(value);
                }
                if(!accessNull){
                    if (firstCellCount == cellValue.size()) {
                        rowValue.add(cellValue);
                    }
                }else{
                    if(flag){
                        rowValue.add(cellValue);
                    }
                }
            }
            return rowValue;
        } catch (Exception e) {
            LOG.error("读取excel异常", e);
        }
        return null;
    }
    
	/**
	 * 报表下载功能
	 * @param out
	 * @param title (为excel表的表名)
	 * @param heads (为excel表的字段名称)
	 * @param data (为excel的数据内容)
	 * @param keys (为map中存放数据的key值)
	 */
	public static void downloadExcel(OutputStream out,String title,String[] heads,List<Map<String,Object>> data,String[] keys) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(title);
		HSSFRow row = sheet.createRow((int) 0);
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		HSSFFont font = wb.createFont();
		font.setFontName("黑体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 10);//设置字体大小
		style.setFont(font);
		HSSFCell cell = null;
		for(int i = 0;i<heads.length;i++) {
			cell = row.createCell(i);
			cell.setCellValue(heads[i]);cell.setCellStyle(style);
		}
		for(int i=0;i<data.size();i++) {
			row = sheet.createRow((int)i+1);
			for(int j=0;j<heads.length;j++) {
				if(data.get(i).get(keys[j]) == null) data.get(i).put(keys[j], "");
				row.createCell(j).setCellValue((String)data.get(i).get(keys[j]).toString());
			}
		}
		try{   
			wb.write(out);
            out.flush();         
            out.close();
		}catch (Exception e){
		   LOG.warn("导出Excel时发生异常：", e);
		}
	}
    
	private static String changeCellToString(HSSFCell cell) {
		String returnValue = "";
		if (null != cell) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				Double doubleValue = cell.getNumericCellValue();
				String str = String.valueOf(doubleValue);
				if (str.contains("E")) {
					Double subStr = Double.parseDouble(str);
					str = new DecimalFormat("#").format(subStr);
				}			
				if (str.contains(".0")) {
					str = str.replace(".0", "");
				}
				returnValue = str;
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				returnValue = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔
				Boolean booleanValue = cell.getBooleanCellValue();
				returnValue = booleanValue.toString();
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				returnValue = "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				returnValue = cell.getCellFormula();
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				returnValue = "";
				break;
			default:
				System.out.println("未知类型");
				break;
			}
		}
		return returnValue.trim();
	}
    
}
