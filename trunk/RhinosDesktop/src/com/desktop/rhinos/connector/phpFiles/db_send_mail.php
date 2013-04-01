<?php
	$to = $_REQUEST['mail'];
	$subject = "Información de registro RhinosApp";
	
	$headers = "From:<RhinosApp>"."\r\n";
	$headers .= 'MIME-Version: 1.0' . "\r\n";
	$headers .= 'Content-type: text/html;charset=utf-8' . "\r\n";

	$message = file_get_contents('templates/welcome_mail.html');
	$message = str_replace('#USER', $_REQUEST['user'], $message);
	$message = str_replace('#MAIL', $_REQUEST['mail'], $message);
	$message = str_replace('#PASSWORD', $_REQUEST['password'], $message);
	$message = str_replace('#USER', $_REQUEST['user'], $message);
	
	mail($to, $subject, $message, $headers);
?>