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
				<h2>Manage Users</h2>
				<!-- Users -->
				<div id="addNewUser" class="collapse">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Add new user</h5>

							<form class="validatedFormAddUser" role="form" name="addUserForm"
								action="CreateUser" onSubmit="" method="post">
								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">First
										name</label>
									<div class="col-lg-9">
										<input name="firstName" class="form-control" type="text"
											placeholder="Enter first name" required>
									</div>
								</div>
								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Last
										name</label>
									<div class="col-lg-9">
										<input name="lastName" class="form-control" type="text"
											placeholder="Enter last name" required>
									</div>
								</div>
								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Username</label>
									<div class="col-lg-9">
										<input name="username" class="form-control" type="text"
											placeholder="Enter username" required>
									</div>
								</div>

								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Email</label>
									<div class="col-lg-9">
										<input name="email" class="form-control" type="email"
											placeholder="Enter email" required>
									</div>
								</div>

								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Rights</label>
									<div class="col-lg-9">
										<select name="rights" class="custom-select"
											id="inlineFormCustomSelect" required>
											<option>User</option>

											<option>Administrator</option>

										</select>
									</div>
								</div>

								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Password</label>
									<div class="col-lg-9">
										<input name="password" id="passwordAddUser"
											class="form-control" type="password"
											placeholder="Enter password" required>
									</div>
								</div>
								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Confirm
										password</label>
									<div class="col-lg-9">
										<input name="password_confirm" class="form-control"
											type="password" placeholder="Enter password again" required>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="btn-group float-right" role="group"
											aria-label="Basic example">
											<button id="cancelAddUser" type="button"
												class="btn btn-outline-danger btn-sm" data-toggle="collapse"
												data-target="#addNewUser">Cancel</button>
											<input type="submit" class="btn btn-outline-success btn-sm"
												value="Add">
										</div>
									</div>
								</div>
							</form>


						</div>
					</div>
				</div>


				<div class="container">
					<div class="row">
						<div class="col">
							<h5>Existing users:</h5>
						</div>
						<div class="col float-right">
							<button id="addNewUserButton" style="display: block"
								class="btn btn-outline-primary btn-sm float-right"
								data-toggle="collapse" data-target="#addNewUser">Add
								new</button>
						</div>

					</div>

					<!--Showing users-->
					<ul class="list-group">

						<li class="list-group-item">

							<div class="row">
								<div class="col-3">
									<h6>Username</h6>
								</div>
								<div class="col-2">
									<h6>Rights</h6>
								</div>
								<div class="col-7">
									<input id="searchusers" class="float-right" name="searchusers"
										placeholder="Search for username" type="text"
										data-list=".userslist">
								</div>
							</div>
						</li>
					</ul>
					<div class="accordion" id="accordionUsers">

						<ul class="list-group userslist">

							<%
								List<User> users = Queries.getAllUsers();
								for (User u : users) {
									if (!u.getUsername().equals(user.getUsername()) && !u.getUsername().equals("NLP")) {
							%>
							<li class="list-group-item">
								<div class="row">
									<div class="col-3">
										<p><%=u.getUsername()%></p>
									</div>
									<div class="col-2">
										<p><%=u.getRights()%></p>
									</div>
									<div class="col-7">
										<div class="btn-group float-right" role="group"
											aria-label="Basic example">
								
												
												<%
												if (u.getUsername().equals("admin")) {
											%>
												
											<button
												class="btn btn-outline-primary btn-sm modifyUserToggler"
												type="button" data-toggle="collapse"
												data-target="#collapse-<%=u.getUsername()%>"
												aria-expanded="false"
												aria-controls="collapse-<%=u.getUsername()%>"
												id="<%=u.getUsername()%>" disabled>Modify</button>
												
											<form action="DeleteUser" method="post">
												<input type="hidden" name="username"
													value="<%=u.getUsername()%>">
												<button type="submit" class="btn btn-outline-danger btn-sm"
													data-toggle="confirmation" data-singleton="true"
													data-popout="true" data-title="Are you sure?"
													data-content="This process cannot be undone."
													data-btn-ok-label="Continue"
													data-btn-ok-class="btn btn-sm btn-outline-danger"
													data-btn-cancel-label="Cancel"
													data-btn-cancel-class="btn btn-sm btn-outline-secondary"
													disabled>Delete</button>
											</form>
											
											<%
												} else {
											%>
											
											<button
												class="btn btn-outline-primary btn-sm modifyUserToggler"
												type="button" data-toggle="collapse"
												data-target="#collapse-<%=u.getUsername()%>"
												aria-expanded="false"
												aria-controls="collapse-<%=u.getUsername()%>"
												id="<%=u.getUsername()%>">Modify</button>
											
											<form action="DeleteUser" method="post">
												<input type="hidden" name="username"
													value="<%=u.getUsername()%>">
												<button type="submit" class="btn btn-outline-danger btn-sm"
													data-toggle="confirmation" data-singleton="true"
													data-popout="true" data-title="Are you sure?"
													data-content="This process cannot be undone."
													data-btn-ok-label="Continue"
													data-btn-ok-class="btn btn-sm btn-outline-danger"
													data-btn-cancel-label="Cancel"
													data-btn-cancel-class="btn btn-sm btn-outline-secondary">Delete</button>
											</form>
											
											<%} %>
											
										</div>
									</div>
								</div>
								<div class="collapse changeUser"
									id="collapse-<%=u.getUsername()%>"
									data-parent="#accordionUsers">
									<form role="form"
										class="validatedFormChangeUser-<%=u.getUsername()%>"
										name="modifyUserForm" action="ModifyUser" method="post">
										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">First
												name</label>
											<div class="col-lg-9">
												<input name="firstName" class="form-control" type="text"
													value="<%=u.getFirstName()%>">
											</div>
										</div>
										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Last
												name</label>
											<div class="col-lg-9">
												<input name="lastName" class="form-control" type="text"
													value="<%=u.getLastName()%>">
											</div>
										</div>
										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Username</label>
											<div class="col-lg-9">
												<input name="username" class="form-control-plaintext"
													type="text" value="<%=u.getUsername()%>" readonly>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Email</label>
											<div class="col-lg-9">
												<input name="email" class="form-control" type="email"
													value="<%=u.getEmail()%>">
											</div>
										</div>

										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Rights</label>
											<div class="col-lg-9">
												<select name="rights" class="custom-select"
													id="inlineFormCustomSelect">
													<%
														if (u.getRights().equals(Queries.Rights.admin)) {
													%>
													<option>user</option>

													<option selected>admin</option>
													<%
														} else {
													%>
													<option selected>user</option>

													<option>admin</option>
													<%
														}
													%>

												</select>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Password</label>
											<div class="col-lg-9">
												<input name="password"
													id="passwordChangeUser-<%=u.getUsername()%>"
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
										<div class="row">
											<div class="col float-right">

												<input type="submit"
													class="btn btn-outline-primary btn-sm btn-block"
													value="Save">
											</div>


										</div>
									</form>
								</div>
							</li>
							<%
								}
								}
							%>

						</ul>
					</div>
				</div>


			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>

<script>
	$(document).ready(function() {

		//toggle new user with cancel
		$("#addNewUserButton").click(function() {
			$("#addNewUserButton").hide();
		});
		$("#cancelAddUser").click(function() {
			$("#addNewUserButton").show();
		});

		$(".validatedFormAddUser").validate({
			rules : {
				password : {
					minlength : 5
				},
				password_confirm : {
					minlength : 5,
					equalTo : "#passwordAddUser"
				}
			}
		});

		$('.changeUser').each(function() {
			var id = this.id.substring(9);
			$(".validatedFormChangeUser-" + id).validate({
				rules : {
					password : {
						minlength : 5
					},
					password_confirm : {
						minlength : 5,
						equalTo : "#passwordChangeUser-" + id
					}
				}
			});
		});

		$('#searchusers').hideseek({
			nodata : 'No results found',
			ignore : '.changeUser'
		});

		//add project select
		$('.my-select').selectpicker();

		$('[data-toggle=confirmation]').confirmation({
			rootSelector : '[data-toggle=confirmation]',
		// other options
		});

	});
</script>

</html>