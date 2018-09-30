<?php


	$con = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
	if (mysqli_connect_errno($con)) {
	    echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	$user_id        = $_POST["uid"];
	$user_longitude = $_POST["user_longitude"];
	$user_latitude  = $_POST["user_latitude"];

	$sql                 = "UPDATE users_table SET user_longitude='$user_longitude',user_latitude='$user_latitude' WHERE uid='$user_id'";
	$response            = array();
	$response["success"] = false;
	if (mysqli_query($con, $sql)) {
	    $response["success"] = true;
	}

	echo json_encode($response);
?>

