<?php
	echo "CommentID:".$_GET['comment_id']."<br>"; //Get the data on the URL by _GET instruction and identifier
	echo "Comment:".$_GET['comment']."<br>";
	echo "Name:".$_GET['name']."<br>";
	echo "ID:".$_GET['id']."<br>";
	
	$commentID = $_GET['comment_id']; //Save data on variables
	$comment = $_GET['comment'];
	$name = $_GET['name'];
	$id = $_GET['id'];
	
	
	$server = "127.0.0.1";
	$user = ""; //MySQL Username
	$password = ""; //MySQL Password
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
	} else {
		echo "Error: " . $sql . "<br>" . $conn->error;
	}

$conn->close(); //Close connection
?>