<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("SELECT id, type, user, name, mail 
							FROM Login NATURAL JOIN Users
							WHERE id='".$_REQUEST['id']."'");
	
	while ($e = mysql_fetch_assoc($q))
		$output[] = $e;

	print(json_encode($output));
	
	mysql_close();
?>