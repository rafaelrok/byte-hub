<template>
  <div
    class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-xl rounded-lg mt-16"
  >
    <div class="px-6">
      <!-- Verificação se os dados do perfil estão carregados -->
      <div v-if="profile">
        <div class="flex flex-wrap justify-center">
          <div class="w-full px-4 flex justify-center pb-14">
            <div class="relative">
              <img
                :alt="profile.username"
                :src="profile.image"
                class="shadow-xl rounded-full h-auto align-middle border-none absolute mt-4 -ml-20 lg:-ml-16 max-w-150-px"
              />
            </div>
          </div>
          <div class="w-full px-4 ml-8 text-center mt-20">
            <div class="flex justify-center py-4 lg:pt-4 pt-8">
              <div class="mr-4 p-3 text-center">
                <span
                  class="text-xl font-bold block uppercase tracking-wide text-blueGray-600"
                >
                  22
                </span>
                <span class="text-sm text-blueGray-400">Post</span>
              </div>
              <div class="mr-4 p-3 text-center">
                <span
                  class="text-xl font-bold block uppercase tracking-wide text-blueGray-600"
                >
                  10
                </span>
                <span class="text-sm text-blueGray-400">Likes</span>
              </div>
              <div class="lg:mr-4 p-3 text-center">
                <span
                  class="text-xl font-bold block uppercase tracking-wide text-blueGray-600"
                >
                  89
                </span>
                <span class="text-sm text-blueGray-400">Comments</span>
              </div>
            </div>
          </div>
        </div>
        <div class="text-center">
          <h3
            class="text-xl font-semibold leading-normal mb-2 text-blueGray-700 mb-2"
          >
            {{ profile.username }}
          </h3>
          <div
            class="text-sm leading-normal mt-0 mb-2 text-blueGray-400 font-bold uppercase"
          >
            <i class="fas fa-map-marker-alt mr-2 text-lg text-blueGray-400"></i>
            Los Angeles, California
          </div>
          <div class="mb-2 text-blueGray-600 mt-10">
            <i class="fas fa-briefcase mr-2 text-lg text-blueGray-400"></i>
            {{ profile.bio }}
          </div>
          <div class="mb-2 text-blueGray-600">
            <i class="fas fa-university mr-2 text-lg text-blueGray-400"></i>
            University of Computer Science
          </div>
          <div class="mb-2 text-blueGray-600">
            <i class="fas fa-address-card mr-2 text-lg text-blueGray-400"></i>
            {{ profile.email }}
          </div>
        </div>
        <div class="mt-10 py-10 border-t border-blueGray-200 text-center">
          <div class="flex flex-wrap justify-center">
            <div class="w-full lg:w-9/12 px-4">
              <p class="mb-4 text-lg leading-relaxed text-blueGray-700">
                {{ truncatedText }}
              </p>
            </div>
          </div>
        </div>
      </div>
      <div v-else>Profile is downloading...</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive } from 'vue'
import { routerPush } from '@/router.ts'
import type { UpdateUser } from '@/services/api.ts'
import { useUserStore } from '@/store/user.ts'

const profile: UpdateUser = reactive({})
const userStore = useUserStore()

onMounted(async () => {
  if (!userStore.isAuthorized) return await routerPush('login')

  profile.image = userStore.user?.image
  profile.username = userStore.user?.username
  profile.bio = userStore.user?.bio
  profile.email = userStore.user?.email
})

function truncateText(text: string, wordLimit: number): string {
  const words = text.split(' ')
  if (words.length <= wordLimit) {
    return text
  }
  return words.slice(0, wordLimit).join(' ') + '...'
}

const longText =
  'An artist of considerable range, Jenna the name taken by Melbourne-raised, Brooklyn-based Nick Murphy writes, performs and records all of his own music, giving it a warm, intimate feel with a solid groove structure. An artist of considerable range.'
const truncatedText = computed(() => truncateText(longText, 12))
</script>
