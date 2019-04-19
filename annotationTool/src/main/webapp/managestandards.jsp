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
				<h2>Manage Standards</h2>
				<!-- Standards -->

				<div id="addNewStandard" class="collapse">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">Add new standard</h5>
							<form class="validatedFormAddStandard" role="form"
								name="addStandardForm" action="CreateStandard" onSubmit=""
								method="post">
								<div class="row">
									<div class="col-3">
										<div id="rootGroup" class="btn-group-vertical">
											<input id="rootName" class="form-control form-control-sm"
												type="text" placeholder="Name of structure" name="name">
											<button id="setRoot" type="button"
												class="btn btn-outline-success btn-sm">Create</button>
										</div>
										<div style="display: none" id="instructions">
											<p>Instructions:</p>
											<p>Right click on a node to make an action!</p>

										</div>
									</div>
									<div class="col-9">
										<div id="jstreecontainer"></div>
									</div>
								</div>
								<div class="row">
									<div class="col">
										<div class="btn-group float-right" role="group"
											aria-label="Basic example">
											<button id="cancelAddStandard" type="button"
												class="btn btn-outline-danger btn-sm" data-toggle="collapse"
												data-target="#addNewStandard">Cancel</button>
											<input id="standard" type="hidden" name="standard" value="">
											<input type="submit" id="saveTree"
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
							<h5>Existing standards:</h5>
						</div>
						<div class="col float-right">
							<button id="addNewStandardButton" style="display: block"
								class="btn btn-outline-primary btn-sm float-right"
								data-toggle="collapse" data-target="#addNewStandard">Add
								new</button>
						</div>

					</div>

					<!--Showing standards-->
					<ul class="list-group">

						<li class="list-group-item">

							<div class="row">
								<div class="col-3">
									<h6>Name</h6>
								</div>
								<div class="col-2">
									<h6></h6>
								</div>
								<div class="col-7">
									<input id="searchstandards" class="float-right"
										name="searchstandards" placeholder="Search for name"
										type="text" data-list=".standardslist">
								</div>
							</div>
						</li>
					</ul>

					<div class="accordion" id="accordionStandards">
						<ul class="list-group standardslist">
							<%
								List<Standard> standards = Queries.getAllStandards();
								for (Standard s : standards) {
							%>
							<li class="list-group-item">
								<div class="row">
									<div class="col-3">
										<p><%=s.getName()%></p>
									</div>
									<div class="col-2">
										<p></p>
									</div>
									<div class="col-7">
										<div class="btn-group float-right" role="group"
											aria-label="Basic example">
											<%
												if (s.getName().equals("BIRADS")) {
											%>
											<form action="CopyStandard" method="post">
												<input type="hidden" name="id" value="<%=s.getPk()%>">
												<input type="hidden" name="name" value="<%=s.getName()%>">
												<button type="submit" class="btn btn-outline-secondary btn-sm"
													>Copy</button>
											</form>
											<button
												class="btn btn-outline-primary btn-sm modifyStandardToggler"
												type="button" data-toggle="collapse"
												data-target="#collapse-<%=s.getName()%>"
												aria-expanded="false"
												aria-controls="collapse-<%=s.getName()%>"
												id="<%=s.getName()%>" disabled>Modify</button>
											<form action="DeleteStandard" method="post">
												<input type="hidden" name="name" value="<%=s.getName()%>">
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
											<form action="CopyStandard" method="post">
												<input type="hidden" name="id" value="<%=s.getPk()%>">
												<input type="hidden" name="name" value="<%=s.getName()%>">
												<button type="submit" class="btn btn-outline-secondary btn-sm"
													>Copy</button>
											</form>
											<button
												class="btn btn-outline-primary btn-sm modifyStandardToggler"
												type="button" data-toggle="collapse"
												data-target="#collapse-<%=s.getName()%>"
												aria-expanded="false"
												aria-controls="collapse-<%=s.getName()%>"
												id="<%=s.getName()%>">Modify</button>
											<form action="DeleteStandard" method="post">
												<input type="hidden" name="name" value="<%=s.getName()%>">
												<button type="submit" class="btn btn-outline-danger btn-sm"
													data-toggle="confirmation" data-singleton="true"
													data-popout="true" data-title="Are you sure?"
													data-content="This process cannot be undone."
													data-btn-ok-label="Continue"
													data-btn-ok-class="btn btn-sm btn-outline-danger"
													data-btn-cancel-label="Cancel"
													data-btn-cancel-class="btn btn-sm btn-outline-secondary">Delete</button>
											</form>
											<%
												}
											%>
										</div>
									</div>
								</div>
								<div class="collapse changeStandard"
									id="collapse-<%=s.getName()%>"
									data-parent="#accordionStandards">
									<form role="form"
										class="validatedFormChangeStandard-<%=s.getName()%>"
										name="modifyStandardForm" action="ModifyStandard"
										method="post">

										<div class="treedata" id="get-<%=s.getName()%>"
											style="display: none"><%=s.getJson()%></div>

										<div class="row">
											<div class="col-3">
												<h6>
													<b>Change name:</b>
												</h6>
												<input type="text" name="name" value="<%=s.getName()%>">
												<h6>Right click on a node to make an action!</h6>
											</div>
											<div class="col-9">
												<div id="displaytree-<%=s.getName()%>"></div>
											</div>
										</div>

										<div class="row">
											<div class="col float-right">
												<input type="hidden" name="id" value="<%=s.getPk()%>">
												<input id="hidden-<%=s.getName()%>" type="hidden"
													name="json"> <input id="saveTree-<%=s.getName()%>"
													type="submit"
													class="btn btn-outline-primary btn-sm btn-block modifyTree"
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
	$(document).ready(function() {
		//toggle new standard with cancel
		$("#addNewStandardButton").click(function() {
			$("#addNewStandardButton").hide();
		});
		$("#cancelAddStandard").click(function() {
			$("#addNewStandardButton").show();
		});

		$('#searchstandards').hideseek({
			nodata : 'No results found',
			ignore : '.changeStandard'
		});

		//display modify standard
		$(".treedata").each(function() {
			var id = this.id;
			var json = $("#" + id).text();
			id = id.substring(4);
			json = $.parseJSON(json);

			$("#displaytree-" + id).jstree({
				"plugins" : [ "dnd", "contextmenu", "types" ],
				"core" : {
					"check_callback" : true, // enable all modifications
					"data" : json
				}
			});
		});

		//save modify standard
		$(".modifyTree").click(function() {

			var id = this.id.substring(9);
			var v = $("#displaytree-" + id).jstree(true).get_json('#', {
				no_state : false,
				no_id : true,
				no_children : false,
				no_data : false,
				no_li_attr : true,
				no_a_attr : true,
				flat : false
			})
			var mytext = JSON.stringify(v);
			$("#hidden-" + id).val(mytext);
		});

		$("#setRoot").click(function() {
			var name = $('#rootName').val();
			$("#jstreecontainer").jstree({
				"plugins" : [ "dnd", "contextmenu", "types" ],
				"core" : {
					"check_callback" : true, // enable all modifications
					"data" : [ {
						"text" : name,
						"icon" : "images/network.png"
					} ]
				}
			});
			$("#rootGroup").hide();
			$("#instructions").show();
		});

		//save the new standard
		$("#saveTree").click(function() {
			var v = $('#jstreecontainer').jstree(true).get_json('#', {
				no_state : false,
				no_id : true,
				no_children : false,
				no_data : false,
				no_li_attr : true,
				no_a_attr : true,
				flat : false
			})
			var mytext = JSON.stringify(v);
			$("#standard").val(mytext);
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