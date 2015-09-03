
<?php
	//when app has uploaded,call this function to generate key_set for this app.
	function keygenerator($appname){
		for($i=0;$i<3;$i++){
			for($j=0;$j<3;$j++){
				$key_set[$i][$j]=array('0'=>substr(md5(uniqid(rand(), true)),0,16),'1'=>0); 
			}
		}
		$sql = "UPDATE `app` SET personal_key='".serialize($key_set)."' WHERE app_id2='".$appname."';";
		$result = mysql_query($sql) or die(mysql_error());
		
		for($k=0;$k<5;$k++){
			$secret[$k]=substr(md5(uniqid(rand(), true)),0,16);
		}
		$sql = "UPDATE `app` SET secret_value='".serialize($secret)."' WHERE app_id2='".$appname."';";
		$result = mysql_query($sql) or die(mysql_error());
	}
	
?>