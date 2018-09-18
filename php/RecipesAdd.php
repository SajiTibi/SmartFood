
<?php
	$con = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");

	if (mysqli_connect_errno($con)){
		echo "Failed to connect to MySQL: " . mysqli_connect_error();
	}

	$recipe_name = $_POST["recipe_name"];
	$recipe_description = $_POST["recipe_description"];
	$recipe_price = $_POST["recipe_price"];
	$recipe_cooker = $_POST["recipe_cooker"];
	$recipe_cooker_id = $_POST["recipe_cooker_id"];
	$recipe_longitude = $_POST["recipe_longitude"];
	$recipe_latitude = $_POST["recipe_latitude"];
	$response = array();
	$response["success"] = false;
	$statement = mysqli_prepare($con, "INSERT INTO recipes_table (recipe_cooker_id,recipe_name,recipe_cooker, 		recipe_description,recipe_price,recipe_longitude,recipe_latitude) VALUES (?,?,?,?,?,?,?)");
	mysqli_stmt_bind_param($statement, "isssddd",$recipe_cooker_id, $recipe_name,$recipe_cooker,$recipe_description, $recipe_price,$recipe_longitude,$recipe_latitude);
	mysqli_stmt_execute($statement);
	$response = array();

	if (!mysqli_connect_errno($con)){
		$response["success"] = true;
	}

	echo json_encode($response);
?>

