<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	SELECT SUM(commission)
						FROM Services 
						WHERE (idUser = '".$_REQUEST['idUser']."') AND (idClient ='".$_REQUEST['idClient']."' 
						ORDER BY name");
	
	while ($e = mysql_fetch_assoc($q))
	$output[] = $e;
	
	mysql_close();
?>