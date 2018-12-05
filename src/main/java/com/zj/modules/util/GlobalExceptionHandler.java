package com.zj.modules.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理
 *
 * @author zj
 * 
 * 2018年9月26日
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static Logger logger = LogManager.getLogger(GlobalExceptionHandler.class.getName());
//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView defaultHandler(HttpServletRequest request, Exception e) throws Exception {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("exception", e);
//        modelAndView.addObject("url", request.getRequestURL());
//        modelAndView.setViewName("error");
//        return modelAndView;
//    }

    /**
     * 在controller里面内容执行之前，校验一些参数不匹配啊，Get post方法不对啊之类的
     */
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        System.out.println("错误");
        logger.error(ex);
        return new ResponseEntity<Object>("网络出错了 network error", HttpStatus.NOT_EXTENDED);

    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object jsonHandler(HttpServletRequest request, Exception e) throws Exception {
        Map returnMap = new HashMap();
//        r.setMessage(e.getMessage());
//        r.setCode(ErrorInfo.ERROR);
//        r.setData("发生异常");
//        r.setUrl(request.getRequestURL().toString());
        log(e, request);

        returnMap.put("code", "500");
        returnMap.put("message", "系统繁忙！");
		return returnMap;
    }

    private void log(Exception ex, HttpServletRequest request) {
        logger.error("************************异常开始*******************************");
//        if(getUser() != null)
//            logger.error("当前用户id是" + getUser().getUserId());
        logger.error(ex);
        logger.error("请求地址：" + request.getRequestURL());
        Enumeration enumeration = request.getParameterNames();
        logger.error("请求参数");
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement().toString();
            logger.error(name + "---" + request.getParameter(name));
        }
        
        logger.error("-------------------");

        StackTraceElement[] error = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : error) {
            logger.error(stackTraceElement.toString());
        }
        logger.error("************************异常结束*******************************");
    }
}
