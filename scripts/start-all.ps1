Write-Host "====================================="
Write-Host "1 - Clean & Build projects"
Write-Host "2 - Skip build, start services only"
Write-Host "====================================="

$choice = Read-Host "Enter your choice (1 or 2)"

if ($choice -eq "1") {
  & "$PSScriptRoot\build-all.ps1"
}

& "$PSScriptRoot\start-infra.ps1"
& "$PSScriptRoot\start-services.ps1"

Write-Host "====================================="
Write-Host "ALL SERVICES STARTED"
Write-Host "====================================="
Write-Host "Check logs in the 'logs' directory for any issues."