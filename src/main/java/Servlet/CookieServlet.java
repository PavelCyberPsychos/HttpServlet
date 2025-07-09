package Servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/cookies")
public class CookieServlet extends HttpServlet {
    private static final String UNIQUE_ID = "userID";
    private final AtomicInteger counter = new AtomicInteger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var cookies = req.getCookies();
        boolean cookieFound = false;

        if (cookies != null) {
            cookieFound = Arrays.stream(cookies)
                    .anyMatch(cookie -> UNIQUE_ID.equals(cookie.getName()));
        }

        if (!cookieFound) {
            var cookie = new Cookie(UNIQUE_ID, "1");
            cookie.setPath("/"); // Send the cookie for all paths
            cookie.setMaxAge(15 * 60); // 15 minutes
            cookie.setHttpOnly(true);
            cookie.setSecure(req.isSecure()); // Only set to true if using HTTPS
            resp.addCookie(cookie);
            counter.incrementAndGet();
        }

        resp.setContentType("text/plain"); // More appropriate content type
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (var writer = resp.getWriter()) {
            writer.write(String.valueOf(counter.get())); // Convert integer to string
        } catch (IOException e) {
            // Log the error appropriately
            System.err.println("Error writing response: " + e.getMessage());
        }
    }
}
