. "$PSScriptRoot\env.ps1"

function Start-ServiceApp($name) {
  $path = Join-Path $BaseDir $name
  $jar  = Get-ChildItem "$path\target\*.jar" | Select-Object -First 1

  Write-Host "Starting $name..."
  $proc = Start-Process java `
    -ArgumentList "$JavaOpts -jar `"$($jar.FullName)`"" `
    -WorkingDirectory $path `
    -PassThru

  $proc.Id | Set-Content (Join-Path $PidDir "$name.pid")
}

Start-ServiceApp "config-server"
Start-Sleep 10

Start-ServiceApp "discovery-service"
Start-Sleep 10

Write-Host "Config and Discovery started"
