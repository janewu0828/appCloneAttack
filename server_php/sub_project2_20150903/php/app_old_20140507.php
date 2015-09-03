<?php
header("Content-Type: text/html; charset=utf-8") ;

function _get($str){
    $val = !empty($_GET[$str]) ? $_GET[$str] : null;
    return $val;
}
session_start();
// $Action = isset($_GET["action"]) ? $_GET["action"] : null;

// appId = hash(apk)
$app_id = htmlspecialchars($_POST["appId"]);
// appId2 = hash(pkg_name) for test
$app_id2 = htmlspecialchars($_POST["appId2"]);
// UUID
$deviceid=$_POST["UUID"];
// IMEI
// $androidid=$_POST["androidid"];


//包含資料庫連接文件
include('conn.php');
mysql_query("set names utf8");
// 檢測用戶身份是否正確
$check_query = mysql_query("select purchase.app_id2, member.deviceid from purchase, member where purchase.username=member.username and purchase.app_id2='$app_id2' and member.deviceid='$deviceid' limit 1");
$arr=array();   //空的陣列
if($result = mysql_fetch_array($check_query)){
    // 登入成功
    $_SESSION['app_id2'] = $result['app_id2'];
    $_SESSION['deviceid'] = $result['deviceid'];
    // $_SESSION['androidid'] = $result['androidid'];

    $sessionid=session_id();    
    $_SESSION['$sessionid'] = $sessionid;
    
    $arr = array(  

    'flag'=>'success',

    'sessionid'=>$sessionid  

 ); 
    
    echo json_encode($arr); 
    
} else {
	
	 $arr = array(  

    'flag'=>'error',

    'sessionid'=>$sessionid  

 ); 
    echo json_encode($arr);  
}
?>
