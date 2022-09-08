/*
 * Copyright © 2011-2015 Kinghood Group All Rights Reserved.
 * 未经本公司正式书面同意，其他任何个人、团体不得使用、复制、修改或发布本软件.
 * 版权所有深圳金雅福控股集团有限公司 www.jinyafu.com.
 */
package com.zj.modules.controller;


/**
 * 单例模式样例
 * 重发mq消息
 * @version 2021-11-1616:15:39
 * @author zhouzj
 */
public class SingleletonRepeatMQMsgService {
    
//    @Autowired
//    DelayedSender delayedSender;
    
    private volatile static SingleletonRepeatMQMsgService repeatMQMsgService = null;
    
    public SingleletonRepeatMQMsgService() {};
    
    /**
     * 双重检查获取实例
     * @Description: 
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2021-11-1616:18:49
     * @Version: 1.0
     * @return
     */
    public static SingleletonRepeatMQMsgService getInstance() {
        if (repeatMQMsgService == null) {
            synchronized (SingleletonRepeatMQMsgService.class) {
                if (repeatMQMsgService == null) {
                    repeatMQMsgService = new SingleletonRepeatMQMsgService();
                }
            }
        }
        return repeatMQMsgService;
    }
    
    
    
    /**
     * 统一异常重发 规则
     * @Description: 
     * @Copyright 深圳金雅福控股集团有限公司
     * @author zhouzj
     * @Date 2021-11-1616:23:21
     * @Version: 1.0
     * @param delayedSender 发送的类
     * @param data 具体的数据
     * @param exchangeName 交换器名
     * @param queueName 队列名
     * @param profilePrefix 前缀
     */
    public void exceptionSendMsg(Object delayedSender, Object data, String exchangeName, String queueName, String profilePrefix) {
        Long[]  maxInterval = {1000l*3, 1000l*10, 1000l*20, 1000l*60*2, 1000l*60*30};
//        int attempts = data.getAttempts();
        int attempts = 1;
        if (attempts > 4) {//只重试5次左右哈，0，1，2，3，4
            return ;
        }
        Long thisMaxInterval = maxInterval[attempts];
        attempts = attempts + 1;
//        data.setAttempts(attempts);
//        delayedSender.sendMessage(
//                profilePrefix + "." + exchangeName,
//                profilePrefix + "." + queueName,
//                data, thisMaxInterval);//间隔10秒
    } 
    
    public static void main(String[] args) {
    	SingleletonRepeatMQMsgService instance = SingleletonRepeatMQMsgService.getInstance();
//    	instance.exceptionSendMsg(delayedSender, data, exchangeName, queueName, profilePrefix);
	}
    
    
    
}
