<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	SELECT Services.id, idClient, name, campaign, service, date, expiry, commission, state
						FROM Clients, Services
						WHERE idUser='".$_REQUEST['idUser']."' AND Clients.id=Services.idClient 
						ORDER BY date");
	
	while ($e = mysql_fetch_assoc($q))
		$output[] = $e;
	
	print(json_encode($output));
	
	mysql_close();
?>