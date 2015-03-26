<!DOCTYPE html>
<html >
	<head>
		<link type="text/css" rel="stylesheet" href="../css/style.css"/>
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
		<meta property="fb:app_id" content="{906104149423872}"/>
		<meta property="fb:admins" content="{10204937825518410}"/>
		<title>Sistemas inteligentes</title>
		<?php
			include('../headerBar.html');
		?>
		<script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>  <!--Import JQuery-->
		<script src="https://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>

		  <script type="text/javascript"> //Handler dataUsage div using JQuery
				$(document).ready(function(){ 
	   			$('#DataUsage').on('click',function(){
	      		$('#DataUsageDiv').toggle('slow');
	   			});
			   });
		  </script>
	</head>
	
	<body>

		<div id="div-nosesion" style="display:none"> <!--Div for show the error screen due to not connection to facebook app handle with JQuery -->
			<!--<script>
				/*$(document).ready(function(){
			    $("a").hover(function(){
			        $("a").css("background-image", "../css/images/facebook_login2.png");
			        },function(){
			        $("a").css("background-image", "../css/images/facebook_login.png");
			    });
			});*/
			</script> -->

			<br>
			<p align="center">
			Este proyecto necesita una conexion a facebook<br>
			Por favor inicia sesion para continuar</p>
			<br>
			<a style="align="center"" href="#" onclick="fblogin()"><img src="../css/images/facebook_login.png"></a>
		</div>

		<div id="fb-root">
			<script src="http://connect.facebook.net/es_LA/all.js"></script>
		</div> 
		
		<script> <!--API ACCESS-->
			window.fbAsyncInit = function() {
				
				FB.init({
							    
					appId      : '906104149423872',
					xfbml      : true,
					status     : true,
					cookie     : true,
					version    : 'v2.2'
				
				//	{scope: 'publish_actions'}
				
				});
				FB.Event.subscribe('comment.create', comment_callback); //Catch the comment event				
				
				FB.getLoginStatus(function(response) { //Get the login access, if not found one, alert you and open a frame with the login form
						if (response.status === 'connected') {
							console.log('Hay una sesion iniciada');
							}else if(response.status === 'not_authorized'){ //If is logged on FB but not in your app alert you and open the login form
								console.log('Hay una sesión en FB pero no en la APP');
			
								$(document).ready(function(){ //JQuery handler for no session div
											$('#div-nosesion').toggle();
									});
								}else {
									console.log('No hay ninguna sesión iniciada');

										$(document).ready(function(){//JQuery handler for no session div
											$('#div-nosesion').toggle();
									});
								}	 
					});
				
			};
		</script>
		
		<br>
		<div id="contenedor">
			<div style="text-align:center"><h1>Sistemas inteligentes</h1></div>
			<p>Aqu&iacute; se hospedaran los comentarios que usaremos para guardarlos en una base de datos para su posterior analisis por medio de una red neuronal para determinar el grado de violencia</p>
			
			<p><a href="#" id="DataUsage">Que datos recopilamos</a></p>
			<div id="DataUsageDiv" style="display:none">
				<p><b>ID de usuario</b>: Requerido para un ordenamiento de datos por caracteres numericos<br>
					<b>Nombre de usuario</b>: Dado que una identificacion numerica no nos proporciona un nombre, lo requerimos<br>
					<b>Comentario</b>: Este es el mensaje que usaremos para analizar con nuestros algoritmos y determinar el grado de violencia<br>
					<b>ID de comentario</b>: Requerido para un ordenamiento de datos<br>
				</p>
			</div>

			<br><br>
			<!-- Like Button -->
			<div class="fb-like" data-share="true" data-width="450" data-show-faces="true"></div>
			<!-- Like Button -->
			<br><br>
			
			<script>
			(function(d, s, id) { /*Comment box API*/
					var js, fjs = d.getElementsByTagName(s)[0];
					if (d.getElementById(id)) return;
					js = d.createElement(s); js.id = id;
					js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&appId=906104149423872&version=v2.0";
					fjs.parentNode.insertBefore(js, fjs);
				}(document, 'script', 'facebook-jssdk'));
			</script>
			<div class="fb-comments" data-href="http://www.theradikalsoftware.tk" data-numposts="15" data-colorscheme="light" notify="true" data-width="1120" candelete="true"></div>
			
			<script>
				var comment_id = "";
				var comment = "";
				var name = "";
				var id = "";
				
				var comment_callback = function(response) { /*Launch with a comment event is present*/
					
					
					comment_id = response.commentID;  //Get the comment ID
					comment = response.message;       //Get the comment content
					
					console.log("comment_callback");
					console.log(response);
					
							/* make the API call */
								FB.api('/me', {fields: 'id, name'}, function(response) { /*Using the Graph API for recollect you basic profile info, specific for your account ID and name*/
									/* handle the result */
									console.log(response);
									
									id = response.id;      //Get the user ID
									name = response.name;  //Get the user name
									console.log(id + name);
								});	

			  		setTimeout(function(){ //Alert with a wait of 3 seconds
						console.log("Comentario publicado con ID "+comment_id+" el mensaje " +comment+ " por la persona: "+name +" con ID: "+id );
						//alert("Comentario publicado por " +name +" con un ID de cuenta" +id +", comento: "+comment +" dicho comentario tiene un ID: " +comment_id);
						window.location.href = "send2bd.php?comment_id=" + comment_id + "&comment=" + comment + "&name=" + name + "&id=" +id; //Call the PHP file with a complex URL using to pass JS variable to PHP
						},3000);
						console.log("Comentario publicado con ID "+comment_id+" el mensaje " +comment+ " por la persona: "+name +" con ID: "+id );
				}
			</script>
			
			
			
			<br><br>
			
			
			<br><br><br>
			<div id="footer">
				<div style="text-align:center"><p>Sistemas Inteligentes - Aula 4206 - Semestre Enero - Junio 2015 FIME-UANL</p></div>
			</div>

			<script type="text/javascript"> //Facebook login function for keep window emergent blocked on calm
			function fblogin(){
				FB.login(function(response){
					if(response.authResponse){
					window.location.reload();
					return false;
					}});
				}
			</script>
			
		</body>
		
	</html>
