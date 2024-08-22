// vite.config.ts
import { URL, fileURLToPath } from 'node:url'
import vue from 'file:///mnt/d/workspace/Spring/byte-hub/frontend/node_modules/.pnpm/@vitejs+plugin-vue@4.6.2_vite@5.3.3_@types+node@20.14.9_sass@1.77.6_terser@5.31.1__vue@3.4.31_typescript@5.2.2_/node_modules/@vitejs/plugin-vue/dist/index.mjs'
import { defineConfig } from 'file:///mnt/d/workspace/Spring/byte-hub/frontend/node_modules/.pnpm/vite@5.3.3_@types+node@20.14.9_sass@1.77.6_terser@5.31.1/node_modules/vite/dist/node/index.js'
import * as path from 'node:path'
var __vite_injected_original_dirname =
  '/mnt/d/workspace/Spring/byte-hub/frontend'
var __vite_injected_original_import_meta_url =
  'file:///mnt/d/workspace/Spring/byte-hub/frontend/vite.config.ts'
var vite_config_default = defineConfig({
  resolve: {
    alias: {
      src: fileURLToPath(
        new URL('src', __vite_injected_original_import_meta_url),
      ),
      '@': path.resolve(__vite_injected_original_dirname, './src'),
    },
  },
  plugins: [vue()],
  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `@import "@/styles/variables.scss";`,
      },
    },
  },
  test: {
    environment: 'happy-dom',
    setupFiles: './src/setupTests.ts',
    globals: true,
    snapshotFormat: {
      escapeString: false,
    },
    coverage: {
      enabled: true,
      provider: 'v8',
      include: ['src'],
      exclude: [
        'src/*.{ts,vue}',
        'src/services/api.ts',
        'src/setupTests.ts',
        'src/utils/test',
        '**/*.d.ts',
      ],
      all: true,
    },
  },
})
export { vite_config_default as default }
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcudHMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCIvbW50L2Qvd29ya3NwYWNlL1NwcmluZy9ieXRlLWh1Yi9mcm9udGVuZFwiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9maWxlbmFtZSA9IFwiL21udC9kL3dvcmtzcGFjZS9TcHJpbmcvYnl0ZS1odWIvZnJvbnRlbmQvdml0ZS5jb25maWcudHNcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfaW1wb3J0X21ldGFfdXJsID0gXCJmaWxlOi8vL21udC9kL3dvcmtzcGFjZS9TcHJpbmcvYnl0ZS1odWIvZnJvbnRlbmQvdml0ZS5jb25maWcudHNcIjsvLy8gPHJlZmVyZW5jZSB0eXBlcz1cInZpdGVzdFwiIC8+XG5cbmltcG9ydCB7IFVSTCwgZmlsZVVSTFRvUGF0aCB9IGZyb20gJ25vZGU6dXJsJ1xuaW1wb3J0IHZ1ZSBmcm9tICdAdml0ZWpzL3BsdWdpbi12dWUnXG5pbXBvcnQgeyBkZWZpbmVDb25maWcgfSBmcm9tICd2aXRlJ1xuaW1wb3J0ICogYXMgcGF0aCBmcm9tICdub2RlOnBhdGgnXG5cbi8vIGh0dHBzOi8vdml0ZWpzLmRldi9jb25maWcvXG5leHBvcnQgZGVmYXVsdCBkZWZpbmVDb25maWcoe1xuICByZXNvbHZlOiB7XG4gICAgYWxpYXM6IHtcbiAgICAgIHNyYzogZmlsZVVSTFRvUGF0aChuZXcgVVJMKCdzcmMnLCBpbXBvcnQubWV0YS51cmwpKSxcbiAgICAgICdAJzogcGF0aC5yZXNvbHZlKF9fZGlybmFtZSwgJy4vc3JjJyksXG4gICAgfSxcbiAgfSxcbiAgcGx1Z2luczogW3Z1ZSgpXSxcbiAgY3NzOiB7XG4gICAgcHJlcHJvY2Vzc29yT3B0aW9uczoge1xuICAgICAgc2Nzczoge1xuICAgICAgICBhZGRpdGlvbmFsRGF0YTogYEBpbXBvcnQgXCJAL3N0eWxlcy92YXJpYWJsZXMuc2Nzc1wiO2AsXG4gICAgICB9LFxuICAgIH0sXG4gIH0sXG4gIHRlc3Q6IHtcbiAgICBlbnZpcm9ubWVudDogJ2hhcHB5LWRvbScsXG4gICAgc2V0dXBGaWxlczogJy4vc3JjL3NldHVwVGVzdHMudHMnLFxuICAgIGdsb2JhbHM6IHRydWUsXG4gICAgc25hcHNob3RGb3JtYXQ6IHtcbiAgICAgIGVzY2FwZVN0cmluZzogZmFsc2UsXG4gICAgfSxcbiAgICBjb3ZlcmFnZToge1xuICAgICAgZW5hYmxlZDogdHJ1ZSxcbiAgICAgIHByb3ZpZGVyOiAndjgnLFxuICAgICAgaW5jbHVkZTogWydzcmMnXSxcbiAgICAgIGV4Y2x1ZGU6IFtcbiAgICAgICAgJ3NyYy8qLnt0cyx2dWV9JyxcbiAgICAgICAgJ3NyYy9zZXJ2aWNlcy9hcGkudHMnLFxuICAgICAgICAnc3JjL3NldHVwVGVzdHMudHMnLFxuICAgICAgICAnc3JjL3V0aWxzL3Rlc3QnLFxuICAgICAgICAnKiovKi5kLnRzJyxcbiAgICAgIF0sXG4gICAgICBhbGw6IHRydWUsXG4gICAgfSxcbiAgfSxcbn0pXG4iXSwKICAibWFwcGluZ3MiOiAiO0FBRUEsU0FBUyxLQUFLLHFCQUFxQjtBQUNuQyxPQUFPLFNBQVM7QUFDaEIsU0FBUyxvQkFBb0I7QUFDN0IsWUFBWSxVQUFVO0FBTHRCLElBQU0sbUNBQW1DO0FBQWlKLElBQU0sMkNBQTJDO0FBUTNPLElBQU8sc0JBQVEsYUFBYTtBQUFBLEVBQzFCLFNBQVM7QUFBQSxJQUNQLE9BQU87QUFBQSxNQUNMLEtBQUssY0FBYyxJQUFJLElBQUksT0FBTyx3Q0FBZSxDQUFDO0FBQUEsTUFDbEQsS0FBVSxhQUFRLGtDQUFXLE9BQU87QUFBQSxJQUN0QztBQUFBLEVBQ0Y7QUFBQSxFQUNBLFNBQVMsQ0FBQyxJQUFJLENBQUM7QUFBQSxFQUNmLEtBQUs7QUFBQSxJQUNILHFCQUFxQjtBQUFBLE1BQ25CLE1BQU07QUFBQSxRQUNKLGdCQUFnQjtBQUFBLE1BQ2xCO0FBQUEsSUFDRjtBQUFBLEVBQ0Y7QUFBQSxFQUNBLE1BQU07QUFBQSxJQUNKLGFBQWE7QUFBQSxJQUNiLFlBQVk7QUFBQSxJQUNaLFNBQVM7QUFBQSxJQUNULGdCQUFnQjtBQUFBLE1BQ2QsY0FBYztBQUFBLElBQ2hCO0FBQUEsSUFDQSxVQUFVO0FBQUEsTUFDUixTQUFTO0FBQUEsTUFDVCxVQUFVO0FBQUEsTUFDVixTQUFTLENBQUMsS0FBSztBQUFBLE1BQ2YsU0FBUztBQUFBLFFBQ1A7QUFBQSxRQUNBO0FBQUEsUUFDQTtBQUFBLFFBQ0E7QUFBQSxRQUNBO0FBQUEsTUFDRjtBQUFBLE1BQ0EsS0FBSztBQUFBLElBQ1A7QUFBQSxFQUNGO0FBQ0YsQ0FBQzsiLAogICJuYW1lcyI6IFtdCn0K
