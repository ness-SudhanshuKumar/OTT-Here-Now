# Resolve base directory safely (works with &)
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$BaseDir   = Resolve-Path "$ScriptDir\.."

# Logs
$Global:LogDir = Join-Path $BaseDir "logs"
$Global:PidDir = Join-Path $LogDir "pids"
$Global:ErrorLog = Join-Path $LogDir "error.log"

New-Item -ItemType Directory -Force -Path $LogDir, $PidDir | Out-Null

# Environment
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-17.0.17.10-hotspot"
$env:SPRING_PROFILES_ACTIVE = "dev"
$Global:JavaOpts = "-Xms512m -Xmx1024m"

Write-Host "Environment initialized"
