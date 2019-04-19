<%@ page import="util.*" import="beans.*" import="java.util.List"%>
<jsp:include page="header.jsp" />

<!-- ----------REDIRECT if not logged in -------------------->

<%
	User user = LoginDao.getToken(request.getCookies());

	if (user == null) {
		response.sendRedirect("Login.jsp");
		user = new User();
	}
	List<Standard> standards = Queries.getAllStandards();
	List<User> users = Queries.getAllUsers();

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
				<h2>Manage Projects</h2>
				<div id="addNewProject" class="collapse">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Add new project</h5>
							<form class="validatedFormAddProject" role="form"
								name="addProjectForm" action="CreateProject" onSubmit=""
								method="post">
								
								<div id="danger-alert2" class="alert alert-danger" role="alert"
		style="display: none">
		Fields are not completed properly!
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>

								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Project
										name</label>
									<div class="col-lg-9">
									<div>
										<input name="projectName" class="form-control" type="text"
											placeholder="Enter project name" required></div>
									</div>
								</div>


								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Select
										users</label>
									<div class="col-lg-9">
										<select class="selectpicker form-control" multiple
											data-live-search="true" name="users" required>
											<%
																		for (User u : users) {
																				if (!u.getUsername().equals("NLP")) {
																	%>
											<option data-subtext="<%=u.getRights()%>"><%=u.getUsername()%></option>
											<%
																		}
																			}
																	%>
										</select>
									</div>
								</div>


								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Select
										Standards</label>
									<div class="col-lg-9">
										<select class="selectpicker form-control" multiple
											data-live-search="true" name="standards" required>
											<%
																		for (Standard s : standards) {
																	%>
											<option><%=s.getName()%></option>
											<%
																		}
																	%>
										</select>
									</div>
								</div>

								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Select
										Collections</label>
									<div class="col-lg-9">
										<select class="selectpicker form-control" multiple
											data-live-search="true" name="collections" id="collectionscheck">
											<%
																		List<Collection> collections = Queries.getAllCollections();
																			for (Collection c : collections) {
																	%>
											<option data-subtext="added by <%=c.getUploader()%>"><%=c.getName()%></option>
											<%
																		}
																	%>
										</select>
									</div>
								</div>

								<div class="form-group row">
									<label class="col-lg-3 col-form-label form-control-label">Select
										Reports</label>
									<div class="col-lg-9">
										<select class="selectpicker form-control" multiple
											data-live-search="true" name="reports" id="reportscheck">
											<%
																		List<Report> uncollectionizedreports = Queries.getAllReportsWithoutCollection();
																			for (Report r : uncollectionizedreports) {
																	%>
											<option data-subtext="added by <%=r.getUploader()%>"><%=r.getPkString()%></option>
											<%
																		}
																	%>
										</select>
									</div>
								</div>


								<div class="row">
									<div class="col">
										<div class="btn-group float-right" role="group"
											aria-label="Basic example">
											<button id="cancelAddProject" type="button"
												class="btn btn-outline-danger btn-sm" data-toggle="collapse"
												data-target="#addNewProject">Cancel</button>
											<input type="hidden" name="creator"
												value="<%=user.getUsername()%>"> <input
												type="submit" id="saveProject"
												class="btn btn-outline-success btn-sm" value="Add">
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
							<h5>Existing projects:</h5>
						</div>
						<div class="col float-right">
							<button id="addNewProjectButton" style="display: block"
								class="btn btn-outline-primary btn-sm float-right"
								data-toggle="collapse" data-target="#addNewProject">Add
								new</button>
						</div>

					</div>

					<!--Showing project-->
					<ul class="list-group">

						<li class="list-group-item">

							<div class="row">
								<div class="col-3">
									<h6>Name</h6>
								</div>
								<div class="col-2">
									<h6>Author</h6>
								</div>
								<div class="col-2">
									<h6>Date</h6>
								</div>
								<div class="col-2">
									<h6>Finished</h6>
								</div>
								<div class="col-3">
									<input id="searchprojects" class="float-right"
										name="searchprojects" placeholder="Search for name"
										type="text" data-list=".projectslist">
								</div>
							</div>
						</li>
					</ul>

					<div class="accordion" id="accordionProjects">
						<ul class="list-group projectslist">
							<%
														List<Project> projects = Queries.getAllProjects();
															for (Project p : projects) {
													%>
							<li class="list-group-item">
								<div class="row">
									<div class="col-3">
										<p><%=p.getName()%></p>
									</div>
									<div class="col-2">
										<p><%=p.getCreator()%></p>
									</div>
									<div class="col-2">
										<p><%=p.getDate()%></p>
									</div>
									<div class="col-2">
										<p><%=p.isFinished()%></p>
									</div>
									<div class="col-3">
										<div class="btn-group float-right" role="group"
											aria-label="Basic example">
											<button
												class="btn btn-outline-primary btn-sm modifyProjectToggler"
												type="button" data-toggle="collapse"
												data-target="#collapse-<%=p.getPk()%>" aria-expanded="false"
												aria-controls="collapse-<%=p.getPk()%>" id="<%=p.getPk()%>">Modify</button>
											<form action="DeleteProject" method="post">
												<input type="hidden" name="name" value="<%=p.getName()%>">
												<button type="submit" class="btn btn-outline-danger btn-sm"
													data-toggle="confirmation" data-singleton="true"
													data-popout="true" data-title="Are you sure?"
													data-content="This process cannot be undone."
													data-btn-ok-label="Continue"
													data-btn-ok-class="btn btn-sm btn-outline-danger"
													data-btn-cancel-label="Cancel"
													data-btn-cancel-class="btn btn-sm btn-outline-secondary">Delete</button>
											</form>
										</div>
									</div>
								</div>
								<div class="collapse changeProject" id="collapse-<%=p.getPk()%>"
									data-parent="#accordionProjects">

									<form class="validatedFormChangeProject-<%=p.getPk()%>"
										role="form" name="addProjectForm" action="ModifyProject"
										onSubmit="" method="post">

										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Project
												name</label>
											<div class="col-lg-9">
												<input name="projectName" class="form-control" type="text"
													value="<%=p.getName()%>" required>
											</div>
										</div>


										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Select
												users</label>
											<div class="col-lg-9">
												<select class="selectpicker form-control" multiple
													data-live-search="true" name="users" required>
													<%
																				String[] addedUsers = p.getUsernames();
																						for (User u : users) {
																							boolean is = false;
																							for (String usr : addedUsers) {
																								if (u.getUsername().equals(usr)) {
																									is = true;
																								}
																							}
																							if (!u.getUsername().equals("NLP")) {

																								if (is) {
																			%>
													<option data-subtext="<%=u.getRights()%>" selected><%=u.getUsername()%></option>
													<%
																				} else {
																			%>
													<option data-subtext="<%=u.getRights()%>"><%=u.getUsername()%></option>
													<%
																				}
																							}
																						}
																			%>
												</select>
											</div>
										</div>


										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Select
												Standards</label>
											<div class="col-lg-9">
												<select class="selectpicker form-control" multiple
													data-live-search="true" name="standards" required>
													<%
																				String[] addedStandards = p.getStandards();
																						for (Standard s : standards) {
																							boolean is = false;
																							for (String std : addedStandards) {
																								if (s.getName().equals(std)) {
																									is = true;
																								}
																							}
																							if (is) {
																			%>
													<option selected><%=s.getName()%></option>
													<%
																				} else {
																			%>
													<option><%=s.getName()%></option>
													<%
																				}
																						}
																			%>
												</select>
											</div>
										</div>


										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Select
												New Collections</label>
											<div class="col-lg-9">
												<select class="selectpicker form-control" multiple
													data-live-search="true" name="collections">
													<%
																				for (Collection c : collections) {
																			%>
													<option data-subtext="added by <%=c.getUploader()%>"><%=c.getName()%></option>
													<%
																				}
																			%>
												</select>
											</div>
										</div>

										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Select
												Reports</label>
											<div class="col-lg-9">
												<select class="selectpicker form-control" multiple
													data-live-search="true" name="reports" required>
													<%
																				String[] addedReports = p.getReports();
																						for (String reportid : addedReports) {
																							int repid = Integer.parseInt(reportid);
																							Report repp = Queries.getReport(repid);
																			%>
													<option data-subtext="added by <%=repp.getUploader()%>"
														selected><%=repid%></option>
													<%
																				}
																			%>
													<%
																				for (Report r : uncollectionizedreports) {
																							boolean alreadyin = false;
																							for (String reportid : addedReports) {
																								if (r.getPkString().equals(reportid)) {
																									alreadyin = true;
																									break;
																								}
																							}
																							if (!alreadyin) {
																			%>
													<option data-subtext="added by <%=r.getUploader()%>"><%=r.getPkString()%></option>
													<%
																				}
																						}
																			%>

												</select>
											</div>
										</div>
										<div class="form-group row">
											<label class="col-lg-3 col-form-label form-control-label">Set
												as finished</label>
											<div class="col-lg-9">
												<select class="selectpicker form-control" name="setfinished"
													required>
													<%
																				if (p.isFinished() == true) {
																			%>
													<option selected>true</option>
													<option>false</option>
													<%
																				} else {
																			%>
													<option>true</option>
													<option selected>false</option>
													<%
																				}
																			%>
												</select>
											</div>
										</div>


										<div class="row">
											<div class="col">
												<input type="hidden" name="creator"
													value="<%=p.getCreator()%>"></input> <input type="hidden"
													name="id" value="<%=p.getPk()%>"></input> <input
													type="submit"
													class="btn btn-outline-primary btn-sm btn-block"
													value="Save">
											</div>
										</div>
									</form>

								</div>
							</li>
							<%
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
$(document).ready(
		function() {
			$("#addNewProjectButton").click(function() {
				$("#addNewProjectButton").hide();
			});
			$("#cancelAddProject").click(function() {
				$("#addNewProjectButton").show();
			});
			
			$('#searchprojects').hideseek({
				nodata : 'No results found',
				ignore : '.changeProjects'
			});
			//add project select
			$('.my-select').selectpicker();
			
			$('[data-toggle=confirmation]').confirmation({
				rootSelector : '[data-toggle=confirmation]',
			// other options
			});
			var checkkk = false;
			
			$(".validatedFormAddProject").validate({
				rules : {
					collections: {
				          required: function(element) {
				              return $("#reportscheck").val() == "";
				          }
				     },
				     reports: {
				          required: function(element) {
				              return $("#collectionscheck").val() == "";
				          }
				     }
				},
				errorPlacement: function(error, element) {
						$("#danger-alert2").fadeTo(2000, 500).slideUp(500, function() {
							$("#danger-alert2").slideUp(500);
						});
				}
				
			});
			
			
			
			
			
			
			
		});
</script>

</html>