<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("SELECT id, type, user, name, mail, p_profit
					  FROM Users NATURAL JOIN Login
					  WHERE id IN ( SELECT child FROM Structure
									WHERE parent='".$_REQUEST['parent']."')");
	
	while ($e = mysql_fetch_assoc($q))
		$output[] = $e;
	
	print(json_encode($output));
	
	mysql_close();
?>