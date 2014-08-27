<?php
 
//require "/path/to/twilio-php/Services/Twilio.php";
require "/var/www/html/twilio-php-master/Services/Twilio.php";

$alarmCondition = $_REQUEST["alarm"]; 
$latitude = $_REQUEST["latitude"];
$longitude = $_REQUEST["longitude"]; 
// set your AccountSid and AuthToken from www.twilio.com/user/account
$AccountSid = "AC33f2d415ff36eef3441cc2b450a2e5d9";
$AuthToken = "141e76796826feabc906654a58bdcfc0";
 
$client = new Services_Twilio($AccountSid, $AuthToken);
 
$fromPhone = "650-397-9961";
$toPhone = "408-718-0622";

$toPeople = array(
//		"+14153109333" => "Srini",
//		"+16505445456" => "Sridhar",
//		"+15108620026" => "Rahulm",
		"+14087180622" => "Kunal",
//		"+15107097986" => "Rahuld",
	);

// Loop over all our friends. $number is a phone number above, and $name is the name next to it
if ($alarmCondition == "true") {
	foreach ($toPeople as $toNumber => $toName) {
		$sms = $client->account->messages->sendMessage($fromPhone, $toNumber,
			"Hey $toName, I'm in danger. Please Help!! I'm located at ($latitude, $longitude)"
		);

		// Display a confirmation message on the screen
		echo "Sent message to" . htmlspecialchars($_GET["alarm"]);
	}	
} else {
	echo "No Alarm";
}


?>
<!DOCTYPE html>
<html>
<body>
<form action="safebuttontwilio.php" method="get" target="_blank">
  alarm:<input type="text" name="alarm"><br>
	lat:<input type="text" name="latitude"><br>
long:<input type="text" name="longitude"><br>
  
<input type="submit" value="Submit">
</form>
</body>
</html>
