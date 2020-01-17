package com.zj.modules.filter;

import com.alibaba.fastjson.JSON;
import com.zj.modules.util.PropertiesUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ParameterRequestWrapper2 extends HttpServletRequestWrapper {

    private final byte[] body;
    private Map<String, String[]> params = new HashMap<String, String[]>();


    public ParameterRequestWrapper2(HttpServletRequest request) {
        // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
        super(request);
        //将参数表，赋予给当前的Map以便于持有request中的参数
        Map<String, String[]> requestMap = request.getParameterMap();
        this.params.putAll(requestMap);

        String sessionStream = getBodyString(request);
        body = sessionStream.getBytes(Charset.forName("UTF-8"));

        this.modifyParameterValues();
    }

    public String getBodyString() {
        return new String(body, Charset.forName("UTF-8"));
    }

    /**
     * 获取请求Body
     *
     * @param request
     * @return
     */
    private String getBodyString(final ServletRequest request) {

        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = cloneInputStream(request.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    /**
     * Description: 复制输入流</br>
     *
     * @param inputStream
     * @return</br>
     */
    public InputStream cloneInputStream(ServletInputStream inputStream) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return byteArrayInputStream;
    }


    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        String paramStr = this.getBodyString();
        String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
//        if (StringUtils.isEmpty(header)) {
//        	header = "application/json;charset=utf-8";
//        }
        int indexOf = 0;
        if (header != null) {
            indexOf = header.indexOf(MediaType.APPLICATION_JSON_VALUE);
        }
        if (indexOf > -1) {
            try {
                Map<String, Object> map = PropertiesUtil.jsonStringToMap(paramStr);

                String tmp = JSON.toJSONString(map);
                if (StringUtils.isNotEmpty(tmp)) {
                    paramStr = tmp;
                }
            } catch (Exception e) {
            }
        }
        // String paramStr = IOUtils.toString(super.getInputStream(), "utf-8");

        final ByteArrayInputStream bais = new ByteArrayInputStream(paramStr.getBytes("utf-8"));


        return new ServletInputStream() {

            @Override
            public int read() throws IOException {
                return bais.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }
        };
    }

    /**
     * 将parameter的值去除空格后重写回去
     */
    public void modifyParameterValues() {
        Set<String> set = params.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String[] values = params.get(key);
            values[0] = values[0].trim();
            params.put(key, values);
        }
    }

    /**
     * 重写getParameter 参数从当前类中的map获取
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * 重写getParameterValues
     */
    public String[] getParameterValues(String name) {
        return params.get(name);
    }


}
