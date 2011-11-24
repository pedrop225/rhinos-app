<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q0 = mysql_query("	INSERT INTO Login (user, password) 
						VALUES ('".$_REQUEST['user']."',
								'".$_REQUEST['password']."')");
	
	$q1 = mysql_query("	SELECT id FROM Login
						WHERE user='".$_REQUEST['user']."'");
	
	$row = mysql_fetch_array($q1);
	
	$q2 = mysql_query("	INSERT INTO Users (id, name, mail)
						VALUES ('".$row['id']."',
								'".$_REQUEST['name']."',
								'".$_REQUEST['mail']."')");
	
	mysql_close();
?>