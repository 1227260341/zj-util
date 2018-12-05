package com.zj.modules.util.fastdfs;

import java.io.File;
import java.io.IOException;

import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.zj.modules.util.LogUtils;
import com.zj.modules.util.PropertiesUtil;


/**
 * <strong>类概要： FastDFS Java客户端工具类</strong> <br>
 * <strong>创建时间： 2017-9-7 上午10:26:48</strong> <br>
 * 
 * @Project springmvc-main(com.wl.bean)
 * @author zj
 * @version 1.0.0
 */
public class FileManager  {
//public class FileManager implements FileManagerConfig {

    private static final long serialVersionUID = 1L;
    private static TrackerClient trackerClient;
    private static TrackerServer trackerServer;
    private static StorageServer storageServer;
    private static StorageClient storageClient;

    static {
        try {
//            String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();
//            LogUtils.debug("classPath:" + classPath + "File.separator:" + File.separator);
            //String fdfsClientConfigFilePath = classPath + File.separator + PropertiesUtil.getValue("client.config.file", null);
            String userDir = System.getProperty("user.dir");
            userDir = userDir.replaceAll("\\\\", "/");
            //String urlP = FileManager.class.getResource("/").getPath();
            //String fdfsClientConfigFilePath = urlP+ File.separator + PropertiesUtil.getValue("client.config.file", null);
            String fdfsClientConfigFilePath = userDir +"/classes"+ File.separator + PropertiesUtil.getValue("client.config.file", null);
//            ClientGlobal.g_anti_steal_token = true;
//            ClientGlobal.init(fdfsClientConfigFilePath);
            //ClientGlobal.g_anti_steal_token = true;
            //ClientGlobalTest.init();
            ClientGlobalInit.initGlobal();

//            String fdfsClientConfigFilePath = classPath + File.separator + CLIENT_CONFIG_FILE;
//            ClientGlobal.init(fdfsClientConfigFilePath);

            trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();

            storageClient = new StorageClient(trackerServer, storageServer);

        } catch (Exception e) {
            LogUtils.error("files manager",e);
        }
    }
    
    public static void test(String str) {
    	String aa= "0";
    }

    /**
     * <strong>方法概要： 文件上传</strong> <br>
     * <strong>创建时间： 2017-9-8 上午10:26:11</strong> <br>
     * 
     * @param FastDFSFile
     *            file
     * @return fileAbsolutePath
     * @author zj
     */
    public static String upload(FastDFSFile file,NameValuePair[] valuePairs) {
        String[] uploadResults = null;
        try {
        	LogUtils.info("filespath = ======================================================" );
//        	String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();
//        	String rrrr = FileManager.class.getResource("/").getFile();
        	String aaa= new File("abc").getCanonicalPath();
        	String bb = System.getProperty("user.dir");
        	LogUtils.info("bb = ======================================================" + bb);
        	LogUtils.info("aaa = ======================================================" + aaa);
//        	String urlP = FileManager.class.getResource("/").getPath();
//            String fdfsClientConfigFilePath = urlP+ File.separator + PropertiesUtil.getValue("client.config.file", null);
//            LogUtils.info("urlP = ======================================================" + urlP);
//        	LogUtils.info("fdfsClientConfigFilePath = ======================================================" + fdfsClientConfigFilePath);
        	
//        	LogUtils.info("filespath = " + FileManager.class);
//        	LogUtils.info("filespath = " + FileManager.class.getResource("/"));
//        	LogUtils.info("filespath = " + FileManager.class.getResource("/").getFile());
//        	LogUtils.debug("filespath = " + FileManager.class);
//        	LogUtils.debug("filespath = " + FileManager.class.getResource("/"));
//        	LogUtils.debug("filespath = " + FileManager.class.getResource("/").getFile());
//        	LogUtils.debug("filespath = " + new File(FileManager.class.getResource("/").getFile()));
//        	LogUtils.debug("filespath = " + new File(FileManager.class.getResource("/").getFile()).getCanonicalPath());
//        	String classPath = new File(FileManager.class.getResource("/").getFile()).getCanonicalPath();
//        	LogUtils.debug("classPath:" + classPath + "File.separator:" + File.separator);
//        	String filespath = PropertiesUtil.getValue("client.config.file", null);
//        	LogUtils.debug("filespath = " + filespath);
        	
        	LogUtils.debug("storageClient:" + storageClient);
        	LogUtils.debug("file.getContent():" + file.getContent());
        	LogUtils.debug("file.getExt():" + file.getExt());
        	LogUtils.debug("valuePairs:" + valuePairs);
            uploadResults = storageClient.upload_file(file.getContent(),file.getExt(), valuePairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String groupName = uploadResults[0];
        String remoteFileName = uploadResults[1];
        
        String protocol = PropertiesUtil.getValue("protocol", null);
        String addr = PropertiesUtil.getValue("tracker.ngnix.addr", null);
        String separator = PropertiesUtil.getValue("separator", null);
        
        String fileAbsolutePath = 
                //+ trackerServer.getInetSocketAddress().getHostName()
                //+ SEPARATOR + TRACKER_NGNIX_PORT 
                separator + groupName
                + separator + remoteFileName;
        
//        String fileAbsolutePath = protocol + addr
//                //+ trackerServer.getInetSocketAddress().getHostName()
//                //+ SEPARATOR + TRACKER_NGNIX_PORT 
//                + separator + groupName
//                + separator + remoteFileName;

//        String fileAbsolutePath = PROTOCOL
//                + TRACKER_NGNIX_ADDR
//                //+ trackerServer.getInetSocketAddress().getHostName()
//                //+ SEPARATOR + TRACKER_NGNIX_PORT 
//                + SEPARATOR + groupName
//                + SEPARATOR + remoteFileName;
        return fileAbsolutePath;
    }
    
    
    /**
     * <strong>方法概要： 文件下载</strong> <br>
     * <strong>创建时间： 2016-9-26 上午10:28:21</strong> <br>
     * 
     * @param String
     *            groupName
     * @param String
     *            remoteFileName
     * @return returned value comment here
     * @author zj
     */
    public static ResponseEntity<byte[]> download(String groupName,
            String remoteFileName,String specFileName) {
        byte[] content = null;
        HttpHeaders headers = new HttpHeaders();
        try {
            content = storageClient.download_file(groupName, remoteFileName);
            headers.setContentDispositionFormData("attachment",  new String(specFileName.getBytes("UTF-8"),"iso-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(content, headers, HttpStatus.CREATED);
    }
    
    

    
    
    
    
}