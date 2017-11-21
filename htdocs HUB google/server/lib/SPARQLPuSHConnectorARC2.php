<?php

include_once('./lib/arc/ARC2.php');

class SPARQLPuSHConnectorARC2 extends SPARQLPuSHConnector {
	
	var $store;
	
	function __construct() {

		$config = array(
		    /* db */
			'db_host' => DB_HOST,
			'db_name' => DB_NAME,
			'db_user' => DB_USER,
			'db_pwd' => DB_PASS,

            /* store */
			'store_name' => DB_STORE,
//            AGGIUNTO VALERIO
            /* store */
//            'store_name' => 'arc_tests',
            /* network */
//            'proxy_host' => '192.168.1.1',
//            'proxy_port' => 8080,
//            /* parsers */
//            'bnode_prefix' => 'bn',
//            /* sem html extraction */
//            'sem_html_formats' => 'rdfa microformats',
//            FINE AGGIUNTO VALERIO
		);

		$this->store = ARC2::getStore($config);  // CREA LO STORE (TABELLE NEL DB MYSQL)
		if (!$this->store->isSetUp()) {
			$this->store->setUp();
		}
	}
	
	public function query($query) {
		$res = $this->store->query(parent::query($query));
        if ($errs = $this->store->getErrors()) {
            print_r(array_values($errs));
//    echo $errs;
            /* $errs contains errors from the store and any called
               sub-component such as the query processor, parsers, or
               the web reader */
            return $errs;
        }
        else return array('vars' => $res['result']['variables'], 'rows' => $res['result']['rows']);

//		return array('vars' => $res['result']['variables'], 'rows' => $res['result']['rows']);
	}
	
}