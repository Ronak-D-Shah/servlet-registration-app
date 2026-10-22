package com.as.samples;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BankInfoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession s = req.getSession();

        if (s.getAttribute("fname") == null) {          // skipped step 1
            resp.sendRedirect("personalinfo.html");
            return;
        }

        String ssn = req.getParameter("ssn") == null ? "" : req.getParameter("ssn").replaceAll("\\D", "");
        String ssnLast4 = ssn.length() >= 4 ? ssn.substring(ssn.length() - 4) : null;

        try (Connection con = DbUtil.getConnection()) {
            con.setAutoCommit(false);
            try {
                int userId;
                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO users (first_name, middle_name, last_name, gender) VALUES (?,?,?,?) RETURNING id")) {
                    ps.setString(1, (String) s.getAttribute("fname"));
                    ps.setString(2, (String) s.getAttribute("mname"));
                    ps.setString(3, (String) s.getAttribute("lname"));
                    ps.setString(4, (String) s.getAttribute("gender"));
                    try (ResultSet rs = ps.executeQuery()) {
                        rs.next();
                        userId = rs.getInt(1);
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO contacts (user_id, address, city, state, country, phone) VALUES (?,?,?,?,?,?)")) {
                    ps.setInt(1, userId);
                    ps.setString(2, (String) s.getAttribute("address"));
                    ps.setString(3, (String) s.getAttribute("city"));
                    ps.setString(4, (String) s.getAttribute("state"));
                    ps.setString(5, (String) s.getAttribute("country"));
                    ps.setString(6, (String) s.getAttribute("phone"));
                    ps.executeUpdate();
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO bank_accounts (user_id, bank_name, account_number, ssn_last4) VALUES (?,?,?,?)")) {
                    ps.setInt(1, userId);
                    ps.setString(2, req.getParameter("bankname"));
                    ps.setString(3, req.getParameter("account"));
                    ps.setString(4, ssnLast4);
                    ps.executeUpdate();
                }

                con.commit();
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(500, "Could not save registration");
            return;
        }

        s.setAttribute("bankname", req.getParameter("bankname"));
        s.setAttribute("account", req.getParameter("account"));
        s.setAttribute("ssnLast4", ssnLast4);
        resp.sendRedirect(req.getContextPath() + "/jsp/success.jsp");
    }
}