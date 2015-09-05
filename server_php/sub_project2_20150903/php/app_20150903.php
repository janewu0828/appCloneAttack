<?php
header("Content-Type: text/html; charset=utf-8") ;

class MCrypt {
  // 初始化向量(IV, Initialization Vector)
  private $iv = 'fedcba9876543210'; 
  // AES加解密的密鑰(personal_key)

  function __construct() {
  }

  function encrypt($key,$str) {
    //$key = $this->hex2bin($key);    
    $iv = $this->iv;

    $td = mcrypt_module_open('rijndael-128', '', 'cbc', $iv);

    mcrypt_generic_init($td, $key, $iv);
    $encrypted = mcrypt_generic($td, $str);

    mcrypt_generic_deinit($td);
    mcrypt_module_close($td);

    return bin2hex($encrypted);
  }
  
  function decrypt($key,$code) {
    //$key = $this->hex2bin($key);
    $code = $this->hex2bin($code);
    $iv = $this->iv;

    $td = mcrypt_module_open('rijndael-128', '', 'cbc', $iv);

    mcrypt_generic_init($td, $key, $iv);
    $decrypted = mdecrypt_generic($td, $code);

    mcrypt_generic_deinit($td);
    mcrypt_module_close($td);

    return utf8_encode(trim($decrypted));
  }
  protected function hex2bin($hexdata) {
    $bindata = '';

    for ($i = 0; $i < strlen($hexdata); $i += 2) {
          $bindata .= chr(hexdec(substr($hexdata, $i, 2)));
    }

    return $bindata;
  }
  function getKey() {

    return $this->key;
  }
}

function _get($str){
  $val = !empty($_GET[$str]) ? $_GET[$str] : null;
  return $val;
}
function xor_string($first,$second) {
  // Our output text
  $outText = bin2hex(pack('H*',$first) ^ pack('H*',$second)); 
  return $outText;
}

session_start();

