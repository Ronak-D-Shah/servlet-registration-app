<HTML>
<BODY>
<%
    // This is a scriptlet.  Notice that the "date"
    // variable we declare here is available in the
    // embedded expression later on.
    System.out.println( "Evaluating date now" );
    java.util.Date date = new java.util.Date();


%>
<font color="green"> Todays Date: </font><%= date %></font>

<center><h1> <font color="red"> Output Page </font> </h1></center>
<font color="blue">================ You entered following Values ==================</font>
<br>
<br>
<br>
<%
String varFirstName = (String) request.getSession().getAttribute("ofname");
String varLastName = (String) request.getSession().getAttribute("olname");
String varMiddleName = (String) request.getSession().getAttribute("omname");
String varHomePhone = (String) request.getSession().getAttribute("ohphone");
String varWorkPhone = (String) request.getSession().getAttribute("owphone");
%>
<%if (varFirstName != null){%>
	FitstName = <%=varFirstName%>
	<br>
<%}%>
<%if (varLastName != null){%>
	LastName = <%=varLastName%>
	<br>
<%}%>
<%if (varMiddleName != null){%>
	MiddleName = <%=varMiddleName%>
	<br>
<%}%>
<%if (varHomePhone != null){%>
	HomePhone = <%=varHomePhone%>
	<br>
<%}%>
<%if (varWorkPhone != null){%>
	WorkPhone = <%=varWorkPhone%>
	<br>
<%}%>
<br>
<font color="blue">================ End of the display=============================</font>
</BODY>
</HTML>