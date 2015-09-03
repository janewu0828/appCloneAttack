<?php
	header("Content-Type: text/html; charset=utf-8") ;
	require_once("keydistribution.php");
	//change session start
	if(@$_REQUEST["personal_key"]&&@$_REQUEST["personal_key2"]&&@$_REQUEST["personal_key3"]&&@$_REQUEST["sess_load_file_name"]){//need change post
		$deviceid=@$_REQUEST["sess_deviceid"];
		$app_id=@$_REQUEST["app_id"];
		$app_id2=@$_REQUEST["app_id2"];	
		$load_file_name=@$_REQUEST["sess_load_file_name"];
		$personal_key=@$_REQUEST["personal_key"];
		$personal_key2=@$_REQUEST["personal_key2"];
		$personal_key3=@$_REQUEST["personal_key3"];

		include('conn.php');
		mysql_query("set names utf8"); 
		$sql="select username from member where personal_key = '".$personal_key."' and personal_key2 = '".$personal_key2."' and personal_key3 = '".$personal_key3."'" or die(mysql_error());
		$arr = mysql_query($sql);
		if($result = mysql_fetch_array($arr)){
			echo $result["username"];
		}
		else{//do tracing need revolk	
			echo "No this user , maybe it have traitors. \n";
			$sql="select * from member where personal_key= '".$personal_key."'" or die(mysql_error());
			$arr = mysql_query($sql);
			$i=0;
			while($result = mysql_fetch_array($arr)){
				// if(mysql_num_rows(mysql_query("SELECT * FROM tracing_log where deviceid='$result[deviceid]' and load_file_name='$load_file_name' and personal_key='$result[personal_key]' and personal_key2='$result[personal_key2]' and personal_key3='$result[personal_key3]'"))!=0)
				// 	mysql_query("UPDATE `tracing_log` SET `marks` = `marks`+1 where deviceid='$result[deviceid]' and load_file_name='$load_file_name' and personal_key='$result[personal_key]' and personal_key2='$result[personal_key2]' and personal_key3='$result[personal_key3]'");
				// else
				// $sql ="INSERT INTO `tracing_log` (`app_id2`, `deviceid`, `load_file_name`, `personal_key`, `personal_key2`, `personal_key3`,`marks`) VALUES ('$app_id2',$result[deviceid]','$load_file_name','$result[personal_key]','$result[personal_key2]','$result[personal_key3]',1)";
				// echo $sql;
					mysql_query("INSERT INTO `tracing_log` (`app_id`, `app_id2`, `deviceid`, `load_file_name`, `personal_key`, `personal_key2`, `personal_key3`,`marks`) VALUES ('$app_id','$app_id2','$result[deviceid]','$load_file_name','$result[personal_key]','$result[personal_key2]','$result[personal_key3]',1)");
				if($i==0){
					redistribution(0,$result['username'],$app_id2,$result['personal_key']);
				    $i++;
				}
				
			}
			// echo "-----------------------\n";
			$sql="select * from member where personal_key2= '".$personal_key2."'" or die(mysql_error());
			$arr = mysql_query($sql);
			$i=0;
			while($result = mysql_fetch_array($arr)){
				// if(mysql_num_rows(mysql_query("SELECT * FROM tracing_log where deviceid='$result[deviceid]' and load_file_name='$load_file_name' and personal_key='$result[personal_key]' and personal_key2='$result[personal_key2]' and personal_key3='$result[personal_key3]'"))!=0)
				// 	mysql_query("UPDATE `tracing_log` SET `marks` = `marks`+1 where deviceid='$result[deviceid]' and load_file_name='$load_file_name' and personal_key='$result[personal_key]' and personal_key2='$result[personal_key2]' and personal_key3='$result[personal_key3]'");
				// else
					mysql_query("INSERT INTO `tracing_log` (`app_id`, `app_id2`, `deviceid`, `load_file_name`, `personal_key`, `personal_key2`, `personal_key3`,`marks`) VALUES ('$app_id','$app_id2','$result[deviceid]','$load_file_name','$result[personal_key]','$result[personal_key2]','$result[personal_key3]',1)");
				if($i==0){
					redistribution(1,$result['username'],$app_id2,$result['personal_key2']);
				    $i++;
				}
			}
			// echo "-----------------------\n";
			$sql="select * from member where personal_key3= '".$personal_key3."'" or die(mysql_error());
			$arr = mysql_query($sql);
			$i=0;
			while($result = mysql_fetch_array($arr)){
				// if(mysql_num_rows(mysql_query("SELECT * FROM tracing_log where deviceid='$result[deviceid]' and load_file_name='$load_file_name' and personal_key='$result[personal_key]' and personal_key2='$result[personal_key2]' and personal_key3='$result[personal_key3]'"))!=0)
				// 	mysql_query("UPDATE `tracing_log` SET `marks` = `marks`+1 where deviceid='$result[deviceid]' and load_file_name='$load_file_name' and personal_key='$result[personal_key]' and personal_key2='$result[personal_key2]' and personal_key3='$result[personal_key3]'");
				// else
					mysql_query("INSERT INTO `tracing_log` (`app_id`, `app_id2`, `deviceid`, `load_file_name`, `personal_key`, `personal_key2`, `personal_key3`,`marks`) VALUES ('$app_id','$app_id2','$result[deviceid]','$load_file_name','$result[personal_key]','$result[personal_key2]','$result[personal_key3]',1)");
				if($i==0){
					redistribution(2,$result['username'],$app_id2,$result['personal_key3']);
				    $i++;
				}
			}
		}
	// $personal_key_update_status=TRUE;
	// $arr                            = array(

 //    'flag'                          => 'notempty',

 //    'time'=>$row['post_time'],

 //    'personal_key_update_status'=>$personal_key_update_status,

 //    'new_personal_key'=>$new_personal_key,

 //    'new_personal_key2'=>$new_personal_key2,

 //    'new_personal_key3'=>$new_personal_key3,

 //    'sessionid'=>$sessionid

 //    );     

 //    echo json_encode($arr);
	}
	else
		echo "personal_keys return error.";
?>