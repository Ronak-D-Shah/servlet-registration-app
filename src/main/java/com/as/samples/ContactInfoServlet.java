package com.as.samples;

import java.io.IOException;
import jakarta.servlet.http.*;

public class ContactInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession s = req.getSession();
        for (String k : new String[]{"address", "city", "state", "country", "phone"})
            s.setAttribute(k, req.getParameter(k));
        resp.sendRedirect("bankinfo.html");
    }
}