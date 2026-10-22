<%@ page contentType="text/html;charset=UTF-8" %>
<html><body>
<% if (request.getAttribute("error") != null) { %>
  <h2 style="color:red">${error}</h2>
<% } else { %>
  <h2>Weather Info - ${city}</h2>
  Temp: ${temp} &deg;F<br>
  Humidity: ${humidity}%<br>
  Rain? ${rain}<br>
  Sunny? ${sunny}<br>
<% } %>
<br><a href="weather.html">Back</a>
</body></html>