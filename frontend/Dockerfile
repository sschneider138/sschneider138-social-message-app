# stage 1 - install and build app
FROM node:22 AS build

WORKDIR /app

COPY package.json ./
COPY pnpm-lock.yaml ./

RUN npm install -g pnpm
RUN npm install

COPY . .

RUN pnpm run build

# stage 2 - build app using nginx
FROM nginx:alpine

COPY --from=build /app/.next /usr/share/nginx/html