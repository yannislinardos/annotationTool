<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="container">
	<div id="success-alert" class="alert alert-success" role="alert"
		style="display: none">
		<strong>Great!</strong>
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
	<div id="danger-alert" class="alert alert-danger" role="alert"
		style="display: none">
		<strong>Something went wrong!</strong> Changes not saved!
		<button type="button" class="close" data-dismiss="alert"
			aria-label="Close">
			<span aria-hidden="true">&times;</span>
		</button>
	</div>
</div>

<script>
	$(document).ready(
					function() {
						//alerts management
						var checkVariable = "${check}";
						
						if(checkVariable == "true") {
							$("#success-alert").fadeTo(2000, 500).slideUp(500, function() {
								$("#success-alert").slideUp(500);
							});
						} else if (checkVariable == "false") {
							$("#danger-alert").fadeTo(2000, 500).slideUp(500, function() {
								$("#danger-alert").slideUp(500);
							});
						} else if (checkVariable != ""){
							$('#success-alert').append(checkVariable + "!");
							$("#success-alert").fadeTo(2000, 500).slideUp(500, function() {
								$("#success-alert").slideUp(500);
							});
						}
	});

</script>