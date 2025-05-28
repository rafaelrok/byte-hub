<template>
  <div class="container mx-auto px-4 pt-24 h-full">
    <v-card
      class="mx-auto pa-12 pb-8"
      elevation="10"
      max-width="448"
      rounded="lg"
      variant="tonal"
    >
      <div class="rounded-t mb-0 px-6 py-6">
        <div class="text-center mb-3">
          <div class="banner-login">
            <v-label>
              <span class="text-2xl font-semibold">SignUp</span>
            </v-label>
          </div>

          <v-img
            class="mx-auto mt-1 mb-4"
            max-width="228"
            src="https://res.cloudinary.com/dkar9uu7g/image/upload/v1726700106/Developer/ByteHub/F_name_kc5arc.png"
          />
        </div>
        <v-card-actions class="justify-center">
          <div class="flex mb-2">
            <v-btn elevation="5" variant="flat" @click="registerSocial('github')">
              <i class="fa-brands fa-github-alt" style="color: #000000; font-size: 18px" />
            </v-btn>
            <v-btn elevation="5" variant="flat" @click="registerSocial('google')">
              <i class="fa-brands fa-google" style="color: #ff0000; font-size: 18px" />
            </v-btn>
            <v-btn elevation="5" variant="flat" @click="registerSocial('microsoft')">
              <i class="fa-brands fa-microsoft" style="color: #2766d3; font-size: 18px" />
            </v-btn>
          </div>
        </v-card-actions>
      </div>

      <hr class="mt-6 border-b-1 border-blueGray-300" />
      <div class="flex-auto px-4 lg:px-10 py-10 pt-0">
        <div class="text-gray-500 text-center mb-3 font-bold">
          <small class="font-semibold">Or sign up with credentials</small>
        </div>

        <v-form
          ref="formRef"
          aria-label="Registration form"
          @submit.prevent="register"
        >
          <div class="relative w-full mb-3">
            <v-text-field
              v-model="form.username"
              label="Name"
              aria-label="Username"
              required
              :rules="[rules.required]"
              @blur="validateField('username')"
            />
          </div>

          <div class="relative w-full mb-3">
            <v-text-field
              v-model="form.email"
              label="Email"
              aria-label="Email"
              required
              :rules="[rules.required, rules.email]"
              @blur="validateField('email')"
            />
          </div>

          <div class="relative w-full mb-3">
            <v-text-field
              v-model="form.password"
              label="Password"
              :append-inner-icon="visible ? 'fas fa-eye' : 'fas fa-eye-slash'"
              :type="visible ? 'text' : 'password'"
              :rules="[rules.required, rules.maxLength]"
              counter="16"
              @click:append-inner="visible = !visible"
              @blur="validateField('password')"
            />
          </div>

          <div class="relative w-full mb-3">
            <v-text-field
              v-model="form.repeatPassword"
              label="Repeat Password"
              :append-inner-icon="visible ? 'fas fa-eye' : 'fas fa-eye-slash'"
              :type="visible ? 'text' : 'password'"
              :rules="[rules.required, rules.passwordMatch]"
              @click:append-inner="visible = !visible"
              @blur="validateField('repeatPassword')"
            />
          </div>

          <div>
            <label class="inline-flex items-center cursor-pointer">
              <input
                id="customCheckLogin"
                type="checkbox"
                class="form-checkbox border-0 rounded text-blueGray-700 ml-1 w-5 h-5 ease-linear transition-all duration-150"
              />
              <span class="ml-2 text-sm font-semibold text-blueGray-600">
                I agree with the
                <a href="javascript:void(0)" class="text-emerald-500">
                  Privacy Policy
                </a>
              </span>
            </label>
          </div>

          <div class="text-center mt-6">
            <v-btn
              class="mb-4"
              color="#059669"
              size="large"
              variant="flat"
              block
              elevation="5"
              type="submit"
              :disabled="!isFormValid"
            >
              Create Account
            </v-btn>
          </div>
        </v-form>
      </div>
    </v-card>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref, computed } from 'vue'
import { useField, useForm } from 'vee-validate'
import * as yup from 'yup'
import { routerPush } from '@/router.ts'
import { api, isFetchError } from '@/services'
import type { NewUser } from '@/services/api.ts'
import { useUserStore } from '@/store/user.ts'
import { signInWithPopup, GoogleAuthProvider, GithubAuthProvider, OAuthProvider, getAuth } from 'firebase/auth'


const visible = ref(false)
const { updateUser } = useUserStore()

const schema = yup.object({
  username: yup.string().required('Name is required'),
  email: yup.string().required('Email is required').email('Invalid email format'),
  password: yup.string().required('Password is required').max(16, 'Password must be at most 16 characters'),
  repeatPassword: yup.string().required('Please repeat your password')
    .oneOf([yup.ref('password')], 'Passwords must match'),
})

const { handleSubmit, errors, validate, meta } = useForm({
  validationSchema: schema,
  initialValues: {
    username: '',
    email: '',
    password: '',
    repeatPassword: '',
  },
})

const { value: username } = useField('username')
const { value: email } = useField('email')
const { value: password } = useField('password')
const { value: repeatPassword } = useField('repeatPassword')

const form: NewUser = reactive({
  username,
  email,
  password,
  repeatPassword,
})

const rules = {
  required: (value: string) => !!value || 'Field is required',
  email: (value: string) => {
    const pattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return pattern.test(value) || 'Invalid e-mail format'
  },
  maxLength: (value: string) => value.length <= 16 || 'Max 16 characters',
  passwordMatch: (value: string) => value === form.password || 'Passwords do not match',
}

const validateField = async (field: keyof typeof form) => {
  await validate(field)
}

const isFormValid = computed(() => {
  return meta.value.valid &&
    form.username &&
    form.email &&
    form.password &&
    form.repeatPassword
})

const register = handleSubmit(async (values) => {
  try {
    const result = await api.users.createUser({ user: values })
    updateUser(result.data.user)
    await routerPush('global-feed')
  } catch (error) {
    if (isFetchError(error)) {
      console.error('API Error:', error)
    }
  }
})

const registerSocial = async (provider: string) => {
  let authProvider;
  switch (provider) {
    case 'google':
      authProvider = new GoogleAuthProvider();
      break;
    case 'github':
      authProvider = new GithubAuthProvider();
      break;
    case 'microsoft':
      authProvider = new OAuthProvider('microsoft.com');
      break;
    default:
      console.error('Unsupported provider');
      return;
  }

  const auth = getAuth();

  try {
    const result = await signInWithPopup(auth, authProvider);
    const user = result.user;
    const response = await api.socialUsers.createUserSocial({
      user: {
        email: user.email,
        username: user.displayName,
        userProvideUid: user.uid,
        provider: provider
      }
    });
    updateUser(response.data.user)
    await routerPush('global-feed')
  } catch (error) {
    console.error('Error during social login:', error);
  }
}

// const registerSocial = handleSubmit(async (values) => {
//   try {
//     const response = await api.socialUsers.createUserSocial({ user: values })
//
//     if (response.data.redirectUrl) {
//       window.location.href = response.data.redirectUrl
//     } else if (response.data.token) {
//       await processSocialLoginResult(response.data.token)
//     }
//   } catch (error) {
//     console.error('Erro ao iniciar login social:', error)
//   }
// })
//
// const processSocialLoginResult = async (token: string) => {
//   try {
//     const userDetails = await api.user.getCurrentUser({ token })
//     updateUser(userDetails.data.user)
//
//     await routerPush('global-feed')
//   } catch (error) {
//     console.error('Erro ao processar resultado do login social:', error)
//   }
// }
</script>
