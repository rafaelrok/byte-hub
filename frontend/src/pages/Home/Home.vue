<template>
  <div class="home-page">
    <div class="banner py-24">
      <div class="container">
        <h1 class="logo-font">ByteHub Community</h1>
        <p>A place to share your knowledge.</p>
      </div>
    </div>

    <div class="container page">
      <div class="row">
        <div class="col-md-9">
          <Suspense>
            <ArticlesList use-global-feed use-my-feed use-tag-feed />
            <template #fallback> Articles are downloading... </template>
          </Suspense>
        </div>

        <div class="col-md-3 mt-12">
          <div class="sidebar">
            <Suspense>
              <PopularTags />
              <template #fallback> Popular tags are downloading... </template>
            </Suspense>
            <Suspense>
              <Search @update-query="updateQuery" />
              <template #fallback> Popular search are downloading... </template>
            </Suspense>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ArticlesList from '@/components/ArticleList/ArticlesList.vue'
import PopularTags from '@/components/Tag/PopularTags.vue'
import Search from '@/components/Search/Search.vue'

const searchQuery = ref([])

function updateQuery(query) {
  searchQuery.value = query
}
</script>
