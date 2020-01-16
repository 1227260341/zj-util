package com.zj.modules.filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import javax.servlet.DispatcherType;

/**
 * Filter配置
 *
 * @author zhouzhenjiang
 * @date 2017年9月23日
 */
@Configuration
public class FilterConfig {
	
    /**
     * 去除参数头尾空格过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean parmsFilterRegistration() {
    	//配置无需过滤的路径或者静态资源，如：css，imgage等
        StringBuffer excludedUriStr = new StringBuffer();
//        excludedUriStr.append("/login/*");
//        excludedUriStr.append(",");
//        excludedUriStr.append("/favicon.ico");
//        excludedUriStr.append(",");
//        excludedUriStr.append("/js/*");

    	
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new ParamsFilter());
        registration.addUrlPatterns("/*");
//        registration.addInitParameter("excludedUri", excludedUriStr.toString());
        registration.setName("paramsFilter");
        registration.setOrder(Integer.MAX_VALUE-1);
        return registration;
    }
}