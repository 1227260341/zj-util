package com.zj.modules.util.disignPattern.observer.thread3;

import java.util.Map;

import org.springframework.web.client.RestClientException;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zj
 * @description: 请求监听工具类
 * @date 2021/8/16
 */
@Data
@Slf4j
public class ObserverRunable implements Runnable {
    EventSource eventSource = new EventSource();
    Map<String, Object> params;
    Object eventObj;

    public ObserverRunable() {

    }

    public ObserverRunable(
            Map<String, Object> params,
            Object eventObj,
            IEventListener eventListener) {
        this.params = params;
        eventSource.addEventListener(eventListener);
        this.params = params;
        this.eventObj = eventObj;
    }


    @Override
    public void run() {
        Event event = new Event(eventObj);
        event.setInvoker(this);
        JSONObject obj = new JSONObject();
        try {
        	obj.put("code", "200");
        	obj.put("message", "线程结束了");
            event.setResult(obj);
        } catch (RestClientException e) {
            e.printStackTrace();
            event.setResult(e);
        } catch (Exception e) {
            e.printStackTrace();
            event.setResult(e);
        }
        eventSource.notifyEvent(event);
    }


}
