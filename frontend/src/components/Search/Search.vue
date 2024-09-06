<template>
  <div class="search-container max-w-lg mx-auto pt-8 mr-16">
    <p class="text-sm font-semibold">Search Article</p>
    <div class="relative">
      <span class="absolute inset-y-0 left-0 flex items-center pl-4 pt-3">
        <i class="fas fa-search text-gray-600"></i>
      </span>
      <input
        type="text"
        v-model="query"
        @input="onSearch"
        placeholder="Search for articles"
        class="w-full p-2 pl-10 mb-4 border border-emerald-900 rounded-full text-gray-300 font-light text-sm"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useSearch } from '@/composable/useSearch'

const props = defineProps({
  query: String
})

const query = ref(props.query)
const searchResults = ref([])
const { fetchSearch, search } = useSearch()

const onSearch = async () => {
  if (query.value) {
    await fetchSearch(query.value)
    searchResults.value = search.value
  } else {
    searchResults.value = []
  }
}

watch(query, onSearch)
</script>
