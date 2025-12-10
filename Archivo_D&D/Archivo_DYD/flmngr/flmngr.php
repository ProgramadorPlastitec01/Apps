<?php

require __DIR__ . '/vendor/autoload.php';
    
use EdSDK\FlmngrServer\FlmngrServer;

// Uncomment line below to enable CORS if your request domain and server domain are different
 header('Access-Control-Allow-Origin: *');

FlmngrServer::flmngrRequest(
    array(
        'dirFiles' => 'files/',
        'dirTmp'   => 'tmp/',
        'dirCache'   => 'cache/'
        
    )
); 

?>
