. "$PSScriptRoot\env.ps1"

Get-ChildItem "$PidDir\*.pid" | ForEach-Object {
  $pid = Get-Content $_
  Write-Host "Stopping $($_.BaseName) (PID=$pid)..."

  try {
    Stop-Process -Id $pid -Force -ErrorAction Stop
  } catch {
    Add-Content $ErrorLog "Failed to stop $($_.BaseName) PID=$pid"
  }

  Remove-Item $_
}

Write-Host "ALL SERVICES STOPPED"
Write-Host "Check logs in the 'logs' directory for any issues."