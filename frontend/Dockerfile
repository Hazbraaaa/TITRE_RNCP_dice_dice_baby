# FRONTEND - DEV ONLY
FROM node:22-alpine

WORKDIR /app

# Copy package.json and package-lock.json and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Sources will be mounted via volumes
CMD ["npm", "run", "dev", "--", "--host"]