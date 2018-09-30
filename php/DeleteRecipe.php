<?php


	$con = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
	if (mysqli_connect_errno($con)) {
	    echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	$recipe_id        = $_POST["recipe_id"];


	$sql                 = "DELETE FROM recipes_table WHERE recipe_id = '$recipe_id'";
	$response            = array();
	$response["success"] = false;
	if (mysqli_query($con, $sql)) {
	    $response["success"] = true;
	}

	echo json_encode($response);
?>

