package hwjdev11_1;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

@WebFilter(value = "/time/*")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req,
                            HttpServletResponse resp,
                            FilterChain chain) throws IOException, ServletException {

        String value = req.getParameter("timezone");
//        Date date =new Date();
//        DateFormat df =new SimpleDateFormat("yyyy-MM-dd   HH:mm:ss ");
//        df.setTimeZone(TimeZone.getTimeZone(value));
//        df.format(date);
//         System.out.println(" data ="+ df.format(date));

        String timeZoneName = TimeZone.getDefault().getDisplayName();//.getID();
        int offset ;
        String sign;

        if(value==null){
            offset =0;
            sign="";
        }else if (value.length()<4) {
            offset = 2;
            sign="";
        }else{
            offset = Integer.parseInt(value.substring(4, value.length()));
            sign = value.substring(3, 4);
        }
        // System.out.println(" sign ="+sign);
        // System.out.println(" offset ="+offset);
        if( value==null ||offset==2||(offset<=18)& (timeZoneName!=null)&("+".equals(sign)||"-".equals(sign))) {
            chain.doFilter(req, resp);
        }
        else {
            resp.setStatus(400);

            resp.setContentType("application/json");
            resp.getWriter().write("{\"Error\": \"Invalid timezone\"}");
            resp.getWriter().close();
        }

    }
}