#!/bin/bash
set -e

cd "$(dirname "$0")/.."

echo "Configurando Three Arquitetura..."

APP_CONFIG="three-arquitetura/src/main/resources/application.properties"

if [ ! -f "$APP_CONFIG" ]; then
    cp "${APP_CONFIG}.example" "$APP_CONFIG"
    echo "application.properties criado!"
else
    echo "application.properties ja existe."
fi

echo "Verificando Java..."
java -version

echo "Verificando Docker..."
docker --version

echo "Verificando variaveis do banco..."

if [ -z "${DB_PASSWORD:-}" ] || [ -z "${DB_ROOT_PASSWORD:-}" ]; then
    echo "AVISO: Variaveis do banco nao configuradas."
else
    echo "Variaveis do banco configuradas!"
fi

echo "Configuracao inicial concluida!"
