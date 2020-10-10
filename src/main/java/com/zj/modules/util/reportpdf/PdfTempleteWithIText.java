package com.zj.modules.util.reportpdf;
import java.io.FileOutputStream;  
import java.util.HashMap;  
import java.util.Map;  
import java.util.Random;  
  
import com.itextpdf.text.pdf.AcroFields;  
import com.itextpdf.text.pdf.AcroFields.Item;  
import com.itextpdf.text.pdf.PdfReader;  
import com.itextpdf.text.pdf.PdfStamper;  
  
/** 
 *  
 * @Title: 利用PDF模板 
 * @Description: 
 * @Copyright: Copyright (c) 2014 
 * @Company: SinoSoft 
 *  
 * @author: ShaoMin 
 * @version: 1.0 
 * @CreateDate：Nov 4, 2014 
 */  
public class PdfTempleteWithIText {  
  
    /** 
     * @author ShaoMin 
     * @param args 
     */  
    public static void main(String[] args) {  
        PdfTempleteWithIText pdfTemplete = new PdfTempleteWithIText();  
  
        try {  
  
            // 1-给PDF表单域赋值  
             pdfTemplete.fillFormDatas();  
  
            // 2-给PDF表格赋值  
//            pdfTemplete.fillTableDatas();  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 获取模板表单，并赋值 固定用法 
     *  
     * @author ShaoMin 
     * @throws Exception 
     */  
    public void fillFormDatas() throws Exception {  
  
        // 1-封装的数据，这边的key与pdf模板的域名保持一致  
        Map<String, String> mMapDatas = new HashMap<String, String>();  
        mMapDatas.put("CustomerName", "SAM-SHO");// 客户姓名  
        mMapDatas.put("ContNo", "123456789098765");// 合同号  
        mMapDatas.put("ContCount", "1");// 保单个数  
        mMapDatas.put("EdorType", "CT-退保");// 保全类型  
        mMapDatas.put("GetMoney", "999.99");// 保全失算金额  
        mMapDatas.put("AcceptName", "人寿保险");// 受理人  
        mMapDatas.put("AcceptDate", "2014-11-1");// 受理日期  
  
        // 2-模板和生成的pdf  
        Random a = new Random();  
        a.nextInt();  
        String tPdfTemplateFile = "source/pdf/templete/EdorTemplete.pdf";// 获取模板路径  
        String tPdfResultFile = "temp/pdf/Edor_" + a.nextInt() + ".pdf";// 生成的文件路径  
  
        // 3-解析PDF模板  
        FileOutputStream fos = new FileOutputStream(tPdfResultFile);// 需要生成PDF  
        PdfReader reader = new PdfReader(tPdfTemplateFile);// 模板  
        PdfStamper mPdfStamper = new PdfStamper(reader, fos);// 解析  
  
        // 4-获取到模板上预定义的参数域  
        AcroFields form = mPdfStamper.getAcroFields();  
        // 获取模板中定义的变量  
        Map<String, Item> acroFieldMap = form.getFields();  
        // 循环解析模板定义的表单域  
        for (Map.Entry<String, Item> entry : acroFieldMap.entrySet()) {  
            // 获得块名  
            String fieldName = entry.getKey();  
            String fieldValue = mMapDatas.get(fieldName);// 通过名字，获取传入的参数值  
            if (!"".equals(fieldValue)) {  
                // 为模板中的变量赋值(key与pdf模板定义的域名一致)  
                form.setField(fieldName, fieldValue);  
                System.out.println(fieldName + "," + fieldValue);  
            }  
        }  
  
        // 模板中的变量赋值之后不能编辑  
        mPdfStamper.setFormFlattening(true);  
        reader.close();// 阅读器关闭,解析器暂时不关闭，因为创建动态表格还需要使用  
        mPdfStamper.close();  
    }  
  
    /** 
     * 给PDF表格赋值 值动态的，一般建议使用模板， 直接创建绝对位置的表格 
     *  
     * @author ShaoMin 
     * @throws Exception 
     */  
    public void fillTableDatas() throws Exception {  
  
        // 1-模板和生成的pdf  
  
        String tPdfTemplateFile = "source/pdf/templete/EdorTemplete.pdf";// 获取模板路径  
        String tPdfResultFile = "temp/pdf/Edor_" + new Random().nextInt() + ".pdf";// 生成的文件路径  
  
        // 2-解析PDF模板  
        FileOutputStream fos = new FileOutputStream(tPdfResultFile);// 需要生成PDF  
        PdfReader reader = new PdfReader(tPdfTemplateFile);// 模板  
        PdfStamper mPdfStamper = new PdfStamper(reader, fos);// 解析  
  
        // 3-获取到模板上预定义的参数域  
        AcroFields form = mPdfStamper.getAcroFields();  
        // 获取模板中定义的变量  
        Map<String, Item> acroFieldMap = form.getFields();  
        // 循环解析模板定义的表单域  
        int len = 4;  
        for (Map.Entry<String, Item> entry : acroFieldMap.entrySet()) {  
            // 获得块名  
            String fieldName = entry.getKey();  
            String fieldValue = "fill_" + len;  
            System.out.println(fieldName + ":" + fieldValue);  
            form.setField(fieldName, fieldValue);  
            len++;  
        }  
  
        // 模板中的变量赋值之后不能编辑  
        mPdfStamper.setFormFlattening(true);  
        reader.close();// 阅读器关闭,解析器暂时不关闭，因为创建动态表格还需要使用  
        mPdfStamper.close();  
  
    }  
  
}