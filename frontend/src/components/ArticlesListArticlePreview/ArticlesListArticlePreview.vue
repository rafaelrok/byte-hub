<template>
  <div class="article-preview mr-4">
    <v-card
      elevation="10"
      height="255px"
      style="min-height: 255px; display: flex; flex-direction: column"
    >
      <v-row no-gutters align="stretch">
        <v-col cols="12" sm="8">
          <v-card-item>
            <v-row no-gutters>
              <div class="article-meta-card">
                <AppLink
                  name="profile"
                  :params="{ username: props.article.author.username }"
                >
                  <img
                    :src="article.author.image"
                    :alt="props.article.author.username"
                  />
                </AppLink>
                <div class="info">
                  <AppLink
                    name="profile"
                    :params="{ username: props.article.author.username }"
                    class="author"
                  >
                    {{ article.author.username }} -
                    <span style="font-size: 12px; color: #a9aaaa">{{
                      new Date(article.createdAt).toDateString()
                    }}</span>
                  </AppLink>
                  <span class="date">{{ article.author.bio }}</span>
                </div>
              </div>
            </v-row>
          </v-card-item>

          <v-card-item>
            <div class="ml-4">
              <v-card-title>
                <AppLink
                  name="article"
                  :params="{ slug: article.slug }"
                  class="preview-link"
                >
                  {{ article.title }}
                </AppLink>
              </v-card-title>
              <v-card-subtitle>{{ article.description }}</v-card-subtitle>
            </div>
            <v-card-text style="min-height: 255px">
              <div v-html="truncatedBody" class="truncated-text"></div>
              <AppLink
                name="article"
                :params="{ slug: article.slug }"
                class="preview-link"
              >
                <span>Read more</span>
              </AppLink>

              <div class="flex relative">
                <v-chip
                  v-for="tag in article.tagList.slice(0, 3)"
                  :key="tag"
                  size="x-small"
                  elevation="2"
                  class="mt-2 mr-1"
                >
                  <v-icon icon="fas fa-tag" start color="#059669"></v-icon>
                  {{ tag }}
                </v-chip>
                <v-card-actions class="absolute right-0">
                  <div class="mb-4">
                    <v-btn
                      variant="plain"
                      elevation="5"
                      size="x-small"
                      append-icon="fas fa-share"
                      class="btn btn-outline-primary btn-sm"
                    >
                    </v-btn>
                    <v-btn
                      variant="plain"
                      elevation="5"
                      size="x-small"
                      append-icon="fas fa-copy"
                      class="btn btn-outline-primary btn-sm"
                    >
                    </v-btn>
                    <v-btn
                      variant="plain"
                      elevation="5"
                      size="x-small"
                      append-icon="ion-heart"
                      :aria-label="
                        article.favorited
                          ? 'Unfavorite article'
                          : 'Favorite article'
                      "
                      class="btn btn-sm"
                      :class="[
                        article.favorited
                          ? 'btn-primary'
                          : 'btn-outline-primary',
                      ]"
                      :disabled="favoriteProcessGoing"
                      @click="() => favoriteArticle()"
                    >
                      {{ article.favoritesCount }}
                    </v-btn>
                  </div>
                </v-card-actions>
              </div>
            </v-card-text>
          </v-card-item>
        </v-col>

        <v-col cols="12" sm="4">
          <v-img
            height="255px"
            src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
            cover
            style="min-height: 255px"
          ></v-img>
        </v-col>
      </v-row>
    </v-card>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue'
import { useFavoriteArticle } from '@/composable/useFavoriteArticle.ts'
import type { Article } from '@/services/api.ts'

interface Props {
  article: Article
}
interface Emits {
  (e: 'update', article: Article): void
}

const truncatedBody = computed(() => {
  const maxLength = 200
  const bodyText = props.article.body.replace(/(<([^>]+)>)/gi, '')
  return bodyText.length > maxLength
    ? bodyText.substring(0, maxLength) + '...'
    : bodyText
})

const props = defineProps<Props>()
const emit = defineEmits<Emits>()

const { favoriteProcessGoing, favoriteArticle } = useFavoriteArticle({
  isFavorited: computed(() => props.article.favorited),
  articleSlug: computed(() => props.article.slug),
  onUpdate: (newArticle: Article): void => emit('update', newArticle),
})
</script>
