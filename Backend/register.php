<?php
 // receive data from app's http request
session_start();
if($_SERVER['SERVER_PORT'] !== 443 &&
   (empty($_SERVER['HTTPS']) || $_SERVER['HTTPS'] === 'off')) {
  header('Location: https://' . $_SERVER['HTTP_HOST'] . $_SERVER['REQUEST_URI']);
  exit;
}

function generateHash($password) {
    if (defined("CRYPT_BLOWFISH") && CRYPT_BLOWFISH) {
        $salt = '$2y$11$' . substr(md5(uniqid(rand(), true)), 0, 22);
        return crypt($password, $salt);
    }
    
}

 $myusername=$_POST["username"];
 $mypassword=$_POST["password"];
 $firstname=$_POST["firstname"];
 $lastname=$_POST["lastname"];
 $email=$_POST["email"];
 $sex=$_POST["sex"];
$emergencyone=$_POST["emergencyone"];
$emergencytwo=$_POST["emergencytwo"];
$emergencythree=$_POST["emergencythree"];
$host = "localhost";
 $username = "ec2-user";
 $pass = "safebutton123";
 $db_name = "safebutton";
 $tbl_name = "Register";
 
 $link = mysqli_connect("$host", "$username", "$pass", "$db_name");
 

$myusername = stripslashes($myusername);
$mypassword = stripslashes($mypassword);
$firstname = stripslashes($firstname);
$lastname = stripslashes($lastname);
$email = stripslashes($email);
$emergencyone = stripslashes($emergencyone);
$emergencythree = stripslashes($emergencythree);
$emergencytwo = stripslashes($emergencytwo);
$sex = stripslashes($sex);

$myusername = mysqli_real_escape_string($link,$myusername);
$mypassword = mysqli_real_escape_string($link,$mypassword);
$firstname = mysqli_real_escape_string($link,$firstname);
$lastname = mysqli_real_escape_string($link,$lastname);
$email = mysqli_real_escape_string($link,$email);
$emergencyone = mysqli_real_escape_string($link,$emergencyone);
$emergencytwo = mysqli_real_escape_string($link,$emergencytwo);
$emergencythree = mysqli_real_escape_string($link,$emergencythree);
$sex = mysqli_real_escape_string($link,$sex);
$mypassword = password_hash($mypassword, PASSWORD_BCRYPT);
$sql="SELECT * FROM $tbl_name WHERE username='$myusername'";
$result = mysqli_query($link,$sql);
$count = mysqli_num_rows($result);
$sqlemail="SELECT * FROM $tbl_name WHERE email='$email'";
$resulte = mysqli_query($sqlemail);
$counte = mysqli_num_rows($resulte);

if ($count==1)
{
	//	header( 'Location:http://ec2-54-84-27-191.compute-1.amazonaws.com/register.php' ) ;

	echo 'username';
}

else if($counte==1)
{
	echo 'email';
}
else if ($counte == 0 and $count == 0) {
mysqli_query($link,"INSERT INTO Register (firstname, lastname, email, username, password, sex, emergencyone, emergencytwo, emergencythree) VALUES ('$firstname', '$lastname', '$email', '$myusername', '$mypassword', '$sex', '$emergencyone', '$emergencytwo', '$emergencythree')");
echo 'Success';
	}



 
?>
<html>
<body>

<form action="register.php" method="post">
first: <input type="text" name="firstname"><br>
last: <input type="text" name="lastname"><br>
emali: <input type="text" name="email"><br>
user: <input type="text" name="username"><br>

pass: <input type="text" name="password"><br>
sex: <input type="text" name="sex"><br>

Eone: <input type="text" name="emergencyone"><br>
Etwo: <input type="text" name="emergencytwo"><br>
Ethree: <input type="text" name="emergencythree"><br>

<input type="submit">
</form>

</body>
</html> 
