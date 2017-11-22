<?php

// Basename of the service - no trailing slash
define('BASENAME', 'http://www.valeriomattioli.com/server');

// PuSH Hub used to broadcast the feeds
define('PUSH_HUB', 'http://pubsubhubbub.appspot.com/');
define('PUSH_HUB_PUBLISH', 'http://pubsubhubbub.appspot.com/publish');

// Type of feed 
// Current choices are 'rss2' or 'atom'
define('FEED', 'atom');

// Type of connection to the RDF store
// Current choices are 'ARC2' or 'SPARQL'
define('CONNECTOR', 'ARC2');

// Edit if CONNECTOR == 'SPARQL'
// Key might be required depends on your SPARQL configuration
define('SPARQL_ENDPOINT', 'http://example.org/sparql/'); // http://example.org/sparql/  http://localhost:3030/Lombardia/
// Aditional parameters to be passed to the endpoint using POST
define('SPARQL_QUERY', 'query=');
// Must return JSON
define('SPARQL_JSON', 'output=json');
// For endpoint requiring a key
define('SPARQL_KEY', 'key=foo');

// Edit if CONNECTOR == 'ARC2'
// You'll also need to download ARC2
define('DB_HOST', '62.149.150.226');
define('DB_NAME', 'Sql808543_3');
define('DB_USER', 'Sql808543');
define('DB_PASS', '6m1lneno8w');
define('DB_STORE', 'server_push'); // DENOMINAZIONE TABELLE NEL DB LOCALE

// For debugging purposes - clients may not be able to subscribe if on
define('DEBUG', 0);
