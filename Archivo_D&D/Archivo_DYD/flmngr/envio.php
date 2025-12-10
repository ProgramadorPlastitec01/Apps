<?php

set_time_limit(300);

$nextcloudUrl = 'https://cloud.plastitec-sa.com/remote.php/webdav/';  
$nextcloudUser = 'adminplas';  
$nextcloudPassword = 'Pl4$t%W4N%2024';   

// Directorio local donde se guardan los archivos
$localDir = 'files/';

// Directorio en Nextcloud donde se moverán los archivos
$remoteDir = 'Pruebas_flmngr/Prueba/';

// Obtener el contenido de la solicitud JSON
$data = json_decode(file_get_contents('php://input'), true);
$lastUploadTime = isset($data['lastUploadTime']) ? $data['lastUploadTime'] : (new DateTime('-2 hours'))->format('c');
$previousUploadTime = isset($data['previousUploadTime']) ? $data['previousUploadTime'] : (new DateTime('-2 hours'))->format('c');

// Convertir a timestamp
$lastUploadTimestamp = strtotime($lastUploadTime);
$previousUploadTimestamp = strtotime($previousUploadTime);

function folderExistsInNextcloud($remoteDirPath, $user, $password, $url) {
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_USERPWD, "$user:$password");
    curl_setopt($ch, CURLOPT_URL, $url . $remoteDirPath);
    curl_setopt($ch, CURLOPT_NOBODY, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);

    curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    return ($httpCode == 200);
}

function createFolderInNextcloud($remoteDirPath, $user, $password, $url) {
    if (!folderExistsInNextcloud($remoteDirPath, $user, $password, $url)) {
        $ch = curl_init();
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_USERPWD, "$user:$password");
        curl_setopt($ch, CURLOPT_URL, $url . $remoteDirPath);
        curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'MKCOL');
        curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
        curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);

        $response = curl_exec($ch);
        $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
        if ($response === false) {
            echo "Error cURL: " . curl_error($ch) . "\n";
        } else if ($httpCode != 201) {
            echo "Error HTTP: Código $httpCode - $response\n";
        } else {
            echo "Carpeta creada exitosamente: " . $remoteDirPath . "\n";
        }
        curl_close($ch);
    } else {
        echo "La carpeta ya existe: " . $remoteDirPath . "\n";
    }
}

function fileExistsInNextcloud($remoteFilePath, $user, $password, $url) {
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_USERPWD, "$user:$password");
    curl_setopt($ch, CURLOPT_URL, $url . $remoteFilePath);
    curl_setopt($ch, CURLOPT_NOBODY, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);

    curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    curl_close($ch);

    return ($httpCode == 200);
}

function getFileModificationTimeFromNextcloud($remoteFilePath, $user, $password, $url) {
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_USERPWD, "$user:$password");
    curl_setopt($ch, CURLOPT_URL, $url . $remoteFilePath);
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, 'HEAD');
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);

    curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    $lastModified = curl_getinfo($ch, CURLINFO_FILETIME);
    curl_close($ch);

    return ($httpCode == 200) ? $lastModified : false;
}

function uploadFileToNextcloud($localFilePath, $remoteFilePath, $user, $password, $url) {
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_USERPWD, "$user:$password");
    curl_setopt($ch, CURLOPT_URL, $url . $remoteFilePath);
    curl_setopt($ch, CURLOPT_PUT, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);

    $fh = fopen($localFilePath, 'r');
    if (!$fh) {
        echo "Error al abrir el archivo local: $localFilePath";
        return;
    }
    curl_setopt($ch, CURLOPT_INFILE, $fh);
    curl_setopt($ch, CURLOPT_INFILESIZE, filesize($localFilePath));

    $response = curl_exec($ch);
    $httpCode = curl_getinfo($ch, CURLINFO_HTTP_CODE);
    if ($response === false) {
        echo "Error cURL: " . curl_error($ch) . "\n";
    } else if ($httpCode != 201 && $httpCode != 204) {
        echo "Error HTTP: Código $httpCode - $response";
    } else {
        echo "Archivo subido exitosamente: " . $remoteFilePath . "\n";
    }
    fclose($fh);
    curl_close($ch);
}

function uploadDirectoryToNextcloud($localDirPath, $remoteDirPath, $user, $password, $url) {
    $todayStart = strtotime('today midnight');
    $todayEnd = strtotime('tomorrow midnight') - 1; // Hasta el final del día de hoy

    if ($handle = opendir($localDirPath)) {
        while (false !== ($file = readdir($handle))) {
            if ($file != '.' && $file != '..') {
                $localFilePath = $localDirPath . $file;
                $remoteFilePath = $remoteDirPath . rawurlencode(str_replace(' ', '_', $file));

                if (is_dir($localFilePath)) {
                    createFolderInNextcloud($remoteFilePath, $user, $password, $url);
                    uploadDirectoryToNextcloud($localFilePath . '/', $remoteFilePath . '/', $user, $password, $url);
                } else {
                    $localFileMTime = filemtime($localFilePath);

                    // Verificar si el archivo fue modificado en el día actual
                    if ($localFileMTime >= $todayStart && $localFileMTime <= $todayEnd) {
                        $uploadRequired = true;

                        if (fileExistsInNextcloud($remoteFilePath, $user, $password, $url)) {
                            $nextcloudFileMTime = getFileModificationTimeFromNextcloud($remoteFilePath, $user, $password, $url);
                            if ($localFileMTime > $nextcloudFileMTime) {
                                $uploadRequired = true;
                            } else {
                                echo "El archivo ya existe y no es más reciente: " . $remoteFilePath . "\n";
                                $uploadRequired = false;
                            }
                        }

                        if ($uploadRequired) {
                            uploadFileToNextcloud($localFilePath, $remoteFilePath, $user, $password, $url);
                        }
                    } else {
                        echo "El archivo no ha sido modificado en el día actual: " . $remoteFilePath . "\n";
                    }
                }
            }
        }
        closedir($handle);
    } else {
        echo "No se pudo abrir el directorio: " . $localDirPath . "\n";
    }
}

// Subir los archivos y carpetas del directorio local a Nextcloud
uploadDirectoryToNextcloud($localDir, $remoteDir, $nextcloudUser, $nextcloudPassword, $nextcloudUrl);

?>
