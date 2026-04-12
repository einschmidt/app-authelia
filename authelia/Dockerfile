ARG BUILD_FROM
# hadolint ignore=DL3006
FROM $BUILD_FROM

# Use bash as the default shell
SHELL ["/bin/bash", "-c"]

# https://github.com/authelia/authelia/releases
ENV AUTHELIA_VERSION="v4.39.19"

# Install Authelia
ARG BUILD_ARCH=amd64
RUN \
  set -eux \
  && BINARCH="${BUILD_ARCH}" \
  && BINARY_NAME="authelia" \
  && if [ "${BUILD_ARCH}" = "aarch64" ]; then BINARCH="arm64"; BINARY_NAME="authelia"; fi \
  && if [ "${BUILD_ARCH}" = "armv7" ] || [ "${BUILD_ARCH}" = "armhf" ]; then BINARCH="arm"; BINARY_NAME="authelia"; fi \
  \
  && mkdir -p /tmp/authelia /etc/authelia \
  && curl -J -L -o /tmp/authelia.tar.gz "https://github.com/authelia/authelia/releases/download/${AUTHELIA_VERSION}/authelia-${AUTHELIA_VERSION}-linux-${BINARCH}.tar.gz" \
  \
  && tar zxvf /tmp/authelia.tar.gz -C /tmp/authelia \
  \
  && mv /tmp/authelia/${BINARY_NAME} /usr/bin/authelia \
  && chmod +x /usr/bin/authelia \
  \
  && test -f /tmp/authelia/authelia.service && mv /tmp/authelia/authelia.service /etc/authelia/authelia.service || echo "authelia.service not found!" \
  && test -f /tmp/authelia/config.template.yml && mv /tmp/authelia/config.template.yml /etc/authelia/config.template.yml || echo "config.template.yml not found!" \
  \
  && rm -rf /tmp/authelia /tmp/authelia.tar.gz

# Copy root filesystem
COPY rootfs /

# Set environment variables
ENV AUTHELIA_SERVER_ADDRESS="tcp://:9091"
ENV AUTHELIA_STORAGE_LOCAL_PATH="/data/db.sqlite3"
ENV AUTHELIA_IDENTITY_VALIDATION_RESET_PASSWORD_JWT_SECRET_FILE="/data/secrets/jwt_secret"
ENV AUTHELIA_SESSION_SECRET_FILE="/data/secrets/session_secret"
ENV AUTHELIA_STORAGE_ENCRYPTION_KEY_FILE="/data/secrets/storage_encryption_key"

# Build arguments
ARG BUILD_ARCH
ARG BUILD_DATE
ARG BUILD_DESCRIPTION
ARG BUILD_NAME
ARG BUILD_REF
ARG BUILD_REPOSITORY
ARG BUILD_VERSION

# Labels
LABEL \
    io.hass.name="${BUILD_NAME}" \
    io.hass.description="${BUILD_DESCRIPTION}" \
    io.hass.arch="${BUILD_ARCH}" \
    io.hass.type="addon" \
    io.hass.version=${BUILD_VERSION} \
    maintainer="Einschmidt" \
    org.opencontainers.image.title="${BUILD_NAME}" \
    org.opencontainers.image.description="${BUILD_DESCRIPTION}" \
    org.opencontainers.image.vendor="Einschmidt" \
    org.opencontainers.image.authors="einschmidt" \
    org.opencontainers.image.licenses="MIT" \
    org.opencontainers.image.url="https://google.com" \
    org.opencontainers.image.source="https://github.com/${BUILD_REPOSITORY}" \
    org.opencontainers.image.documentation="https://github.com/${BUILD_REPOSITORY}/blob/main/README.md" \
    org.opencontainers.image.created=${BUILD_DATE} \
    org.opencontainers.image.revision=${BUILD_REF} \
    org.opencontainers.image.version=${BUILD_VERSION}