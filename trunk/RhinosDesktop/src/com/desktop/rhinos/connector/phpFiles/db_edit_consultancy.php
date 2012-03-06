<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	UPDATE Consultancy
								SET name='".$_REQUEST['name']."',
								consultant='".$_REQUEST['consultant']."',
								tlf_1='".$_REQUEST['tlf_1']."',
								tlf_2='".$_REQUEST['tlf_2']."',
								mail='".$_REQUEST['mail']."'
								WHERE (id='".$_REQUEST['id']."')");	
	mysql_close();
?>