<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	DELETE FROM Permissions 
						WHERE 	idUser='".$_REQUEST['idUser']."' AND
								campaign='".$_REQUEST['campaign']."'");
	
	mysql_close();
?>