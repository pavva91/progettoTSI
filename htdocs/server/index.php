<?php

$config = include('./config.php');
include_once('./lib/SPARQLPuSH.php');

$sp = new SPARQLPuSH($config);
$sp->go();

?>