/*
 * Copyright (c) 2019-2029 深圳金雅福控股集团有限公司 All Rights Reserved.FileName: EventSource.java@author: lean.yang@date: 19-7-16 下午6:09@version: 1.0
 */

package com.zj.modules.util.disignPattern.observer.thread3;

import java.util.Iterator;
import java.util.Vector;

/**
 * @author zj
 * @title: EventSource
 * @description: 事件源
 * @date 2021/8/16
 */
public class EventSource {
    private Vector list = new Vector();

    public void addEventListener(IEventListener eventListener) {
        list.add(eventListener);
    }

    public void deleteEventListener(IEventListener eventListener) {
        list.remove(eventListener);
    }

    public void notifyEvent(Event eventListener) {
        Iterator it = list.iterator();
        while (it.hasNext())
            ((IEventListener) it.next()).onHandleComplete(eventListener);

    }
}
