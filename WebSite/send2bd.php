<!DOCTYPE html>
<html>
<head>
	<title>Recepcion de resultados</title>
	<link type="text/css" rel="stylesheet" href="../css/style.css"/>
	<meta content="text/html; charset=iso-8859-1" http-equiv="Content-Type">
</head>
<body>
<?php
	echo "CommentID: ".$_GET['comment_id']."<br>"; //Get the data on the URL by _GET instruction and identifier
	echo "Comment: ".$_GET['comment']."<br>";
	echo "Name: ".$_GET['name']."<br>";
	echo "ID: ".$_GET['id']."<br>";
	
	$commentID = $_GET['comment_id']; //Save data on variables
	$comment = $_GET['comment'];
		if(eregi("'", $comment)){
			$varReemplazar="'";
			$varReemplazo=" ";
			$newComment = str_replace($varReemplazar, $varReemplazo, $comment); /*Replace character ' (if exist) on comment due to DB errors*/
			$comment = $newComment;
			print "<br>";
			echo "Cadena reemplazada por caracteres invalidos en comentario";
			print "<br>";
		}
	$name = $_GET['name'];
	$id = $_GET['id'];
	
	
	$server = "127.0.0.1";
	$user = "inteligentes";
	$password = "NBQPROTOCOL";
	$dbname = "inteligentes";
	
	// Create connection
$conn = new mysqli($server, $user, $password, $dbname);
	// Check connection
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 
	$sql = "INSERT INTO usuario (ID_Comentario, Usu_Comentario, Usu_Nombre, ID_Facebook) 
	VALUES ('$commentID', '$comment', '$name', '$id')";

	if ($conn->query($sql) === TRUE) { //Verify if the query was successful or not
		print "<br><br>";
		echo "Nuevo comentario agregado exitosamente a la BD";
		echo "<br>";
		echo "Espera 3 segundos para redirigirte a la p&aacute;gina principal del proyecto...";
		echo "<script>setTimeout(function(){   //A wait of 3 seconds
						window.history.back();
						},3000);
						</script>";
	} else {
		echo "Error: " . $sql . "<br>" . $conn->error;
		echo "POR FAVOR CONTACTANOS PARA INFORMARNOS SOBRE EL ERROR";
	}

$conn->close(); //Close connection
?>
</body>
</html>
