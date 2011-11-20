<?php
	include 'db_settings.php';
	
	mysql_connect($mysql_host, $mysql_user, $mysql_password);
	mysql_select_db($mysql_database);
	
	$q = mysql_query("	INSERT INTO Campinfo (id, service, commission) 
						VALUES ('".$_REQUEST['id']."',
								'".$_REQUEST['service']."',
								'".$_REQUEST['commission']."')");
	
	print("#<b>".$_REQUEST['service']."</b># Inserted.");
	
	mysql_close();
?>