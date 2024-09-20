FROM mcr.microsoft.com/devcontainers/typescript-node:1-16-bullseye
RUN apt update && apt install openjdk-11-jdk -y
# install chrome
RUN curl -fSsL https://dl.google.com/linux/linux_signing_key.pub | sudo gpg --dearmor | sudo tee /usr/share/keyrings/google-chrome.gpg >> /dev/null
# Add google chrome repository
RUN echo deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main | sudo tee /etc/apt/sources.list.d/google-chrome.list
# Refresh apt and install google-chrome
RUN sudo apt update && sudo apt install google-chrome-stable -y