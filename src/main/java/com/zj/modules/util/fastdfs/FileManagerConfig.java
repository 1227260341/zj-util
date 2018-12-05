package com.zj.modules.util.fastdfs;

import java.io.Serializable;

public interface FileManagerConfig extends Serializable {

    public static final String FILE_DEFAULT_AUTHOR = "zj";

    public static final String PROTOCOL = "http://";

    public static final String SEPARATOR = "/";

    public static final String TRACKER_NGNIX_ADDR = "files.21cnjy.com";

    public static final String TRACKER_NGNIX_PORT = "22122";

    public static final String CLIENT_CONFIG_FILE = "client.conf";
}
