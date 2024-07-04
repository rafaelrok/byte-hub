<!--<template>-->
<!--  <div class="auth-page pt-24">-->
<!--    <div class="container page">-->
<!--      <div class="row">-->
<!--        <div class="col-md-6 offset-md-3 col-xs-12">-->
<!--          <h1 class="text-xs-center">-->
<!--            Sign up-->
<!--          </h1>-->
<!--          <p class="text-xs-center">-->
<!--            <AppLink name="login">-->
<!--              Have an account?-->
<!--            </AppLink>-->
<!--          </p>-->

<!--          <ul class="error-messages">-->
<!--            <li-->
<!--              v-for="(error, field) in errors"-->
<!--              :key="field"-->
<!--            >-->
<!--              {{ field }} {{ error ? error[0] : '' }}-->
<!--            </li>-->
<!--          </ul>-->

<!--          <form-->
<!--            ref="formRef"-->
<!--            aria-label="Registration form"-->
<!--            @submit.prevent="register"-->
<!--          >-->
<!--            <fieldset class="form-group">-->
<!--              <input-->
<!--                v-model="form.username"-->
<!--                aria-label="Username"-->
<!--                class="form-control form-control-lg"-->
<!--                type="text"-->
<!--                required-->
<!--                placeholder="Your Name"-->
<!--              >-->
<!--            </fieldset>-->
<!--            <fieldset class="form-group">-->
<!--              <input-->
<!--                v-model="form.email"-->
<!--                aria-label="Email"-->
<!--                class="form-control form-control-lg"-->
<!--                type="email"-->
<!--                required-->
<!--                placeholder="Email"-->
<!--              >-->
<!--            </fieldset>-->
<!--            <fieldset class="form-group">-->
<!--              <input-->
<!--                v-model="form.password"-->
<!--                aria-label="Password"-->
<!--                class="form-control form-control-lg"-->
<!--                type="password"-->
<!--                :minLength="8"-->
<!--                required-->
<!--                placeholder="Password"-->
<!--              >-->
<!--            </fieldset>-->
<!--            <button-->
<!--              type="submit"-->
<!--              class="btn btn-lg btn-primary pull-xs-right"-->
<!--              :disabled="!(form.email && form.username && form.password)"-->
<!--            >-->
<!--              Sign up-->
<!--            </button>-->
<!--          </form>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<template>
  <div class="container mx-auto px-4 pt-24 h-full">
    <div class="flex content-center items-center justify-center h-full">
      <div class="w-full lg:w-6/12 px-4">
        <div
          class="relative flex flex-col min-w-0 break-words w-full mb-6 shadow-lg rounded-lg bg-gray-200 border-0"
        >
          <div class="rounded-t mb-0 px-6 py-6">
            <div class="text-center mb-3">
              <h6 class="text-green-accent-4 text-sm font-bold">
                Sign up with
              </h6>
            </div>
            <div class="btn-wrapper text-center">
              <button
                class="bg-white active:bg-blueGray-50 text-blueGray-700 font-normal px-4 py-2 rounded outline-none focus:outline-none mr-2 mb-1 uppercase shadow hover:shadow-md inline-flex items-center font-bold text-xs ease-linear transition-all duration-150"
                type="button"
              >
                <img alt="..." class="w-5 mr-1" :src="github" />
                Github
              </button>
              <button
                class="bg-white active:bg-blueGray-50 text-blueGray-700 font-normal px-4 py-2 rounded outline-none focus:outline-none mr-1 mb-1 uppercase shadow hover:shadow-md inline-flex items-center font-bold text-xs ease-linear transition-all duration-150"
                type="button"
              >
                <img alt="..." class="w-5 mr-1" :src="google" />
                Google
              </button>
            </div>
            <hr class="mt-6 border-b-1 border-blueGray-300" />
          </div>
          <div class="flex-auto px-4 lg:px-10 py-10 pt-0">
            <div class="text-green-darken-1 text-center mb-3 font-bold">
              <small>Or sign up with credentials</small>
            </div>

            <ul class="error-messages">
              <li v-for="(error, field) in errors" :key="field">
                {{ field }} {{ error ? error[0] : '' }}
              </li>
            </ul>

            <form
              ref="formRef"
              aria-label="Registration form"
              @submit.prevent="register"
            >
              <div class="relative w-full mb-3">
                <label
                  class="block uppercase text-green-accent-4 text-xs font-bold mb-2"
                  htmlFor="grid-password"
                >
                  Name
                </label>
                <input
                  v-model="form.username"
                  aria-label="Username"
                  required
                  type="text"
                  class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  placeholder="Name"
                />
              </div>

              <div class="relative w-full mb-3">
                <label
                  class="block uppercase text-green-accent-4 text-xs font-bold mb-2"
                  htmlFor="grid-password"
                >
                  Email
                </label>
                <input
                  v-model="form.email"
                  aria-label="Email"
                  required
                  type="email"
                  class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  placeholder="Email"
                />
              </div>

              <div class="relative w-full mb-3">
                <label
                  class="block uppercase text-green-accent-4 text-xs font-bold mb-2"
                  htmlFor="grid-password"
                >
                  Password
                </label>
                <input
                  v-model="form.password"
                  aria-label="Password"
                  required
                  type="password"
                  class="border-0 px-3 py-3 placeholder-blueGray-300 text-blueGray-600 bg-white rounded text-sm shadow focus:outline-none focus:ring w-full ease-linear transition-all duration-150"
                  placeholder="Password"
                  :minLength="8"
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
                <button
                  class="bg-green-accent-4 text-white active:bg-blueGray-600 text-sm font-bold uppercase px-6 py-3 rounded shadow hover:shadow-lg outline-none focus:outline-none mr-1 mb-1 w-full ease-linear transition-all duration-150"
                  type="submit"
                  :disabled="!(form.email && form.username && form.password)"
                >
                  Create Account
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { reactive, ref } from 'vue'
import { routerPush } from '../../router.ts'
import { api, isFetchError } from '../../services'
import type { NewUser } from '@/services/api.ts'
import { useUserStore } from '@/store/user.ts'
import github from '@/assets/img/github.svg'
import google from '@/assets/img/google.svg'

const formRef = ref<HTMLFormElement | null>(null)
const form: NewUser = reactive({
  username: '',
  email: '',
  password: '',
})

const { updateUser } = useUserStore()

const errors = ref()

async function register() {
  errors.value = {}

  if (!formRef.value?.checkValidity()) return

  try {
    const result = await api.users.createUser({ user: form })
    updateUser(result.data.user)
    await routerPush('global-feed')
  } catch (error) {
    if (isFetchError(error)) errors.value = error.error?.errors
  }
}
</script>
