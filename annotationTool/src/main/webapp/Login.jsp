<%@ page import="util.*" import="beans.*" import="java.util.List"%>

<jsp:include page="header.jsp" />

<!-- ----------REDIRECT if not logged in -------------------->

<%
	User user = LoginDao.getToken(request.getCookies());

	if (user != null) {
		response.sendRedirect("Profile.jsp");
		user = new User();
	}
%>

<!-- -------------------------------------- -->

<body>
	<div class="container h-100">
	
		<div class="d-flex justify-content-center h-100">
		
			<div class="user_card">
			
				<div class="d-flex justify-content-center">
					<div class="brand_logo_container">
						<img src="images/logo.png" class="brand_logo" alt="Logo">
					</div>
				</div>
				
				<div class="d-flex justify-content-center form_container">
				
					<form method="post" action="Login">
						
						<div id="danger-alert" class="alert alert-danger" role="alert"
						style="display: none; text-align: center;">
						<strong>Credentials wrong!</strong>
						<br>Try again!
					</div>
						<div class="input-group mb-3">
							<div class="input-group-append">
								<span class="input-group-text"><i class="fas fa-user"></i></span>
							</div>
							<input type="text" name="username"
								class="form-control input_user" value="" placeholder="username">
						</div>
						<div class="input-group mb-2">
							<div class="input-group-append">
								<span class="input-group-text"><i class="fas fa-key"></i></span>
							</div>
							<input type="password" name="password"
								class="form-control input_pass" value="" placeholder="password">
						</div>
						<div class="d-flex justify-content-center mt-3 login_container">
							<button type="submit" name="button" class="btn login_btn">Login</button>
						</div>
					</form>
				</div>


				<div class="mt-4">
					<div class="d-flex justify-content-center links">Don't have
						an account?</div>
					<div class="d-flex justify-content-center links">Ask the
						administrator to sign you up.</div>
						
				</div>
			</div>
		</div>
	</div>


	<jsp:include page="footer.jsp" />
</body>
<script>
	$(document).ready(function() {
		//alerts management
		var checkVariable = "${failed}";

		if (checkVariable == "true") {
			$("#danger-alert").fadeTo(2000, 500).slideUp(500, function() {
				$("#danger-alert").slideUp(500);
			});
		}
	});
</script>
</html>