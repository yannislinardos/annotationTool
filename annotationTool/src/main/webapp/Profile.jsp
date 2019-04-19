<%@ page import="util.*" import="beans.*" import="java.util.List"%>
<jsp:include page="header.jsp" />

<!-- ----------REDIRECT if not logged in -------------------->

<%
	User user = LoginDao.getToken(request.getCookies());

	if (user == null) {
		response.sendRedirect("Login.jsp");
		user = new User();
	}
%>

<!-- -------------------------------------- -->

<body>

	<!-- Navbar -->
	<jsp:include page="navbar.jsp" />

	<!-- Alert -->
	<jsp:include page="alert.jsp" />


	<!-- Library -->
	<div class="container">
		<div class="card">
			<div class="card-body">
				<div class="card-title">
					<%
						int uploads = Queries.countReports(user.getUsername());
						int annot = Queries.countAnnotations(user.getUsername());
					%>
					<h2><%=user.getUsername()%></h2>
					<h5><%=uploads%>
						uploads |
						<%=annot%>
						annotations
					</h5>
				</div>

				<div class="tab-pane fade show active" id="basicInfo"
					role="tabpanel" aria-labelledby="basicInfo-tab">
					<div class="tab-pane" id="edit">
						<form name="myDetailsForm" action="ModifyMyself" method="post"
							class="validatedFormChangeMe">
							<div class="form-group row">
								<label class="col-lg-3 col-form-label form-control-label">First
									name</label>
								<div class="col-lg-9">
									<input name="firstName" class="form-control" type="text"
										value="<%=user.getFirstName()%>">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-3 col-form-label form-control-label">Last
									name</label>
								<div class="col-lg-9">
									<input name="lastName" class="form-control" type="text"
										value="<%=user.getLastName()%>">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-3 col-form-label form-control-label">Email</label>
								<div class="col-lg-9">
									<input name="email" class="form-control" type="email"
										value="<%=user.getEmail()%>">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-3 col-form-label form-control-label">Rights</label>
								<div class="col-lg-9">
									<input class="form-control-plaintext" type="text"
										value="<%=user.getRights()%>" readonly>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-3 col-form-label form-control-label">Username</label>
								<div class="col-lg-9">
									<input name="username" class="form-control-plaintext"
										type="text" value="<%=user.getUsername()%>" readonly>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-3 col-form-label form-control-label">Password</label>
								<div class="col-lg-9">
									<input name="password" id="passwordChangeMe"
										class="form-control" type="password" value=""
										placeholder="Type the new password or leave it blank for no change.">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-lg-3 col-form-label form-control-label">Confirm
									password</label>
								<div class="col-lg-9">
									<input name="password_confirm" class="form-control"
										type="password" value=""
										placeholder="Retype the new password or leave it blank for no change.">
								</div>
							</div>
							<div class="form-group row">
								<div class="col-lg-9">
									<input type="submit" class="btn btn-outline-primary"
										value="Save changes">
								</div>
							</div>

						</form>
					</div>


				</div>
			</div>
			</div>
			<jsp:include page="footer.jsp" />
		</div>
</body>

<script>
	$(document).ready(function() {

		//validate password
		$(".validatedFormChangeMe").validate({
			rules : {
				password : {
					minlength : 5
				},
				password_confirm : {
					minlength : 5,
					equalTo : "#passwordChangeMe"
				}
			}
		});
		//add project select
		$('.my-select').selectpicker();

		//confirmation tagsss
		$('[data-toggle=confirmation]').confirmation({
			rootSelector : '[data-toggle=confirmation]',
		// other options
		});

	});
</script>

</html>