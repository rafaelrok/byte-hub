<template>
  <nav
    class="top-0 fixed z-50 w-full px-2 py-2 navbar-expand-lg bg-white shadow-2xl"
  >
    <div
      class="container px-4 mx-auto flex flex-wrap items-center justify-between"
    >
      <AppLink class="navbar-brand" name="global-feed"
        >byteHub community</AppLink
      >

      <ul class="nav navbar-nav pull-xs-right align-center">
        <li v-for="link in navLinks" :key="link.name" class="nav-item mt-1">
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
          <div class="nav-item mr-4 mt-2 text-center">
            <v-menu open-on-hover>
              <template v-slot:activator="{ props }">
                <template v-if="notificationCount > 0">
                  <v-badge
                    v-bind="props"
                    :color="'error'"
                    :content="notificationCount"
                  >
                    <i
                      class="ion-email mb-1 text-2xl"
                      @click="toggleNotificationDropdown"
                      @mouseover="handleHover"
                    ></i>
                  </v-badge>
                </template>
                <template v-else>
                  <i
                    class="ion-email-unread mb-1 text-2xl"
                    @click=""
                    @mouseover=""
                  ></i>
                </template>
              </template>

              <v-card min-width="300">
                <v-list>
                  <v-list-item
                    :prepend-avatar="userProfileImage"
                    subtitle="Follow and likes"
                    title="Notifications"
                  ></v-list-item>
                </v-list>

                <v-divider inset></v-divider>

                <v-list class="align-center items-center">
                  <template v-if="followers.length > 0">
                    <v-list-item
                      prepend-icon="ion-person-add"
                      v-for="follower in followers"
                      :key="follower.username"
                    >
                      <v-list-item-title>
                        <AppLink
                          class="pr-4 text-gray-700 hover:bg-gray-100 text-sm"
                          :name="'profile'"
                          :params="{ username: follower.username }"
                        >
                          {{ follower.username }}
                        </AppLink>
                      </v-list-item-title>
                    </v-list-item>

                    <v-card-actions>
                      <v-spacer></v-spacer>
                      <AppLink
                        class="block px-4 py-2 text-gray-700 hover:bg-gray-100 no-underline text-sm"
                        name="profile"
                        :params="{ username: username }"
                        @click="closeProfileMenu"
                      >
                        View All
                      </AppLink>
                      <v-btn
                        class="text-sm"
                        size="small"
                        color="primary"
                        variant="plain"
                        @click="markAllAsRead"
                      >
                        Mark as read
                      </v-btn>
                    </v-card-actions>
                  </template>
                </v-list>
              </v-card>
            </v-menu>
          </div>

          <button
            @click="toggleProfileMenu"
            class="flex items-center focus:outline-none"
          >
            <img
              :src="userProfileImage"
              alt="User Avatar"
              class="w-8 h-8 rounded-full mt-1"
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
import { computed, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import type { RouteParams } from 'vue-router'
import { storeToRefs } from 'pinia'
import { AppRouteNames, routerPush } from '@/router.ts'
import { useUserStore } from '@/store/user.ts'
import AppLink from '@/components/AppLink/AppLink.vue'
import { useNotificationMessage } from '@/composable/useNotificationMessage.ts'

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
)
const userId = computed(() => user.value?.id || null)
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

/*
 * Notifications
 * Implementação de notificações
 */
const showBadge = ref(true)
const showEmailIcon = ref(true)
const isNotificationDropdownOpen = ref(false)
const { updateFollowReading } = useNotificationMessage(userId.value)
const { getNotificationMessage } = useNotificationMessage(userId.value)
const { getTotalNotificationMessage } = useNotificationMessage(userId.value)
const notificationMessage = ref<any>(null)
const countFollow = ref<number>(0)

const fetchNotifications = async () => {
  if (userId.value) {
    const follower = await getNotificationMessage()
    const totalMessage = await getTotalNotificationMessage()
    if (follower) {
      notificationMessage.value = follower
    }
    if (totalMessage) {
      countFollow.value = totalMessage
    }
  }
}

watch(
  userId,
  async (newUserId) => {
    if (newUserId) {
      await fetchNotifications()
      await fetchUnreadNotificationCount()
    }
  },
  { immediate: true },
)

const notificationCount = computed(() => countFollow.value)
const followers = computed(() => notificationMessage.value)

const toggleNotificationDropdown = async () => {
  isNotificationDropdownOpen.value = !isNotificationDropdownOpen.value
  showBadge.value = false
  showEmailIcon.value = false
}

const handleHover = async () => {
  showBadge.value = false
  showEmailIcon.value = false
}

const markAllAsRead = async () => {
  const notifications = await getNotificationMessage()
  if (notifications) {
    notificationMessage.value = notifications
  }

  for (const notification of notificationMessage.value) {
    await updateFollowReading(notification.id)
  }

  await fetchNotifications()
  await fetchUnreadNotificationCount()
}

const fetchUnreadNotificationCount = async () => {
  const totalUnread = await useNotificationMessage(
    userId.value,
  ).getTotalNotificationMessage()
  countFollow.value = totalUnread.count
}

/*
 * Profile Menu
 * Implementação do menu de perfil
 */
const toggleProfileMenu = () => {
  isProfileMenuOpen.value = !isProfileMenuOpen.value
}

const closeProfileMenu = () => {
  isProfileMenuOpen.value = false
}

const closeNotificationDropdown = () => {
  isNotificationDropdownOpen.value = false
}

const handleClickOutside = (event: MouseEvent) => {
  const profileMenuWrapper = document.querySelector('.nav-item.relative')
  if (
    profileMenuWrapper &&
    !profileMenuWrapper.contains(event.target as Node)
  ) {
    closeProfileMenu()
    closeNotificationDropdown()
  }
}

onMounted(() => {
  document.addEventListener('click', handleClickOutside)
  const interval = setInterval(async () => {
    await fetchNotifications()
  }, 5000)

  onBeforeUnmount(() => {
    clearInterval(interval)
  })
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
