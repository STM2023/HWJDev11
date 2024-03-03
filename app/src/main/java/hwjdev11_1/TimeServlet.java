package hwjdev11_1;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.standard.expression.GreaterThanExpression;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.FileTemplateResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.lang.String;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.*;

@WebServlet(value = "/time")
public class TimeServlet  extends HttpServlet{
    private static final String DATE_FORMAT = "yyyy-MM-dd   HH:mm:ss  zzz";
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException  {
        engine = new TemplateEngine();
        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setPrefix(getServletContext().getRealPath("/WEB-INF/templates/"));     //"C:/Users/Admin/HWJDev11_1/  //
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // resp.setIntHeader("Refresh", 5);

        resp.setContentType("text/html");

//time?timezone=UTC%2B2

        String value = req.getParameter("timezone");

        Cookie[] cookies = req.getCookies();
        String lastTimezone="";

        if (value == null) {

            if (req.getCookies()!= null) {
                for (Cookie cookie : cookies) {
                    lastTimezone = cookie.getValue();
                }
            }
            if (!Objects.equals(lastTimezone, "")){
                ThymeleafTest(req,resp,lastTimezone);
//                resp.getWriter().write(format.format(timeZone(lastTimezone)));
            }else{

                ThymeleafTest(req,resp,value);
            }
        } else {

            resp.addCookie(new Cookie("lastTimezone", value));

            ThymeleafTest(req,resp,value);
        }
    }
    public ZonedDateTime timeZone(String value) {
        ZoneId zone = ZoneId.of(value);
        return ZonedDateTime.now(zone);
    }
    public void ThymeleafTest(HttpServletRequest req, HttpServletResponse resp, String value) throws IOException {

        Date dateNow = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(" yyyy-MM-dd   HH:mm:ss   zzz");

        Context simpleContext=null;
        if (value==null) {
            simpleContext = new Context(
                    req.getLocale(),
                    Map.of("dataTimeTimezone", format1.format(dateNow))
            );
        }else {
            format1.setTimeZone(TimeZone.getTimeZone(value));
            DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
            simpleContext = new Context(
                    req.getLocale(),
                    Map.of("dataTimeTimezone",format.format(timeZone(value)).toString())
            );
        }
        engine.process("test", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}

