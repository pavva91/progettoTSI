<?php

//require_once('./simplepie/simplepie.inc'); OLD ver 1.3 simplepie
require_once('./simplepie/build/compile.php');
require_once('./simplepie/SimplePie.compiled.php');

$data = file_get_contents('./feed');

$feed = new SimplePie();
$feed->set_raw_data($data);
$feed->init();

$out = "<dl>";

$items = $feed->get_items();
foreach ($items as $item)
{
	$url = $item->get_link();
	$title = $item->get_title();
	$date = $item->get_date();
	$author = $item->get_author();
	$desc = $item->get_description();
	$out .= "<dt><a href='$url'>$title</a> ($author - $date)</dt><dd>$desc</dd>";
}

echo $out . '</dl>';

?>
