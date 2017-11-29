package com.valerio; /**
 * Created by valer on 27/11/2017.
 */

import com.valerio.job.AddOverThresholdMeasuresJob;
import com.valerio.job.AddUnderThresholdMeasuresJob;
import com.valerio.job.AddUponThresholdMeasuresJob;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener()
public class BackgroundJobManager implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    private ScheduledExecutorService scheduler;

    // Public constructor is required by servlet spec
    public BackgroundJobManager() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        System.out.println("context initialized");

    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
        System.out.println("context destroyed");
//        scheduler.shutdown();
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new AddOverThresholdMeasuresJob(),0,5,TimeUnit.SECONDS);
        scheduler.scheduleAtFixedRate(new AddUponThresholdMeasuresJob(),0,2500,TimeUnit.MILLISECONDS);
        scheduler.scheduleAtFixedRate(new AddUnderThresholdMeasuresJob(), 0, 1, TimeUnit.SECONDS);
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
        scheduler.shutdown();
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
