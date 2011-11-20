<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("INSERT INTO Campaigns (name) VALUES ('".$_REQUEST['name']."')");
	
	print("#<b>".$_REQUEST['name']."</b># Inserted.");
	
	mysql_close();
?>