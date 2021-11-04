利用观察者模式来获取线程中的数据或者回调函数

代码菜鸟旺仔 2015-08-18 22:30:32  1880  收藏
分类专栏： java并发编程 文章标签： 线程 java 线程 java
版权

java并发编程
专栏收录该内容
34 篇文章0 订阅
订阅专栏
首先
//抽象主题角色，watched：被观察
public interface Watched
{
    public void addWatcher(Watcher watcher);
 
    public void removeWatcher(Watcher watcher);
 
    public void notifyWatchers(Watcher watcher,String str);
 
}


//抽象观察者角色
public interface Watcher
{
    public void update(String str);
 
}


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


public class ConcreteWatcher implements Watcher
{
 
    public void update(String str)
    {
        System.out.println(str);
    }
 
}

public class TestThread extends Thread {
	private Watcher watcher;
	// 将被观察者对象传入线程，这里充分显示了面向对象开发的好处
	public TestThread (Watcher watcher) {
		this.watcher = watcher;
	}
	public void run() {
		Watched concreteWatched = new ConcreteWatched();
		concreteWatched.notifyWatchers(watcher, "我是:" + hashCode());
	}
 
}


public class Test
{
    public static void main(String[] args)
    {
        Watched girl = new ConcreteWatched();
        // 将观察者加入队列中
        Watcher watcher1 = new ConcreteWatcher();
        girl.addWatcher(watcher1);
        TestThread t = new TestThread(watcher1);
        t.start();
    }
}
抛出结果

我是:743319257
0
