<?
	//when user sign up, call this function to distribute Personal_key for this user.
	
	function keydistribution($username){
	require_once("conn.php");
		$appname='Guess A of hearts';
		$sql = "SELECT personal_key FROM app WHERE note='".$appname."';";
		$result = mysql_query($sql) or die(mysql_error());
		$row2=mysql_fetch_array($result);
		
		$key_set= unserialize($row2[0]);

		for($i=0;$i<3;$i++){ 
			$n=rand(0,2);
			while($key_set[$i][$n][1]==2){
				$n=rand(0,2);
			};
			$key_set[$i][$n][1]++;
			if($i==0){
				$sql = "UPDATE `member` SET personal_key='".$key_set[$i][$n][0]."' WHERE username='".$username."';";
			}else{
				$sql = "UPDATE `member` SET personal_key".($i+1)."='".$key_set[$i][$n][0]."' WHERE username='".$username."';";
			}
			$result = mysql_query($sql) or die('MySQL query error');
		}	
		$sql = "SELECT note FROM purchase WHERE username='".$username."';";
		$result = mysql_query($sql) or die(mysql_error());
		$row2=mysql_fetch_array($result);
		
		$sql = "UPDATE `app` SET personal_key='".serialize($key_set)."' WHERE note='".$row2[0]."';";
		$result = mysql_query($sql) or die(mysql_error());
	}
?>