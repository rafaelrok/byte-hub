<template>
  <div class="banner">
    <div class="container">
      <h1>{{ article.title }}</h1>

      <ArticleDetailMeta
        v-if="article"
        :article="article"
        @update="updateArticle"
      />
    </div>
  </div>

  <div class="container page mb-10">
    <h2 class="article-subtitle">{{ article.title }}</h2>
    <div class="row article-content">
      <div id="article-content" class="col-md-12" v-html="articleHandledBody" />
    </div>

    <hr />
  </div>
</template>

<script setup lang="ts">
import { computed, reactive } from 'vue'
import { useRoute } from 'vue-router'
import marked from '@/plugins/marked.ts'
import { api } from '@/services'
import type { Article } from '@/services/api.ts'
import ArticleDetailMeta from '../ArticleDetailMeta/ArticleDetailMeta.vue'

const route = useRoute()
const slug = route.params.slug as string
const article: Article = reactive(
  await api.articles.getArticle(slug).then((res) => res.data.article),
)

const articleHandledSubtitle = computed(() => marked(article.title))
const articleHandledBody = computed(() => marked(article.body))

function updateArticle(newArticle: Article) {
  Object.assign(article, newArticle)
}
</script>
