<?php
// Dreamweaver的函式庫
if (!function_exists("GetSQLValueString")) {
    function GetSQLValueString($theValue, 
        $theType, 
        $theDefinedValue            = "",
        $theNotDefinedValue         = "")
    {

      if (PHP_VERSION < 6) {
        $theValue                   = get_magic_quotes_gpc() ? stripslashes($theValue) : $theValue;
      }

        $theValue                   = function_exists("mysql_real_escape_string") ? mysql_real_escape_string($theValue) : mysql_escape_string($theValue);

      switch ($theType) {
        case "text":                
            $theValue               = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
          break;    
        case "long":                
        case "int":                 
            $theValue               = ($theValue != "") ? intval($theValue) : "NULL";
          break;
        case "double":              
            $theValue               = ($theValue != "") ? doubleval($theValue) : "NULL";
          break;
        case "date":                
            $theValue               = ($theValue != "") ? "'" . $theValue . "'" : "NULL";
          break;
        case "defined":             
            $theValue               = ($theValue != "") ? $theDefinedValue : $theNotDefinedValue;
          break;
      }
      return $theValue;

    }
}

header("Content-Type: text/html; charset=utf-8") ;
// 包含資料庫連接文件
include('conn.php');
//獲取從客戶端傳遞的參數，接收POST/GET的資料
$deviceid=@$_REQUEST["sess_deviceid"];
$load_file_name=@$_REQUEST["sess_load_file_name"];
$personal_key=@$_REQUEST["sess_personal_key"];
$personal_key2=@$_REQUEST["sess_personal_key2"];
$personal_key3=@$_REQUEST["sess_personal_key3"];
//獲取客戶端傳遞的session標識
$sessionid=$_POST['sess_sessionid'];

session_id($sessionid);
//將會根據session id獲得原來的session
session_start(); 
//獲取服務器端原來session記錄的username,並且根據客戶端傳過來的username比較進行驗證操作
$sess_app_id=$_SESSION['app_id'];
$sess_app_id2=$_SESSION['app_id2'];
$sess_deviceid=$_SESSION['deviceid'];

// $database_dblink                    = "islab";
$database_dblink                    = "islab22222";
$username_dblink                    = "root";
$password_dblink                    = "";
// 建立資料庫連線
$dblink                             = mysql_pconnect("localhost", $username_dblink, $password_dblink) or trigger_error(mysql_error(),E_USER_ERROR);

mysql_query("SET NAMES utf8",$dblink); 
mysql_query("SET CHARACTER_SET_CLIENT=utf8",$dblink); 
mysql_query("SET CHARACTER_SET_RESULTS=utf8",$dblink); 
mysql_select_db($database_dblink, $dblink);

if($deviceid==$sess_deviceid){

    $arr                            = array();   //空的陣列

    // 如果有資料
    if (strcmp(trim($load_file_name), "")!=0)
    { 
        // 將資料輸入進資料庫
        $insertSQL                  = sprintf(
            "INSERT INTO `tracing_log` (`app_id`,`app_id2`, `deviceid`, `load_file_name`, `personal_key`, `personal_key2`, `personal_key3`) 
            VALUES ('%s','%s','%s','%s','%s','%s','%s');", $sess_app_id,$sess_app_id2,$deviceid,$load_file_name,$personal_key,$personal_key2,$personal_key3);
        
        mysql_query($insertSQL, $dblink) or die(mysql_error());
    }

    // 從資料庫撈出來最後一筆資料
    $query_rs                       = "SELECT * FROM `tracing_log` order by t_id desc limit 0,1";
    $rs                             = mysql_query($query_rs, $dblink) or die(mysql_error());
    $row                            = mysql_fetch_assoc($rs);

    $personal_key_update_status=TRUE;
    $new_personal_key="0123456789abcdef";
    $new_personal_key2="0123456789abcdef";
    $new_personal_key3="0123456789abcdef";
         
    $arr                            = array(

    'flag'                          => 'notempty',

    'time'=>$row['post_time'],

    'personal_key_update_status'=>$personal_key_update_status,

    'new_personal_key'=>$new_personal_key,

    'new_personal_key2'=>$new_personal_key2,

    'new_personal_key3'=>$new_personal_key3,

    'sessionid'=>$sessionid

    );     

    echo json_encode($arr); 
    
} 
else {
	
    $arr                            = array(

    'flag'                          => 'empty',

    'sessionid'=>$sessionid

    );	

    echo json_encode($arr);  
}

?>