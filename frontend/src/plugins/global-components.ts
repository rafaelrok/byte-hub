import type { App } from 'vue'
import AppLink from 'src/components/AppLink/AppLink.vue'

export default function registerGlobalComponents(app: App): void {
  app.component('AppLink', AppLink)
}
