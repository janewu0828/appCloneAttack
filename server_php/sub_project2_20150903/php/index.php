<?php
	require_once("conn.php");
	require_once("keygenerator.php");
	require_once("keydistribution.php");
	$appname='7d070d811e4fe1167622cbcc03e3cebc73347242';
	$arr=array("projecttwotwo");
	keygenerator($appname);
	foreach ($arr as $value) {
		keydistribution($value,$appname);
    }

?>