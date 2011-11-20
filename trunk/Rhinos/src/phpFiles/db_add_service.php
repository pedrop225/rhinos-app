<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Services (id, idUser, idClient, service, campaign, tlf_1, tlf_2, commission, date) 
						VALUES(	'".$_REQUEST['idUser']."',
								'".$_REQUEST['idClient']."',
								'".$_REQUEST['service']."',
								'".$_REQUEST['campaign']."',
								'".$_REQUEST['tlf_1']."',
								'".$_REQUEST['tlf_2']."',
								'".$_REQUEST['commission']."',
								'".$_REQUEST['date']."')");
	
	mysql_close();
?>
