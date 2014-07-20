<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("DELETE FROM Services WHERE idClient='".$_REQUEST['id']."'");
	$q = mysql_query("DELETE FROM Clients WHERE id='".$_REQUEST['id']."'");
	$q = mysql_query("DELETE FROM Address WHERE id='".$_REQUEST['id']."'");

	mysql_close();
?>