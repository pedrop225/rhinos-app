<?php
	$options = array(	"location" => "http://www.pedroapv.com/rhino/ws/server.php", 
						"uri" => "http://www.pedroapv.com/rhino/ws/");
	$client = new SoapClient(NULL, $options);
	
	echo $client->login("a", "a");
?>