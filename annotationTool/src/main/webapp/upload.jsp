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
				<h2>Uploads</h2>
				<div class="accordion" id="accordionUpload">
					<div class="card">
						<div class="card-header" id="plaintextdivheader">
							<h2 class="mb-0">
								<button class="btn btn-outline-dark btn-block" type="button"
									data-toggle="collapse" data-target="#collapseplaintextDiv"
									aria-expanded="false" aria-controls="collapseplaintextDiv">Add
									plaintext</button>
							</h2>
						</div>

						<div id="collapseplaintextDiv" class="collapse"
							aria-labelledby="plaintextdivheader"
							data-parent="#accordionUpload">
							<div class="card-body">
								<form role="form" class="" name="" action="UploadText" method="post">
									<div class="form-group">
										<label for="plaintext">Text that has to be annotated:</label>
										<textarea name="content" class="form-control" id="plaintext" rows="10"
											placeholder="Enter text here." required></textarea>
									</div>
									<div class="form-group">
										<label for="addCollectionCsv">Collection:</label>
										<input id="addCollectionCsv" name="collectionname"class="form-control" type="text" placeholder="Enter new collection name or/and select from the existing ones:">
										
										<select class="selectpicker form-control" multiple
										 name="collections">
										<%
											List<Collection> collections = Queries.getAllCollections();
											for (Collection c : collections) {
										%>
											<option><%=c.getName()%></option>
										<%
											}
										%>
										</select>
									</div>
									<input type="hidden" name="username" value="<%=user.getUsername()%>">
									<input type="submit" class="btn btn-outline-primary btn-sm"
										value="Add">
								</form>
							</div>
						</div>
					</div>

					<div class="card">
						<div class="card-header" id="multipletextdivheader">
							<h2 class="mb-0">
								<button class="btn btn-outline-dark btn-block" type="button"
									data-toggle="collapse" data-target="#collapsemultipletextDiv"
									aria-expanded="false" aria-controls="collapsemultipletextDiv">Add
									multiple files</button>
							</h2>
						</div>

						<div id="collapsemultipletextDiv" class="collapse"
							aria-labelledby="multipletextdivheader"
							data-parent="#accordionUpload">
							<div class="card-body">
								<form role="form" id="addmultipleForm" name="" action="UploadDocuments"
									method="post" enctype="multipart/form-data">
									<div class="alert alert-danger" id="wrongtypemultipleAlert">
										<strong>Wrong extension! </strong> The only extensions allowed
										are: .txt or .pdf .
									</div>
									<div class="alert alert-danger" id="wrongsizemultipleAlert">
										<strong>Wrong size! </strong> The only size allowed
										is smaller than 2MB .
									</div>
									
									<p>The file formats allowed are .txt and .pdf! The size allowed is smaller than 2MB!</p>
									
									<div class="form-group">
										<input name="files" id="filesToUpload" type="file"
											class="form-control-file border" multiple
											onchange="makeFileList();" required>
									</div>


									<label for="fileList">Texts that have to be annotated:</label>
									<ul class="list-group" id="fileList">
										<li class="list-group-item">No files selected</li>
									</ul>
									<div class="form-group">
										<label for="addCollectionCsv">Collection:</label>
										<input id="addCollectionCsv" name="collectionname"class="form-control" type="text" placeholder="Enter new collection name or/and select from the existing ones:">
										
										<select class="selectpicker form-control" multiple
											 name="collections">
										<%
											for (Collection c : collections) {
										%>
											<option><%=c.getName()%></option>
										<%
											}
										%>
										</select>
									</div>
									<input type="hidden" name="username" value="<%=user.getUsername()%>">
									<input type="submit" class="btn btn-outline-primary btn-sm"
										value="Add">
								</form>
							</div>
						</div>
					</div>

					<div class="card">
						<div class="card-header" id="csvdivheader">
							<h2 class="mb-0">
								<button class="btn btn-outline-dark btn-block" type="button"
									data-toggle="collapse" data-target="#csvDiv"
									aria-expanded="false" aria-controls="csvDiv">Add csv</button>
							</h2>
						</div>

						<div id="csvDiv" class="collapse" aria-labelledby="csvdivheader"
							data-parent="#accordionUpload">
							<div class="card-body">
								<div class="alert alert-danger" id="wrongtypeCsvAlert">
									<strong>Wrong extension! </strong> The only extension allowed
									is: .csv .
								</div>
								<div class="alert alert-danger" id="wrongsizeCsvAlert">
									<strong>Wrong size! </strong> The only size allowed
									is lower than 20 MB .
								</div>

								<p>The file format allowed is .csv! The size allowed is smaller than 20MB!</p>

								<form role="form" id="addcsvForm" name="" action="UploadCsv"
									method="post" enctype="multipart/form-data">
									<div class="form-group">
										<input name="csvfile" id="csvfiles" type="file"
											class="form-control-file border" onchange="" required>
									</div>
									<div class="form-group">
										<label for="addCollectionCsv">Collection:</label>
										<input id="addCollectionCsv" name="collectionname"class="form-control" type="text" placeholder="Enter new collection name or/and select from the existing ones:">
										
										<select class="selectpicker form-control" multiple
											 name="collections">
										<%
											for (Collection c : collections) {
										%>
											<option><%=c.getName()%></option>
										<%
											}
										%>
										</select>
									</div>
									<input type="hidden" name="username" value="<%=user.getUsername()%>">
									<input type="submit" class="btn btn-outline-primary btn-sm"
										value="Add">
								</form>
							</div>
						</div>
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
						//multipletextsubmit
						$("#wrongsizemultipleAlert").hide();
						$("#wrongtypemultipleAlert").hide();
						$('#addmultipleForm')
								.on(
										'submit',
										function(e) {
											e.preventDefault();
											var fileExtension = [ 'txt', 'pdf' ];
											var wrong = false;
											var size = false;
											for (var i = 0; i < $('#filesToUpload').get(0).files.length; ++i) {
												if ($.inArray($('#filesToUpload').get(0).files[i].name.split('.').pop().toLowerCase(), fileExtension) == -1) {
													wrong=true;
													
												}
												//CHANGE HERE FILESIZE
												if($('#filesToUpload').get(0).files[i].size >= 2000000) {
													size=true;
												}
 											}
											if(wrong == true || size == true) {
												if(wrong == true) {
													$("#wrongtypemultipleAlert").fadeTo(2000, 500)
													.slideUp(
															500,
															function() {
																$("#wrongtypemultipleAlert")
																		.slideUp(500);
															});
												}
												if(size == true) {
													$("#wrongsizemultipleAlert").fadeTo(2000, 500)
													.slideUp(
															500,
															function() {
																$("#wrongsizemultipleAlert")
																		.slideUp(500);
															});
												}
											} else {
												this.submit();
											}
										});

						//csv submit
						$("#wrongtypeCsvAlert").hide();
						$("#wrongsizeCsvAlert").hide();

						$('#addcsvForm').on(
								'submit',
								function(e) {
									e.preventDefault();
									var fileExtension = [ 'csv' ];
									if ($.inArray($("#csvfiles").val().split(
											'.').pop().toLowerCase(),
											fileExtension) == -1) {
										$("#wrongtypeCsvAlert").fadeTo(2000,
												500).slideUp(
												500,
												function() {
													$("#wrongtypeCsvAlert")
															.slideUp(500);
												});
										//CHANGE HERE CSV FILESZIE
									} else if($("#csvfiles").get(0).files[0].size >= 20000000) {
										
										$("#wrongsizeCsvAlert").fadeTo(2000,
												500).slideUp(
												500,
												function() {
													$("#wrongsizeCsvAlert")
															.slideUp(500);
												});
									} else {
										this.submit();
									}
								});
						
	});

	function makeFileList() {

		var input = document.getElementById("filesToUpload");
		var ul = document.getElementById("fileList");
		while (ul.hasChildNodes()) {
			ul.removeChild(ul.firstChild);
		}

		for (var i = 0; i < input.files.length; i++) {
			var li = document.createElement("li");
			li.className = 'list-group-item';
			li.innerHTML = input.files[i].name;
			ul.appendChild(li);
		}
		if (!ul.hasChildNodes()) {
			var li = document.createElement("li");
			li.className = 'list-group-item';
			li.innerHTML = 'No Files Selected';
			ul.appendChild(li);
		}
	}
</script>

</html>