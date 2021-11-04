package com.zj.modules.util.disignPattern.observer.thread2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
 
/**观察者管理
 * @author huangziwang
 *
 */
public class ConcreteWatched implements Watched
{
    // 存放观察者
    private static List<Watcher> list = new ArrayList<Watcher>();
 
    public void addWatcher(Watcher watcher)
    {
        list.add(watcher);
    }
 
    public void removeWatcher(Watcher watcher)
    {
        list.remove(watcher);
    }
 
    public void notifyWatchers(Watcher watcher1,String str)
    {
        // 自动调用实际上是主题进行调用的
    	Iterator<Watcher> e = list.iterator();
    	while (e.hasNext()) {
    		Watcher watcher = e.next();
                //精确找出观察者返回函数并从队列中删除
    		if (watcher.equals(watcher1)) {
        		watcher.update(str);
        		e.remove();
        		System.out.println(list.size());
    		}
		}
    }
 
}
