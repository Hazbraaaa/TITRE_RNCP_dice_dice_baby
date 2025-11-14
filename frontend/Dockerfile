# FRONTEND - DEV ONLY
FROM node:22-alpine

# Dossier interne du conteneur
WORKDIR /app

# Installer seulement les deps (optimisé)
COPY package.json package-lock.json ./
RUN npm install

# Les sources seront montées via volumes
CMD ["npm", "run", "dev", "--", "--host"]