<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create User Form</title>
</head>
<body>

<form method="post" action="CreateUser">

username: <input name="username" type="text"/>
password: <input name="password" type="password"/>
First name: <input name="firstName" type="text"/>
Last Name: <input name="lastName" type="text"/>
email: <input name="email" type="text"/>
Rights <select name="rights">
<option>User</option>
<option>Administrator</option>
</select>

<input type="submit" />
</form>

</body>
</html>