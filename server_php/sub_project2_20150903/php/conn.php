<?php
/*****************************
*数据库连接
*****************************/
$database_dblink = "islab22222";
$username_dblink = "root";
$password_dblink = "";

$conn = @mysql_connect("localhost",$username_dblink ,$password_dblink );
if (!$conn){
    die("连接数据库失败：" . mysql_error());
}

mysql_select_db($database_dblink, $conn);
//字符转换，读库
mysql_query("set character set 'utf-8'");
//写库
mysql_query("set names 'utf-8'");
?>
