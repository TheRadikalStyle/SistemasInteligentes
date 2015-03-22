<!DOCTYPE html>
<html >
	<head>
		<link type="text/css" rel="stylesheet" href="../css/style.css"/>
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
		<meta property="fb:app_id" content="{FACEBOOK_APPID}"/>
		<meta property="fb:admins" content="{FACEBOOK_ADMIN_ID}"/>
		<title>Sistemas inteligentes</title>
		<?php
			include('../headerBar.html');
		?>
	</head>
	
	<body>
		<div id="fb-root">
			<script src="http://connect.facebook.net/es_LA/all.js"></script>
		</div> 
		
		
		<script> <!--API ACCESS-->
			window.fbAsyncInit = function() {
				
				FB.init({
							    
					appId      : 'FACEBOOK_APPID',
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
								alert("Inicia sesion en facebook y concede los permisos necesarios, verifica que el bloqueador de elementos emergentes no bloquee el pop-up de login de facebook, si es la segunda vez que vez este aviso recarga la pagina");
								FB.login(function(response){
								if(response.authResponse){
								window.location.reload();
								return false;
								}});
								}else {
									console.log('No hay ninguna sesión iniciada');
									alert("Inicia sesion en facebook y concede los permisos necesarios, verifica que el bloqueador de elementos emergentes no bloquee el pop-up de login de facebook, si es la segunda vez que vez este aviso recarga la pagina");
									FB.login(function(response){
									if(response.authResponse){
									window.location.reload();
									return false;
									}});
								}	 
					});
				
			};
		</script>
		
		<br>
		<div id="contenedor">
			<div style="text-align:center"><h1>Sistemas inteligentes</h1></div>
			<p>Aqu&iacute; se hospedaran los comentarios que usaremos para guardarlos en una base de datos para su posterior analisis por medio de una red neuronal para determinar el grado de violencia</p>
			
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
					js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&appId=FACEBOOK_APPID&version=v2.0";
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
			
		</body>
		
	</html>
