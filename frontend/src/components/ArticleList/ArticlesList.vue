<template>
  <ArticlesListNavigation v-bind="$attrs" :tag="tag" :username="username" />

  <div v-if="articlesDownloading" class="article-preview">
    Articles are downloading...
  </div>
  <div v-else-if="articles.length === 0" class="article-preview">
    No articles are here... yet.
  </div>
  <template v-else>
    <ArticlesListArticlePreview
      v-for="(article, index) in articles"
      :key="article.slug"
      :article="article"
      @update="(newArticle) => updateArticle(index, newArticle)"
    />

    <div class="flex items-center justify-center">
      <AppPagination
        :count="articlesCount"
        :page="page"
        @page-change="changePage"
      />
    </div>
  </template>
</template>

<script setup lang="ts">
import { useArticles } from '@/composable/useArticles.ts'
import AppPagination from '../Pagination/AppPagination.vue'
import ArticlesListArticlePreview from '../ArticlesListArticlePreview/ArticlesListArticlePreview.vue'
import ArticlesListNavigation from '../ArticlesListNavigation/ArticlesListNavigation.vue'

const {
  fetchArticles,
  articlesDownloading,
  articlesCount,
  articles,
  updateArticle,
  page,
  changePage,
  tag,
  username,
} = useArticles()

await fetchArticles()
</script>
