<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Clients (id, name, tlf_1, tlf_2, mail, consultancy) 
						VALUES(	'".$_REQUEST['id']."',
								'".$_REQUEST['name']."',
								'".$_REQUEST['tlf_1']."',
								'".$_REQUEST['tlf_2']."',
								'".$_REQUEST['mail']."',
								'".$_REQUEST['consultancy']."')");
	
	$q = mysql_query("	INSERT INTO Address (id, tipo_via, nombre_via, numero, 
											portal, escalera, piso, puerta, poblacion,
											municipio, cp)
						VALUES(	'".$_REQUEST['id']."',
								'".$_REQUEST['tipo_via']."',
								'".$_REQUEST['nombre_via']."',
								'".$_REQUEST['numero']."',
								'".$_REQUEST['portal']."',
								'".$_REQUEST['escalera']."',
								'".$_REQUEST['piso']."',
								'".$_REQUEST['puerta']."',
								'".$_REQUEST['poblacion']."',
								'".$_REQUEST['municipio']."',
								'".$_REQUEST['cp']."')");
	mysql_close();
?>