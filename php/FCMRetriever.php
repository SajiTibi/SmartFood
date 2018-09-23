<?php

	$user_id = $_POST["uid"];
	$con = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
	$sql = "SELECT * FROM users_table WHERE uid=$user_id";
	$result = $con->query($sql);
	$response = array();
 	$response["success"] = false;  
	if ($result->num_rows > 0) {
	    // output data of each row
	    $response["success"] = true;  
	    while($row = $result->fetch_assoc()) {
	        $curr = array();
		$curr["fcm_token"]=$row["fcm_token"];
		$response[$row["uid"]]=$curr;
	    }
	}
	echo json_encode($response);
	$con->close();
	?> 

 
