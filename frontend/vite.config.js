import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";

export default defineConfig({
  plugins: [react()],
  server: {
    port: 5173,
    proxy: {
      // auth-service (port 8080)
      "/api/auth": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/oauth2": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },
      "/login": {
        target: "http://localhost:8080",
        changeOrigin: true,
      },

      // quantity-service (port 8081)
      "/api/quantity": {
        target: "http://localhost:8081",
        changeOrigin: true,
      },
    },
  },
});