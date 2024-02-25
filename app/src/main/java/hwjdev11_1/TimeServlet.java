package hwjdev11_1;

import org.thymeleaf.standard.expression.GreaterThanExpression;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@WebServlet(value = "/time")
public class TimeServlet  extends HttpServlet{
    private static final String DATE_FORMAT = "yyyy-MM-dd   HH:mm:ss  zzz";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // resp.setIntHeader("Refresh", 5);

        resp.setContentType("text/html");

//time?timezone=UTC%2B2
        String value = req.getParameter("timezone");

        Date dateNow = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(" yyyy-MM-dd   HH:mm:ss   zzz");

        Cookie[] cookies = req.getCookies();
        String lastTimezone="";

        if (value == null) {
            for (Cookie cookie : cookies) {
                lastTimezone =cookie.getValue();
            }
            if (!Objects.equals(lastTimezone, "")){

                DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
                resp.getWriter().write(format.format(timeZone(lastTimezone)));
            }else{
                resp.getWriter().write(format1.format(dateNow));
            }
        } else {
            resp.addCookie(new Cookie("lastTimezone", value));

            DateTimeFormatter format = DateTimeFormatter.ofPattern(DATE_FORMAT);
            resp.getWriter().write(format.format(timeZone(value)));
            resp.getWriter().close();
        }
    }
public ZonedDateTime timeZone(String value) {
    ZoneId zone = ZoneId.of(value);
    return ZonedDateTime.now(zone);
}
}