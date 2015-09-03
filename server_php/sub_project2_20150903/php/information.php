<?php
	header("Content-Type: text/html; charset=utf-8") ;
	$deviceid=$_POST["UUID"];//need apps.apkid , apps.name , app.app_id2 
	include('conn.php');
	mysql_query("set names utf8");
	$sql = "select apps.apkid ,apps.name ,apps.icon ,member.deviceid ,purchase.app_id2 from apps,member,purchase where member.deviceid='$deviceid' and purchase.username=member.username and purchase.note=apps.name";  
	$result = mysql_query($sql) or die(mysql_error());
	$arr=array();
	$img_url="http://140.118.19.64:8081/../../islab/public/";
	if(mysql_num_rows($result)!=0){
	    while($row=mysql_fetch_array($result)){
	    	//echo $row["app_id2"]." , ".$row["apkid"]." , ".$row["name"]." ; \n";
	    	$arr[]=array(
	    		"app_id2"=>$row["app_id2"],
	    		"apkid"=>$row["apkid"],
	    		"name"=>$row["name"],
	    		"img_url"=>$img_url.$row["icon"],
	    		"filename"=>$row["icon"]
	    	);
	    	// $arr["app_id2"][]=$row["app_id2"];
	    	// $arr["apkid"][]=$row["apkid"];
	    	// $arr["name"][]=$row["name"];
	    }
	}
	else{
		$arr["flag"]="fail";
	}
	$json=json_encode($arr);
	print_r($json);
?>	
