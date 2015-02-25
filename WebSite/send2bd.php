<?php
	echo "CommentID:".$_GET['comment_id']."<br>";
	echo "Comment:".$_GET['comment']."<br>";
	echo "Name:".$_GET['name']."<br>";
	echo "ID:".$_GET['id']."<br>";
	
	$commentID = $_GET['comment_id'];
	$comment = $_GET['comment'];
	$name = $_GET['name'];
	$id = $_GET['id'];
	
	
	$server = "127.0.0.1"; /*Change if you have the database on other location*/
	$user = "YOUR MYSQL USER";
	$password = "YOUR MYSQL PASSWORD";
	$dbname = "YOUR MYSQL DATABASE NAME";
	
	/* Create connection*/
	$conn = new mysqli($server, $user, $password, $dbname);
	/* Check connection*/
	if ($conn->connect_error) {
		die("Connection failed: " . $conn->connect_error);
	} 
	$sql = "INSERT INTO usuario (ID_Comentario, Usu_Comentario, Usu_Nombre, ID_Facebook)
	VALUES ('$commentID', '$comment', '$name', '$id')";
	
	if ($conn->query($sql) === TRUE) {
		echo "New record created successfully";
		} else {
		echo "Error: " . $sql . "<br>" . $conn->error;
	}
	
	$conn->close();
?>