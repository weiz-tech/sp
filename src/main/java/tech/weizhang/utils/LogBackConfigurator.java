package tech.weizhang.utils;

import org.slf4j.LoggerFactory;

import java.io.File;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class LogBackConfigurator {

    /**
     * @Desp: 加载logback配置
     * @Author: weizhang
     * @Date: 2020/7/7 11:13
     */
    public static void load(String  path) {
        LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
        File classpathXmlConfigFile = new File(path);
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(context);
        context.reset();
        try{
            configurator.doConfigure(classpathXmlConfigFile);
            StatusPrinter.printInCaseOfErrorsOrWarnings(context);
        }catch (JoranException e){
            throw new RuntimeException("加载logback配置文件失败！");
        }

    }
}
