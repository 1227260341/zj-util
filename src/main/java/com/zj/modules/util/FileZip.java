package com.zj.modules.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩/解压文件或文件夹
 *
 * @author zj
 * 
 * 2018年12月14日
 */
public class FileZip {

	
	private static void zip(ZipOutputStream out, File f, String base) throws Exception
    {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            base = (base.length() == 0 ? "" : base + "/");
            for (int i = 0; i < files.length; i++) {
                zip(out, files[i], base + files[i].getName());
            }
        } else {
            out.putNextEntry(new ZipEntry(base));
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
            int c;
            
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            in.close();
        }
    }
    
    private static void zip(File inputFileName, String zipFileName) throws Exception
    {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        zip(out, inputFileName, "");
        out.close();
    }
    
    //压缩文件，inputFileName表示要压缩的文件（可以为目录）,zipFileName表示压缩后的zip文件
    public static void zip(String inputFileName, String zipFileName) throws Exception
    {
        zip(new File(inputFileName), zipFileName);
    }
    
    //解压,zipFileName表示待解压的zip文件，unzipDir表示解压后文件存放目录
    public static void unzip(String zipFileName, String unzipDir) throws Exception
    {
        ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
        ZipEntry entry;
        while ((entry = in.getNextEntry()) != null) {
            
            String fileName = entry.getName();
           
            //有层级结构，就先创建目录
            String tmp;
            int index = fileName.lastIndexOf('/');
            if (index != -1) {
                tmp = fileName.substring(0, index);
                tmp = unzipDir + "/" + tmp;
                File f = new File(tmp);
                f.mkdirs();
            }
            
            //创建文件
            fileName = unzipDir + "/" + fileName;
            File file = new File(fileName);
            file.createNewFile();
            
            FileOutputStream out = new FileOutputStream(file);
            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
            out.close();
        }
        in.close();
    }
    
    public static void main(String[] args)
    {
        try {
            String inputFileName = "F:\\test";
            String zipFileName = "F:\\test.zip";
            zip(inputFileName, zipFileName);
            
            unzip(zipFileName, inputFileName);
 
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	
	
	
}
