. "$PSScriptRoot\env.ps1"

$services = @(
  "authorization-server",
  "event-service",
  "gateway-service",
  "profile-service",
  "report-service",
  "report-service"
)

foreach ($s in $services) {
  $path = Join-Path $BaseDir $s
  $jar  = Get-ChildItem "$path\target\*.jar" | Select-Object -First 1

  Write-Host "Starting $s..."
  $proc = Start-Process java `
    -ArgumentList "$JavaOpts -jar `"$($jar.FullName)`"" `
    -WorkingDirectory $path `
    -PassThru

  $proc.Id | Set-Content (Join-Path $PidDir "$s.pid")
  Start-Sleep 5
}
