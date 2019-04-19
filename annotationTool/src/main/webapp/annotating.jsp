<%@ page import="util.*" import="beans.*" import="java.util.List"
	import="java.util.regex.Pattern"%>

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

<%
	//setting the report and standards
	String reportid = (String) request.getAttribute("reportid");
	String annotationid = (String) request.getAttribute("annotationid");
	String rep = "";
	boolean modify = false;
	if (annotationid != null) {
		if (annotationid.equals("notset")) {
			Report report = Queries.getReport(Integer.parseInt(reportid));
			rep = report.getContent();
			//checkChars(rep);
		} else {
			Annotated annot = Queries.getAnnotated(Integer.parseInt(annotationid));
			rep = annot.getHtml();
			modify = true;
		}
	}

	String standard = (String) request.getAttribute("standard");
	Standard std = Queries.getStandard(standard);
	String jsons = std.getJson();
	jsons = jsons.replaceAll(Pattern.quote("\"opened\":false"), "\"opened\":true, checked:true");
	jsons = jsons.replaceAll(Pattern.quote("\"opened\":true"), "\"opened\":false, checked:true");
	jsons = jsons.replaceAll(Pattern.quote("\"selected\":true"), "\"selected\":false");
	jsons = jsons.replaceFirst(Pattern.quote("\"opened\":false"), "\"opened\":true, checked:true");

	String jso = jsons;

	String projectid = (String) request.getAttribute("projectid");
%>

