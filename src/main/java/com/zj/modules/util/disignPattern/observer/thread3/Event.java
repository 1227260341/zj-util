/*
 * Copyright (c) 2019-2029 深圳金雅福控股集团有限公司 All Rights Reserved.FileName: ThreadEvent.java@author: lean.yang@date: 19-7-16 下午6:00@version: 1.0
 */

package com.zj.modules.util.disignPattern.observer.thread3;

import java.util.EventObject;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zj
 * @title: ThreadEvent
 * @description: 事件状态类
 * @date 2021/8/16
 */
@Setter
@Getter
public class Event extends EventObject {
    /**
	 * TODO
	 */
	private static final long serialVersionUID = 1L;
	private Object result;
    private Object invoker;
    
    public Event(Object source) {
        super(source);
    }
    
    public Event(Object source, Object result) {
        super(source);
        this.result = result;
    }


    @Override
    public Object getSource() {
        return super.getSource();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