// // appId
// $app_id = htmlspecialchars($_POST["appId"]);//need post
// appId
$app_id = $_POST["appId"];
// appId2
$app_id2  = htmlspecialchars($_POST["appId2"]);
$jarFlag = htmlspecialchars($_POST["jarFlag"]);
// UUID
$deviceid=$_POST["UUID"];// need post
$jarname=$_POST["jarName"]; // need post 
//包含資料庫連接文件
include('conn.php');
mysql_query("set names utf8");  
// 檢測用戶身份是否正確
// $check_query = mysql_query("select purchase.app_id, member.deviceid from purchase, member where purchase.username=member.username and purchase.app_id='$app_id' and member.deviceid='$deviceid' limit 1");
$check_query                    = mysql_query("select purchase.app_id2, member.deviceid from purchase, member where purchase.username=member.username and purchase.app_id2='$app_id2' and member.deviceid='$deviceid' limit 1");
$arr = array();   //空的陣列
if($result = mysql_fetch_array($check_query)){
    // 登入成功
    // $_SESSION['app_id'] = $result['app_id'];
    $_SESSION['app_id2'] = $result['app_id2'];
    $_SESSION['deviceid'] = $result['deviceid'];
    //jarname save session
    $_SESSION['jarname'] = $jarname;
    $_SESSION['sessionid']     = session_id();

    // AES
    $mcrypt = new MCrypt();
    // get secret value
    // $sql = "SELECT secret_value FROM app WHERE app_id='".$_SESSION['app_id']."';";
    $sql = "SELECT secret_value FROM app WHERE app_id2='".$_SESSION['app_id2']."';";
    $result = mysql_query($sql) or die(mysql_error());
    $row1=mysql_fetch_array($result); 
    $secret_value_set= unserialize($row1[0]);

    //分配 secret value
    for($i=0;$i<3;$i++){
      $n=rand(0,4);
      $secret_value[]=$secret_value_set[$n];
    } 
    //get personal keys
    $sql = "SELECT personal_key,personal_key2,personal_key3 FROM member WHERE deviceid='".$_SESSION['deviceid']."';";
    $result = mysql_query($sql) or die(mysql_error());
    $row2=mysql_fetch_array($result); 

    // echo ("pk1 : ".$row2[0]."sv1 : ".$secret_value[0]."<br>");
    // echo ("pk2 : ".$row2[1]."sv2 : ".$secret_value[1]."<br>");
    // echo ("pk3 : ".$row2[2]."sv3 : ".$secret_value[2]."<br>");

    //ebs
    $enable_block1 = $mcrypt->encrypt($row2[0],$secret_value[0]);
    $enable_block2 = $mcrypt->encrypt($row2[1],$secret_value[1]);
    $enable_block3 = $mcrypt->encrypt($row2[2],$secret_value[2]);

    // echo $enable_block3."<br>";
    // $test=$mcrypt->decrypt($row2[2],$enable_block3);
    // echo $test."<br>";

    //xor secret value to session key
    $xor_key=xor_string($secret_value[0],$secret_value[1]);
    $xor_key=xor_string($xor_key,$secret_value[2]);
    // echo $xor_key."<br>";

    // //get jar contents
    // $sql = "SELECT ".$jarname." FROM app WHERE app_id='".$_SESSION['app_id']."';";
    // $sql = "SELECT ".$jarname." FROM app WHERE app_id2='".$_SESSION['app_id2']."';";
    // $result = mysql_query($sql) or die(mysql_error());
    // $row3=mysql_fetch_array($result); 
    // $jar_contents=file_get_contents($row3[0]);
    if($jarFlag=="0")
      $sql = "SELECT jar FROM app WHERE app_id2='".$_SESSION['app_id2']."';";
    else 
      $sql = "SELECT "."jar".$jarFlag." FROM app WHERE app_id2='".$_SESSION['app_id2']."';";
    $result = mysql_query($sql) or die(mysql_error());
    $row3=mysql_fetch_array($result); 
    $jar_contents=file_get_contents("../download/".$row3[0]);
    // echo $jar_contents;
    // file_put_contents('new_encrypted.jar', $jar_contents);

    // cbs
    $cipher_block = $mcrypt->encrypt($xor_key,$jar_contents);
    file_put_contents('../download/encrypted/'.$jarname, $cipher_block);

    $d_jar_contents=file_get_contents('../download/encrypted/'.$jarname);
    $d_cipher_block = $mcrypt->decrypt($xor_key,$d_jar_contents);
    $d_jarname='aaa.jar';
    file_put_contents('../download/encrypted/'.$d_jarname, $d_cipher_block);

    $logo_jar_contents=file_get_contents("../download/"."logo.png");
    $logo_cipher_block = $mcrypt->encrypt($xor_key,$logo_jar_contents);
    $logo_jarname='enc_logo.png';
    file_put_contents('../download/encrypted/'.$logo_jarname, $logo_cipher_block);

    $d_logo_jar_contents=file_get_contents('../download/encrypted/'.$logo_jarname);
    $d_logo_cipher_block = $mcrypt->decrypt($xor_key,$d_logo_jar_contents);
    $d_logo_jarname='dec_logo.png';
    file_put_contents('../download/encrypted/'.$d_logo_jarname, $d_logo_cipher_block);

    // echo $cipher_block;
    $cipher_jar_uri = "http://140.118.109.165:8081/sub_project2/download/encrypted/".$jarname;
    $arr = array(
      'flag'=>'success',

      'enable_block'=>$enable_block1,
      'enable_block2'=>$enable_block2,
      'enable_block3'=>$enable_block3,
      'cipher_jar_uri' =>$cipher_jar_uri,
      'hahaha' =>$xor_key,
      'sessionid'=>$_SESSION['sessionid']
    ); 
          // 'app_id'=>$_SESSION['app_id'],  
    echo json_encode($arr); 
}
else{
	
    $arr = array(
    'flag'=> 'error',
    ); 
    echo json_encode($arr);  
}

?>
