<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO CampInfo (id, service, commission) 
						VALUES ('".$_REQUEST['id']."',
								'".$_REQUEST['service']."',
								'".$_REQUEST['commission']."')");	
	mysql_close();
?>