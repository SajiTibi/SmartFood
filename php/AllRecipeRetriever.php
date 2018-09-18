<?php


	$conn                 = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
	$sql                 = "SELECT * FROM recipes_table";
	$result              = $conn->query($sql);
	$response            = array();
	$response["success"] = false;
	if ($result->num_rows > 0) {
	    // output data of each row
	    $response["success"] = true;
	    while ($row = $result->fetch_assoc()) {
		$curr                          = array();
		$curr["recipe_name"]                  = $row["recipe_name"];
		$curr["recipe_description"]           = $row["recipe_description"];
		$curr["recipe_price"]              = $row["recipe_price"];
		$curr["recipe_longitude"]        = $row["recipe_longitude"];
		$curr["recipe_latitude"]        = $row["recipe_latitude"];
		$curr["recipe_id"]        = $row["recipe_id"];
		$response[$row["recipe_id"]]    = $curr;
	    }
	}
	echo json_encode($response);
	$conn->close();
?> 

 
