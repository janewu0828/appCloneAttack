<?php
header("Content-Type: text/html; charset=utf-8") ;

function _get($str){
    $val = !empty($_GET[$str]) ? $_GET[$str] : null;
    return $val;
}
session_start();
$Action = isset($_GET["action"]) ? $_GET["action"] : null;
// appId = hash(apk)
$app_id = htmlspecialchars($_POST["appId"]);
// appId2 = hash(pkg_name) for test
$app_id2 = htmlspecialchars($_POST["appId2"]);
// UUID
$deviceid=$_POST["UUID"];

// 包含資料庫連接文件
include('conn.php');
mysql_query("set names utf8");
// 檢測用戶身份是否正確
$sql="select apps.userid, member.deviceid "
    . "from member, apps "
    . "where apps.userid=member.username "
    . "and member.deviceid='$deviceid' ";
// $sql2 = "select apps.name, apps.ver, apps.cmt, apps.updated_at "
//     . "from member, apps, tracing_log "
//     . "where apps.userid=member.username "
//     . "and member.deviceid=$deviceid "
//     . "group by apps.name";
$sql3="select apps.userid, member.deviceid, apps.name, apps.ver, apps.cmt, apps.updated_at "
    . "from member, apps, tracing_log "
    . "where apps.userid=member.username "
    . "and member.deviceid='$deviceid' "
    . "group by apps.name";

$check_query = mysql_query($sql);
$arr=array();	//空的陣列

$check_query2 = mysql_query($sql3);
$row=array();	//空的陣列
$name='';	//空的字串
$ver='';	//空的字串
$cmt='';	//空的字串
$updated_at='';	//空的字串

if($result = mysql_fetch_array($check_query)){
    // 登入成功
    $_SESSION['userid'] = $result['userid'];
    $_SESSION['deviceid'] = $result['deviceid'];
    $sessionid=session_id();    
    $_SESSION['$sessionid'] = $sessionid;

	$i=0;
	while ($row = mysql_fetch_assoc($check_query2)) {
	    // echo $row["name"].'<br />';
	    // echo $row["cmt"].'<br />';
	    // echo $row["ver"].'<br />';

	    $name=$name.','.$row["name"];
	    $ver=$ver.','.$row["ver"];
	    $cmt=$cmt.','.$row["cmt"];
	    $updated_at=$updated_at.','.$row["updated_at"];

		$arr[$i]= array(  

		'flag'=>'success',

		'name'=>$row["name"],  

		'ver'=>$row["ver"],  

		'cmt'=>urlencode($row["cmt"]),  

		'updated_at'=>$row["updated_at"], 

		'sessionid'=>$sessionid  

		); 

		$i++;
		 
	}

	echo urldecode(json_encode($arr));

	// $arr = array(  

	// 'flag'=>'success',

	// 'name'=>$name,  

	// 'ver'=>$ver,  

	// 'cmt'=>urlencode($cmt),  

	// 'updated_at'=>$updated_at, 

	// 'sessionid'=>$sessionid  
	//
	// ); 

	// echo urldecode(json_encode($arr)); 
    
} else {
	
	$arr = array(  

    'flag'=>'error',
    
    'name'=>'',  

    'ver'=>'',  

    'cmt'=>'',  

    'updated_at'=>'', 

    'sessionid'=>$sessionid  

	); 

    echo json_encode($arr);  
}
?>
