<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	SELECT id FROM Login NATURAL JOIN Users 
						WHERE (user='".$_REQUEST['user']."') OR (mail='".$_REQUEST['mail']."')");
	
	while ($e = mysql_fetch_assoc($q))
		$output[] = $e;
	
	print(json_encode($output));
	
	mysql_close();
?>