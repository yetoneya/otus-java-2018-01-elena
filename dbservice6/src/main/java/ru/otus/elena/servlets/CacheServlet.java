
package ru.otus.elena.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.elena.servlet.services.TemplateProcessor;
import ru.otus.elena.cache.Cache;
import ru.otus.elena.dbservice.configuration.ServiceConfiguration;

public class CacheServlet extends HttpServlet {
    private static final String PARAMETERS_PAGE_TEMPLATE = "cacheparameters.html";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    //@Autowired
    private TemplateProcessor templateProcessor;

    @Override
    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        ApplicationContext context = (ApplicationContext) request.getSession().getAttribute("context");
        if (context == null) {
            context = new AnnotationConfigApplicationContext(ServiceConfiguration.class);
            request.getSession().setAttribute("context", context);
        }
        templateProcessor=context.getBean(TemplateProcessor.class);
        String page = null;
        String login = (String) request.getSession().getAttribute("login");
        if (login == null) {
            page = templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
        } else {
            Cache cache = (Cache) request.getSession().getAttribute("cache");

            long size = cache.getCacheSize();
                long hit = cache.getHitCount();
                long miss = cache.getMissCount();
                pageVariables.put("size", size);
                pageVariables.put("hit", hit);
                pageVariables.put("miss", miss);
                page = templateProcessor.getPage(PARAMETERS_PAGE_TEMPLATE, pageVariables);         
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().println(page);
        response.setStatus(HttpServletResponse.SC_OK);

    }
}
