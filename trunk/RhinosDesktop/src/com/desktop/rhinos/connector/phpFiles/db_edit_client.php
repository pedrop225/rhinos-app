<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	UPDATE Clients 
								SET name='".$_REQUEST['name']."',
								b_date = '".$_REQUEST['b_date']."',
								tlf_1='".$_REQUEST['tlf_1']."',
								tlf_2='".$_REQUEST['tlf_2']."',
								mail='".$_REQUEST['mail']."',
								consultancy='".$_REQUEST['consultancy']."'
								WHERE (id='".$_REQUEST['id']."')");	
	
	$q = mysql_query("	UPDATE Address
								SET tipo_via='".$_REQUEST['tipo_via']."',
								nombre_via='".$_REQUEST['nombre_via']."',
								numero='".$_REQUEST['numero']."',
								portal='".$_REQUEST['portal']."',
								escalera='".$_REQUEST['escalera']."',
								piso='".$_REQUEST['piso']."',
								puerta='".$_REQUEST['puerta']."',
								poblacion='".$_REQUEST['poblacion']."',
								municipio='".$_REQUEST['municipio']."',
								cp='".$_REQUEST['cp']."'
								WHERE (id='".$_REQUEST['id']."')");	
	mysql_close();
?>