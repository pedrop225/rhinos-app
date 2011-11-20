<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	DELETE
						FROM Services 
						WHERE 	(idUser = '".$_REQUEST['idUser']."') AND
								(date = '".$_REQUEST['date']."') AND 
								(campaign ='".$_REQUEST['campaign']."') AND
								(service = '".$_REQUEST['service']."')");

	mysql_close();
?>