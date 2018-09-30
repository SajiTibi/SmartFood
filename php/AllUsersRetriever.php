<?php


	$conn                 = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
	$sql                 = "SELECT * FROM users_table";
	$result              = $conn->query($sql);
	$response            = array();
	$response["success"] = false;
	if ($result->num_rows > 0) {
	    // output data of each row
	    $response["success"] = true;
	    while ($row = $result->fetch_assoc()) {
		$curr                          = array();
		$curr["email_address"]                  = $row["email_address"];
		$curr["user_type"]           = $row["user_type"];
		$curr["fcm_token"]              = $row["fcm_token"];
		$curr["uid"]        = $row["uid"];
		$curr["user_longitude"] = $row["user_longitude"];
		$curr["user_latitude"] = $row["user_latitude"];
		$response[$row["uid"]]    = $curr;
	    }
	}
	echo json_encode($response);
	$conn->close();
?> 

 
