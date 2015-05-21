
<?php
	//when app has uploaded,call this function to generate key_set for this app.
	
	function keygenerator($appname){
	require_once("conn.php");
		for($i=0;$i<3;$i++){
			for($j=0;$j<3;$j++){
				$key_set[$i][$j]=array('0'=>substr(md5(uniqid(rand(), true)),0,16),'1'=>0); 
			}
		}
		$sql = "UPDATE `app` SET personal_key='".serialize($key_set)."' WHERE note='".$appname."';";
		$result = mysql_query($sql) or die(mysql_error());
		
	}
	
?>