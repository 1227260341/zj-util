package com.zj.modules.util;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import cn.afterturn.easypoi.excel.export.styler.AbstractExcelExportStyler;
import cn.afterturn.easypoi.excel.export.styler.IExcelExportStyler;

/**
 * 样式的默认实现
 * @author zj
 *  2018年8月31日 下午5:36:08
 */
public class ExcelExportStylerDefaultImpl extends AbstractExcelExportStyler
                                          implements IExcelExportStyler {

    public ExcelExportStylerDefaultImpl(Workbook workbook) {
        super.createStyles(workbook);
    }

    @Override
    public CellStyle getTitleStyle(short color) {
        CellStyle titleStyle = workbook.createCellStyle();
//        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
//        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        titleStyle.setWrapText(true);
    	// 创建一个居中格式  
        titleStyle.setAlignment(HorizontalAlignment.CENTER); 
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
 		
 		// 设置边框样式
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
 		// 设置边框颜色
        titleStyle.setTopBorderColor(HSSFColor.BLACK.index);
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        titleStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        titleStyle.setRightBorderColor(HSSFColor.BLACK.index);
 		
// 	    Font font = workbook.createFont();  
// 	    font.setFontHeightInPoints((short) 14);  
// 	    font.setFontName("微软雅黑"); 
// 	    font.setBold(true);
// 	    //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
// 	    titleStyle.setFont(font);
        return titleStyle;
    }

    /**
     * zj 复数行样式
     */
    @Override
    public CellStyle stringSeptailStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            style.setWrapText(true);
        }
        // 设置边框样式
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
 		// 设置边框颜色
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        return style;
    }

    /**
     * 标题样式
     */
    @Override
    public CellStyle getHeaderStyle(short color) {
        CellStyle titleStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 14);  
        font.setFontName("微软雅黑"); 
        font.setBold(true);
        titleStyle.setFont(font);
        titleStyle.setAlignment(HorizontalAlignment.CENTER); 
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
//        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
//        titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 设置边框样式
        titleStyle.setBorderTop(BorderStyle.THIN);
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
 		// 设置边框颜色
        titleStyle.setTopBorderColor(HSSFColor.BLACK.index);
        titleStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        titleStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        titleStyle.setRightBorderColor(HSSFColor.BLACK.index);
        
// 	    Font font = workbook.createFont();  
// 	    font.setFontHeightInPoints((short) 14);  
// 	    font.setFontName("微软雅黑"); 
// 	    font.setBold(true);
// 	    //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
// 	    titleStyle.setFont(font);
        return titleStyle;
    }

    /**
     * 单数行样式
     */
    @Override
    public CellStyle stringNoneStyle(Workbook workbook, boolean isWarp) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setDataFormat(STRING_FORMAT);
        if (isWarp) {
            style.setWrapText(true);
        }
        // 设置边框样式
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
 		// 设置边框颜色
        style.setTopBorderColor(HSSFColor.BLACK.index);
        style.setBottomBorderColor(HSSFColor.BLACK.index);
        style.setLeftBorderColor(HSSFColor.BLACK.index);
        style.setRightBorderColor(HSSFColor.BLACK.index);
        return style;
    }
    
    @Override
    public CellStyle getStyles(boolean noneStyler, ExcelExportEntity entity) {
    	// TODO Auto-generated method stub
    	return super.getStyles(noneStyler, entity);
    }
    
    @Override
    public CellStyle getStyles(Cell cell, int dataRow, ExcelExportEntity entity, Object obj, Object data) {
    	// TODO Auto-generated method stub
    	return super.getStyles(cell, dataRow, entity, obj, data);
    }

}
