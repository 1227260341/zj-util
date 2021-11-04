package com.zj.modules.util.disignPattern.observer.thread2;
//抽象主题角色，watched：被观察
public interface Watched
{
    public void addWatcher(Watcher watcher);
 
    public void removeWatcher(Watcher watcher);
 
    public void notifyWatchers(Watcher watcher,String str);
 
}