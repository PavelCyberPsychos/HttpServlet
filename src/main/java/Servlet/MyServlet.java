package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/jakarta")
public class MyServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var paramValue = req.getParameterValues("param");
        var parametrMap = req.getParameterMap();

        for (Map.Entry<String, String[]> enrtry : parametrMap.entrySet()) {
            System.out.println("Parametr name: " + enrtry.getKey());
            String[] values = enrtry.getValue();
            for (String v : values) {
                System.out.println("Value: " + v);
            }
        }


        resp.setContentType("text/html");

        try (var writer = resp.getWriter()) {
            writer.write("hello from first servlet");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (var reader = req.getReader();
             var linse = reader.lines()) {

            linse.forEach(System.out::println);
        }

    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
