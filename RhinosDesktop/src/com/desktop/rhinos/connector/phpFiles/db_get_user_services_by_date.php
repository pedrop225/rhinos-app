<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$date_type = ($_REQUEST['date_type'] == 0) ? 'date' : 'expiry';
	
	$q = mysql_query("	SELECT Services.id, idClient, name, campaign, service, date, expiry, commission, state, notes
						FROM Clients, Services
						WHERE idUser='".$_REQUEST['idUser']."' AND 
								Clients.id=Services.idClient 		AND
								$date_type >='".$_REQUEST['date_in']."' AND 
								$date_type <='".$_REQUEST['date_out']."'
						ORDER BY date");
	
	while ($e = mysql_fetch_assoc($q))
		$output[] = $e;
	
	print(json_encode($output));
	
	mysql_close();
?>