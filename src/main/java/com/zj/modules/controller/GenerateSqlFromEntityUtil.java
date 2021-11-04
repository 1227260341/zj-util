package com.zj.modules.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlElement;

import com.zj.modules.payment.entity.ServOrder;

/**
 * 根据实体生成对应的 表
 * @author zzj
 *
 */
public class GenerateSqlFromEntityUtil {

	public static void main(String[] a) {
		Class[] classs = {
//				TlAddressEntity.class, TlCommentEntity.class, TlCustomerEntity.class, TlHardinfoEntity.class, TlOrderInfoEntity.class,
//				TlHotelEntity.class, TlResourceCommentEntity.class, TlHotelTypeEntity.class, TlResourceCustomerEntity.class,
//				TlResourceHotelEntity.class, TlMeetingPlaceEntity.class, TlResourceMeetingEntity.class, TlResourceRoomTypeEntity.class,
//				TlResourceUserEntity.class, TlRoleEntity.class, TlRoomEntity.class, TlRoomTypeDictionaryEntity.class, TlRoomTypeEntity.class,
//				TlUsersEntity.class, TlVersionEntity.class
				};
		
		// 实体类的位置
		Class klass = ServOrder.class;
		// 生成的sql语句的位置
		String outputPath = "E:/out/outSql/tlhouserent.sql";
		for (int i = 0; i < classs.length; i ++) {
			generateTableSql(classs[i], outputPath, null);
		}
//		generateTableSql(klass, outputPath, null);
		System.out.println("生成结束");
	}

	public static void writeFile(String content, String outputPath) {
		File file = new File(outputPath);
		System.out.println("文件路径: " + file.getAbsolutePath());
		// 输出文件的路径
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter out = null;

		try {
			// 如果文件存在，就删除
			if (file.exists()) {
//				file.delete();
			}
			file.createNewFile();
			fos = new FileOutputStream(file, true);
			osw = new OutputStreamWriter(fos);
			out = new BufferedWriter(osw);
			out.write(content);
			// 清空缓冲流，把缓冲流里的文本数据写入到目标文件里
			out.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void generateTableSql(Class obj, String outputPath, String tableName) {
		// tableName 如果是 null，就用类名做表名
		if (tableName == null || tableName.equals("")) {
			tableName = obj.getName();
			tableName = tableName.substring(tableName.lastIndexOf(".") + 1);
		}
		// 表名用大写字母
//		TlAddressEntity
//		tableName = tableName.toUpperCase();
		tableName = upperCharToUnderLine(tableName);

		Field[] fields = obj.getDeclaredFields();
		Object param;
		String column;

		StringBuilder sb = new StringBuilder();

		sb.append("drop table if exists ").append(tableName).append(";\r\n");

		sb.append("\r\n");

		sb.append("create table ").append(tableName).append("(\r\n");

		System.out.println(tableName);

		boolean firstId = true;

		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];

			column = f.getName();

			System.out.println(column + ", " + f.getType().getSimpleName());

			param = f.getType();
			String fieldTypeName = f.getType().getSimpleName();
			sb.append(column); // 一般第一个是主键

			if (param instanceof Integer || fieldTypeName.equals("int") || fieldTypeName.equals("Integer")) {
				sb.append(" INTEGER ");
			} else if (fieldTypeName.equals("long") || fieldTypeName.equals("Long")) {
				sb.append(" BIGINT ");
			}
			else if (fieldTypeName.equals("double") || fieldTypeName.equals("BigDecimal")) {
				sb.append(" DECIMAL ");
			}
			else if (fieldTypeName.equals("Date")) {
				sb.append(" DATETIME ");
			}
			else {
				// 注意：根据需要，自行修改 varchar 的长度。这里设定为长度等于 50
				int length = 50;
				sb.append(" VARCHAR(" + length + ")");
			}

			if (firstId == true) {
				sb.append(" PRIMARY KEY ");
				firstId = false;
			}

			// 获取字段中包含 fieldMeta 的注解

			// 获取属性的所有注释
			Annotation[] allAnnotations = f.getAnnotations();

			XmlElement xmlElement = null;
			Class annotationType = null;

			for (Annotation an : allAnnotations) {
				sb.append(" COMMIT '");
				xmlElement = (XmlElement) an;
				annotationType = an.annotationType();
				param = ((XmlElement) an).name();
				System.out.println("属性 " + f.getName() + " ----- 的注释类型有: " + param);
				sb.append(param).append("'");
			}

			if (i != fields.length - 1) { // 最后一个属性后面不加逗号
				sb.append(", ");
			}

			sb.append("\n");
		}

		String sql = sb.toString();

		sql = sb.substring(0, sql.length() - 1) + "\n) " + "ENGINE = INNODB DEFAULT CHARSET = utf8;\r\n";

		writeFile(sql, outputPath);
	}
	
	

    public static String upperCharToUnderLine(String param) {
        Pattern p = Pattern.compile("[A-Z]");
        if (param == null || param.equals("")) {
            return "";
        }
        StringBuilder builder = new StringBuilder(param);
        Matcher mc = p.matcher(param);
        int i = 0;
        while (mc.find()) {
//            System.out.println(builder.toString());
//            System.out.println("mc.start():" + mc.start() + ", i: " + i);
//            System.out.println("mc.end():" + mc.start() + ", i: " + i);
            builder.replace(mc.start() + i, mc.end() + i, "_" + mc.group().toLowerCase());
            i++;
        }

        if ('_' == builder.charAt(0)) {
            builder.deleteCharAt(0);
        }
        return builder.toString();
    }
	
	
	
	
}
