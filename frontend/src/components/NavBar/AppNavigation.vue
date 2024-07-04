<template>
  <nav
    class="top-0 fixed z-50 w-full flex flex-wrap items-center justify-between px-2 py-3 navbar-expand-lg bg-white shadow"
  >
    <div
      class="container px-4 mx-auto flex flex-wrap items-center justify-between"
    >
      <AppLink class="navbar-brand" name="global-feed"
        >byteHub community</AppLink
      >

      <ul class="nav navbar-nav pull-xs-right">
        <li v-for="link in navLinks" :key="link.name" class="nav-item">
          <AppLink
            class="nav-link"
            active-class="active"
            :name="link.name"
            :params="link.params"
            :aria-label="link.title"
            @click="closeProfileMenu"
          >
            <i v-if="link.icon" :class="link.icon"></i>
            {{ link.title }}
          </AppLink>
        </li>
        <li
          v-if="isAuthorized"
          class="nav-item relative"
          ref="profileMenuWrapper"
        >
          <button
            @click="toggleProfileMenu"
            class="flex items-center focus:outline-none"
          >
            <img
              :src="userProfileImage"
              alt="User Avatar"
              class="w-8 h-8 rounded-full"
            />
          </button>
          <ul
            v-if="isProfileMenuOpen"
            class="absolute right-0 mt-2 w-48 bg-white shadow-lg py-2 rounded-lg"
            @mouseleave="closeProfileMenu"
          >
            <li>
              <AppLink
                class="block px-4 py-2 text-gray-700 hover:bg-gray-100"
                name="profile"
                :params="{ username: username }"
                @click="closeProfileMenu"
              >
                <i class="ion-person mr-1"></i>
                Profile
              </AppLink>
            </li>
            <li>
              <AppLink
                class="flex mr-2 px-4 py-2 text-gray-700 hover:bg-gray-100"
                name="settings"
                :params="{ username: navLinks[0].params?.settings }"
                @click="closeProfileMenu"
              >
                <i class="ion-gear-a mr-1"></i>
                Settings
              </AppLink>
            </li>
            <li>
              <AppLink
                class="block w-full text-left px-4 py-2 text-gray-700 hover:bg-gray-100"
                aria-label="Logout"
                @click="onLogout"
              >
                <i class="ion-log-out mr-1"></i>
                Logout
              </AppLink>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script setup lang="ts">
import { computed, ref, onMounted, onBeforeUnmount } from 'vue'
import type { RouteParams } from 'vue-router'
import { storeToRefs } from 'pinia'
import { AppRouteNames, routerPush } from '@/router.ts'
import { useUserStore } from '@/store/user.ts'
import AppLink from '@/components/AppLink/AppLink.vue'

interface NavLink {
  name: AppRouteNames
  params?: Partial<RouteParams>
  title: string
  icon?: string
  display: 'all' | 'anonym' | 'authorized'
}

const { user } = storeToRefs(useUserStore())
const userStore = useUserStore()

const username = computed(() => user.value?.username)
const displayStatus = computed(() => (username.value ? 'authorized' : 'anonym'))
const userProfileImage = computed(
  () => user.value?.image || 'default-avatar.png',
) // Assumindo que há um campo de imagem no user store ou um avatar padrão

const allNavLinks = computed<NavLink[]>(() => [
  {
    name: 'global-feed',
    title: 'Home',
    display: 'all',
  },
  {
    name: 'login',
    title: 'Sign in',
    display: 'anonym',
    icon: 'ion-log-in',
  },
  {
    name: 'register',
    title: 'Sign up',
    display: 'anonym',
    icon: 'ion-person-add',
  },
  {
    name: 'create-article',
    title: 'New Post',
    display: 'authorized',
    icon: 'ion-compose',
  },
])

const navLinks = computed(() =>
  allNavLinks.value.filter(
    (l) => l.display === displayStatus.value || l.display === 'all',
  ),
)

const isProfileMenuOpen = ref(false)
const isAuthorized = computed(() => displayStatus.value === 'authorized')

const toggleProfileMenu = () => {
  isProfileMenuOpen.value = !isProfileMenuOpen.value
}

const closeProfileMenu = () => {
  isProfileMenuOpen.value = false
}

const handleClickOutside = (event: MouseEvent) => {
  const profileMenuWrapper = document.querySelector('.nav-item.relative')
  if (
    profileMenuWrapper &&
    !profileMenuWrapper.contains(event.target as Node)
  ) {
    closeProfileMenu()
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
})

onBeforeUnmount(() => {
  document.removeEventListener('click', handleClickOutside)
})

async function onLogout() {
  userStore.updateUser(null)
  closeProfileMenu()
  await routerPush('global-feed')
}
</script>
