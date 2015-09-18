<?php
header("Content-Type: text/html; charset=utf-8") ;

    function strtohex($x) 
    {
        $s='';
        foreach (str_split($x) as $c) $s.=sprintf("%02X",ord($c));
        return($s);
    } 

    $iv = 'fedcba9876543210';
    $pass = '1234567812345678';
    $method = 'aes-128-cbc';

    // echo "\niv in hex to use: ".strtohex ($iv);
    // echo '<br>';
    // echo '<br>';
    // echo "\nkey in hex to use: ".strtohex ($pass);
    // echo '<br>';
    // echo '<br>';
    // echo "\n";
    
    /** openssl encrypt **/
    $source_folder_path='../download';
    $output_folder_path=$source_folder_path.'/encrypted';

    $source=file_get_contents($source_folder_path.'/output123.jar');
    $data_enc_path='/encrypted123_openssl.jar';

    file_put_contents ($output_folder_path.$data_enc_path,openssl_encrypt ($source, $method, $pass, true, $iv));  
    $exec = "openssl enc -".$method." -d -in file.encrypted -nosalt -nopad -K ".strtohex($pass)." -iv ".strtohex($iv);

    exec ($exec);
    // echo 'executing: '.$exec."\n\n";
    // echo '<br>';
    // echo '<br>';

    /** openssl decrypt **/
    $source_data=file_get_contents($output_folder_path.$data_enc_path);
    $data_dec_path='/dec_encrypted123_openssl.jar';

    file_put_contents ($output_folder_path.$data_dec_path,openssl_decrypt ($source_data, $method, $pass, true, $iv));  
    $exec = "openssl dec -".$method." -d -in file.encrypted -nosalt -nopad -K ".strtohex($pass)." -iv ".strtohex($iv);

    exec ($exec);
    // echo 'executing: '.$exec."\n\n";
    // echo '<br>';
    // echo '<br>';

?>