<body>

	<!-- Navbar -->
	<jsp:include page="navbar.jsp" />

	<!-- Middle part where the maggic happens -->
	<form method="post" action="toXMLServlet">

		<div class="container-fluid">
			<div class="row">
				<div class="col-3">
					<div class="card" style="overflow-y: scroll; height: 352px;">
						<h6 class="card-header">Entity</h6>
						<div class="card-body" id="entities"></div>
					</div>
				</div>

				<div class="col-9" onclick="myFunction(event)">
					<div class="card" style="overflow-y: scroll; height: 352px;">
						<h6 class="card-header">File</h6>
						<div class="card-body" id="report-wrapper">
							<p class="report-click" id="report"><%=rep%></p>
							<input type="hidden" id="report1" name="report1"
								readonly="readonly"></input>
						</div>
					</div>
				</div>

			</div>



			<!-- 	{#This is the lower part of the page where Relations are shown#}-->

			<div class="row" style="padding-top: 20px">
				<div class="col-8">
					<div class="card" id="lower-panel"
						style="overflow-y: scroll; height: 120px;">
						<h6 class="card-header">Log</h6>
						<div class="card-body ">
							<div class="relations" id="rel">
								<!-- <p id="details"></p> -->
								<p id="name"></p>
								<p id="class"></p>
							</div>
						</div>
					</div>
				</div>

				<div class="col-4">
					<div class="card">
<!-- 						<h6 class="card-header">Controls</h6>
 -->					
						<div class="card-body" style="height: 120px">
						
							<div class="row">
								<div class="col">
									<div class="input-group input-group-sm">
										<div class="input-group-prepend">
											<button class="btn btn-outline-primary btn-sm lower-button"
												type="button" id="btn-extF" for="granularity">Select
												by:</button>
										</div>
										<select class="custom-select" id="granularity">
											<option value="character">Character</option>
											<option value="word">Word</option>
										</select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<div class="btn-group btn-group-sm btn-block btn-vertical"
										role="group">
										<button class="btn btn-outline-danger btn-sm lower-button"
											type="button" onclick="deleteAnno(getChosenAnno())"
											id="btn-d">Delete annotation</button>
										<button class="btn btn-outline-danger btn-sm lower-button"
											type="button" id="btn-all">Delete All annotations</button>

									</div>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<ul>
									</ul>
								</div>
							</div>
							<div class="row">
								<div class="col">
									<%
										if (modify == true) {
									%>
									<input type="hidden" name="annotationid"
										value="<%=annotationid%>">
									<%
										} else {
									%>
									<input type="hidden" name="annotationid" value="notset">
									<%
										}
									%>

									<input type="hidden" name="projectid" value="<%=projectid%>">
									<input type="hidden" name="reportid" value="<%=reportid%>">
									<input type="hidden" name="standardid" value="<%=std.getPk()%>">
									<input type="hidden" name="username"
										value="<%=user.getUsername()%>">
									<div class="btn-group btn-group-sm btn-block d-flex"
										role="group">
										<button class="btn btn-outline-success w-100 btn-sb"
											type="submit" id="savebutton" name="button" value="progress"
											onclick="beforeLoad()">Save progress</button>
										<button class="btn btn-outline-success w-100 btn-sb"
											type="submit" name="button" value="exit">Save and
											Exit</button>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</form>
	<div style="padding-top: 0px">
		<jsp:include page="footer.jsp" />
	</div>
</body>

<script>


	setInterval(function(){ 
	   $("#savebutton").click();
	},120000);
	
	var json = <%=jso%>;
 	var ids ;
 	var max = 0;
	var idC = -1;
	var numberHighlighted = 0;
	
	
    $( window ).on( "load", function() {
        //console.log( "window loaded" );
        
        ids = $("#report").find("SPAN");
  	   	
        //console.log(ids);
  	    for(var i=0;i<ids.length;i++){
  		  idC = ids[i].id;
  		  //console.log("N " +idC);
  		  if(max<idC){
  			 max = idC;
  		  }
  		  //console.log("DDDD");
  	   	}
  	    if(max != 0){
 		//console.log("MAX " +max);
 		numberHighlighted = parseInt(max) ;
 		numberHighlighted++;
  	    }
    });
   
    //console.log("MAX " +max);
    
	var idChosen = -1;

    var word = "";

    var mapOfWords = new Map(); // [id, [span object, range object ,selection object]]

    var setCurrentchosenAnnotation = "";

    

    var jsonStructure = json;
    var mapOfDisp = new Map();
	var keysss = [];           

           
        //-------------------------Selected Text------------------------------------
     
        function surroundSelection(id,color,name) {
        	var bool = false;
            var span = document.createElement("span");
            //span.style.fontWeight = "bold";
            span.style.background = color;
            span.setAttribute("title",name);
            span.setAttribute("class",id);

            //span.setAttribute('onclick','showDetails('+numberHighlighted+')');
            span.setAttribute("id", numberHighlighted);     //add an id to all the highlighted text 
           	// span.setAttribute("name",name);
            
            //console.log("title: " + span.getAttribute("title"));
             
            var st;
            var en;
                
            if (window.getSelection) {
            		var paragraphRange = document.getElementById("report");
                    var sel = window.getSelection();
                   // if(sel.parentNode.tagName === 'SPAN'){
                    	//console.log(sel.anchorNode.childNodes);
                    //}
                    
                    var txt = sel.toString();
                    var element = sel.getRangeAt(0).startContainer.parentNode;
                    var testrange = sel.getRangeAt(0);
                    //console.log("dd " + testrange.startOffset + " ::: " + testrange.endOffset)
                    
                    var fNpEnN = sel.focusNode.parentElement.nodeName;
                    var aNpEnN = sel.anchorNode.parentElement.nodeName;
                    
                    
                    
                    
                    
                    if( (( aNpEnN == "P" && fNpEnN == "P" ) || ( aNpEnN == "SPAN" && fNpEnN == "SPAN"  )) && fNpEnN !== null ){
                    	if(element.tagName == "P" && element.id == "report" || element.tagName == "SPAN"){		//Check if highlighted word is inside the element <paragraph> and if element id is "report".	

						//PART WHERE A CHARACTER TURN TO AN ENTIRE WORD
                    	    var sell;

                    	    // Check for existence of window.getSelection() and that it has a
                    	    // modify() method. IE 9 has both selection APIs but no modify() method.
                    	    if (window.getSelection && (sell = window.getSelection()).modify) {
                    	        sell = window.getSelection();
                    	        if (!sell.isCollapsed) {

                    	            // Detect if selection is backwards
                    	            var range = document.createRange();
                    	            range.setStart(sel.anchorNode, sel.anchorOffset);
                    	            range.setEnd(sel.focusNode, sel.focusOffset);
                    	            var backwards = range.collapsed;
                    	            range.detach();

                    	            // modify() works on the focus of the selection
                    	            var endNode = sell.focusNode, endOffset = sel.focusOffset;
                    	            sell.collapse(sel.anchorNode, sel.anchorOffset);

                    	            var direction = [];
                    	            if (backwards) {
                    	                direction = ['backward', 'forward'];
                    	            } else {
                    	                direction = ['forward', 'backward'];
                    	            }

                    	            sell.modify("move", direction[0], "character");
                    	            sell.modify("move", direction[1], "word");
                    	            sell.extend(endNode, endOffset);
                    	            sell.modify("extend", direction[1], "character");
                    	            sell.modify("extend", direction[0], "word");
                    	            //console.log(range.startOffset + " -- " + range.endOffset);

                    	        }
                    	    } else if ( (sell = document.selection) && sell.type != "Control") {
                    	        var textRange = sell.createRange();
                    	        if (textRange.text) {
                    	            textRange.expand("word");
                    	            // Move the end back to not include the word's trailing space(s),
                    	            // if necessary
                    	            while (/\s$/.test(textRange.text)) {
                    	                textRange.moveEnd("character", -1);
                    	            }
                    	            textRange.select();
                    	            //console.log(textRange.startOffset + " - " + textRange.endOffset);
                    	            //sel = textRange;
                    	        }
                    	    }
                    	////ENDS HERE
	                   
                    	//if(window.getSelection()){
                    	//	console.log(window.getSelection());
                    	//}
                    	
                    	
	                        if(!isNullOrWhiteSpace(txt)){
	                        		if (sel.rangeCount) {
		                            	
		                                var range = sel.getRangeAt(0).cloneRange(); //return all details for the selected area 
		                                var rangeToBeSaved;
		                                st = range.startOffset;
		                                en = range.endOffset;
		                                // console.log(range.startOffset);
		                                // console.log(range.endOffset);
		                                

		                                range.surroundContents(span);
		                                sel.removeAllRanges();
		                                sel.addRange(range);

		                                ///console.log(range.endContainer.childNodes[1]);
		                                //console.log(sel);
		                                //console.log(st + " + " + en);
		                                
										rangeToBeSaved = [st,en]; 			//range of the word
		                                mapOfWords.set(numberHighlighted,[span,rangeToBeSaved,sel]);

		                                sel.collapse(document,0);
		                                //sel = "";

		                                numberHighlighted++;
		                                //console.log(mapOfWords);
		                            }
	                            }
	                    }else{
	                    	alert("Not Allowed");
	                    }
                    
	                }else{
	                	alert("Selected annotation action not allowed: Overlapping annotations!");
	                }
            	}
            
            if(mapOfDisp.size != 0){
            	notShow(category);
            }
            
            
            }

   

            function isNullOrWhiteSpace(str) {
                return (!str || str.length === 0 || /^\s*$/.test(str))
            }

            function deleteAnno(id){
            	//console.log(id);
            	var all = $("span[id='"+id+"']");
            	//console.log(all);
	
	                if(id === null || id === "" || all.length ==0 ){
	                    alert("no Selected Word");
	                }else{
	                    //console.log("IN delete "+ id);
	                    //console.log("getchosenAnno " +  mapOfWords.get(id));
	                    $('#'+id).contents().unwrap();
	                    //mapOfWords.delete(id);
	                    document.getElementById("name").innerHTML ="";
	                    //document.getElementById("details").innerHTML ="";
	                    document.getElementById("class").innerHTML ="";
	                    //console.log(mapOfWords);
	                }
            	
            }



        //--------------Annotating--------------------------------------------------

       


            //!!!!!!!!!!!NEXT STEP
            //JSTREE
    
            
    $(function () {
    
      $("#entities").jstree({
        'core' : {
            'data' : jsonStructure
        },
        checkbox: {
            three_state : false, // to avoid that fact that checking a node also check others
            whole_node : false,  // to avoid checking the box just clicking the node 
            tie_selection : false // for checking without selecting and selecting without checking
          },
          plugins: ['checkbox']
      }).bind("loaded.jstree", function (event, data) {
    	    $(this).jstree("uncheck_all");
    	    $(this).jstree("check_all");

      });

      

      $("#entities").bind("select_node.jstree", 
        function(evt, data){
            //get the node name on select
	    	  var node_name = data.node.text;
	          var node_color = data.node.data.color;
	          var node_id = data.node.id;
 	          //console.log(data.node.id);
 	          if(notAllow[0] != node_name){
	              surroundSelection(node_id,node_color,node_name);
	          }
    	});
      
      
      $("#entities").bind("check_node.jstree uncheck_node.jstree", function(e, data) {
    	  //data.node.id 
    	  //data.node.text
    	  //data.node.state.checked ? ' CHECKED': ' NOT CHECKED'
    	  if(data.node.state.checked){
    		  var color = mapOfDisp.get(data.node.id); 	//get the color
    		  mapOfDisp.delete(data.node.id);
    		  keysss = $.grep(keysss, function(value) {
    			  return value != data.node.id;
    			});
    		  //console.log(keysss);


				
    		  //console.log(data.node.text);
    		  var all = $("span[class='"+data.node.id+"']");
    		  for(var o=0;o<all.length;o++){
	   			   //console.log(all[o]);
	   			   all[o].style.background = color;
	   		   }
    		  //console.log(allSel);
    	  }
    	  if(!data.node.state.checked){
    		  mapOfDisp.set(data.node.id,data.node.data.color);
    		  keysss.push(data.node.id);
    		  //console.log(keysss);


    		  var all = $("span[class='"+data.node.id+"']");
	   		   for(var o=0;o<all.length;o++){
	   			   //console.log(all[o]);
	   			   all[o].style.fontWeight = "";
	   			   all[o].style.border = "none";
	   			   all[o].style.background = "initial";
	   		   }
    	  }
    	});
      
      
    });   
       
    $(document).ready(function(){
    	$(".btn-sb").click(function getReportHtml(){
    		/* $(".jstree li[role=treeitem]").each(function () {
    		     $(".jstree").jstree('select_node', this)
    		}); */

		for(var key in keysss) {
			var color = mapOfDisp.get(keysss[key]); 	//get the color		  
  		  	var all = $("span[class='"+keysss[key]+"']");
  		  	for(var o=0;o<all.length;o++){
	   			all[o].style.background = color;
	   		}
		}
    		 	
    	document.getElementById("report1").value = $("#report").html(); 
       	});
    });
    
    $(document).ready(function(){
	    $("#btn-all").click(function(){
	    	var spns = $("#report").find("SPAN");
	    	var ans = confirm("Are you sure?");
			if(ans==true){
		    	for(var i=0;i<spns.length;i++){
			    	//console.log(spns[i].id);
			    	deleteAnno(spns[i].id);
		    	}
			}	
	   // 	if(mapOfWords.size != 0){
		//    	var ans = confirm("Are\ you sure?");
		//		if(ans==true){
		//	    	for (const v of mapOfWords.keys()) {
		//	    		deleteAnno(v)
		//	    	}
		//		}
	    //	}
	    });
    });
    
    //$(document).ready(function(){
    	//$("#btn-d").click(function()){
    		
    	//});
    //	});
    
    $(document).ready(function(){
    	$("#btn-extF").click(function(){
    		let select = document.querySelector('select');
    		var sel = window.getSelection();
    		sel.modify('extend', 'forward', select.value);
    	});
    });
    
    $(document).ready(function(){
    	$("#btn-dis").click(function(){
    		var select = document.getElementsByClassName('report1');
    		for(var i=0;i<select.length;i++){
    			$(select[i]).css("background-color","white");
    			$(select[i]).css("font-weight" , "none");
    		}
    	});
    });
    
  //DONT ALLOW Categories to be selected
    var notAllow = [];
    $(document).ready(function(){
        for(var i=0;i<jsonStructure.length;i++){
            notAllow.push(jsonStructure[i].text);
        	//console.log(jsonStructure[i].text);
        }
    });
    
    
    var id = "";
    var boolID= true;
    function getChosenAnno(){
        return id;
    }
    
    function myFunction(event) { 
		//console.log(event.target.innerText);
    	if(boolID){
	    	if(event.target.nodeName === "SPAN" && event.target.id != ""){
	    		if(!mapOfDisp.has(event.target.className)){
		    		//event.target.style.fontWeight = "bold";
		    		event.target.style.border= "thin solid"	;
		    		//document.getElementById("details").innerHTML = " id : " + event.target.id;
		    		document.getElementById("name").innerHTML = "name : '" + event.target.innerText+ "'";
		    		document.getElementById("class").innerHTML = "label : "+event.target.title;
	    		}
	    		boolID= false;
	    	}
    	} else if(id !== "" && id !== event.target.id){
    		if(document.getElementById(id) != null){
	    		document.getElementById(id).style.fontWeight = "";
	    		document.getElementById(id).style.border = "none";
	    		//document.getElementById("details").innerHTML = " id : ";
	    		document.getElementById("name").innerHTML = "name : " ;
	    		document.getElementById("class").innerHTML = "class : ";
    		}
    		boolID = true;
    	}
    	//console.log(id +"=== "+event.target.id);
		id = event.target.id ;
    	
	}

    function beforeLoad(){
    	
    	if(document.getElementById(id) != null){
    		document.getElementById(id).style.fontWeight = "";
    		document.getElementById(id).style.border = "none";
    		//document.getElementById("details").innerHTML = " id : ";
    		document.getElementById("name").innerHTML = "name : " ;
    		document.getElementById("class").innerHTML = "class : ";
		}
		boolID = true;
    }
    
   function notShow(elem){
	   //console.log(elem);
	   if(mapOfDisp.has(elem)){
		   var value;
		   var all = $("span[class='"+elem+"']");
		   for(var o=0;o<all.length;o++){
			   //console.log(all[o]);
			   all[o].style.background = "initial";
		   }
	   }
//	   if(mapOfDisp.has(elem)){						//if key of map has class name
//		   alert("YES");
//	   }
   }
    
   		
 		
 
    
    </script>


</html>



