<template>
  <div>
    <main class="profile-page">
      <section class="relative block h-500-px">
        <div
          class="absolute top-0 w-full h-full bg-center bg-cover"
          style="
            background-image: url('https://images.unsplash.com/photo-1499336315816-097655dcfbda?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2710&q=80');
          "
        >
          <span
            id="blackOverlay"
            class="w-full h-full absolute opacity-50 bg-black"
          ></span>
        </div>
        <div
          class="top-auto bottom-0 left-0 right-0 w-full absolute pointer-events-none overflow-hidden h-70-px"
          style="transform: translateZ(0)"
        >
          <svg
            class="absolute bottom-0 overflow-hidden"
            xmlns="http://www.w3.org/2000/svg"
            preserveAspectRatio="none"
            version="1.1"
            viewBox="0 0 2560 100"
            x="0"
            y="0"
          >
            <polygon class="text-blueGray-200 fill-current"></polygon>
          </svg>
        </div>
      </section>
      <section class="relative py-16 bg-blueGray-200">
        <div class="container mx-auto px-4">
          <div
            class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-xl rounded-lg -mt-64"
          >
            <div class="px-6">
              <div class="flex flex-wrap justify-center">
                <div
                  class="w-full lg:w-3/12 px-4 lg:order-2 flex justify-center"
                >
                  <div class="relative">
                    <img
                      :alt="profile.username"
                      :src="profile.image"
                      class="shadow-xl rounded-full h-auto align-middle border-none absolute -m-16 -ml-20 lg:-ml-16 max-w-150-px"
                    />
                  </div>
                </div>
                <div
                  class="w-full lg:w-4/12 px-4 lg:order-3 lg:text-right lg:self-center"
                >
                  <div class="py-6 px-3 mt-32 sm:mt-0">
                    <button
                      class="bg-emerald-500 active:bg-emerald-600 uppercase text-white font-bold hover:shadow-md shadow text-xs px-4 py-2 rounded outline-none focus:outline-none sm:mr-2 mb-1 ease-linear transition-all duration-150"
                      type="button"
                    >
                      Connect
                    </button>

                    <AppLink
                      v-if="showEdit"
                      class="btn btn-sm btn-outline-secondary action-btn"
                      name="settings"
                      aria-label="Edit profile settings"
                    >
                      <i class="ion-gear-a space" />
                      Edit profile settings
                    </AppLink>
                  </div>
                </div>
                <div class="w-full lg:w-4/12 px-4 lg:order-1">
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
                      <span class="text-sm text-blueGray-400">Favorites</span>
                    </div>
                  </div>
                </div>
              </div>
              <div class="text-center mt-12">
                <h3
                  class="text-4xl font-semibold leading-normal mb-2 text-blueGray-700 mb-2"
                  v-if="profile.bio"
                >
                  {{ profile.username }}
                </h3>
                <div
                  class="text-sm leading-normal mt-0 mb-2 text-blueGray-400 font-bold uppercase"
                >
                  <i class="mr-2 text-lg text-blueGray-400"></i>
                  Teresópolis, Rio de Janeiro
                </div>
                <div class="mb-2 text-blueGray-600 mt-10">
                  <i
                    class="fas fa-briefcase mr-2 text-lg text-blueGray-400"
                  ></i>
                  {{ profile.bio }}
                </div>
                <div class="mb-2 text-blueGray-600">
                  <i
                    class="fas fa-university mr-2 text-lg text-blueGray-400"
                  ></i>
                  University of Computer Science
                </div>
                <div class="mb-2 text-blueGray-600">
                  <i
                    class="fas fa-address-card mr-2 text-lg text-blueGray-400"
                  ></i>
                  {{ profile.email }}
                </div>
              </div>
              <div class="mt-10 py-10 border-t border-blueGray-200 text-center">
                <div class="flex flex-wrap justify-center">
                  <div class="w-full lg:w-9/12 px-4">
                    <p class="text-lg leading-relaxed text-blueGray-700">
                      Sou um software enginner Java com Spring boot e ReactJS
                      com TypeScript. Gosto de falar muito sobre
                      desenvolvimento, principalmente envolvendo todo universo
                      de Java, Spring Boot e Quarkus. Meus objetos são
                      profissionais e educacionais, desejo me tornar um
                      profissional Java , estou desenvolvendo um site com blog
                      onde vou publicar diversos temas da minha área
                      profissional então busco muito conhecimento aqui com
                      ChatGPT.
                    </p>
                  </div>
                </div>
              </div>
            </div>
            <div class="profile-page pt-14">
              <div class="user-info">
                <div class="container">
                  <div class="row">
                    <div class="col-xs-12 col-md-10 offset-md-1">
                      <div v-if="!profile" class="align-left">
                        Profile is downloading...
                      </div>
                      <template v-else>
                        <button
                          v-if="showFollow"
                          class="btn btn-sm btn-outline-secondary action-btn"
                          :disabled="followProcessGoing"
                          @click="toggleFollow"
                        >
                          <i class="ion-plus-round space" />
                          {{ profile.following ? 'Unfollow' : 'Follow' }}
                          {{ profile.username }}
                        </button>
                      </template>
                    </div>
                  </div>
                </div>
              </div>

              <div class="container">
                <div class="row">
                  <div class="col-xs-12 col-md-10 offset-md-1">
                    <Suspense>
                      <ArticlesList use-user-feed use-user-favorited />
                      <template #fallback>
                        Articles are downloading...
                      </template>
                    </Suspense>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
    </main>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import { storeToRefs } from 'pinia'
import ArticlesList from '@/components/ArticleList/ArticlesList.vue'
import { useFollow } from '@/composable/useFollowProfile.ts'
import { useProfile } from '@/composable/useProfile.ts'
import type { Profile } from '@/services/api.ts'
import { useUserStore } from '@/store/user.ts'
import AppLink from '@/components/AppLink/AppLink.vue'

const route = useRoute()
const username = computed<string>(() => route.params.username as string)

const { profile, updateProfile } = useProfile({ username })

const { followProcessGoing, toggleFollow } = useFollow({
  following: computed<boolean>(() => profile.value?.following ?? false),
  username,
  onUpdate: (newProfileData: Profile) => updateProfile(newProfileData),
})

const { user, isAuthorized } = storeToRefs(useUserStore())

const showEdit = computed<boolean>(
  () => isAuthorized && user.value?.username === username.value,
)
const showFollow = computed<boolean>(
  () => user.value?.username !== username.value,
)
</script>

<style scoped>
.space {
  margin-right: 4px;
}
.align-left {
  text-align: left;
}
</style>
