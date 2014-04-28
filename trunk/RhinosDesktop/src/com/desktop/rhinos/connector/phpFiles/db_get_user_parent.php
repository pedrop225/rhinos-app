<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	SELECT parent, name, mail, p_profit
						FROM Structure INNER JOIN Users ON Structure.parent = Users.id 
						WHERE child = '".$_REQUEST['child']."'");
	
	while ($e = mysql_fetch_assoc($q))
		$output[] = $e;

	print(json_encode($output));
	
	mysql_close();
?>