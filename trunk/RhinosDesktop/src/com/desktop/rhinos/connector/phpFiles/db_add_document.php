<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Documents (idService, name, date, doc)
							VALUES(	'".$_REQUEST['idService']."',
									'".$_REQUEST['name']."',
									'".$_REQUEST['date']."',
									'".$_REQUEST['doc']."')");
	
	mysql_close();
?>