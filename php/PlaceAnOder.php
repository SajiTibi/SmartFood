<?php


    $con = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
if (mysqli_connect_errno($con)) { echo "Failed to connect to MySQL: " . mysqli_connect_error(); }


    $recipe_id = $_POST["recipe_id"];
    $recipe_cooker_id = $_POST["recipe_cooker_id"];
    $recipe_purchaser_id = $_POST["recipe_purchaser_id"];

    
    $statement = mysqli_prepare($con, "INSERT INTO orders (recipe_id, recipe_cooker_id,recipe_purchaser_id) VALUES (?,?,?)");
    mysqli_stmt_bind_param($statement, "iii", $recipe_id,  $recipe_cooker_id,$recipe_purchaser_id);
    mysqli_stmt_execute($statement);
    
    $response = array();

    if (!mysqli_connect_errno($con)) {     $response["success"] = true;   }
    echo json_encode($response);
?>
