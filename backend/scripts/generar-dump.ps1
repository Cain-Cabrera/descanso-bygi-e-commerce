[CmdletBinding()]
param(
    [string]$PgHost = 'localhost',
    [int]$Port = 5432,
    [string]$Database = 'descanso_by_gi',
    [string]$Username
)

$ErrorActionPreference = 'Stop'

if ([string]::IsNullOrWhiteSpace($Username)) {
    $Username = Read-Host 'Usuario de PostgreSQL'
}

if ([string]::IsNullOrWhiteSpace($Username)) {
    throw 'Tenés que indicar el usuario de PostgreSQL.'
}

# Busca pg_dump en PATH y usa la instalación habitual de PostgreSQL 18 como alternativa.
$pgDumpCommand = Get-Command 'pg_dump.exe' -ErrorAction SilentlyContinue
if ($pgDumpCommand) {
    $pgDump = $pgDumpCommand.Source
} else {
    $pgDump = 'C:\Program Files\PostgreSQL\18\bin\pg_dump.exe'
}

if (-not (Test-Path -LiteralPath $pgDump)) {
    throw "No se encontró pg_dump. Instalá las herramientas de PostgreSQL o agregá pg_dump.exe al PATH."
}

# Guarda el archivo dentro del repositorio para que pueda entregarse junto al proyecto.
$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot '..\..')).Path
$dumpDirectory = Join-Path $repoRoot 'database\dumps'
New-Item -ItemType Directory -Path $dumpDirectory -Force | Out-Null

$timestamp = Get-Date -Format 'yyyyMMdd_HHmmss'
$dumpPath = Join-Path $dumpDirectory "descanso_by_gi_$timestamp.sql"

# El dump incluye el esquema y el catálogo. Las tablas que pueden contener datos personales
# o pedidos reales se conservan en el esquema, pero se exportan vacías.
$pgDumpArguments = @(
    "--host=$PgHost"
    "--port=$Port"
    "--username=$Username"
    "--dbname=$Database"
    '--format=plain'
    '--no-owner'
    '--no-privileges'
    '--password'
    '--exclude-table-data=public.usuarios'
    '--exclude-table-data=public.carritos'
    '--exclude-table-data=public.items_carrito'
    '--exclude-table-data=public.pedidos'
    '--exclude-table-data=public.detalles_pedido'
    "--file=$dumpPath"
)

Write-Host "Generando dump de '$Database' en: $dumpPath"
Write-Host 'pg_dump va a solicitar la contraseña; no queda guardada en el script.'

try {
    & $pgDump @pgDumpArguments
    if ($LASTEXITCODE -ne 0) {
        throw "pg_dump terminó con código $LASTEXITCODE."
    }

    $dumpFile = Get-Item -LiteralPath $dumpPath
    if ($dumpFile.Length -eq 0) {
        throw 'pg_dump generó un archivo vacío.'
    }

    Write-Host "Dump creado correctamente ($([math]::Round($dumpFile.Length / 1MB, 2)) MB)."
} catch {
    Remove-Item -LiteralPath $dumpPath -Force -ErrorAction SilentlyContinue
    throw
}
