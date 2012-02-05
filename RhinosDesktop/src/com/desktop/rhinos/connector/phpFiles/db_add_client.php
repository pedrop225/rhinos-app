<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Clients (id, idType, name, tlf_1, tlf_2, mail, address) 
						VALUES(	'".$_REQUEST['id']."',
								'".$_REQUEST['id_Type']."',
								'".$_REQUEST['name']."',
								'".$_REQUEST['tlf_1']."',
								'".$_REQUEST['tlf_2']."',
								'".$_REQUEST['mail']."',
								'".$_REQUEST['address']."')");	
	mysql_close();
?>