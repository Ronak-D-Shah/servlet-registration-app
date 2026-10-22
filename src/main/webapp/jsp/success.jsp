<%@ page contentType="text/html;charset=UTF-8" %>
<%!
    // Escapes HTML so user input can't inject markup
    static String h(Object o) {
        if (o == null) return "";
        return o.toString().replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
    static String last4(Object o) {
        if (o == null) return "";
        String s = o.toString();
        return s.length() <= 4 ? s : "****" + s.substring(s.length() - 4);
    }
%>
<%
    if (session.getAttribute("fname") == null) {
        response.sendRedirect(request.getContextPath() + "/personalinfo.html");
        return;
    }
%>
<html><body>
<h2 style="color:green">You have registered successfully</h2>

<h3>Personal Info</h3>
First Name: <%= h(session.getAttribute("fname")) %><br>
Middle Name: <%= h(session.getAttribute("mname")) %><br>
Last Name: <%= h(session.getAttribute("lname")) %><br>
Gender: <%= h(session.getAttribute("gender")) %><br>

<h3>Contact Info</h3>
Address: <%= h(session.getAttribute("address")) %><br>
City: <%= h(session.getAttribute("city")) %><br>
State: <%= h(session.getAttribute("state")) %><br>
Country: <%= h(session.getAttribute("country")) %><br>
Phone: <%= h(session.getAttribute("phone")) %><br>

<h3>Bank Info</h3>
Bank Name: <%= h(session.getAttribute("bankname")) %><br>
Account #: <%= h(last4(session.getAttribute("account"))) %><br>
SSN: ***-**-<%= h(session.getAttribute("ssnLast4")) %><br>

<br><a href="<%= request.getContextPath() %>/personalinfo.html">Register another</a>
</body></html>