<jsp:include page="header.jsp"/>

<body>

	<jsp:include page="navbar.jsp"/>
	

  <!-- Standards -->
  <div class="container">
    <div class="card">
      <div class="card-body">
        <div class="row">
          <div class="col">
            <h3>Standards</h3>
          </div>
          <div class="col">
            <button id="addNewStandard" style="display:block" class="btn btn-outline-primary float-right"
              data-toggle="collapse" data-target="#addNewStandard" onclick="changeVisibilityAdd();">Add
              new</button>
          </div>
        </div>
        <div id="addNewStandard" class="collapse">
          <div class="card">
            <div class="card-body">
              <h5 class="card-title">Add new standard</h5>
              <div class="row">
                <div class="col-3">
                  <div id="rootGroup" class="btn-group-vertical">
                    <input id="rootName" class="form-control form-control-sm" type="text"
                      placeholder="Name of structure">
                    <button id="setRoot" type="button" class="btn btn-outline-success btn-sm">Create</button>
                  </div>
                  <div style="display:none" id="instructions">
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
                  <div class="btn-group float-right" role="group" aria-label="Basic example">
                    <button type="button" class="btn btn-outline-danger" data-toggle="collapse"
                      data-target="#addNewStandard" onclick="changeVisibilityAdd();">Cancel</button>
                    <button id="saveTree" type="button" class="btn btn-outline-success">Save</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <p>Existing standars:</p>
        <div class="table-responsive">
          <table class="table table-striped">
            <thead class="thead">
              <tr>
                <th>#</th>
                <th>Name</th>
                <th>Description</th>
                <th>Author</th>
                <th>Upload date</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td>1</td>
                <td>BIRADS</td>
                <td>For breast radiographies</td>
                <td>Dr. White</td>
                <td>27-02-2019</td>
                <td>
                  <div class="btn-group" role="group" aria-label="Basic example">
                    <button type="button" class="btn btn-outline-secondary btn-sm">View</button>
                    <button type="button" class="btn btn-outline-primary btn-sm">Modify</button>
                    <button type="button" class="btn btn-outline-danger btn-sm">Delete</button>
                  </div>
                </td>
              </tr>
              <tr>
                <td>2</td>
                <td>BIRADS</td>
                <td>For breast radiographies</td>
                <td>Dr. White</td>
                <td>27-02-2019</td>
                <td>
                  <div class="btn-group" role="group" aria-label="Basic example">
                    <button type="button" class="btn btn-outline-secondary btn-sm">View</button>
                    <button type="button" class="btn btn-outline-primary btn-sm">Modify</button>
                    <button type="button" class="btn btn-outline-danger btn-sm">Delete</button>
                  </div>
                </td>
              </tr>
              <tr>
                <td>3</td>
                <td>BIRADS</td>
                <td>For breast radiographies</td>
                <td>Dr. White</td>
                <td>27-02-2019</td>
                <td>
                  <div class="btn-group" role="group" aria-label="Basic example">
                    <button type="button" class="btn btn-outline-secondary btn-sm">View</button>
                    <button type="button" class="btn btn-outline-primary btn-sm">Modify</button>
                    <button type="button" class="btn btn-outline-danger btn-sm">Delete</button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>

  <jsp:include page="footer.jsp"/>
  
</body>

<script>
  function changeVisibilityAdd() {
    if (document.getElementById("addNewStandard").style.display == "none") {
      document.getElementById("addNewStandard").style.display = "block";
    } else {
      document.getElementById("addNewStandard").style.display = "none";
    }
  }



  $(function () {
    $("#setRoot").click(function () {
      var name = $('#rootName').val();
      $("#jstreecontainer").jstree({
        "plugins": ["dnd", "contextmenu", "types"],
        "core": {
          "check_callback": true, // enable all modifications
          "data": [
            { "text": name,
              "icon":"images/network.png" }
          ]
        }
      });
      $("#rootGroup").hide();
      $("#instructions").show();
    });

    $("#saveTree").click(function () {
      var v = $('#jstreecontainer').jstree(true).get_json('#', { no_state: true, no_id: true, no_children: false, no_data: true, no_li_attr: true, no_a_attr: true, flat: false})
      var mytext = JSON.stringify(v);
      alert(mytext);
    });

  });
</script>

</html>