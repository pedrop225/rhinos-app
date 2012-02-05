<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("TRUNCATE TABLE Campaigns");
	$q = mysql_query("TRUNCATE TABLE CampInfo");
		
	mysql_close();
?>