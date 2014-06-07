<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	SELECT DISTINCT Clients.id, name, Clients.tlf_1, Clients.tlf_2, mail, address, consultancy
						FROM Clients, Services 
						WHERE (Clients.id = Services.idClient) and (idUser = '".$_REQUEST['idUser']."')
						ORDER BY date DESC
						LIMIT 0, 150");
	
	while ($e = mysql_fetch_assoc($q))
		$output[] = $e;

	print(json_encode($output));
	
	mysql_close();
?>