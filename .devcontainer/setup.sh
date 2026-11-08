#!/bin/bash
set -e

cd "$(dirname "$0")/.."

echo "Configurando Three Arquitetura..."

cp three-arquitetura/src/main/resources/application.properties.example \
   three-arquitetura/src/main/resources/application.properties

echo "Arquivo application.properties criado!"

echo "Verificando Docker..."
docker --version

echo "Configuração inicial concluída!"
