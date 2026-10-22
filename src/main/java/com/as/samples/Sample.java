package com.as.samples;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Sample  extends HttpServlet{

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException
    {
        //Get registration details and set to the EMailAddress
        String firstName = request.getParameter("fname");
        String middleName = request.getParameter("mname");
        String lastName = request.getParameter("lname");
        String homePhone = request.getParameter("hphone");
        String workPhone = request.getParameter("wphone");
        System.out.println("First Name is " + firstName);
        System.out.println("Last Name is " + lastName);
        request.getSession().setAttribute("ofname", firstName);
        request.getSession().setAttribute("omname", middleName);
        request.getSession().setAttribute("olname", lastName);
        request.getSession().setAttribute("ohphone", homePhone);
        request.getSession().setAttribute("owphone", workPhone);
        response.setContentType("text/html");
        response.sendRedirect("jsp/output.jsp");
    }

}