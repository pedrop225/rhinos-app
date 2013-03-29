<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	UPDATE Services
								SET state='".$_REQUEST['state']."'
								WHERE (id='".$_REQUEST['id']."')");	
	mysql_close();
?>