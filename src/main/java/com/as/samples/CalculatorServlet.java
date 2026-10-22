package com.as.samples;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class CalculatorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/calc.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String result;
        try {
            int a = Integer.parseInt(req.getParameter("int1"));
            int b = Integer.parseInt(req.getParameter("int2"));
            switch (req.getParameter("op")) {
                case "+": result = String.valueOf(a + b); break;
                case "-": result = String.valueOf(a - b); break;
                case "*": result = String.valueOf(a * b); break;
                case "/": result = (b == 0) ? "Cannot divide by zero" : String.valueOf((double) a / b); break;
                default:  result = "Unknown operation";
            }
        } catch (NumberFormatException e) {
            result = "Please enter valid integers";
        }
        req.setAttribute("result", result);
        req.getRequestDispatcher("/jsp/calc.jsp").forward(req, resp);
    }
}