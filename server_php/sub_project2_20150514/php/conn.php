<?php
/*****************************
*資料庫連接
*****************************/
// $database_dblink = "islab";
$database_dblink = "islab22222";
$username_dblink = "root";
$password_dblink = "";

$conn = @mysql_connect("localhost",$username_dblink ,$password_dblink );
if (!$conn){
    die("database connection error: " . mysql_error());
}

mysql_select_db($database_dblink, $conn);
//字符轉換，讀取資料庫
mysql_query("set character set 'utf-8'");
//寫入資料庫
mysql_query("set names 'utf-8'");
?>
