package com.cloudapp.web.main.call;

import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CallThreadListener implements ServletContextListener {

    private CallThread socketThread = null;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if(socketThread == null){
            socketThread = WebApplicationContextUtils.getWebApplicationContext(sce.
                    getServletContext()).getBean(CallThread.class);
            socketThread.start();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (socketThread != null && !socketThread.isInterrupted()){
            socketThread.closeSocketServe();
            socketThread.interrupt();
        }
    }


}
