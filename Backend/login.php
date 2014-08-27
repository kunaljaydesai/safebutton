<?php
 // receive data from app's http request
session_start();
if($_SERVER['SERVER_PORT'] !== 443 &&
   (empty($_SERVER['HTTPS']) || $_SERVER['HTTPS'] === 'off')) {
  header('Location: https://' . $_SERVER['HTTP_HOST'] . $_SERVER['REQUEST_URI']);
  exit;
}

if (count($_POST)>0){
 $myusername=$_POST["username"];
 $mypassword=$_POST["password"];

 $host = "localhost";
 $username = "ec2-user";
 $pass = "safebutton123";
 $db_name = "safebutton";
 $tbl_name = "Register";
$link = mysqli_connect("$host", "$username", "$pass", "$db_name");
 

$myusername = stripslashes($myusername);
$mypassword = stripslashes($mypassword);


$myusername = mysqli_real_escape_string($link,$myusername);
$mypassword = mysqli_real_escape_string($link, $mypassword);
//$mypassword = generateHash($mypassword);

$sql="SELECT * FROM $tbl_name WHERE username='$myusername'";

$result = mysqli_query($link, $sql);
$row = mysqli_fetch_array($result);
$hash = $row['password'];
if (password_verify($mypassword, $hash)){
	$_SESSION['firstname'] = $row[firstname];
$_SESSION['lastname'] = $row[lastname];
$_SESSION['email'] = $row[email];
$_SESSION['username'] = $row[username];
$_SESSION['password'] = $row[password];
$_SESSION['sex'] = $row[sex];

	echo 'Success';
	
	header("Location:home.php");

//	setcookie("user", "$row['firstname']",time()+3600);
//	header('Location:http://ec2-54-84-27-191.compute-1.amazonaws.com/cookietest.php' ) ;

}
else{
//	header( 'Location:http://ec2-54-84-27-191.compute-1.amazonaws.com/login.php' ) ;
	echo 'Failure';
}
}
?>
<html>
<body>

<form action="login.php" method="post">
<input type="text" name="username"><br>
<input type="text" name="password"><br>
<input type="submit">
</form>

</body>
</html>
  


