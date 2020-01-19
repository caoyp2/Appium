package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    public Logger logger;

    public Log(Class<?> clazz){
       logger = LoggerFactory.getLogger(clazz);
    }

    public static void main(String[] args) {
       Log log = new Log(Log.class);
       log.info("这是一个日志。。。。。");

    }

    public  void info(Object obj){
        logger.info(obj.toString());
    }
}
