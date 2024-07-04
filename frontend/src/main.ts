import { createApp } from 'vue'
import { createPinia } from 'pinia'

// styles
import '@fortawesome/fontawesome-free/css/all.min.css'
import '@/assets/styles/tailwind.css'

import { library } from '@fortawesome/fontawesome-svg-core'
import { fas } from '@fortawesome/free-solid-svg-icons'
import { far } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'

import App from './App.vue'

import registerGlobalComponents from './plugins/global-components'
import setAuthorizationToken from './plugins/set-authorization-token'
import { router } from './router'

// library.add(faHeart);
library.add(fas, far)

const vuetify = createVuetify({
  components,
  directives,
})

const app = createApp(App)

app.component('font-awesome-icon', FontAwesomeIcon)
app.use(vuetify)
app.use(createPinia())
app.use(router)

setAuthorizationToken()
registerGlobalComponents(app)

app.mount('#app')
