<template>
  <p class="font-semibold text-sm">Popular Tags</p>

  <div class="tag-list">
    <AppLink
      v-for="tag in tags"
      :key="tag"
      name="tag"
      :params="{ tag }"
      :aria-label="tag"
      class="tag-pill tag-default"
    >
      {{ tag }}
    </AppLink>
  </div>
</template>

<script setup lang="ts">
import { useTags } from '@/composable/useTags.ts'
import AppLink from '@/components/AppLink/AppLink.vue'

const { tags, fetchTags } = useTags()

await fetchTags()
</script>
