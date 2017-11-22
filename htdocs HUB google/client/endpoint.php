<?php
// L'HUB dopo la POST del register contenente il mode(=subscribe), callback(http://localhost/client/endpoint.php e il topic(feed creato dal server)
// se l'hub ritorna 202 allora va a verificare il callback inviando una richiesta al callback e il calback deve rispondere con
// codice HTTP 200 e echo the hub.challenge inviato dall'hub
$challenge = $_GET['hub_challenge'];

if($challenge) {
	// Echo the challenge to confirm subscription
	header('HTTP/1.1 200 "Found"', null, 200); // Risponde alla verifica dell'hub con codice HTTP 200
//    header('HTTP/1.0 400 "Not Found"', true, 400);
	print $challenge; // e echo challenge
//    echo $challenge;
} else {
	// Store the feed received by th PuSH hub
	$f = fopen(dirname(__FILE__).'/feed','w');
	fwrite($f, $HTTP_RAW_POST_DATA);
	fclose($f);
}
