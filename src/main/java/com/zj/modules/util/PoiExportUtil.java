package com.zj.modules.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;  
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;  
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;  
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject; 

/**
 * 振江通用 excel导出
 *
 * @author zj
 * 
 * 2018年8月23日
 */
public class PoiExportUtil {
	
	public static Workbook wbXLS = null;
	public static Workbook wbXLSX = null;
	public static CellStyle titleStyle = null;//自定义头部样式
	public static CellStyle contentStyle = null;//自定义内容样式
	public static short rowHeight = 500;
	public static int columnWidth  = 20*256;
	public static Map<Integer, Integer> columnWidthMap = null;//存入对应的宽度
	public static List<String> sheetNames = null;//所有sheetNames
	
	public static void init() {//初始化
		wbXLS = null;
		wbXLSX = null;
		titleStyle = null;//自定义头部样式
		contentStyle = null;//自定义内容样式
		rowHeight = 500;
		columnWidth  = 20*256;
		columnWidthMap = null;//存入对应的宽度
		sheetNames = null;
	}

	/**
	 * 入口
	 * List<CellRangeAddress> CRAs 跨行列 list
	 * 
	 * zj
	 * 2018年8月23日
	 */
	public static boolean export(String outPath, String sheetName, ArrayList<String> titleDefine, 
			ArrayList<ArrayList<Object>> datas, List<CellRangeAddress> CRAs) {
    	return exportAssemble(outPath, sheetName, titleDefine, null, datas, CRAs);
    }
	
	/**
	 * 入口
	 * List<CellRangeAddress> CRAs 跨行列 list
	 * 导出多个sheet
	 * zj
	 * 2018年8月27日
	 */
	public static boolean exportMoreSheet(String outPath, String sheetName, ArrayList<String> titleDefine, 
			ArrayList<String> titleGroupDefine, ArrayList<ArrayList<Object>> datas, List<CellRangeAddress> CRAs,
			Boolean enableStyle, Boolean eanbleCRA) {
		if (sheetName == null || sheetName.trim().equals("")) {
		    sheetName = "Sheet1";
		}
		LinkedHashMap<String, ArrayList<String>> titleDefineMap = null;
		LinkedHashMap<String, ArrayList<String>> titleGroupDefineMap = null;
		if (titleDefine != null) {
		    titleDefineMap = new LinkedHashMap<String, ArrayList<String>>();
		    titleDefineMap.put(sheetName, titleDefine);
		}
		if (titleGroupDefine != null) {
		    titleGroupDefineMap = new LinkedHashMap<String, ArrayList<String>>();
		    titleGroupDefineMap.put(sheetName, titleGroupDefine);
		}
		LinkedHashMap<String, ArrayList<ArrayList<Object>>> datasMap = new LinkedHashMap<String, ArrayList<ArrayList<Object>>>();
		if (datas != null) {
		    datasMap.put(sheetName, datas);
		}
		return dowmloadMoreSheet(outPath, titleDefineMap, titleGroupDefineMap, datasMap, CRAs, enableStyle, eanbleCRA);
    }
	
	
	
	public static boolean exportAssemble(String outPath, String sheetName, ArrayList<String> titleDefine, 
			ArrayList<String> titleGroupDefine, ArrayList<ArrayList<Object>> datas, List<CellRangeAddress> CRAs) {
			if (sheetName == null || sheetName.trim().equals("")) {
			    sheetName = "Sheet1";
			}
			LinkedHashMap<String, ArrayList<String>> titleDefineMap = null;
			LinkedHashMap<String, ArrayList<String>> titleGroupDefineMap = null;
			if (titleDefine != null) {
			    titleDefineMap = new LinkedHashMap<String, ArrayList<String>>();
			    titleDefineMap.put(sheetName, titleDefine);
			}
			if (titleGroupDefine != null) {
			    titleGroupDefineMap = new LinkedHashMap<String, ArrayList<String>>();
			    titleGroupDefineMap.put(sheetName, titleGroupDefine);
			}
			LinkedHashMap<String, ArrayList<ArrayList<Object>>> datasMap = new LinkedHashMap<String, ArrayList<ArrayList<Object>>>();
			if (datas != null) {
			    datasMap.put(sheetName, datas);
			}
			return dowmload(outPath, titleDefineMap, titleGroupDefineMap, datasMap, CRAs);
			
	}
	
	
	public static boolean dowmload(String outPath, LinkedHashMap<String, ArrayList<String>> titleDefine, 
			LinkedHashMap<String, ArrayList<String>> titleGroupDefine, LinkedHashMap<String, 
			ArrayList<ArrayList<Object>>> datas, List<CellRangeAddress> CRAs) {
		String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
		System.out.println(fileType);
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
		    wb = new HSSFWorkbook();
//		    wb = wbXLS;
		} else if (fileType.equals("xlsx")) {
		    wb = new XSSFWorkbook();
//		    wb = wbXLSX;
		} else {
		    System.out.println("您的文档格式不正确！");
		    return false;
		}
		
//		if (titleStyle == null) {//说名没有自定义样式  则使用默认样式 
			titleStyle = defaultTitleStyle(wb);
//		}
	    
//	    if (contentStyle == null) {//说名没有自定义样式  则使用默认样式 
	    	contentStyle = defaultContentStyle(wb);
//		} 
	    
