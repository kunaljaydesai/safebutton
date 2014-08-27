<?php
session_start();
?>
<html>
<head>
<title>User Login</title>

</head>
<body>
<?php
if($_SESSION["firstname"] && $_SESSION["lastname"] && $_SESSION["username"] && $_SESSION["password"] && $_SESSION["sex"] && $_SESSION["email"]) {
?>
<?php $json = array('firstname'=> $_SESSION["firstname"], 'lastname' => $_SESSION["lastname"], 'email' => $_SESSION["email"], 'username' => $_SESSION["username"], 'password' => $_SESSION["password"], 'sex' => $_SESSION["sex"]); echo json_encode($json);  ?>
<?php
}
?>
<a href="http://ec2-54-84-27-191.compute-1.amazonaws.com/logout.php">logout</a>

</body>
</html>
