# yamllint disable rule:line-length
---
#################################################################
# Authelia Configuration File
#
# Below are the typical settings that could be configured here 
# but are instead set via environment variables:
#
# server:
#   address: "tcp://:9091"  # Authelia listens on port 9091 inside the container.
#                           # When running as a Home Assistant app, the external 
#                           # port can be remapped in the app configuration.
#
# storage:
#   local:
#     path: "/data/db.sqlite3"  # SQLite database file location
#   encryption_key: "/data/secrets/storage_encryption_key"  # Encryption key for storage
#
# identity_validation:
#   reset_password:
#     jwt_secret: "/data/secrets/jwt_secret"  # Secret for password reset JWTs
#
# session:
#   secret: "/data/secrets/session_secret"  # Secret used for session encryption
#
# Auto-Generated Keys and Secrets:
# - All required keys and secrets (JWT secret, session secret, storage encryption key) 
#   are automatically generated during installation.
# - These secrets are securely stored in `/data/secrets/` and referenced via environment variables.
#
# Why These Shouldn't Be Configured Here:
# - Environment variables always override configuration file values.
# - Keeping secrets and critical paths in environment variables improves security.
#################################################################

# Misc
log:
  level: info  # Logging level (info, debug, warn, error)

theme: auto  # UI theme (auto, light, dark)

# First Factor
authentication_backend:
  file:
    path: /config/users.yml  # Path to the user credentials file

# Session
session:
  cookies:
    - domain: {{ .domain }}  # Domain where cookies will be set
      authelia_url: "https://authelia.{{ .domain }}"  # Authelia service URL
      default_redirection_url: "https://{{ .domain }}" # Redirection URL

# Notifications
notifier:
  filesystem:
    filename: /config/emails.txt  # Location where notification emails are stored

# Security
access_control:
  default_policy: deny  # Default policy (deny all unless specified)
  rules:
    - domain: "public.{{ .domain }}"
      policy: bypass  # No authentication required
    - domain: "secure.{{ .domain }}"
      policy: two_factor  # Requires two-factor authentication

regulation:
  max_retries: 3       # Max login attempts before banning
  find_time: "2 minutes"  # Time window for detecting failed attempts
  ban_time: "5 minutes"  # Duration of ban after failed attempts

...
# yamllint enable rule:line-length
