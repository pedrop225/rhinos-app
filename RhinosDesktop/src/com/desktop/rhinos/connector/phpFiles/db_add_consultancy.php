<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Consultancy (name, consultant, tlf_1, tlf_2, mail) 
						VALUES(	'".$_REQUEST['name']."',
									'".$_REQUEST['consultant']."',
									'".$_REQUEST['tlf_1']."',
									'".$_REQUEST['tlf_2']."',
									'".$_REQUEST['mail']."')");	
	mysql_close();
?>