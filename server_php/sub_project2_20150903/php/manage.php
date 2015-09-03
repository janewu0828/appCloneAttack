<?php
include('conn.php');
mysql_query("set names utf8"); 


echo '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>CITY</title>
<meta http-equiv="Content-Language" content="English" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
<link rel="stylesheet" type="text/css" href="bootstrap-3.3.5-dist/css/bootstrap-theme.css"/>
<link rel="stylesheet" type="text/css" href="bootstrap-table-master/src/bootstrap-table.css" />
<script src="bootstrap-table.js"></script>
<script language="JavaScript" src="action.js"></script>
</head>
<body>

<div id="wrap">
<div id="header">
<h1>&nbsp;</h1>

</div>
<div class="carousel-caption" style="margin-left:100px">
<h1>Tracing List from Enhancer</h1>
<h3>Mangers can delete some illigel users in this website.</h3>
</div>
<div id="content">
<div class="left"> 
<table data-toggle="table" data-url="test.json" data-cache="false" data-height="299">
    <thead>
        <tr>
            <th data-field="deviceid" style="background-color:#BDBDBD">Device ID</th>
            <th data-field="load_file_name" style="background-color:#BDBDBD">Load File Name</th>
            <th data-field="number_of_mark" style="background-color:#BDBDBD">Number of Mark</th>
            <th data-field="action" style="background-color:#BDBDBD">Action</th>
        </tr>';
        $arr=mysql_query("SELECT deviceid, load_file_name, count(*) FROM tracing_log GROUP BY deviceid, load_file_name HAVING count(*) > 1")or die(mysql_error());;
		while($result = mysql_fetch_array($arr)){
			echo ' <tr>
            <th data-field="deviceid">'.$result["deviceid"].'</th>
            <th data-field="load_file_name">'.$result["load_file_name"].'</th>
            <th data-field="number_of_mark">'.$result["count(*)"].'</th>
            <th data-field="action"><button type="button" class="btn btn-danger" onclick="delete_user(\'' . $result["deviceid"] . '\');">Delete</button></th>
        	</tr>';
		}
   echo '</thead>
</table>
<h2><a href="http://www.worldclasslasik.com
">License and terms of use</a></h2>
<div class="articles">
Matrix Rounded is a CSS template that is free and fully standards compliant. <a href="http://www.worldclasslasik.com
/">Newyorklasik</a> designed this template.
This template is allowed for all uses, including commercial use, as it is released under the <a href="http://creativecommons.org/licenses/by/3.0/">Creative Commons Attributions</a> license, so you’re pretty much free to do whatever you want with it  (even use it commercially) provided you keep the links in the footer  intact. Aside from that, have fun with it :)
<br /><br />
<img src="images/pic.jpg" alt="Example pic" style="border: 3px solid http://www.worldclasslasik.com
ccc;" />
<br /><br />
Donec nulla. Aenean eu augue ac nisl tincidunt rutrum. Proin erat justo, pharetra eget, posuere at, malesuada 
et, nulla. Donec pretium nibh sed est faucibus suscipit. Nunc nisi. Nullam vehicula. In ipsum lorem, bibendum sed, 
consectetuer et, gravida id, erat. Ut imperdiet, leo vel condimentum faucibus, risus justo feugiat purus, vitae 
congue nulla diam non urna.
</div>
<h2><a href="http://www.worldclasslasik.com
">Title with a link - Example of heading 2</a></h2>
<div class="articles">
Donec nulla. Aenean eu augue ac nisl tincidunt rutrum. Proin erat justo, pharetra eget, posuere at, malesuada 
et, nulla. Donec pretium nibh sed est faucibus suscipit. Nunc nisi. Nullam vehicula. In ipsum lorem, bibendum sed, 
consectetuer et, gravida id, erat. Ut imperdiet, leo vel condimentum faucibus, risus justo feugiat purus, vitae 
congue nulla diam non urna.
</div>
</div>

<div class="right"> 

<h2>Categories :</h2>
<ul>
<li><a href="http://www.worldclasslasik.com
">World Politics</a></li> 
<li><a href="http://www.worldclasslasik.com
">Europe Sport</a></li> 
<li><a href="http://www.worldclasslasik.com
">Networking</a></li> 
<li><a href="http://www.worldclasslasik.com
">Nature - Africa</a></li>
<li><a href="http://www.worldclasslasik.com
">SuperCool</a></li> 
<li><a href="http://www.worldclasslasik.com
">Last Category</a></li>
</ul>

<h2>Archives</h2>
<ul>
<li><a href="http://www.worldclasslasik.com
">January 2007</a></li> 
<li><a href="http://www.worldclasslasik.com
">February 2007</a></li> 
<li><a href="http://www.worldclasslasik.com
">March 2007</a></li> 
<li><a href="http://www.worldclasslasik.com
">April 2007</a></li>
<li><a href="http://www.worldclasslasik.com
">May 2007</a></li> 
<li><a href="http://www.worldclasslasik.com
">June 2007</a></li> 
<li><a href="http://www.worldclasslasik.com
">July 2007</a></li> 
<li><a href="http://www.worldclasslasik.com
">August 2007</a></li> 
<li><a href="http://www.worldclasslasik.com
">September 2007</a></li>
<li><a href="http://www.worldclasslasik.com
">October 2007</a></li>
<li><a href="http://www.worldclasslasik.com
">November 2007</a></li>
<li><a href="http://www.worldclasslasik.com
">December 2007</a></li>
</ul>

</div>

</div>
<div style="clear: both;"> </div>

<div id="footer">
Design by <a href="http://www.worldclasslasik.com">new york lasik surgery</a> and <a href="http://www.getnetset.com">cpa website design</a>
</div>
</div>

</body>
</html>';

?>