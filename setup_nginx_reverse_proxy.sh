#!/bin/bash

# Stop on any error
set -e

# Install Nginx
echo "Installing Nginx..."
sudo dnf install -y nginx

# Create Nginx configuration for reverse proxy
echo "Setting up Nginx configuration for reverse proxy..."
cat <<EOF | sudo tee /etc/nginx/conf.d/reverse-proxy.conf
server {
    listen 80;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host \$host;
        proxy_set_header X-Real-IP \$remote_addr;
        proxy_set_header X-Forwarded-For \$proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto \$scheme;
    }
}
EOF

# Remove default Nginx configuration
sudo rm -f /etc/nginx/sites-enabled/default

# Enable and restart Nginx to apply changes
echo "Enabling and restarting Nginx..."
sudo systemctl enable nginx
sudo systemctl restart nginx

echo "Nginx reverse proxy setup complete."
