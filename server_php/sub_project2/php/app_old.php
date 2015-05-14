<?php
header("Content-Type: text/html; charset=utf-8") ;

class MCrypt {
    // 初始化向量(IV, Initialization Vector)
    private $iv = 'fedcba9876543210'; 
    // AES加解密的密鑰(personal_key)
    private $key = '0123456789abcdef'; 
    // private $iv = '9876543210'; 
    // private $key = '0123456789'; 


    function __construct() {
    }

    function encrypt($str) {

      //$key = $this->hex2bin($key);    
      $iv = $this->iv;

      $td = mcrypt_module_open('rijndael-128', '', 'cbc', $iv);

      mcrypt_generic_init($td, $this->key, $iv);
      $encrypted = mcrypt_generic($td, $str);

      mcrypt_generic_deinit($td);
      mcrypt_module_close($td);

      return bin2hex($encrypted);
    }

    function decrypt($code) {
      //$key = $this->hex2bin($key);
      $code = $this->hex2bin($code);
      $iv = $this->iv;

      $td = mcrypt_module_open('rijndael-128', '', 'cbc', $iv);

      mcrypt_generic_init($td, $this->key, $iv);
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
    $val                        = !empty($_GET[$str]) ? $_GET[$str] : null;
    return $val;
}

session_start();

$Action                         = isset($_GET["action"]) ? $_GET["action"] : null;
// appId
$app_id                         = htmlspecialchars($_POST["appId"]);
// UUID
$deviceid=$_POST["UUID"];
// IMEI
// $androidid=$_POST["IMEI"];

// 包含資料庫連接文件
include('conn.php');
mysql_query("set names utf8");
// 檢測用戶身份是否正確
$check_query                    = mysql_query("select purchase.app_id2, member.deviceid from purchase, member where purchase.username=member.username and purchase.app_id2='$app_id' and member.deviceid='$deviceid' limit 1");
$arr                            = array();   //空的陣列
if($result                      = mysql_fetch_array($check_query)){
    // 登入成功
    $_SESSION['app_id2']        = $result['app_id2'];
    $_SESSION['deviceid']       = $result['deviceid'];
    // $_SESSION['androidid']      = $result['androidid'];

    $sessionid                  = session_id();
    $_SESSION['$sessionid']     = $sessionid;

    // AES
    $mcrypt = new MCrypt();
    // Encrypt(secret_value)
    // $enable_block = $mcrypt->encrypt("9999999999123456"); //java.lang.StringIndexOutOfBoundsException
    // $enable_block2 = $mcrypt->encrypt("888888888123456"); //java.lang.StringIndexOutOfBoundsException
    $enable_block = $mcrypt->encrypt("1111111111123456");
    $enable_block2 = $mcrypt->encrypt("222222222123456");
    $enable_block3 = $mcrypt->encrypt("333333333123456");

    $arr                        = array(

    'flag'                      => 'success',
    
    'app_id'=>$result['app_id2'],  

    'deviceid'=>$result['deviceid'],  

    'enable_block'=>$enable_block,
    'enable_block2'=>$enable_block2,
    'enable_block3'=>$enable_block3,

    'sessionid'=>$sessionid  

    ); 
    
    
    echo json_encode($arr); 
    
} else {
	
    $arr                        = array(

    'flag'                      => 'error',

    'sessionid'=>$sessionid  

    ); 

    echo json_encode($arr);  
}

?>
