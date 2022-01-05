package com.cloudapp.web.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;

public abstract class CommonUtil {
	
	public static Date getDate(String str){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");        
		Date date = null;    
		if(str != "" && str != null){
			try {    
				date = format.parse(str);   
			} catch (ParseException e) {   
			    e.printStackTrace();    
			}
		}
		return date;
	}
	
	
	/**
     * 把单元格内的类型转换至String类型
     */
    @SuppressWarnings("deprecation")
    public static String ConvertCellStr(Cell cell) {
    	String cellStr = "";
        switch (cell.getCellType()) {

        case Cell.CELL_TYPE_STRING:
            // 读取String
            cellStr = cell.getStringCellValue().toString();
            break;

        case Cell.CELL_TYPE_BOOLEAN:
            // 得到Boolean对象的方法
            cellStr = String.valueOf(cell.getBooleanCellValue());
            break;

        case Cell.CELL_TYPE_NUMERIC:

            // 先看是否是日期格式
            if (DateUtil.isCellDateFormatted(cell)) {

                // 读取日期格式
                Date date = cell.getDateCellValue();
				DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
				cellStr= formater.format(date);
            } else {
                // 读取数字
                cellStr = String.valueOf(cell.getNumericCellValue());
            }
            break;

        case Cell.CELL_TYPE_FORMULA:
            // 读取公式
            cellStr = cell.getCellFormula().toString();
            break;
        }
        return cellStr;
    }
}
