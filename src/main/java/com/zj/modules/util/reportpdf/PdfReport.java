package com.zj.modules.util.reportpdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.util.StringUtils;

/**
 * 导出pdf基础工具类
 * 
 * @version 2020-9-1810:53:43
 * @author zhouzj
 */
public class PdfReport {
    
    // main测试
    public static void main(String[] args) throws Exception {
        try {
            // 1.新建document对象
            Document document = new Document(PageSize.A4);// 建立一个Document对象
            
            // 2.建立一个书写器(Writer)与document对象关联
            File file = new File("D:\\PDFDemo.pdf");
            file.createNewFile();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
            writer.setPageEvent(new Watermark("HELLO ITEXTPDF"));// 水印
            writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚
            
            // 3.打开文档
            document.open();
            document.addTitle("Title@PDF-Java");// 标题
            document.addAuthor("Author@umiz");// 作者
            document.addSubject("Subject@iText pdf sample");// 主题
            document.addKeywords("Keywords@iTextpdf");// 关键字
            document.addCreator("Creator@umiz`s");// 创建者
            
            // 4.向文档中添加内容
            new PdfReport().generatePDF(document);
            
            // 5.关闭文档
            document.close();
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 定义全局的字体静态变量
    public static Font titlefont;
    public static Font headfont;
    public static Font keyfont;
    public static Font textfont;
    public static Font bigTextfont;
    // 最大宽度
    private static int maxWidth = 520;
    // 静态代码块
    static {
        try {
            // 不同字体（这里定义为同一种字体：包含不同字号、不同style）
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            titlefont = new Font(bfChinese, 16, Font.BOLD);
            headfont = new Font(bfChinese, 14, Font.BOLD);
            keyfont = new Font(bfChinese, 10, Font.BOLD);
            textfont = new Font(bfChinese, 10, Font.NORMAL);
            bigTextfont = new Font(bfChinese, 16, Font.NORMAL);
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 生成PDF文件
    public void generatePDF(Document document) throws Exception {
        
        // 段落
        Paragraph paragraph = new Paragraph("美好的一天从早起开始！", titlefont);
        paragraph.setAlignment(1); // 设置文字居中 0靠左 1，居中 2，靠右
        paragraph.setIndentationLeft(12); // 设置左缩进
        paragraph.setIndentationRight(12); // 设置右缩进
        paragraph.setFirstLineIndent(24); // 设置首行缩进
        paragraph.setLeading(20f); // 行间距
        paragraph.setSpacingBefore(5f); // 设置段落上空白
        paragraph.setSpacingAfter(10f); // 设置段落下空白
        
        // 直线
        Paragraph p1 = new Paragraph();
        p1.add(new Chunk(new LineSeparator()));
        
        // 点线
        Paragraph p2 = new Paragraph();
        p2.add(new Chunk(new DottedLineSeparator()));
        
        // 超链接
        Anchor anchor = new Anchor("baidu");
        anchor.setReference("www.baidu.com");
        
        // 定位
        Anchor gotoP = new Anchor("goto");
        gotoP.setReference("#top");
        
        // 添加图片
        Image image = Image.getInstance(
                "https://img-blog.csdn.net/20180801174617455?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNzg0ODcxMA==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70");
        image.setAlignment(Image.ALIGN_CENTER);
        image.scalePercent(40); // 依照比例缩放
        
        // 表格
        PdfPTable table = createTable(new float[] { 40, 120, 120, 120, 80, 80 });
        table.addCell(createCell("美好的一天", headfont, Element.ALIGN_LEFT, 6, false));
        table.addCell(createCell("早上9:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("中午11:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("中午13:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("下午15:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("下午17:00", keyfont, Element.ALIGN_CENTER));
        table.addCell(createCell("晚上19:00", keyfont, Element.ALIGN_CENTER));
        Integer totalQuantity = 0;
        for (int i = 0; i < 5; i++) {
            table.addCell(createCell("起床", textfont));
            table.addCell(createCell("吃午饭", textfont));
            table.addCell(createCell("午休", textfont));
            table.addCell(createCell("下午茶", textfont));
            table.addCell(createCell("回家", textfont));
            table.addCell(createCell("吃晚饭", textfont));
            totalQuantity++;
        }
        table.addCell(createCell("总计", keyfont));
        table.addCell(createCell("", textfont));
        table.addCell(createCell("", textfont));
        table.addCell(createCell("", textfont));
        table.addCell(createCell(String.valueOf(totalQuantity) + "件事", textfont));
        table.addCell(createCell("", textfont));
        
        document.add(paragraph);
        document.add(anchor);
        document.add(p2);
        document.add(gotoP);
        document.add(p1);
        document.add(table);
        document.add(image);
    }
    
    /** ------------------------创建表格单元格的方法start---------------------------- */
    /**
     * 创建单元格(指定字体)
     * 
     * @param value
     * @param font
     * @return
     */
    public static PdfPCell createCell(String value, Font font) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    
    /**
     * 创建单元格（指定字体、水平..）
     * 
     * @param value
     * @param font
     * @param align
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并）
     * 
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, int colspan) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }
    
    /**
     * 创建单元格（指定字体、水平居..、单元格跨x列合并、设置单元格内边距）
     * 
     * @param value
     * @param font
     * @param align
     * @param colspan
     * @param boderFlag
     * @return
     */
    public static PdfPCell createCell(String value, Font font, int align, int colspan, boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setColspan(colspan);
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(3.0f);
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        else if (boderFlag) {
//            cell.setBorder(0);
            cell.setPaddingTop(0.0f);
            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }
    
    public static PdfPCell createCellWithCustom(String value, Font font, Integer horizontal) {
        return createCellWithCustom(value, font, horizontal, null, null, null, null, null, null);
    }
    
    public static PdfPCell createCellWithCustom(String value, Font font, Integer horizontal, Integer colspan, Integer  rowspan, Float fixedHeight) {
        return createCellWithCustom(value, font, horizontal, null, colspan, rowspan, fixedHeight, null, null);
    }
    
    /**
     * 自定义表格cell
     * @version 2020-9-1814:03:35
     * @author zhouzj
     * @param value
     * @param font
     * @param horizontal 水平方式
     * @param vertical  垂直样式
     * @param colspan 跨列
     * @param rowspan 跨行
     * @param height 高度
     * @param padding 填充
     * @param boderFlag 是否需要边框， true 需要， false 不需要
     * @return
     */
    public static PdfPCell createCellWithCustom(String value, Font font, Integer horizontal, Integer vertical, Integer colspan, Integer  rowspan, Float fixedHeight, Float padding, Boolean boderFlag) {
        PdfPCell cell = new PdfPCell();
        if (horizontal == null) {
            horizontal = Element.ALIGN_CENTER;//设置垂直居中
        }
        if (vertical == null) {
            vertical = Element.ALIGN_MIDDLE;
        }
        
        if (colspan != null) {
            cell.setColspan(colspan);
        }
        if (rowspan != null) {
            cell.setRowspan(rowspan);
        }
        if (fixedHeight != null) {
            cell.setFixedHeight(fixedHeight);
        }
        if (padding == null ) {
            padding = 3.0f;
        }
        if (boderFlag == null) {
            boderFlag = true;
        }
        if (font == null) {
            font = textfont;
        }
        cell.setVerticalAlignment(vertical);
        cell.setHorizontalAlignment(horizontal);//设置水平方式
        cell.setPhrase(new Phrase(value, font));
        cell.setPadding(padding);
        
        if (!boderFlag) {
            cell.setBorder(0);
            cell.setPaddingTop(15.0f);
            cell.setPaddingBottom(8.0f);
        }
        else if (boderFlag) {
//            cell.setBorder(0);
//            cell.setPaddingTop(0.0f);
//            cell.setPaddingBottom(15.0f);
        }
        return cell;
    }
    
    /**
     * 创建单元格（指定字体、水平..、边框宽度：0表示无边框、内边距）
     * 
     * @param value
     * @param font
     * @param align
     * @param borderWidth
     * @param paddingSize
     * @param flag
     * @return
     */
    public PdfPCell createCell(String value, Font font, int align, float[] borderWidth, float[] paddingSize,
            boolean flag) {
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        cell.setBorderWidthLeft(borderWidth[0]);
        cell.setBorderWidthRight(borderWidth[1]);
        cell.setBorderWidthTop(borderWidth[2]);
        cell.setBorderWidthBottom(borderWidth[3]);
        cell.setPaddingTop(paddingSize[0]);
        cell.setPaddingBottom(paddingSize[1]);
        if (flag) {
            cell.setColspan(2);
        }
        return cell;
    }
    
    /** ------------------------创建表格单元格的方法end---------------------------- */
    
    /** --------------------------创建表格的方法start------------------- --------- */
    /**
     * 创建默认列宽，指定列数、水平(居中、右、左)的表格
     * 
     * @param colNumber
     * @param align
     * @return
     */
    public PdfPTable createTable(int colNumber, int align) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(align);
            table.getDefaultCell().setBorder(1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    
    /**
     * 创建指定列宽、列数的表格
     * 
     * @param widths
     * @return
     */
    public static PdfPTable createTable(float[] widths) {
        PdfPTable table = new PdfPTable(widths);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }
    
    /**
     * 创建空白的表格
     * 
     * @return
     */
    public PdfPTable createBlankTable() {
        PdfPTable table = new PdfPTable(1);
        table.getDefaultCell().setBorder(0);
        table.addCell(createCell("", keyfont));
        table.setSpacingAfter(20.0f);
        table.setSpacingBefore(20.0f);
        return table;
    }
    
    /** --------------------------创建表格的方法end------------------- --------- */
    
    public static Document getDocument(String filePath, String watermarkStr) throws Exception{
        // 1.新建document对象
        Document document = new Document(PageSize.A4);// 建立一个Document对象

        // 2.建立一个书写器(Writer)与document对象关联
        File file = new File(filePath);
        file.createNewFile();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        if (!StringUtils.isEmpty(watermarkStr)) {
            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Watermark watermark2 = new Watermark(watermarkStr);
            watermark2.FONT = new Font(bfChinese, 30, Font.BOLD, new GrayColor(0.95f));
            writer.setPageEvent(watermark2);// 水印
        }
//        writer.setPageEvent(new MyHeaderFooter());// 页眉/页脚

        // 3.打开文档
        document.open();
        document.addTitle("Title@PDF-Java");// 标题
        document.addAuthor("Author@zj");// 作者
        document.addSubject("Subject@iText pdf sample");// 主题
        document.addKeywords("Keywords@iTextpdf");// 关键字
        document.addCreator("Creator@zj");// 创建者
        
        return document;
    }
    
}