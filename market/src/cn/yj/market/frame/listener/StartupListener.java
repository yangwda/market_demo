package cn.yj.market.frame.listener;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.yj.market.frame.util.ConstantsContainer;

/**
 * 
 * Application级对象的创建和销毁
 * 
 */
public class StartupListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(StartupListener.class) ;

    private static ApplicationContext ctx;

    public static ApplicationContext getCtx() {
        return ctx;
    }

    /**
     * 创建Application对象
     */
    public void contextInitialized(ServletContextEvent event) {
        logger.info("Initializing Application ......");
        ServletContext context = event.getServletContext();

        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);

        try {
            Resource resource = new ClassPathResource("/sys.properties");
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            ConstantsContainer.setVariable(props);
            logger.info("Initializing Properties is ok ......");
        } catch (IOException e) {
            logger.warn("Initializing Properties Error ......");
        }

    }

    /**
     * 销毁Application对象
     */
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
