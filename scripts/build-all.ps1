. "$PSScriptRoot\env.ps1"

$projects = @(
  "config-server",
  "discovery-service",
  "authorization-server",
  "event-service",
  "gateway-service",
  "profile-service",
  "report-service"
)

foreach ($p in $projects) {
  Write-Host "Building $p..."
  Push-Location (Join-Path $BaseDir $p)

  & .\mvnw.cmd clean install -DskipTests 2>> $ErrorLog
  if ($LASTEXITCODE -ne 0) {
    throw "Build failed for $p"
  }

  Pop-Location
}

Write-Host "All builds completed successfully"
