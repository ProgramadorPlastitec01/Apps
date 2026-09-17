@echo off
title Iniciar Servicios DHF - Diseno y Desarrollo
echo ======================================================================
echo           INICIANDO TODOS LOS SERVICIOS DE DISENO Y DESARROLLO
echo ======================================================================
echo.

echo [1/3] Levantando base de datos MySQL (XAMPP) en puerto 3307...
start "MySQL Server 3307" /d "C:\xampp\mysql\bin" "mysqld.exe" --defaults-file="C:\xampp\mysql\bin\my.ini" --standalone
echo [OK] Intento de inicio de MySQL enviado.
echo.

echo [2/3] Levantando backend OnlyOffice (Docker: Postgres, MinIO, OnlyOffice)...
cd /d "c:\Users\prog.aprendiz2\Downloads\AG_DEV\office-platform\office-platform"
docker compose up -d
echo [OK] Contenedores de OnlyOffice activos.
echo.

echo [3/3] Sincronizando archivos JSP/JS al despliegue de Tomcat...
set "TOMCAT_DEPLOY=C:\Users\prog.aprendiz2\AppData\Roaming\NetBeans\8.2\apache-tomcat-8.0.27.0_base\webapps\DisenoDesarrollo"
if exist "%TOMCAT_DEPLOY%" (
    xcopy /E /Y /I "c:\Users\prog.aprendiz2\Documents\NetBeansProjects\DisenoDesarrollo\web\*" "%TOMCAT_DEPLOY%\" > nul
    echo [OK] Archivos sincronizados en Tomcat.
)

echo [4/4] Levantando Servidor Apache Tomcat (Tomcat base en puerto 8085)...
set "CATALINA_BASE=C:\Users\prog.aprendiz2\AppData\Roaming\NetBeans\8.2\apache-tomcat-8.0.27.0_base"
set "CATALINA_HOME=C:\Program Files\Apache Software Foundation\Apache Tomcat 8.0.27"
set "JRE_HOME=C:\Program Files\Java\jdk1.8.0_211"

echo Cambiando a directorio bin de Tomcat...
cd /d "C:\Program Files\Apache Software Foundation\Apache Tomcat 8.0.27\bin"
echo Ejecutando catalina.bat start...
call catalina.bat start

echo.
echo ======================================================================
echo          ¡TODOS LOS SERVICIOS HAN SIDO INICIADOS!
echo ======================================================================
echo.
echo Puedes acceder a la aplicacion desde:
echo -- Principal (Tomcat): http://localhost:8085/DisenoDesarrollo/
echo -- Gestor OnlyOffice: http://localhost:8080/swagger-ui.html
echo.
echo (Esta ventana se cerrara en 5 segundos)
timeout /t 5 > nul
