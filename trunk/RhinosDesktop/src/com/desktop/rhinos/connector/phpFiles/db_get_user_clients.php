<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	SELECT DISTINCT Clients.id, b_date, name, Clients.tlf_1, Clients.tlf_2, mail, consultancy,
										tipo_via, nombre_via, numero, portal, escalera, piso, puerta, 
										poblacion, municipio, cp
						FROM Clients, Address, Services 
						WHERE (Clients.id = Services.idClient) and (Clients.id = Address.id) and (idUser = '".$_REQUEST['idUser']."')
						ORDER BY date DESC
						LIMIT 0, 150");
	
	while ($e = mysql_fetch_assoc($q))
		$output[] = $e;

	print(json_encode($output));
	
	mysql_close();
?>