<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("SELECT * FROM Clients NATURAL JOIN Address WHERE id ='".$_REQUEST['id']."'");
	
	while ($e = mysql_fetch_assoc($q))
		$output[] = $e;
	
	print(json_encode($output));
	
	mysql_close();
?>