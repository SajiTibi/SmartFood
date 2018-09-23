<?php


    $con = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
    
    $email_address = $_POST["email_address"];
    $user_password = $_POST["user_password"];
    
    $sql = "SELECT * FROM users_table";
    $result = mysqli_query($con, $sql);

    
  $response = array();
  $response["success"] = false;

while($row = mysqli_fetch_assoc($result)){

    if($row["email_address"]==$email_address && $row["user_password"] == $user_password){
        $response["success"] = true;
        $response["uid"] = $row["uid"];
	$response["user_type"]= $row["user_type"];
	$response["fcm_token"] = $row["fcm_token"];
        break;
    }
}

    
    echo json_encode($response);
?>
