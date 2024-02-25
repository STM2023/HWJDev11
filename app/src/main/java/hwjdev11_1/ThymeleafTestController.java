package hwjdev11_1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet("/template")
public class ThymeleafTestController extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() throws ServletException  {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("C:/Users/Admin/HWJDev11_1/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  ServletException , IOException {
        resp.setContentType("text/html");


        Date dateNow = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat(" yyyy-MM-dd   HH:mm:ss   zzz");

        Context simpleContext = new Context(
                req.getLocale(),
                Map.of("dataTimeTimezone", format1.format(dateNow))
        );

        engine.process("test", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}



