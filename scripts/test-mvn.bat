@echo off

REM Go to project root (IMPORTANT)
cd /d "C:\Users\P7114330\Sudhanshu\OTT-Here&Now\profile-service"

REM Verify mvnw.cmd exists
if not exist mvnw.cmd (
    echo mvnw.cmd NOT FOUND in this directory
    exit /b 1
)

REM Run Maven Wrapper
call mvnw.cmd clean install

if errorlevel 1 (
    echo Build failed
    exit /b 1
)

echo Build successful
