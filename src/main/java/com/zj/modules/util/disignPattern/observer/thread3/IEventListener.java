/*
 * Copyright (c) 2019-2029 深圳金雅福控股集团有限公司 All Rights Reserved.FileName: IEventListener.java@author: lean.yang@date: 19-7-16 下午6:04@version: 1.0
 */

package com.zj.modules.util.disignPattern.observer.thread3;

/**
 * @author zj
 * @title: IEventListener
 * @description: 线程监听接口
 * @date 2021/8/16
 */
public interface IEventListener {
    void onHandleComplete(Event event);
}
