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


	<div class="container">
		<div class="card">
			<div class="card-body">
				<h2>Help - Annotator User Manual</h2>
				<p>Below one can find instructions on how to use the Annotator
					platform.</p>



				<div class="row">
					<div class="col-6">
					<div class="card" style="margin: 30px 15px 30px 30px;">
							
							<div class="card-body">
							
								<h3 class="card-title">Login Page</h3>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/sDUncJCg27Y"
									allowfullscreen></iframe>
							</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">&rarr; Login with your
										credentials.</li>
									<li class="list-group-item">&rarr; If you forgot your
										credentials please contact the admin of the platform.</li>
									<li class="list-group-item">&rarr; If you are not signed
										up ask the admin of the platform to sign you up.</li>
								</ul>
							</div>
						</div>
						<div class="card" style="margin: 30px 15px 30px 30px;">
							<div class="card-body">
								<h3 class="card-title">Upload Page</h3>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/KZwRlTn0DQI"
									allowfullscreen></iframe>
							</div>
								<div class="card-header">
									<b>Add PlainText</b>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">&rarr; In this section one can
										manually type reports.</li>
									<li class="list-group-item">&rarr; Add the report to an
										existing collection from the dropdown. *(not obligatory)</li>
									<li class="list-group-item">&rarr; Click Add to submit
										your report.</li>
								</ul>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/JCVY21L6UUw"
									allowfullscreen></iframe>
							</div>
								<div class="card-header">
									<b>Add Multiple files </b>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">&rarr; One can upload multiple
										files in pdf or txt format.</li>
									<li class="list-group-item">&rarr; Add the reports to an
										existing collection from the dropdown. *(not obligatory)</li>
									<li class="list-group-item">&rarr; Click Add to upload
										your files.</li>
								</ul>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/UB9KOYjqE-A"
									allowfullscreen></iframe>
							</div>
							
								<div class="card-header">
									<b>Add CSV </b>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">&rarr; One can upload CSV
										file.</li>
									<li class="list-group-item">&rarr; Add the file to a
										collection from the dropdown. *(not obligatory)</li>
									<li class="list-group-item">&rarr; Click Add to submit
										your file.</li>
								</ul>
							</div>
						</div>
						<div class="card" style="margin: 30px 15px 30px 30px;">
							
							<div class="card-body">
								<h3 class="card-title">Annotation Page</h3>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/QLXC77ajpJA"
									allowfullscreen></iframe>
							</div>
								<p class="card-text">In this page one can annotate reports.
									To annotate, select a word, sentence or paragraph with your
									mouse and then select a leaf from the structure tree (Entity
									window) to indicate your annotation.</p>
								<p class="card-text">Select which annotations will be
									displayed on the report by selecting or deselecting the
									checkboxes on the structure tree.</p>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">&rarr; Click on the annotated
										word and details will be displayed in Log window.</li>
									<li class="list-group-item">&rarr; To delete annotation,
										select the annotation and click on Delete annotation button.</li>
									<li class="list-group-item">&rarr; Delete all annotations
										by clicking on Delete All annotations button.</li>
									<li class="list-group-item">&rarr; Save your progress and
										continue to annotate by clicking on Save progress button.</li>
									<li class="list-group-item">&rarr; Save your progress and
										go back to the profile page by clicking on Save annotation &
										Exit.</li>

								</ul>
							</div>
						</div>

					</div>
					<div class="col-6">
						<div class="card" style="margin: 30px 30px 30px 15px;">
							
							<div class="card-body">
								<h3 class="card-title">Profile Page</h3>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/hdZoNbiC_sw"
									allowfullscreen></iframe>
							</div>
								<div class="card-header">
									<b>General Info Tab</b>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">&rarr; Here you can see and
										edit all your credentials except from your rights as a user.</li>
									<li class="list-group-item">&rarr; Change your
										credentials, if needed, and press Save changes.</li>

								</ul>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/SGhqZ8OQU9s"
									allowfullscreen></iframe>
							</div>
								<div class="card-header">
									<b>Projects Tab</b>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">&rarr; Here you can see your
										projects classified as current and finished.</li>
									<li class="list-group-item">&rarr; Note: The standards and
										reports should be added to a project by an administrator
										before they can be used.</li>
									<li class="list-group-item">For each project :</li>
									<li class="list-group-item">&rarr; The label displays the
										name of the project and the standard that can be used.</li>
									<li class="list-group-item">&rarr; Click Download to
										download the project as .CSV (only possible for
										administrators)</li>
									<li class="list-group-item">&rarr; Click on Expand to
										expand and see the reports included in the project.</li>
									<li class="list-group-item">When clicking the Expand
										button:</li>
									<li class="list-group-item">&rarr; Choose the standard
										from the dropdown to annotate the report.</li>
									<li class="list-group-item">&rarr; Click on the Annotate
										button to annotate a new, clear instance of that report.</li>
									<li class="list-group-item">&rarr; To see other
										annotations of a report, click on the Annotations button.</li>
									<li class="list-group-item">When clicking on Annotations
										button:</li>
									<li class="list-group-item">&rarr; Edit an existing
										annotated report by clicking on Edit button.</li>
									<li class="list-group-item">&rarr; Report annotated by you
										could be deleted by Delete button.</li>
								</ul>
								<%
									if (user.getRights() == Queries.Rights.admin) {
								%>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/43YFP-dyCao"
									allowfullscreen></iframe>
							</div>
								<div class="card-header">
									<b>Manage Users Tab</b>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">&rarr; Add new user to the
										system by clicking on Add New.</li>
									<li class="list-group-item">&rarr; Modify users
										credentials by clicking on Modify.</li>
									<li class="list-group-item">&rarr; Delete user by clicking
										on Delete.</li>
								</ul>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/qLJpLubGco0"
									allowfullscreen></iframe>
							</div>
								<div class="card-header">
									<b>Manage Standards Tab</b>
								</div>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">&rarr; Add new standard to the
										system by clicking on Add New.</li>
									<li class="list-group-item">&rarr; Modify standard by
										clicking on Modify.</li>
									<li class="list-group-item">&rarr; Delete standard by
										clicking on Delete.</li>
								</ul>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/F4quSfuOhQA"
									allowfullscreen></iframe>
							</div>
								<div class="card-header">
									<b>Manage Projects Tab</b>
								</div>
								<p class="card-text">A project contains reports, annotation
									standards and users. Each user can see his/her projects and
									annotate the reports with the available standards. It is the
									way an administrator assigns jobs to users. In a project the
									administrator can set the name, the users that allowed to see
									and edit the reports, the standards to be used in a project,
									collections of reports or single reports to be annotated and
									whether the project is finished or not.</p>
								<ul class="list-group list-group-flush">
									<li class="list-group-item">&rarr; Add new project to the
										system by clicking on Add New.</li>
									<li class="list-group-item">&rarr; Modify projects details
										by clicking on Modify.</li>
									<li class="list-group-item">&rarr; Delete project by
										clicking on Delete.</li>
								</ul>
								<div class="embed-responsive embed-responsive-16by9 card-img-top">
								<iframe class="embed-responsive-item"
									src="https://www.youtube.com/embed/buwAFEM4Ryo"
									allowfullscreen></iframe>
							</div>
								<div class="card-header">
									<b>Manage Reports Tab</b>
								</div>
								<p class="card-text">One can find collections of reports as
									well as reports that are not in collection.</p>
								<ul class="list-group list-group-flush">

									<li class="list-group-item">Reports that are not in
										collection:</li>
									<li class="list-group-item">&rarr; Click on Expand to see
										reports that do not belong in any collection.</li>
									<li class="list-group-item">&rarr; View report by clicking
										on View Text.</li>
									<li class="list-group-item">&rarr; You can add the stand
										alone reports to collections by clicking on Edit
										report-collection.</li>
									<li class="list-group-item">&rarr; Delete a report with
										Delete button.</li>

									<li class="list-group-item">For Collections:</li>
									<li class="list-group-item">&rarr; Click on Expand to see
										reports in the collection.</li>
									<li class="list-group-item">&rarr; Click on Delete to
										delete a collection.</li>
									<li class="list-group-item">&rarr; View report by clicking
										on View Text.</li>
									<li class="list-group-item">&rarr; Delete a report with
										Delete button.</li>

								</ul>
								<%
									}
								%>


							</div>
						</div>


					</div>
				</div>
			</div>
		</div>
		<jsp:include page="footer.jsp" />
	</div>
</body>