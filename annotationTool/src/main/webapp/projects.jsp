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
				<h2>Projects</h2>
				<div class="row">
					<div class="col">
						<h5>Current projects:</h5>
					</div>
				</div>
				<div class="accordion" id="accordionMyProjects">
					<%
						List<Project> myprojects = Queries.getProjectsFromUser(user.getUsername(), false);
						if (myprojects.size() == 0) {
					%>

					<ul class="list-group">
						<li class="list-group-item">No current projects</li>
					</ul>


					<%
						} else {
							for (Project p : myprojects) {
								String[] standardids = p.getStandards();
								boolean hasBirads = false;
								String standards = "";
								String std = "";
								for (String s : standardids) {
									if (s.equals("BIRADS")) {
										hasBirads = true;
										standards = standards + " <span class=\"badge badge-secondary\">" + s + "</span>";
									} else {
										standards = standards + " <span class=\"badge badge-dark\">" + s + "</span>";
									}
									if (std.equals("")) {
										std = s;
									} else {
										std = std + "-split-" + s;
									}
								}
								boolean isAutoProject = Queries.isAutoProject(p.getPk());
					%>
					<div class="card">
						<div class="card-header" id="myprojectsheader-<%=p.getPk()%>">
							<div class="row">
								<div class="col-8">
									Project: <b><%=p.getName()%> </b> | Standards: <b><%=standards%></b>
								</div>
								<div class="col-4">
									<div class="btn-group float-right" role="group">
										<%
											if (hasBirads == true && isAutoProject == false) {
										%>
										<form action="AutomaticAnnotationBatch" method="post">
											<input type="hidden" name="project_id" value="<%=p.getPk()%>">
											<button type="submit"
												class="btn btn-outline-secondary btn-sm">Auto
												annotate all BIRADS</button>
										</form>
										<%
											}
										%>
										<%
											if (user.getRights() == Queries.Rights.admin) {
										%>
										<form action="DownloadAnnotations" method="post">
											<input type="hidden" name="projectid" value="<%=p.getPk()%>">
											<button type="submit" class="btn btn-outline-success btn-sm">Download</button>
										</form>

										<%
											}
										%>
										<button class="btn btn-outline-secondary btn-sm" type="button"
											data-toggle="collapse"
											data-target="#collapseproject-<%=p.getPk()%>"
											aria-expanded="false"
											aria-controls="collapseproject-<%=p.getPk()%>">
											Expand</button>
									</div>
								</div>

							</div>
						</div>

						<div id="collapseproject-<%=p.getPk()%>" class="collapse"
							aria-labelledby="myprojectsheader-<%=p.getPk()%>"
							data-parent="#accordionMyProjects">
							<div class="card-body">
								<div class="accordion" id="accordionProject-<%=p.getPk()%>">
									<ul class="list-group">

										<%
											String[] reportss = p.getReports();
													for (String reportid : reportss) {
														int rid = Integer.parseInt(reportid);
														Report r = Queries.getReport(rid);
														boolean isAutoAnnotated = Queries.isAutoAnnotated(r.getPk(), p.getPk());
										%>


										<li class="list-group-item">
											<div class="row">
												<div class="col-5">
													Report: <b><%=r.getPkString()%></b> <small>added
														on: <b><%=r.getDate()%></b> by: <b><%=r.getUploader()%></b>
													</small>
												</div>
												<div class="col-2">
													<%
														if (hasBirads == true && isAutoAnnotated == false) {
													%>
													<form action="AutomaticAnnotationReport" method="post">
														<input type="hidden" name="project_id"
															value="<%=p.getPk()%>"> <input type="hidden"
															name="report_id" value="<%=r.getPk()%>">
														<button type="submit"
															class="btn btn-outline-secondary btn-sm btn-block"
															id="autoAnnReport-<%=p.getPk()%>-<%=r.getPk()%>"
															style="display: none">Auto annotate BIRADS</button>
													</form>
													<%
														}
													%>

												</div>
												<div class="col-5">
													<form action="AnnotateReport" method="post">
														<div class="row">
															<div class="col-6">
																<select name="standard"
																	id="<%=p.getPk()%>-<%=r.getPk()%>"
																	class="selectpicker form-control form-control-sm form-control-block selectMyCurrentStandard"
																	title="Choose standard..." required>
																	<%
																		for (String s : standardids) {
																						Standard standard = Queries.getStandard(s);
																	%>
																	<option><%=standard.getName()%></option>
																	<%
																		}
																	%>
																</select>
															</div>

															<div class="col-6">
																<input type="hidden" name="project"
																	value="<%=p.getPk()%>"> <input type="hidden"
																	name="report" value="<%=r.getPk()%>"> <input
																	type="hidden" name="annotation" value="notset">
																<div
																	class="btn-group btn-group-sm btn-group-block float-right">
																	<button type="submit"
																		class="btn btn-outline-primary  btn-sm btn-block">Annotate</button>
																	<%
																		List<Annotated> annotations = Queries.getAnnotations(r.getPk(), p.getPk());
																					if (annotations.size() != 0) {
																	%>

																	<button class="btn btn-outline-secondary btn-sm"
																		type="button" data-toggle="collapse"
																		data-target="#annotationDiv-<%=r.getPkString()%>"
																		aria-expanded="false"
																		aria-controls="annotationDiv-<%=r.getPkString()%>">
																		Annotations</button>
																	<%
																		} else {
																	%>
																	<span class="d-inline-block" tabindex="0"
																		data-toggle="tooltip" data-placement="right"
																		title="No annotations at all!">

																		<button class="btn btn-outline-secondary btn-sm"
																			style="pointer-events: none;" disabled>
																			Annotations</button>
																	</span>

																	<%
																		}
																	%>
																</div>
															</div>
														</div>
													</form>
												</div>
											</div>

											<div id="annotationDiv-<%=r.getPkString()%>" class="collapse">
												<div class="card-body">
													<ul class="list-group">

														<%
															boolean none = true;
																		for (Annotated a : annotations) {
																			Standard stan = Queries.getStandard(a.getStandard_id());
																			if (user.getRights() == Queries.Rights.admin) {
																				none = false;
														%>
														<li class="list-group-item">
															<div class="row">
																<div class="col-8">
																	<%
																		if (a.getAnnotator().equals("NLP")) {
																	%>
																	Annotation: <b><%=a.getPk()%></b> <small> was
																		added <b>automatically using NLP </b>. You have to
																		review this annotation!
																	</small>
																	<%
																		} else {
																	%>
																	Annotation: <b><%=a.getPk()%></b> <small>added
																		on: <b><%=a.getDate()%></b> by: <b><%=a.getAnnotator()%></b>
																		with standard: <b><%=stan.getName()%></b>
																	</small>
																	<%
																		}
																	%>
																</div>
																<div class="col-4">
																	<input type="hidden" name="project"
																		value="<%=p.getPk()%>"> <input type="hidden"
																		name="report" value="<%=r.getPk()%>">
																	<div
																		class="btn-group btn-group-sm btn-group-block float-right">
																		<form action="AnnotateReport" method="post">
																			<input type="hidden" name="project"
																				value="<%=a.getProject_id()%>"> <input
																				type="hidden" name="report"
																				value="<%=a.getReport_id()%>"> <input
																				type="hidden" name="annotation"
																				value="<%=a.getPk()%>"> <input type="hidden"
																				name="standard" value="<%=stan.getName()%>">
																			<%
																				if (a.getAnnotator().equals("NLP")) {
																			%>
																			<button type="submit"
																				class="btn btn-outline-secondary btn-sm float-right">Review</button>
																			<%
																				} else {
																			%>
																			<button type="submit"
																				class="btn btn-outline-primary btn-sm float-right">Edit</button>
																			<%
																				}
																			%>
																		</form>

																		<form action="DeleteAnnotation" method="post">
																			<input type="hidden" name="annotationid"
																				value="<%=a.getPk()%>">
																			<button type="submit"
																				class="btn btn-outline-danger btn-sm"
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
														</li>
														<%
															} else {
																				if (user.getUsername().equals(a.getAnnotator()) || a.getAnnotator().equals("NLP")) {
																					none = false;
														%>

														<li class="list-group-item">
															<div class="row">
																<div class="col-8">
																	<%
																		if (a.getAnnotator().equals("NLP")) {
																	%>
																	Annotation: <b><%=a.getPk()%></b> <small> was
																		added <b>automatically using NLP </b>. You have to
																		review this annotation!
																	</small>
																	<%
																		} else {
																	%>
																	Annotation: <b><%=a.getPk()%></b> <small>added
																		on: <b><%=a.getDate()%></b> by: <b><%=a.getAnnotator()%></b>
																		with standard: <b><%=stan.getName()%></b>
																	</small>
																	<%
																		}
																	%>
																</div>
																<div class="col-4">
																	<input type="hidden" name="project"
																		value="<%=p.getPk()%>"> <input type="hidden"
																		name="report" value="<%=r.getPk()%>">
																	<div
																		class="btn-group btn-group-sm btn-group-block float-right">
																		<form action="AnnotateReport" method="post">
																			<input type="hidden" name="project"
																				value="<%=a.getProject_id()%>"> <input
																				type="hidden" name="report"
																				value="<%=a.getReport_id()%>"> <input
																				type="hidden" name="annotation"
																				value="<%=a.getPk()%>"> <input type="hidden"
																				name="standard" value="<%=stan.getName()%>">
																			<%
																				if (a.getAnnotator().equals("NLP")) {
																			%>
																			<button type="submit"
																				class="btn btn-outline-secondary btn-sm float-right">Review</button>
																			<%
																				} else {
																			%>
																			<button type="submit"
																				class="btn btn-outline-primary btn-sm float-right">Edit</button>
																			<%
																				}
																			%>
																		</form>

																		<form action="DeleteAnnotation" method="post">
																			<input type="hidden" name="annotationid"
																				value="<%=a.getPk()%>">
																			<button type="submit"
																				class="btn btn-outline-danger btn-sm"
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
														</li>

														<%
															}
																			}
																		}
																		if (none) {
														%>

														<li class="list-group-item">No annotations made by
															this user</li>
														<%
															}
														%>
													</ul>
												</div>
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
					<%
						}
						}
					%>
				</div>


				<div class="row">
					<div class="col">
						<h5>Finished projects:</h5>
					</div>
				</div>
				<div class="accordion" id="accordionMyFinishedProjects">
					<%
						List<Project> myfinishedprojects = Queries.getProjectsFromUser(user.getUsername(), true);
						if (myfinishedprojects.size() == 0) {
					%>

					<ul class="list-group">
						<li class="list-group-item">No finished projects</li>
					</ul>


					<%
						} else {
							for (Project p : myfinishedprojects) {
								String[] standardids = p.getStandards();
								boolean hasBirads = false;
								String standards = "";
								String std = "";
								for (String s : standardids) {
									if (s.equals("BIRADS")) {
										hasBirads = true;
										standards = standards + " <span class=\"badge badge-secondary\">" + s + "</span>";
									} else {
										standards = standards + " <span class=\"badge badge-dark\">" + s + "</span>";
									}
									if (std.equals("")) {
										std = s;
									} else {
										std = std + "-split-" + s;
									}
								}
								boolean isAutoProject = Queries.isAutoProject(p.getPk());
					%>
					<div class="card">
						<div class="card-header"
							id="myfinishedprojectsheader-<%=p.getPk()%>">
							<div class="row">
								<div class="col-8">
									Project: <b><%=p.getName()%> </b> | Standards: <b><%=standards%></b>
								</div>
								<div class="col-4">
									<div class="btn-group float-right" role="group">
										<%
											if (hasBirads == true && isAutoProject == false) {
										%>
										<form action="AutomaticAnnotationBatch" method="post">
											<input type="hidden" name="project_id" value="<%=p.getPk()%>">
											<button type="submit"
												class="btn btn-outline-secondary btn-sm">Auto
												annotate all BIRADS</button>
										</form>
										<%
											}
										%>
										<%
											if (user.getRights() == Queries.Rights.admin) {
										%>
										<form action="DownloadAnnotations" method="post">
											<input type="hidden" name="projectid" value="<%=p.getPk()%>">
											<button type="submit" class="btn btn-outline-success btn-sm">Download</button>
										</form>
										<%
											}
										%>
										<button class="btn btn-outline-secondary btn-sm" type="button"
											data-toggle="collapse"
											data-target="#collapseproject-<%=p.getPk()%>"
											aria-expanded="false"
											aria-controls="collapseproject-<%=p.getPk()%>">
											Expand</button>
									</div>
								</div>

							</div>
						</div>

						<div id="collapseproject-<%=p.getPk()%>" class="collapse"
							aria-labelledby="myfinishedprojectsheader-<%=p.getPk()%>"
							data-parent="#accordionMyFinishedProjects">
							<div class="card-body">
								<div class="accordion" id="accordionProject-<%=p.getPk()%>">
									<ul class="list-group">

										<%
											String[] reportss = p.getReports();
													for (String reportid : reportss) {
														int rid = Integer.parseInt(reportid);
														Report r = Queries.getReport(rid);
														boolean isAutoAnnotated = Queries.isAutoAnnotated(r.getPk(), p.getPk());
										%>

										<li class="list-group-item">
											<div class="row">
												<div class="col-5">
													Report: <b><%=r.getPkString()%></b> <small>added
														on: <b><%=r.getDate()%></b> by: <b><%=r.getUploader()%></b>
													</small>
												</div>
												<div class="col-2">
													<%
														if (hasBirads == true && isAutoAnnotated == false) {
													%>
													<form action="AutomaticAnnotationReport" method="post">
														<input type="hidden" name="project_id"
															value="<%=p.getPk()%>"> <input type="hidden"
															name="report_id" value="<%=r.getPk()%>">
														<button type="submit"
															class="btn btn-outline-secondary btn-sm btn-block"
															id="autoAnnReport-<%=p.getPk()%>-<%=r.getPk()%>"
															style="display: none">Auto annotate BIRADS</button>
													</form>
													<%
														}
													%>

												</div>
												<div class="col-5">
													<form action="AnnotateReport" method="post">
														<div class="row">
															<div class="col-6">
																<select name="standard"
																	id="<%=p.getPk()%>-<%=r.getPk()%>"
																	class="selectpicker form-control form-control-sm form-control-block selectMyCurrentStandard"
																	title="Choose standard..." required>
																	<%
																		for (String s : standardids) {
																						Standard standard = Queries.getStandard(s);
																	%>
																	<option><%=standard.getName()%></option>
																	<%
																		}
																	%>
																</select>
															</div>

															<div class="col-6">
																<input type="hidden" name="project"
																	value="<%=p.getPk()%>"> <input type="hidden"
																	name="report" value="<%=r.getPk()%>"> <input
																	type="hidden" name="annotation" value="notset">
																<div
																	class="btn-group btn-group-sm btn-group-block float-right">
																	<button type="submit"
																		class="btn btn-outline-primary  btn-sm btn-block">Annotate</button>
																	<%
																		List<Annotated> annotations = Queries.getAnnotations(r.getPk(), p.getPk());
																					if (annotations.size() != 0) {
																	%>

																	<button class="btn btn-outline-secondary btn-sm"
																		type="button" data-toggle="collapse"
																		data-target="#annotationDiv-<%=r.getPkString()%>"
																		aria-expanded="false"
																		aria-controls="annotationDiv-<%=r.getPkString()%>">
																		Annotations</button>
																	<%
																		} else {
																	%>
																	<span class="d-inline-block" tabindex="0"
																		data-toggle="tooltip" data-placement="right"
																		title="No annotations at all!">

																		<button class="btn btn-outline-secondary btn-sm"
																			style="pointer-events: none;" disabled>
																			Annotations</button>
																	</span>

																	<%
																		}
																	%>
																</div>
															</div>
														</div>
													</form>
												</div>
											</div>

											<div id="annotationDiv-<%=r.getPkString()%>" class="collapse">
												<div class="card-body">
													<ul class="list-group">

														<%
															boolean none = true;
																		for (Annotated a : annotations) {
																			Standard stan = Queries.getStandard(a.getStandard_id());
																			if (user.getRights() == Queries.Rights.admin) {
																				none = false;
														%>
														<li class="list-group-item">
															<div class="row">
																<div class="col-8">
																	<%
																		if (a.getAnnotator().equals("NLP")) {
																	%>
																	Annotation: <b><%=a.getPk()%></b> <small> was
																		added <b>automatically using NLP </b>. You have to
																		review this annotation!
																	</small>
																	<%
																		} else {
																	%>
																	Annotation: <b><%=a.getPk()%></b> <small>added
																		on: <b><%=a.getDate()%></b> by: <b><%=a.getAnnotator()%></b>
																		with standard: <b><%=stan.getName()%></b>
																	</small>
																	<%
																		}
																	%>
																</div>
																<div class="col-4">
																	<input type="hidden" name="project"
																		value="<%=p.getPk()%>"> <input type="hidden"
																		name="report" value="<%=r.getPk()%>">
																	<div
																		class="btn-group btn-group-sm btn-group-block float-right">
																		<form action="AnnotateReport" method="post">
																			<input type="hidden" name="project"
																				value="<%=a.getProject_id()%>"> <input
																				type="hidden" name="report"
																				value="<%=a.getReport_id()%>"> <input
																				type="hidden" name="annotation"
																				value="<%=a.getPk()%>"> <input type="hidden"
																				name="standard" value="<%=stan.getName()%>">
																			<%
																				if (a.getAnnotator().equals("NLP")) {
																			%>
																			<button type="submit"
																				class="btn btn-outline-secondary btn-sm float-right">Review</button>
																			<%
																				} else {
																			%>
																			<button type="submit"
																				class="btn btn-outline-primary btn-sm float-right">Edit</button>
																			<%
																				}
																			%>
																		</form>

																		<form action="DeleteAnnotation" method="post">
																			<input type="hidden" name="annotationid"
																				value="<%=a.getPk()%>">
																			<button type="submit"
																				class="btn btn-outline-danger btn-sm"
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
														</li>
														<%
															} else {
																				if (user.getUsername().equals(a.getAnnotator()) || a.getAnnotator().equals("NLP")) {
																					none = false;
														%>

														<li class="list-group-item">
															<div class="row">
																<div class="col-8">
																	<%
																		if (a.getAnnotator().equals("NLP")) {
																	%>
																	Annotation: <b><%=a.getPk()%></b> <small> was
																		added <b>automatically using NLP </b>. You have to
																		review this annotation!
																	</small>
																	<%
																		} else {
																	%>
																	Annotation: <b><%=a.getPk()%></b> <small>added
																		on: <b><%=a.getDate()%></b> by: <b><%=a.getAnnotator()%></b>
																		with standard: <b><%=stan.getName()%></b>
																	</small>
																	<%
																		}
																	%>
																</div>
																<div class="col-4">
																	<input type="hidden" name="project"
																		value="<%=p.getPk()%>"> <input type="hidden"
																		name="report" value="<%=r.getPk()%>">
																	<div
																		class="btn-group btn-group-sm btn-group-block float-right">
																		<form action="AnnotateReport" method="post">
																			<input type="hidden" name="project"
																				value="<%=a.getProject_id()%>"> <input
																				type="hidden" name="report"
																				value="<%=a.getReport_id()%>"> <input
																				type="hidden" name="annotation"
																				value="<%=a.getPk()%>"> <input type="hidden"
																				name="standard" value="<%=stan.getName()%>">
																			<%
																				if (a.getAnnotator().equals("NLP")) {
																			%>
																			<button type="submit"
																				class="btn btn-outline-secondary btn-sm float-right">Review</button>
																			<%
																				} else {
																			%>
																			<button type="submit"
																				class="btn btn-outline-primary btn-sm float-right">Edit</button>
																			<%
																				}
																			%>
																		</form>

																		<form action="DeleteAnnotation" method="post">
																			<input type="hidden" name="annotationid"
																				value="<%=a.getPk()%>">
																			<button type="submit"
																				class="btn btn-outline-danger btn-sm"
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
														</li>

														<%
															}
																			}
																		}
																		if (none) {
														%>

														<li class="list-group-item">No annotations made by
															this user</li>
														<%
															}
														%>
													</ul>
												</div>
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
					<%
						}
						}
					%>
				</div>



			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>

<script>
	$(document).ready(
			function() {

				//handle the display of auto annot report birads
				$('.selectMyCurrentStandard').each(
						function() {
							$(this).on(
									'change',
									function() {
										var option = $(
												"#" + $(this).attr('id')
														+ " option:selected")
												.text();
										var id = "#autoAnnReport-"
												+ $(this).attr('id');
										if (option == "BIRADS") {
											$(id).show();
										} else {
											$(id).hide();
										}
									});
						});
				//tooltip
				$('[data-toggle="tooltip"]').tooltip();
				
				//add project select
				$('.my-select').selectpicker();
				
				$('[data-toggle=confirmation]').confirmation({
					rootSelector : '[data-toggle=confirmation]',
				// other options
				});

			});
</script>

</html>
<
