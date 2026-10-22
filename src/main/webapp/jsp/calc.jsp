<%@ page contentType="text/html;charset=UTF-8" %>
<html><body>
<h2>Calculator</h2>
<form method="post" action="calc">
  Int1 <input name="int1" value="${param.int1}"><br>
  Int2 <input name="int2" value="${param.int2}"><br>
  Result: <b>${result}</b><br><br>
  <input type="submit" name="op" value="+">
  <input type="submit" name="op" value="-">
  <input type="submit" name="op" value="*">
  <input type="submit" name="op" value="/">
</form>
</body></html>