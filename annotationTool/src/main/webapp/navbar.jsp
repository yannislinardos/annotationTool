<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="util.*" import="beans.*" import="java.util.List"%>


<!-- ----------REDIRECT if not logged in -------------------->

<%
	User user = LoginDao.getToken(request.getCookies());

	if (user == null) {
		response.sendRedirect("Login.jsp");
		user = new User();
	}
%>

<!-- Navigation bar -->
<nav class="navbar navbar-expand-lg fixed-top navbar-custom">
	<div class="container">

		<!-- Brand -->
		<img src="images/logotwentnnotator.png" height="25"
			class="navbar-brand d-inline-block align-self-center" alt="">



		<!-- Links -->
		<ul class="navbar-nav">
			<li class="nav-item"><a class="nav-link" href="Profile.jsp">Profile</a>
			</li>
			<li class="nav-item"><a class="nav-link" href="projects.jsp">Projects</a>
			</li>
			<%
				if (user.getRights() == Queries.Rights.admin) {
			%>
			<li class="nav-item dropdown">
			<a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> Manage </a>
				<div class="dropdown-menu" aria-labelledby="navbarDropdown">
					<a class="dropdown-item" href="manageusers.jsp">Manage Users</a> 
					<a class="dropdown-item" href="managestandards.jsp">Manage
						Standards</a> 
					<a class="dropdown-item" href="manageprojects.jsp">Manage
						Projects</a>
					<a class="dropdown-item" href="managereports.jsp">Manage
						Reports/Collections</a>
				</div></li>

			<%} %>
			<li class="nav-item"><a class="nav-link" href="upload.jsp">Upload</a>
			</li>
			<li class="nav-item"><a class="nav-link" href="help.jsp">Help</a></li>
			<li class="nav-item"><a class="nav-link" href="Logout">Log
					out</a></li>
		</ul>
	</div>
</nav>
