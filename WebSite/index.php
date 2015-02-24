<!-- AUTHOR: David Ochoa Gutierrez, TheRadikalSoftware 2015-->

<!DOCTYPE html>
<html >
	<head>
		<link type="text/css" rel="stylesheet" href="../css/style.css"
		<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
		<meta content="AUTHOR: David Ochoa Gutierrez - TheRadikalSoftware 2015" />
		<meta property="fb:app_id" content="{YOUR FACEBOOK APP ID}"/>
		<meta property="fb:admins" content="{YOUR FACEBOOK ACCOUNT ID}"/>
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
				
				//FB.init({
				//	FB.login(function(){			    
				//	appId      : 'YOUR FACEBOOK APP ID',
				//	xfbml      : true,
				//	status     : true,
				//	cookie     : true,
				//	version    : 'v2.2'
				//	},
				//	{scope: 'publish_actions'}
				//	);
				//};
				FB.Event.subscribe('comment.create', comment_callback); //Al comentar pase algo
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
				(function(d, s, id) {
					var js, fjs = d.getElementsByTagName(s)[0];
					if (d.getElementById(id)) return;
					js = d.createElement(s); js.id = id;
					js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&appId= YOUR FACEBOOK APP ID &version=v2.0";
					fjs.parentNode.insertBefore(js, fjs);
				}(document, 'script', 'facebook-jssdk'));
			</script>
			<div class="fb-comments" data-href="http://www.YOUR DOMAIN.COM" data-numposts="15" data-colorscheme="light" notify="true" data-width="1120" candelete="true"></div>
			
			<script>
				var comment_id = "";
				var comment = "";
				var name = "";
				var id = "";
				
				var comment_callback = function(response) {
					
					
					comment_id = response.commentID;
					comment = response.message;
					
					console.log("comment_callback");
					console.log(response);
					
					
					FB.getLoginStatus(function(response) {
						if (response.status === 'connected') {
							console.log('Hay una sesion iniciada');

							/* make the API call */
								FB.api('/me', {fields: 'id, name'}, function(response) {
									/* handle the result */
									console.log(response);
									
									id = response.id;       /*ESTAS VARIABLES IRAN A BASE DE DATOS*/
									name = response.name;
									console.log(id + name);
								});	
									}
						else {
							alert("Inicia sesi&oacute;n en facebook y concede los permisos necesarios");
							FB.login();
					} 
				});
					

			  		setTimeout(function(){
						console.log("Comentario publicado con ID "+comment_id+" el mensaje " +comment+ " por la persona: "+name +" con ID: "+id );
						alert("Comentario publicado por " +name +" con un ID de cuenta" +id +", comento: "+comment +" dicho comentario tiene un ID: " +comment_id);
						},3000);
						console.log("Comentario publicado con ID "+comment_id+" el mensaje " +comment+ " por la persona: "+name +" con ID: "+id );
				}
				//alert("Comentario publicado con ID "+comment_id+" el mensaje " +comment+ " por la persona: "+name +" con ID: "+id );
			</script>
			
			
			
			<br><br>
			
			
			<br><br><br>
			<div id="footer">
				<div style="text-align:center"><p>Sistemas Inteligentes - Aula 4206 - Semestre Enero - Junio 2015 FIME-UANL</p></div>
			</div>
			
		</body>
		
	</html>
