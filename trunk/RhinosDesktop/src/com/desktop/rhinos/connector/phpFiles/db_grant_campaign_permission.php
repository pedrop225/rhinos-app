<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Permissions (idUser, campaign) 
						VALUES(	'".$_REQUEST['idUser']."',
								'".$_REQUEST['campaign']."')");	
	mysql_close();
?>