		// 填充数据
		Iterator<String> tIterator = null;
		if (titleDefine != null) {
		    tIterator = titleDefine.keySet().iterator();
		} else if (datas != null) {
		    tIterator = datas.keySet().iterator();
		}
		
		if (tIterator != null) {
		    while (tIterator.hasNext()) {
				String sheetName = tIterator.next();
				
				// 创建sheet对象
				Sheet sheet = wb.createSheet(sheetName);
				sheet.setDefaultRowHeight(rowHeight);
				int rowNo = 0;
					
				if (datas != null) {
				    ArrayList<ArrayList<Object>> sheetDatas = datas.get(sheetName);
				    
				    //合并行列
				    if (CRAs != null && CRAs.size() > 0) {
				    	for (CellRangeAddress CRA : CRAs) {
				    		sheet.addMergedRegion(CRA);
				    	}
				    }
				    
				    /*sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
				    sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 2));
				    sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 6));
				    sheet.addMergedRegion(new CellRangeAddress(0, 2, 7, 7));
				    sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 8 + parentSize*2 -1));
				    for (int j = 0; j < parentSize; j ++) {
				    	int lastColumn = 8 + j*2 + 1;
				    	sheet.addMergedRegion(new CellRangeAddress(1, 1, 8 + j*2 , lastColumn));
				    }*/
				    
				    if (sheetDatas != null) {
						// 循环写入行数据
						for (int i = 0; i < sheetDatas.size(); i++) {
							
						    Row row = sheet.createRow(rowNo++);
						    row.setHeight(rowHeight);
						    ArrayList<Object> rowDatas = sheetDatas.get(i);
						    if (rowDatas != null) {
								// 循环写入列数据
								for (int j = 0; j < rowDatas.size(); j++) {
									
									sheet.setColumnWidth(j, columnWidth);
									if (columnWidthMap != null && columnWidthMap.get(j) != null && columnWidthMap.get(j) > 0) {//给上指定的列 指定的宽度
										sheet.setColumnWidth(j, columnWidthMap.get(j));
									}
											
									Cell cell = row.createCell(j);
									if(i == 0) {
								    	cell.setCellStyle(titleStyle);
								    } else {
								    	cell.setCellStyle(contentStyle);
								    }
								    Object colValue = rowDatas.get(j);
								    if (colValue == null) {
								    	continue;
								    }
								    if (colValue instanceof String) {
								    	cell.setCellValue((String) colValue);
								    } else if (colValue instanceof Double) {
								    	cell.setCellValue((Double) colValue);
								    } else if (colValue instanceof Boolean) {
								    	cell.setCellValue((Boolean) colValue);
								    } else if (colValue instanceof java.util.Date) {
								    	cell.setCellValue((java.util.Date) colValue);
								    } else if (colValue instanceof java.util.Calendar) {
								    	cell.setCellValue((java.util.Calendar) colValue);
								    } else if (colValue instanceof Double) {
								    	cell.setCellValue((Double) colValue);
								    } else if (colValue instanceof Double) {
								    	cell.setCellValue((Double) colValue);
								    } else {
								    	cell.setCellValue(colValue.toString());
								    }
								}
							}
						}
					}
				}
		    }
		}
		try {
		    File tFile = new File(outPath);
		    if (tFile.getParentFile().exists() == false) {
				System.out.println("创建目录：" + tFile.getParentFile().getAbsolutePath());
				tFile.getParentFile().mkdirs();
		    }
		    // 创建文件流
		    OutputStream stream = new FileOutputStream(outPath);
		    // 写入数据
		    wb.write(stream);
		    // 关闭文件流
			    stream.close();
		} catch (Exception e) {
	    	e.printStackTrace();
	    	return false;
		}
		return true;
	}
	
	public static boolean dowmloadMoreSheet(String outPath, LinkedHashMap<String, ArrayList<String>> titleDefine, 
			LinkedHashMap<String, ArrayList<String>> titleGroupDefine, LinkedHashMap<String, 
			ArrayList<ArrayList<Object>>> datas, List<CellRangeAddress> CRAs,
			Boolean enableStyle, Boolean eanbleCRA) {
		String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
		System.out.println(fileType);
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
//		    wb = new HSSFWorkbook();
		    wb = wbXLS;
		} else if (fileType.equals("xlsx")) {
//		    wb = new XSSFWorkbook();
		    wb = wbXLSX;
		} else {
		    System.out.println("您的文档格式不正确！");
		    return false;
		}
		
		CreationHelper createHelper = wb.getCreationHelper(); 
		
		if (enableStyle) {
//		if (titleStyle == null) {//说名没有自定义样式  则使用默认样式 
			titleStyle = defaultTitleStyle(wb);
//		}
	    
//	    if (contentStyle == null) {//说名没有自定义样式  则使用默认样式 
	    	contentStyle = defaultContentStyle(wb);
//		}  
		} else {
			titleStyle = null;
			contentStyle = null;
		}
		// 填充数据
		Iterator<String> tIterator = null;
		if (titleDefine != null) {
		    tIterator = titleDefine.keySet().iterator();
		} else if (datas != null) {
		    tIterator = datas.keySet().iterator();
		}
		
		if (tIterator != null) {
		    while (tIterator.hasNext()) {
				String sheetName = tIterator.next();
				
				// 创建sheet对象
				Sheet sheet = wb.createSheet(sheetName);
				sheet.setDefaultRowHeight(rowHeight);
				int rowNo = 0;
				
				if (datas != null) {
				    ArrayList<ArrayList<Object>> sheetDatas = datas.get(sheetName);
				    
				    //合并行列
				    if (eanbleCRA) {
					    if (CRAs != null && CRAs.size() > 0) {
					    	for (CellRangeAddress CRA : CRAs) {
					    		sheet.addMergedRegion(CRA);
					    	}
					    }
				    }
				    
				    /*sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
				    sheet.addMergedRegion(new CellRangeAddress(0, 1, 1, 2));
				    sheet.addMergedRegion(new CellRangeAddress(0, 1, 3, 6));
				    sheet.addMergedRegion(new CellRangeAddress(0, 2, 7, 7));
				    sheet.addMergedRegion(new CellRangeAddress(0, 0, 8, 8 + parentSize*2 -1));
				    for (int j = 0; j < parentSize; j ++) {
				    	int lastColumn = 8 + j*2 + 1;
				    	sheet.addMergedRegion(new CellRangeAddress(1, 1, 8 + j*2 , lastColumn));
				    }*/
				    
				    if (sheetDatas != null) {
						// 循环写入行数据
						for (int i = 0; i < sheetDatas.size(); i++) {
							
						    Row row = sheet.createRow(rowNo++);
						    row.setHeight(rowHeight);
						    ArrayList<Object> rowDatas = sheetDatas.get(i);
						    if (rowDatas != null) {
								// 循环写入列数据
								for (int j = 0; j < rowDatas.size(); j++) {
									
									sheet.setColumnWidth(j, columnWidth);
									if (columnWidthMap != null && columnWidthMap.get(j) != null && columnWidthMap.get(j) > 0) {//给上指定的列 指定的宽度
										sheet.setColumnWidth(j, columnWidthMap.get(j));
									}
											
									Cell cell = row.createCell(j);
									
									//设置上跳转sheet链接
									if (j == 0 && sheetNames != null && sheetNames.size() >= rowNo) {
										String needLinkSheetName = sheetNames.get(rowNo - 1);
										Hyperlink link2 = createHelper.createHyperlink(Hyperlink.LINK_DOCUMENT);  
									    link2.setAddress(needLinkSheetName + "!A1");  
									    cell.setHyperlink(link2);  
									}
									
									if(i == 0) {
								    	cell.setCellStyle(titleStyle);
								    } else {
								    	cell.setCellStyle(contentStyle);
								    }
								    Object colValue = rowDatas.get(j);
								    if (colValue == null) {
								    	continue;
								    }
								    if (colValue instanceof String) {
								    	cell.setCellValue((String) colValue);
								    } else if (colValue instanceof Double) {
								    	cell.setCellValue((Double) colValue);
								    } else if (colValue instanceof Boolean) {
								    	cell.setCellValue((Boolean) colValue);
								    } else if (colValue instanceof java.util.Date) {
								    	cell.setCellValue((java.util.Date) colValue);
								    } else if (colValue instanceof java.util.Calendar) {
								    	cell.setCellValue((java.util.Calendar) colValue);
								    } else if (colValue instanceof Double) {
								    	cell.setCellValue((Double) colValue);
								    } else if (colValue instanceof Double) {
								    	cell.setCellValue((Double) colValue);
								    } else {
								    	cell.setCellValue(colValue.toString());
								    }
								}
							}
						}
					}
				}
		    }
		}
		try {
		    File tFile = new File(outPath);
		    if (tFile.getParentFile().exists() == false) {
				System.out.println("创建目录：" + tFile.getParentFile().getAbsolutePath());
				tFile.getParentFile().mkdirs();
		    }
		    // 创建文件流
		    OutputStream stream = new FileOutputStream(outPath);
		    // 写入数据
		    wb.write(stream);
		    // 关闭文件流
			    stream.close();
		} catch (Exception e) {
	    	e.printStackTrace();
	    	return false;
		}
		return true;
	}
	
	/**
	 * 设置默认头部样式
	 * zj
	 * 2018年8月23日
	 */
	public static CellStyle defaultTitleStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		// 创建一个居中格式  
		style.setAlignment(HorizontalAlignment.CENTER); 
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		
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
		
	    Font font = wb.createFont();  
	    font.setFontHeightInPoints((short) 14);  
	    font.setFontName("微软雅黑"); 
	    font.setBold(true);
	    //font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    style.setFont(font);
	    
	    return style;
	}
	
	/**
	 * 设置默认内容样式
	 * zj
	 * 2018年8月23日
	 */
	public static CellStyle defaultContentStyle(Workbook wb) {
		CellStyle style2 = wb.createCellStyle();
		// 创建一个居中格式  
		style2.setAlignment(HorizontalAlignment.CENTER); 
		style2.setVerticalAlignment(VerticalAlignment.CENTER);
		
		// 设置边框样式
		style2.setBorderTop(BorderStyle.THIN);
		style2.setBorderBottom(BorderStyle.THIN);
		style2.setBorderLeft(BorderStyle.THIN);
		style2.setBorderRight(BorderStyle.THIN);
		// 设置边框颜色
		style2.setTopBorderColor(HSSFColor.BLACK.index);
		style2.setBottomBorderColor(HSSFColor.BLACK.index);
		style2.setLeftBorderColor(HSSFColor.BLACK.index);
		style2.setRightBorderColor(HSSFColor.BLACK.index);
		
	    Font font2 = wb.createFont();  
	    font2.setFontHeightInPoints((short) 11);  
	    font2.setFontName("微软雅黑"); 
	    //font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    style2.setFont(font2);
	    
	    return style2;
	}
	
	
	public static void setWBXLS() {
		wbXLS = new HSSFWorkbook();
		
	}
	
	public static void setWBXLSX() {
		wbXLSX = new XSSFWorkbook();
	}
	
	@SuppressWarnings("unchecked")
    public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
		    Object v = json.get(k);
		    // 如果内层还是数组的话，继续解析
		    if (v instanceof JSONArray) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Iterator<JSONObject> it = ((JSONArray) v).iterator();
			while (it.hasNext()) {
			    JSONObject json2 = it.next();
			    list.add(parseJSON2Map(json2.toString()));
			}
			map.put(k.toString(), list);
		    } else {
			map.put(k.toString(), v);
		    }
		}
		return map;
    }

}
