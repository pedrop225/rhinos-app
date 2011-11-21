<?php
	$to = $_REQUEST['mail'];
	$subject = "Confirmación de registro (RhinosApp)";
	
	$headers = "From:<RhinosApp>"."\r\n";
	$headers .= 'MIME-Version: 1.0' . "\r\n";
	$headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";

	$message = "<html>\n\t<body>";
	$message .= "	Estimado <b>".$_REQUEST['user'].":</b></br></br>";
	$message .= "<p>Con este mail le confirmamos que su dirección de correo (".$_REQUEST['mail'].") ha sido registrada recientemente en el servidor ". 
				"de RhinosApp. Rogamos no responda al mismo, ya que ha sido elaborado por un sistema de gestión automática. ". 
				"A continuación se encuentran los datos de su registro. </p></br></br>".
				
				"<b>Datos de registro:</b></br>".

				"<blockquote>".
				"usuario: <b>".$_REQUEST['user']."</b><br>".
				"contraseña: <b>".$_REQUEST['password']."</b></br></br>".
				"</blockquote>".
				
				"Atentamente: </br>".
				"<blockquote><b>RhinosApp Services</b></blockquote>";
	
	$message .= "\t</body>\n</html>";
	
	mail($to, $subject, $message, $headers);
?>