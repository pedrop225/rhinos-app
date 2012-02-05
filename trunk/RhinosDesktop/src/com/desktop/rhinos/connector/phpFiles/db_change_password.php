<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	UPDATE Login 
						SET password = '".$_REQUEST['newpass']."'
						WHERE user = '".$_REQUEST['user']."'");

	mysql_close();
?>