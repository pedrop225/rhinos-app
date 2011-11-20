<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	SELECT DISTINCT Clients.id, idType, name, Clients.tlf_1, Clients.tlf_2, mail, address 
						FROM Clients, Services 
						WHERE (Clients.id = idClient) AND (campaign ='".$_REQUEST['campaign']."' 
						ORDER BY name");
	
	while ($e = mysql_fetch_assoc($q))
		$output[] = $e;
		
	mysql_close();
?>