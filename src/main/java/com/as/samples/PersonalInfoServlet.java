package com.as.samples;

import java.io.IOException;
import jakarta.servlet.http.*;

public class PersonalInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession s = req.getSession();
        s.setAttribute("fname", req.getParameter("fname"));
        s.setAttribute("mname", req.getParameter("mname"));
        s.setAttribute("lname", req.getParameter("lname"));
        s.setAttribute("gender", req.getParameter("gender"));
        resp.sendRedirect("contactinfo.html");
    }
}