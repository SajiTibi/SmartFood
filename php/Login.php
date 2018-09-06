<?php


    $con = mysqli_connect("localhost", "id7006105_admin", "asd123", "id7006105_users_db");
    
    $email = $_POST["email"];
    $password = $_POST["password"];
    
    $sql = "SELECT * FROM users_table";
    $result = mysqli_query($con, $sql);

    
  $response = array();
  $response["success"] = false;

while($row = mysqli_fetch_assoc($result)){

    if($row["email"]==$email && $row["password"] == $password){
        $response["success"] = true;
        $response["uid"] = $row["uid"];
        break;
    }
}

    
    echo json_encode($response);
?>
