<?php


    $con = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
if (mysqli_connect_errno($con)) { echo "Failed to connect to MySQL: " . mysqli_connect_error(); }


    $email_address = $_POST["email_address"];
    $user_password = $_POST["user_password"];
    $user_type = $_POST["user_type"];
    $user_longitude = $_POST["user_longitude"];
    $user_latitude = $_POST["user_latitude"];
    $sql = "SELECT * FROM users_table WHERE email_address = '$email_address'";
    $result = $con->query($sql);

    $response = array();
    $response["success"] = false;
    if ($result->num_rows > 0) {
    	echo json_encode($response);
	exit(1);
    }
    $statement = mysqli_prepare($con, "INSERT INTO users_table (email_address, user_password,user_type,user_longitude,user_latitude) VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssidd", $email_address,  $user_password,$user_type,$user_longitude,$user_latitude);
    mysqli_stmt_execute($statement);
    
    $response = array();

    if (!mysqli_connect_errno($con)) {     $response["success"] = true;   }
    echo json_encode($response);
?>
