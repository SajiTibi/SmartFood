<?php


	$con = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
	if (mysqli_connect_errno($con)) {
	    echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}
	$user_id             = $_POST["uid"];
	$fcm_token = $_POST["fcm_token"];

	$sql                 = "UPDATE users_table SET fcm_token='$fcm_token' WHERE uid='$user_id'";
	$response            = array();
	$response["success"] = false;
	if (mysqli_query($con, $sql)) {
	    $response["success"] = true;
	}

	echo json_encode($response);
?>
