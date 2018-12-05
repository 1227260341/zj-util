package com.zj.modules.util.fastdfs;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.csource.common.IniFileReader;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.TrackerGroup;

import com.zj.modules.util.PropertiesUtil;


/**
 * 实现初始化 ClientGlobal类
 *
 * @author zj
 * 
 * 2017年10月17日
 */
public class ClientGlobalInit {
//	 public static int g_connect_timeout;
//	 public static int g_network_timeout;
//	 public static String g_charset;
//	 public static int g_tracker_http_port;
//	 public static boolean g_anti_steal_token;
//	 public static String g_secret_key;
//	 public static TrackerGroup g_tracker_group;
//	 public static final int DEFAULT_CONNECT_TIMEOUT = 5;
//	 public static final int DEFAULT_NETWORK_TIMEOUT = 30;
	
	 public static void initGlobal() {
		 if (ClientGlobal.g_connect_timeout < 0) {
			 ClientGlobal.g_connect_timeout = 5;
		 } else {
			 ClientGlobal.g_connect_timeout *= 1000;
		 }
		 if (ClientGlobal.g_network_timeout < 0)
		 {
			 ClientGlobal.g_network_timeout = 30;
			 }
		 ClientGlobal.g_network_timeout *= 1000;
		 if ((ClientGlobal.g_charset == null) || (ClientGlobal.g_charset.length() == 0))
		 {
			 ClientGlobal.g_charset = "ISO8859-1";
			 }
		 
		 String trackerServer = PropertiesUtil.getValue("tracker_server", null);
		 if (trackerServer == null || "".equals(trackerServer)) {
			 try {
				throw new MyException(
							"the value of item \"tracker_server\" is invalid, tracker_server is not configure");
			} catch (MyException e) {
				e.printStackTrace();
			}
		 }
		 String[] parts = trackerServer.split("\\:", 2);
		 if (parts.length != 2){
			try {
				throw new MyException(
						"the value of item \"tracker_server\" is invalid, the correct format is host:port");
			} catch (MyException e) {
				e.printStackTrace();
			}
		}
		InetSocketAddress[] tracker_servers = new InetSocketAddress[1];
		tracker_servers[0] = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
		
		ClientGlobal.g_tracker_group = new TrackerGroup(tracker_servers);
		 
	 }
	 
}