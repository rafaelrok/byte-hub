<template>
  <div class="ml-16">
    <ArticlesListNavigation v-bind="$attrs" :tag="tag" :username="username" />

    <div v-if="articlesDownloading" class="article-preview">
      Articles are downloading...
    </div>
    <div v-else-if="articles.length === 0" class="article-preview">
      No articles are here... yet.
    </div>
    <template v-else>
      <v-container>
        <v-row>
          <v-col
            v-for="(article, index) in paginatedArticles"
            :key="article.slug"
            cols="12"
          >
            <ArticlesListArticlePreview
              :article="article"
              @update="(newArticle) => updateArticle(getOriginalIndex(index), newArticle)"
            />
          </v-col>
        </v-row>
      </v-container>

      <div class="text-center">
        <v-container>
          <v-row justify="center">
            <v-col cols="8">
              <v-container class="max-width">
                <v-pagination
                  v-model="currentPage"
                  :length="totalPages"
                  @update:model-value="changePage"
                  class="my-4"
                  color="#059669"
                  active-color="#4e8752"
                  variant="tonal"
                  size="32px"
                  rounded
                  next-icon="fas fa-angle-right"
                  prev-icon="fas fa-angle-left"
                ></v-pagination>
              </v-container>
            </v-col>
          </v-row>
        </v-container>
      </div>

<!--      <v-pagination-->
<!--        v-model="currentPage"-->
<!--        :length="totalPages"-->
<!--        @update:model-value="changePage"-->
<!--        :color="articles.length > 0 ? 'green-darken-3' : 'green-lighten-1'"-->
<!--      ></v-pagination>-->
    </template>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useArticles } from '@/composable/useArticles.ts'
import ArticlesListArticlePreview from '../ArticlesListArticlePreview/ArticlesListArticlePreview.vue'
import ArticlesListNavigation from '../ArticlesListNavigation/ArticlesListNavigation.vue'

const {
  fetchArticles,
  articlesDownloading,
  articles,
  updateArticle,
  tag,
  username,
} = useArticles()

const itemsPerPage = 5
const currentPage = ref(1)

const totalPages = computed(() => Math.ceil(articles.value.length / itemsPerPage))

const paginatedArticles = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return articles.value.slice(start, end)
})

const changePage = (page: number) => {
  currentPage.value = page
}

const getOriginalIndex = (paginatedIndex: number) => {
  return (currentPage.value - 1) * itemsPerPage + paginatedIndex
}

await fetchArticles()
</script>
