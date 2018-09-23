<?php


	$conn                 = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
	$recipe_cooker_id    = $_POST["recipe_cooker_id"];
	$sql                 = "SELECT * FROM orders WHERE recipe_cooker_id = '$recipe_cooker_id'";
	$result              = $conn->query($sql);
	$response            = array();
	$response["success"] = false;
	if ($result->num_rows > 0) {
	    // output data of each row
	    $response["success"] = true;
	    while ($row = $result->fetch_assoc()) {
		$curr                          = array();
		$curr["recipe_purchaser_id"]                  = $row["recipe_purchaser_id"];
		$curr["time_order_placed"]           = $row["time_order_placed"];
		$curr["order_id"]        = $row["order_id"];
		$curr["recipe_id"]        = $row["recipe_id"];
		$response[$row["order_id"]]    = $curr;
	    }
	}
	echo json_encode($response);
	$conn->close();
?> 

 
