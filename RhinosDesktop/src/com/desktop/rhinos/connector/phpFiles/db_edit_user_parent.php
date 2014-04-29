<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	UPDATE Structure
								SET parent='".$_REQUEST['parent']."',
								p_profit='".$_REQUEST['p_profit']."'
								WHERE (child='".$_REQUEST['child']."')");
	mysql_close();
?>