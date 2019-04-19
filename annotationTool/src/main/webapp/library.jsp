<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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

	<jsp:include page="navbar.jsp" />


	<!-- Library -->
	<div class="container">
		<div class="card">
			<div class="card-body">
				<h2>Library</h2>

				<div class="accordion" id="accordionCollections">
					<%
						List<Collection> collections = Queries.getAllCollections();
						for (Collection c : collections) {
					%>
					<div class="card">
						<div class="card-header" id="collectionheader-<%=c.getId()%>">
							<h2 class="mb-0">
								<button class="btn" type="button"
									data-toggle="collapse"
									data-target="#collapsecollection-<%=c.getId()%>"
									aria-expanded="false"
									aria-controls="collapsecollection-<%=c.getId()%>">
									<%
										if (c.getName().equals("not_in_collection")) {
									%>
									Reports that aren't in any collection
									<%
										} else {
									%>
									<b><%=c.getName()%> </b> <small> added by: <u> <%=c.getUploader()%> </u></small>
									<%
										}
									%>
								</button>
							</h2>
						</div>

						<div id="collapsecollection-<%=c.getId()%>" class="collapse"
							aria-labelledby="collectionheader-<%=c.getId()%>"
							data-parent="#accordionCollections">
							<div class="card-body">
								<div class="accordion" id="accordionCollection-<%=c.getId()%>">
									<%
										List<Integer> reports = Queries.getReportIDs(c.getId());
											for (int report_id : reports) {
												Report r = Queries.getReport(report_id);
									%>
									<div class="card">
										<div class="card-header"
											id="reportheader-<%=r.getPkString()%>">
											<h2 class="mb-0">
												<button class="btn" type="button"
													data-toggle="collapse"
													data-target="#reportDiv-<%=r.getPkString()%>"
													aria-expanded="false"
													aria-controls="reportDiv-<%=r.getPkString()%>">
													Report: <b><%=r.getPkString()%></b> <small>added
														on: <b><%=r.getDate()%></b> <%
 	if (c.getName().equals("not_in_collection")) {
 %> by: <b><%=r.getUploader()%></b> <%
 	}
 %>
													</small>
												</button>
											</h2>
										</div>

										<div id="reportDiv-<%=r.getPkString()%>" class="collapse"
											aria-labelledby="reportheader-<%=r.getPkString()%>"
											data-parent="#accordionCollection-<%=c.getId()%>">
											<div class="card-body">
												<ul class="list-group">

													<li class="list-group-item">
														<div class="row">
															<div class="col-9">
																<p><b>Comments: </b><%=r.getComments()%></p>
															</div>
															<div class="col-3">
																<div class="btn-group float-right" role="group">
																	<button type="button" class="btn btn-outline-success">Annotate</button>
																	<button type="button" class="btn btn-outline-danger">Delete</button>
																</div>
															</div>
														</div>
													</li>
													<li class="list-group-item">Annotation 1 made by:
														admin on: 25.04.2019 with: BIRADS
														<div class="btn-group float-right" role="group">
															<button type="button"
																class="btn btn-outline-primary btn-sm">Modify/View</button>
															<button type="button"
																class="btn btn-outline-danger btn-sm">Delete</button>
														</div>
													</li>
													<li class="list-group-item">Annotation 2 made by:
														mihaimdm on: 25.04.2019 with: BIRADS
														<div class="btn-group float-right" role="group">
															<button type="button"
																class="btn btn-outline-primary btn-sm">Modify/View</button>
															<button type="button"
																class="btn btn-outline-danger btn-sm">Delete</button>
														</div>
													</li>
												</ul>
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
	</div>

	<jsp:include page="footer.jsp" />

	<script>
		
	</script>
</body>

</html>