<template>
  <div class="mt-20 align-center">
    <v-card
      class="mx-auto pa-12 pb-8"
      elevation="10"
      max-width="448"
      rounded="lg"
      variant="tonal"
    >
      <div class="banner-login">
        <v-label>
          <span class="text-2xl font-semibold">Login</span>
        </v-label>
      </div>

      <v-img
        class="mx-auto mt-1 mb-12"
        max-width="228"
        src="https://res.cloudinary.com/dkar9uu7g/image/upload/v1726700106/Developer/ByteHub/F_name_kc5arc.png"
      ></v-img>

      <div class="text-medium-emphasis">Email</div>

      <ul class="error-messages">
        <li v-for="(error, field) in errors" :key="field">
          {{ field }} {{ error ? error[0] : '' }}
        </li>
      </ul>

      <v-form ref="formRef" aria-label="Login form" @submit.prevent="login">
        <v-text-field
          v-model="form.email"
          density="comfortable"
          placeholder="Email address"
          variant="underlined"
          :error-messages="errors.email"
          required
        ></v-text-field>

        <div
          class="text-medium-emphasis d-flex align-center justify-space-between"
        >
          Password

          <a
            class="text-caption text-decoration-none text-blue"
            href="#"
            rel="noopener noreferrer"
            target="_blank"
          >
            Forgot login password?</a
          >
        </div>

        <v-text-field
          v-model="form.password"
          :append-inner-icon="visible ? 'fas fa-eye' : 'fas fa-eye-slash'"
          :type="visible ? 'text' : 'password'"
          density="comfortable"
          placeholder="Enter your password"
          variant="underlined"
          @click:append-inner="visible = !visible"
          :error-messages="errors.password"
          required
        ></v-text-field>

        <v-card-actions class="justify-center">
          <div class="flex mb-2">
            <v-btn elevation="5" variant="flat" class="transition-transform duration-300 hover:scale-110 hover:bg-gray-200" @click="handleSocialLogin('github')">
              <i class="fa-brands fa-github-alt" style="color: #000000; font-size: 18px" />
            </v-btn>
            <v-btn elevation="5" variant="flat" class="transition-transform duration-300 hover:scale-110 hover:bg-red-100" @click="handleSocialLogin('google')">
              <i class="fa-brands fa-google" style="color: #ff0000; font-size: 18px" />
            </v-btn>
            <v-btn elevation="5" variant="flat" class="transition-transform duration-300 hover:scale-110 hover:bg-blue-100" @click="handleSocialLogin('microsoft')">
              <i class="fa-brands fa-microsoft" style="color: #2766d3; font-size: 18px" />
            </v-btn>
          </div>
        </v-card-actions>

        <v-card class="mb-4" color="surface-variant" variant="tonal">
          <!--        <v-card-text class="text-medium-emphasis text-caption">-->
          <!--          Warning: After 3 consecutive failed login attempts, you account will be temporarily locked for three hours. If you must login now, you can also click "Forgot login password?" below to reset the login password.-->
          <!--        </v-card-text>-->
        </v-card>

        <v-btn
          class="mb-4"
          color="#059669"
          size="large"
          variant="flat"
          active
          block
          :loading="isLoading"
          elevation="5"
          type="submit"
          :disabled="!form.email || !form.password || isLoading"
        >
          Log In
        </v-btn>

        <v-card-text class="text-center">
          <AppLink name="register">
            <a class="text-emerald-600 text-decoration-none">
              Sign up now <v-icon icon="fas fa-chevron-right"></v-icon>
            </a>
          </AppLink>
        </v-card-text>
      </v-form>
    </v-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import * as yup from 'yup'
import { routerPush } from '@/router.ts'
import { api, isFetchError } from '@/services'
import type { LoginUser } from '@/services/api.ts'
import { useUserStore } from '@/store/user.ts'
import AppLink from '@/components/AppLink/AppLink.vue'

const formRef = ref<HTMLFormElement | null>(null)
const form: LoginUser = reactive({
  email: '',
  password: '',
})

const { updateUser } = useUserStore()

const errors = ref<Record<string, any>>({})
const isLoading = ref(false)
const visible = ref(false)

const schema = yup.object({
  email: yup.string().required('Email is required').email('Invalid email format'),
  password: yup.string().required('Password is required'),
})

async function login() {
  errors.value = {}
  isLoading.value = true

  if (!formRef.value?.checkValidity()) {
    isLoading.value = false
    return
  }

  try {
    await schema.validate(form, { abortEarly: false })
    await new Promise(resolve => setTimeout(resolve, 1000))

    const result = await api.users.login({ user: form })
    updateUser(result.data.user)
    await routerPush('global-feed')
  } catch (error) {
    if (error instanceof yup.ValidationError) {
      errors.value = error.inner.reduce((acc, err) => {
        if (err.path) {
          acc[err.path] = err.message
        }
        return acc
      }, {} as Record<string, string>)
    } else if (isFetchError(error)) {
      errors.value = error.error?.errors || {
        message: 'Unknown error, please try again.',
      }
    } else {
      console.error(error)
      errors.value = { message: 'An unexpected error occurred.' }
    }
  } finally {
    isLoading.value = false
  }
}

async function handleSocialLogin(provider: string) {
  errors.value = {}
  isLoading.value = true

  try {
    const response = await api.socialUsers.loginSocial({ provider })

    if (response.data.redirectUrl) {
      window.location.href = response.data.redirectUrl
    } else if (response.data.token) {
      await processSocialLoginResult(response.data.token)
    }
  } catch (error) {
    console.error('Erro ao iniciar login social:', error)
    if (isFetchError(error)) {
      errors.value = error.error?.errors || {
        message: 'Erro ao iniciar login social. Por favor, tente novamente.',
      }
    } else {
      errors.value = { message: 'Um erro inesperado ocorreu durante o login social.' }
    }
  } finally {
    isLoading.value = false
  }
}

async function processSocialLoginResult(token: string) {
  try {
    const userDetails = await api.socialUsers.getUserDetails({ token })
    updateUser(userDetails.data.user)

    await routerPush('global-feed')
  } catch (error) {
    console.error('Erro ao processar resultado do login social:', error)
    if (isFetchError(error)) {
      errors.value = error.error?.errors || {
        message: 'Erro ao processar login social. Por favor, tente novamente.',
      }
    } else {
      errors.value = { message: 'Um erro inesperado ocorreu ao processar o login social.' }
    }
  }
}
</script>

<style scoped>
.banner-login {
  margin-left: 8.5rem;
}
.error-messages {
  list-style: none;
  padding: 0;
}
.error-messages li {
  color: #ff0000;
}
.error-messages li::before {
  content: '⚠️ ';
}
.error-messages li:not(:last-child) {
  margin-bottom: 0.5rem;
}

</style>
