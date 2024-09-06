<template>
  <div class="mr-16">
    <p class="font-semibold text-sm">Popular Tags</p>

    <div class="tag-list">
      <v-chip
        v-for="tag in tags"
        size="small"
        elevation="1"
        class="tag-pill mr-2 my-1"
      >
        <AppLink :key="tag" name="tag" :params="{ tag }" :aria-label="tag">
          <v-icon icon="fas fa-tag" start color="#059669"></v-icon>
          {{ tag }}
        </AppLink>
      </v-chip>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useTags } from '@/composable/useTags.ts'
import AppLink from '@/components/AppLink/AppLink.vue'

const { tags, fetchTags } = useTags()

await fetchTags()
</script>
