<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />

<body>

	<jsp:include page="navbar.jsp" />
	<div class="container">
		<div class="jumbotron text-center">
			<h1>Error 403 - Forbidden</h1>
			<p>You are either not logged in, or don't have the privileges to
				view this page.</p>
			<p>Please refer to the administrator if you think you should be
				able to view this page.</p>
			<form class="form-signin">
				<button class="btn btn-lg btn-primary btn-block" type="submit"
					formaction="/login.jsp">Login</button>
			</form>
		</div>
	</div>

	<jsp:include page="footer.jsp" />
</body>

</html>
