import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import tailwindcss from "@tailwindcss/vite";

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    tailwindcss(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  // --- BAGIAN INI YANG BARU DITAMBAHKAN ---
  server: {
    proxy: {
      // Setiap kali frontend minta '/kamus', oper ke Spring Boot (8080)
      '/kamus': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      },
      // Jaga-jaga kalau mau pakai endpoint '/hello' juga
      '/hello': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false,
      }
    }
  }
})
