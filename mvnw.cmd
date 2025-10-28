@ECHO OFF
SETLOCAL

REM --- Base dir del proyecto (carpeta de este script) ---
SET "MAVEN_PROJECTBASEDIR=%~dp0"
IF "%MAVEN_PROJECTBASEDIR:~-1%"=="\" SET "MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%"

REM --- Rutas del wrapper ---
SET "WRAPPER_DIR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper"
SET "WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar"
SET "WRAPPER_PROP=%WRAPPER_DIR%\maven-wrapper.properties"

REM --- Descargar wrapper jar si no existe ---
IF NOT EXIST "%WRAPPER_JAR%" (
  FOR /F "usebackq tokens=1,2 delims==" %%A IN ("%WRAPPER_PROP%") DO (
    IF "%%A"=="wrapperUrl" SET WRAPPER_URL=%%B
  )
  IF "%WRAPPER_URL%"=="" (
    SET "WRAPPER_URL=https://repo.maven.apache.org/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
  )
  WHERE powershell >NUL 2>&1
  IF %ERRORLEVEL% EQU 0 (
    powershell -Command "Invoke-WebRequest -UseBasicParsing -OutFile '%WRAPPER_JAR%' '%WRAPPER_URL%'"
  ) ELSE (
    WHERE certutil >NUL 2>&1
    IF %ERRORLEVEL% EQU 0 (
      certutil -urlcache -split -f "%WRAPPER_URL%" "%WRAPPER_JAR%"
    ) ELSE (
      ECHO Cannot download %WRAPPER_URL%
      EXIT /B 1
    )
  )
)

REM --- Java ---
SET "JAVA_EXE=java"
IF DEFINED JAVA_HOME SET "JAVA_EXE=%JAVA_HOME%\bin\java"

REM --- Ejecutar Maven Wrapper con la propiedad requerida ---
"%JAVA_EXE%" -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -cp "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*
"@ | Set-Content ".\mvnw.cmd" -Encoding ASCII
