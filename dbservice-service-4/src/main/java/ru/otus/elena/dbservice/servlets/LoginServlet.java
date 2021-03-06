
package ru.otus.elena.dbservice.servlets;

import ru.otus.elena.dbservice.services.TemplateProcessor;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.elena.dbservice.main.DBServiceContext;
import ru.otus.elena.dbservice.services.ServiceSetting;

public class LoginServlet extends HttpServlet {

    private static final String ACTIONS_PAGE_TEMPLATE = "adminactions.html";
    private static final String LOGIN_PAGE_TEMPLATE="login.html";

    //@Autowired
    private TemplateProcessor templateProcessor=DBServiceContext.getTemplateProcessor();;
    //@Autowired
    private ServiceSetting serviceSetting=DBServiceContext.getServiceSetting();

    @Override
    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        String page=null;
        try {
            JSONParser parser = new JSONParser();
            InputStream ins = LoginServlet.class.getResourceAsStream("/json/password.json");
            InputStreamReader reader = new InputStreamReader(ins);
            JSONObject object = (JSONObject) parser.parse(reader);
            String login = (String) object.get("login");
            String password = (String) object.get("password");

            if (!(login.equalsIgnoreCase(request.getParameter("login"))
                    && password.equalsIgnoreCase(request.getParameter("password")))) {
                pageVariables.put("result", "invalid loogin or password");
                page = templateProcessor.getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
            } else {
                serviceSetting.setLogin(login);
                response.sendRedirect(request.getContextPath() + "/action");
                // pageVariables.put("result", "");
                //page = templateProcessor.getPage(ACTIONS_PAGE_TEMPLATE, pageVariables);
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(page);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
