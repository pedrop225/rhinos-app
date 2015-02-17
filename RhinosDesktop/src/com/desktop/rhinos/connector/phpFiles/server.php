<?php
	class ROperations {
		
		private $mysql_host = "mysql14.000webhost.com";
		private $mysql_database = "a6294139_db";
		private $mysql_user = "a6294139_user";
		private $mysql_password = "456123a";
		
		function login($user, $password) {
			$db = new mysqli($mysql_host, $mysql_user, $mysql_password, $mysql_database);	
		}
	}
	
	$options = array("uri"=>"http://www.pedroapv.com/rhino/ws/");
	$server = new SoapServer(NULL, $options);
	$server->setClass("ROperations");
	$server->handle();
?>