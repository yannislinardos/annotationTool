<%@ page import="util.*" import="beans.*" import="java.util.List"%>
<jsp:include page="header.jsp" />

<!-- ----------REDIRECT if not logged in -------------------->

<%
	User user = LoginDao.getToken(request.getCookies());

	if (user == null) {
		response.sendRedirect("Login.jsp");
		user = new User();
	}
	
	List<Collection> collections = Queries.getAllCollections();

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
				<h2>Manage Reports/Collections</h2>
				<div class="container">
					<div class="row">
						<div class="col">
							<h5>Existing reports:</h5>
						</div>
					</div>
					<div class="accordion" id="accordionCollections">
						<div class="card">
							<div class="card-header" id="collectionheader-NoCollection">
								<div class="row">
									<div class="col">Reports that are not in collection</div>
									<div class="col">
										<div class="btn-group float-right" role="group">
											<button class="btn btn-outline-secondary btn-sm"
												type="button" data-toggle="collapse"
												data-target="#collapsecollection-NoCollection"
												aria-expanded="false"
												aria-controls="collapsecollection-NoCollection">
												Expand</button>

										</div>
									</div>

								</div>
							</div>

						</div>

						<div id="collapsecollection-NoCollection" class="collapse"
							aria-labelledby="collectionheader-NoCollection"
							data-parent="#accordionCollections">
							<div class="card-body">
								<div class="accordion" id="accordionCollection-NoCollection">

									<%
																List<Report> reportss = Queries.getAllReportsWithoutCollection();
																	for (Report r : reportss) {
															%>

									<div class="card">
										<div class="card-header"
											id="reportheader-<%=r.getPkString()%>">
											<div class="row">
												<div class="col-4">
													Report: <b><%=r.getPkString()%></b> <small>added
														on: <b><%=r.getDate()%></b> by: <b><%=r.getUploader()%></b>
													</small>
												</div>
												<div class="col-8">

													<div class="btn-group float-right" role="group">

														<button class="btn btn-outline-secondary btn-sm"
															type="button" data-toggle="collapse"
															data-target="#reportDiv-<%=r.getPkString()%>"
															aria-expanded="false"
															aria-controls="reportDiv-<%=r.getPkString()%>">
															View text</button>

														<button class="btn btn-outline-success btn-sm"
															type="button" data-toggle="collapse"
															data-target="#moveDiv-<%=r.getPkString()%>"
															aria-expanded="false"
															aria-controls="moveDiv-<%=r.getPkString()%>">
															Edit report-collection</button>


														<form action="DeleteReport" method="post">
															<input type="hidden" name="id" value="<%=r.getPk()%>">
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
										</div>

										<div id="moveDiv-<%=r.getPkString()%>" class="collapse"
											data-parent="#accordionCollection-NoCollection">
											<div class="card-body">
												<form action="MoveToCollection" method="post">
													<div class="form-group row">
														<label class="col-lg-3 col-form-label form-control-label">Select
															Collections to move this report to</label>
														<div class="col-lg-9">
															<input type="hidden" name="collid" value=""> <input
																type="hidden" name="reportid" value="<%=r.getPk()%>">
															<select name="collections" multiple class="form-control"
																required>
																<%
																							/* 																List<Collection> collections = Queries.getAllCollections();
																																																							 */
																									for (Collection coll : collections) {
																						%>
																<option><%=coll.getName()%></option>
																<%
																							}
																						%>
															</select>
														</div>
													</div>

													<button type="submit"
														class="btn btn-outline-success btn-sm btn-block">Move
														to collection</button>
												</form>

											</div>
										</div>

										<div id="reportDiv-<%=r.getPkString()%>" class="collapse"
											aria-labelledby="reportheader-<%=r.getPkString()%>"
											data-parent="#accordionCollection-NoCollection">
											<div class="card-body">
												<%=r.getContent()%>
											</div>
										</div>



									</div>

									<%
																}
															%>
								</div>

							</div>
						</div>


						<%
													/* 						List<Collection> collections = Queries.getAllCollections();
																													 */
														for (Collection c : collections) {
												%>
						<div class="card">
							<div class="card-header" id="collectionheader-<%=c.getId()%>">
								<div class="row">
									<div class="col">
										<b><%=c.getName()%> </b> <small> added by: <u><%=c.getUploader()%></u></small>
									</div>
									<div class="col">
										<div class="btn-group float-right" role="group">
											<button class="btn btn-outline-secondary btn-sm"
												type="button" data-toggle="collapse"
												data-target="#collapsecollection-<%=c.getId()%>"
												aria-expanded="false"
												aria-controls="collapsecollection-<%=c.getId()%>">
												Expand</button>
											<form action="DeleteCollection" method="post">
												<input type="hidden" name="id" value="<%=c.getId()%>">
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
							</div>

							<div id="collapsecollection-<%=c.getId()%>" class="collapse"
								aria-labelledby="collectionheader-<%=c.getId()%>"
								data-parent="#accordionCollections">
								<div class="card-body">
									<div class="accordion" id="accordionCollection-<%=c.getId()%>">

										<%
																	List<Integer> reports = Queries.getReportIDs(c.getId());
																			for (int reportid : reports) {
																				Report r = Queries.getReport(reportid);
																%>

										<div class="card">
											<div class="card-header"
												id="reportheader-<%=r.getPkString()%>">
												<div class="row">
													<div class="col-4">
														Report: <b><%=r.getPkString()%></b> <small>added
															on: <b><%=r.getDate()%></b> by: <b><%=r.getUploader()%></b>
														</small>
													</div>
													<div class="col-8">

														<div class="btn-group float-right" role="group">

															<button class="btn btn-outline-secondary btn-sm"
																type="button" data-toggle="collapse"
																data-target="#reportDiv-<%=r.getPkString()%>"
																aria-expanded="false"
																aria-controls="reportDiv-<%=r.getPkString()%>">
																View text</button>

															<button class="btn btn-outline-success btn-sm"
																type="button" data-toggle="collapse"
																data-target="#moveDiv-<%=r.getPkString()%>"
																aria-expanded="false"
																aria-controls="moveDiv-<%=r.getPkString()%>">
																Edit report-collection</button>


															<form action="DeleteReport" method="post">
																<input type="hidden" name="id" value="<%=r.getPk()%>">
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
											</div>

											<div id="moveDiv-<%=r.getPkString()%>" class="collapse"
												data-parent="#accordionCollection-<%=c.getId()%>">
												<div class="card-body">
													<form action="MoveToCollection" method="post">
														<div class="form-group row">
															<label class="col-lg-3 col-form-label form-control-label">Select
																Collections to move this report to</label>
															<div class="col-lg-9">
																<input type="hidden" name="collid"
																	value="<%=c.getId()%>"> <input type="hidden"
																	name="reportid" value="<%=r.getPk()%>"> <select
																	name="collections" multiple class="form-control"
																	required>
																	<%
																								for (Collection coll : collections) {
																							%>
																	<option><%=coll.getName()%></option>
																	<%
																								}
																							%>
																</select>
															</div>
														</div>

														<button type="submit"
															class="btn btn-outline-success btn-sm btn-block">Move
															to collection</button>
													</form>
													<form action="DeleteFromCollection" method="post">
														<input type="hidden" name="collid" value="<%=c.getId()%>">
														<input type="hidden" name="reportid"
															value="<%=r.getPk()%>">
														<button type="submit"
															class="btn btn-outline-danger btn-sm btn-block"
															data-toggle="confirmation" data-singleton="true"
															data-popout="true" data-title="Are you sure?"
															data-content="This process cannot be undone."
															data-btn-ok-label="Continue"
															data-btn-ok-class="btn btn-sm btn-outline-danger"
															data-btn-cancel-label="Cancel"
															data-btn-cancel-class="btn btn-sm btn-outline-secondary">
															Remove from collection</button>
													</form>
												</div>
											</div>

											<div id="reportDiv-<%=r.getPkString()%>" class="collapse"
												aria-labelledby="reportheader-<%=r.getPkString()%>"
												data-parent="#accordionCollection-<%=c.getId()%>">
												<div class="card-body">
													<%=r.getContent()%>
												</div>
											</div>

										</div>

										<%
																	}
																%>
									</div>

								</div>
							</div>
						</div>
						<%
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
			
//add project select
$('.my-select').selectpicker();

$('[data-toggle=confirmation]').confirmation({
	rootSelector : '[data-toggle=confirmation]',
// other options
});
		});
</script>

</html>