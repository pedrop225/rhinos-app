<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("DELETE FROM Campaigns");
	
	print("db_clear_campaigns.php executed.");
	
	mysql_close();
?>