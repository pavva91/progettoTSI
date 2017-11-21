<?php

require_once("./subscriber.php");


require_once("./config.php");
/*
require_once('./simplepie/simplepie.inc');
*/

require_once('./simplepie/build/compile.php');
require_once('./simplepie/SimplePie.compiled.php');

parse_str($_SERVER['QUERY_STRING']);

function curl_get($url) {
		$ch = curl_init();
		curl_setopt($ch, CURLOPT_URL, $url);
		curl_setopt($ch, CURLOPT_HEADER, 0);
		curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
		$response = curl_exec($ch);
		if ($error = curl_error($ch)) {
			return array("$error.", "", 0);
		}

		$status_code = curl_getinfo($ch, CURLINFO_HTTP_CODE);
		$status_line = substr($response, 0, strcspn($response, "\n\r"));
		curl_close($ch);
		print_r($response);
		return array($response, $status_line, $status_code);
}
http://localhost/client/register.php?action=r&query=SELECT+%3Fsubject+%0D%0AWHERE+%7B%0D%0A++%3Fsubject+%3Chttp%3A%2F%2F
//www.dati.lombardia.it%2Fresource%2Fnf78-nj6b%2Fidsensore%3E+%222135%22.%0D%0A%7D&endpoint=http%3A%2F%2Flocalhost%2Fserver%2F
// Registration
if($action=='r' && $query) {
	// Get the feed
	$url = $endpoint.'?query='.urlencode($query);
//	echo $endpoint;
//	echo $url;

	$data = file_get_contents($url);
	$feed = new SimplePie();
	echo "data:";
	echo $data;
	echo ":data";

	$feed->set_raw_data($data);
	$feed->init();
	// Retrieve info
	$hubs = $feed->get_links('hub');
	$hub = $hubs[0];
	$urls = $feed->get_links('self');
	$url = $urls[0];
	// Subscribe
	echo $hub; // null quando metto la query mia, funziona quando $hub==http://pubsubhubbub.appspot.com/
	if (is_null($hub)){
		echo "CIAO";
	}else{
		echo "bella";
		echo $hub;
	}

	$s = new Subscriber($hub.'subscribe', BASENAME."/endpoint.php");
	$s->subscribe($url);
	if($s) {
		echo "Successfully registered at Server's Feed:  $url <br>to Hub:  ${hub}subscribe <br>with callback:  ".BASENAME."/endpoint.php";
	}
}
// Un-registration
elseif($action=='u') {
	$s = new Subscriber($hub.'subscribe', BASENAME."/endpoint.php");
	$s->unsubscribe($feed);
	if($s) {
		echo "Successfully unregistered $feed from ${hub}";
	}
}
// Display the form
else {
	echo "
<h2>Register</h2>

<form action='register.php'>
<input type='hidden' name='action' value='r'/>

Query:

<textarea name=\"query\" rows=\"10\" cols=\"80\">
SELECT ?uri ?author ?title ?date ?description
WHERE {
  ?uri a sioct:MicroblogPost ;
    sioc:has_creator ?author ;
    sioc:content ?description ;
    dct:title ?title ;
    dct:created ?date .	
} ORDER BY DESC(?date)
</textarea>

<br/>

sparqlPuSH interface (server SPARQL Endpoint):
<input name=\"endpoint\" size=\"80\"/>
	
<br/>

<input type=\"submit\" value=\"Register\"/>
</form>

<hr/>

<h2>Un-register</h2>

<h2>Register</h2>

<form action='register.php'>
<input type='hidden' name='action' value='u'/>

Feed URL:
<input name=\"feed\" size=\"80\"/>


<br/>

Hub:
<input name=\"hub\" size=\"80\" value=\"http://pubsubhubbub.appspot.com/\"/>

<br/>

<input type=\"submit\" value=\"Un-register\"/>
</form>

";

}

?>
