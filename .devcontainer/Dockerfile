FROM mcr.microsoft.com/devcontainers/java:1-21-bookworm

RUN find /etc/apt/sources.list.d/ -type f \
    -exec sed -i '/dl\.yarnpkg\.com/s/^/# /' {} \;
