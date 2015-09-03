<?php 
    $s1 = '6732e13e44837534';
    $s2 = '8a00dd5e4e2b2806';
    $s3 = '6d521886d5caed11';
    $x = bin2hex(pack('H*',$s1) ^ pack('H*',$s2));
    $y = bin2hex(pack('H*',$x) ^ pack('H*',$s3));
    echo $y;
?>