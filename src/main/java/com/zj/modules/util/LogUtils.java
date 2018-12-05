package com.zj.modules.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 日志工具类
 *
 * @author hedongzhou
 *
 */
public class LogUtils {

	/**
	 * 获取日对象
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	public static Logger get(Object obj) {
		return get(obj.getClass());
	}

	/**
	 * 获取日志对象
	 * 
	 * @param cls
	 *            类型
	 * @return
	 */
	public static Logger get(Class<?> cls) {
		return get(cls.getName());
	}

	/**
	 * 获取日志对象
	 * 
	 * @param name
	 *            名称
	 * @return
	 */
	public static Logger get(String name) {
		return Logger.getLogger(name);
	}

	/**
	 * 提示
	 * 
	 * @param message
	 *            消息
	 */
	public static void info(Object message) {
		log(Level.INFO, message, null);
	}

	/**
	 * 警告
	 * 
	 * @param message
	 *            消息
	 */
	public static void warn(Object message) {
		log(Level.WARN, message, null);
	}

	/**
	 * 调试
	 * 
	 * @param message
	 *            消息
	 */
	public static void debug(Object message) {
		log(Level.DEBUG, message, null);
	}

	/**
	 * 错误
	 * 
	 * @param message
	 *            消息
	 */
	public static void error(Object message) {
		log(Level.ERROR, message, null);
	}

	/**
	 * 错误
	 * 
	 * @param e
	 *            异常
	 */
	public static void error(Throwable e) {
		log(Level.ERROR, e.getMessage(), e);
	}

	/**
	 * 错误
	 * 
	 * @param message
	 *            消息
	 * @param e
	 *            异常
	 */
	public static void error(Object message, Throwable e) {
		log(Level.ERROR, message, e);
	}

	/**
	 * 打印日志
	 * 
	 * @param level
	 *            日志等级
	 * @param message
	 *            消息
	 * @param e
	 *            异常
	 */
	public static void log(Level level, Object message, Throwable e) {
		Logger logger = null;

		StackTraceElement[] stack = Thread.currentThread().getStackTrace();
		final  String logUtilClassName = LogUtils.class.getName();
		if (stack[2].getClassName().equals(logUtilClassName)) {
			logger = get(stack[3].getClassName());
		} else {
			logger = get(stack[2].getClassName());
		}

		if (logger != null) {
//			int levelFlag = -1;
//			if (level.getClass().equals(Level.INFO)) {
//				levelFlag = Level.ERROR_INT;
//			} else if (level.getClass().equals(Level.WARN)) {
//				levelFlag = Level.WARN_INT;
//			} else if (level.getClass().equals(Level.DEBUG)) {
//				levelFlag = Level.DEBUG_INT;
//			} else if (level.getClass().equals(Level.ERROR)) {
//				levelFlag = Level.ERROR_INT;
//			}
			logger.callAppenders(new LoggingEvent(logUtilClassName, logger, level, message, e));
//			if (levelFlag == -1) {
//				if (!logger.getLoggerRepository().isDisabled(levelFlag) && level.isGreaterOrEqual(logger.getEffectiveLevel())) {
//					logger.callAppenders(new LoggingEvent(LogUtils.class.getName(), logger, level, message, e));
//				}
//			}
		}
	}

}
