<?php
    $servername = "localhost";
    $username = "root"; 
    $password = "RootyTootyBooty25!"; 
    $dbname = "requested_appointments"; 

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

    echo "Connected successfully";
?>
