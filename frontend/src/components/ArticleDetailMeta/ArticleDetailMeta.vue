<template>
  <div class="article-meta">
    <AppLink name="profile" :params="{ username: article.author.username }">
      <img :src="article.author.image" :alt="article.author.username" />
    </AppLink>

    <div class="info">
      <AppLink
        name="profile"
        :params="{ username: article.author.username }"
        class="author"
      >
        {{ article.author.username }}
      </AppLink>

      <span class="date">{{ article.author.bio }}</span>
      <span class="date"
        >Posted: {{ new Date(article.createdAt).toLocaleDateString() }}</span
      >
    </div>

    <button
      v-if="displayFollowButton"
      :aria-label="article.author.following ? 'Unfollow' : 'Follow'"
      class="btn btn-sm btn-outline-secondary space"
      :disabled="followProcessGoing"
      @click="toggleFollow"
    >
      <i class="ion-plus-round space" />
      {{ article.author.following ? 'Unfollow' : 'Follow' }}
      {{ article.author.username }}
    </button>

    <button
      :aria-label="
        article.favorited ? 'Unfavorite article' : 'Favorite article'
      "
      class="btn btn-sm space"
      :class="[article.favorited ? 'btn-primary' : 'btn-outline-primary']"
      :disabled="favoriteProcessGoing"
      @click="favoriteArticle"
    >
      <i class="ion-heart space" />
      {{ article.favorited ? 'Unfavorite' : 'Favorite' }} Article
      <span class="counter">({{ article.favoritesCount }})</span>
    </button>

    <AppLink
      v-if="displayEditButton"
      aria-label="Edit article"
      class="btn btn-outline-secondary btn-sm space"
      name="edit-article"
      :params="{ slug: article.slug }"
    >
      <i class="ion-edit space" /> Edit Article
    </AppLink>

    <button
      v-if="displayEditButton"
      aria-label="Delete article"
      class="btn btn-outline-danger btn-sm"
      @click="onDelete"
    >
      <i class="ion-trash-a" /> Delete Article
    </button>

    <div class="flex pt-4">
      <ul class="tag-list">
        <li
          v-for="tag in article.tagList"
          :key="tag"
          class="tag-default tag-pill tag-outline"
        >
          {{ tag }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, toRefs } from 'vue'
import { storeToRefs } from 'pinia'
import { useFavoriteArticle } from '@/composable/useFavoriteArticle.ts'
import { useFollow } from '@/composable/useFollowProfile.ts'
import { routerPush } from '@/router.ts'
import { api } from '@/services'
import type { Article, Profile } from '@/services/api.ts'
import { useUserStore } from '@/store/user.ts'
import AppLink from '@/components/AppLink/AppLink.vue'

interface Props {
  article: Article
}
interface Emits {
  (e: 'update', article: Article): void
}

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const { article } = toRefs(props)
const { user, isAuthorized } = storeToRefs(useUserStore())

const displayEditButton = computed(
  () =>
    isAuthorized.value &&
    user.value?.username === article.value.author.username,
)
const displayFollowButton = computed(
  () =>
    isAuthorized.value &&
    user.value?.username !== article.value.author.username,
)

const { favoriteProcessGoing, favoriteArticle } = useFavoriteArticle({
  isFavorited: computed(() => article.value.favorited),
  articleSlug: computed(() => article.value.slug),
  onUpdate: (newArticle: Article) => emit('update', newArticle),
})

async function onDelete() {
  await api.articles.deleteArticle(article.value.slug)
  await routerPush('global-feed')
}

const { followProcessGoing, toggleFollow } = useFollow({
  following: computed(() => article.value.author.following),
  username: computed(() => article.value.author.username),
  onUpdate: (author: Profile) => {
    const newArticle = { ...article.value, author }
    emit('update', newArticle)
  },
})
</script>

<style scoped>
.space {
  margin-right: 8px;
}
</style>
