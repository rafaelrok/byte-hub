import { createApp } from 'vue'
import { createPinia } from 'pinia'

// styles
import '@fortawesome/fontawesome-free/css/all.min.css'
import '@/assets/styles/tailwind.css'
import '@/assets/styles/index.css'
import 'highlight.js/styles/monokai.css'
// import '@/assets/styles/main.css'

import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { initFlowbite } from 'flowbite'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
// import { VBtn } from 'vuetify/components'

import 'dotenv'

// Firebase imports
import { initializeApp } from 'firebase/app'
import { getAuth } from 'firebase/auth'

import App from './App.vue'

import registerGlobalComponents from './plugins/global-components'
import setAuthorizationToken from './plugins/set-authorization-token'
import { router } from './router'

library.add(fas, far)

const vuetify = createVuetify({
  components,
  directives,
})

// Firebase configuration
const firebaseConfig = {
  apiKey: import.meta.env.VITE_FIREBASE_API_KEY,
  authDomain: import.meta.env.VITE_FIREBASE_AUTH_DOMAIN,
  projectId: import.meta.env.VITE_FIREBASE_PROJECT_ID,
  storageBucket: import.meta.env.VITE_FIREBASE_STORAGE_BUCKET,
  messagingSenderId: import.meta.env.VITE_FIREBASE_MESSAGING_SENDER_ID,
  appId: import.meta.env.VITE_FIREBASE_APP_ID
};

// Initialize Firebase
const firebaseApp = initializeApp(firebaseConfig)
const auth = getAuth(firebaseApp)


const app = createApp(App)

app.component('font-awesome-icon', FontAwesomeIcon)
app.use(initFlowbite)
app.use(vuetify)
app.use(createPinia())
app.use(router)

setAuthorizationToken()
registerGlobalComponents(app)

app.provide(auth, "auth")

app.mount('#app')
