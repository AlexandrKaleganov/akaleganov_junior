package listener;


import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

/**
 * Простая реализация для регистрации события,
 * когда атрибут добавлен, удален или заменен в контексте сервлета.
 */

@WebListener
public class AppContextAttributeListener implements ServletContextAttributeListener {

    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
        System.out.println("ServletContext attribute added::{" + servletContextAttributeEvent.getName() + ","
                + servletContextAttributeEvent.getValue() + "}");
    }

    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
        System.out.println("ServletContext attribute replaced::{" + servletContextAttributeEvent.getName() + ","
                + servletContextAttributeEvent.getValue() + "}");
    }

    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
        System.out.println("ServletContext attribute removed::{" + servletContextAttributeEvent.getName() + ","
                + servletContextAttributeEvent.getValue() + "}");
    }

}