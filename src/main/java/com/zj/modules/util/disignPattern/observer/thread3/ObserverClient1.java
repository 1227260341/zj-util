package com.zj.modules.util.disignPattern.observer.thread3;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;

public class ObserverClient1 {
	
	@Autowired
    TaskExecutor taskExecutor;
	
	
    public static void main(String[] args) {
    	ObserverClient1 client = new ObserverClient1();
    	
    	Map<String, Object> params = new ConcurrentHashMap<>();
        params.put("message", "输出了");
        
        Map<String, Object> eventObj = new ConcurrentHashMap<>();
        params.put("data", "进去的");
        
        ObserverRunable httpCommand = new ObserverRunable(params, eventObj, new IEventListener() {
            @Override
            public void onHandleComplete(Event event) {
                // MQ delay retry bu invoker
                ObserverRunable httpCommandRetry = (ObserverRunable) event.getInvoker();
                Map<String, Object> paramsReturn = httpCommandRetry.getParams();
                Map<String, Object> result = (Map<String, Object>) event.getResult();
                System.out.println("回调成功了：" + result.get("message"));
            }
        });
        
        Thread thread = new Thread(httpCommand, "12314124124");
        thread.start();
        System.out.println("thread id =" + thread.getId());
//        client.taskExecutor.execute(httpCommand);
    	
//        taskExecutor.execute(httpCommand);
    }
}
