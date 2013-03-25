<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Services (idUser, idClient, service, campaign, commission, date, expiry) 
						VALUES(	'".$_REQUEST['idUser']."',
								'".$_REQUEST['idClient']."',
								'".$_REQUEST['service']."',
								'".$_REQUEST['campaign']."',
								'".$_REQUEST['commission']."',
								'".$_REQUEST['date']."',
								'".$_REQUEST['expiry']."')");
	
	mysql_close();
?>
