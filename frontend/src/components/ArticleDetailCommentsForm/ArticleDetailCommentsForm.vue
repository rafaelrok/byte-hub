<template>
  <h2 class="flex font-bold text-uppercase py-4">Leave your comment</h2>
  <p v-if="!profile">
    <AppLink name="login"> Sign in </AppLink> or
    <AppLink name="register"> sign up </AppLink> to add comments on this
    article.
  </p>
  <form v-else class="card comment-form" @submit.prevent="submitComment">
    <v-card class="shadow-2xl" >
      <v-textarea
        v-model="comment"
        color="light-green-darken-3"
        variant="filled"
        auto-grow
        aria-label="Write comment"
        class="form-control"
        placeholder="Write a comment..."
        :rows="3"
      />
      <div class="flex card-footer shadow-2xl">
        <img
          :src="profile.image"
          class="comment-author-img"
          :alt="profile.username"
        />
        <div class="flex flex-col pl-2">
          <h3 class="text-subtitle-2 text-gray-400">
            {{ profile.username }}
          </h3>
          <span class="text-sm text-gray-500">
          {{ profile.bio }}
        </span>
        </div>
        <v-btn
          aria-label="Submit"
          type="submit"
          :disabled="comment === ''"
          class="btn btn-sm btn-primary ml-auto"
          elevation="5"
          variant="plain"
        >
          Comment
        </v-btn>
      </div>
    </v-card>
  </form>
</template>

<script setup lang="ts">
import { computed, ref } from 'vue'
import { storeToRefs } from 'pinia'
import { useProfile } from '@/composable/useProfile.ts'
import { api } from '@/services'
import type { Comment } from '@/services/api.ts'
import { useUserStore } from '@/store/user.ts'

interface Props {
  articleSlug: string
}

const props = defineProps<Props>()
const emit = defineEmits<{
  (e: 'addComment', comment: Comment): void
}>()

const { user } = storeToRefs(useUserStore())

const username = computed(() => user.value?.username ?? '')
const { profile } = useProfile({ username })

const comment = ref('')

async function submitComment() {
  const newComment = await api.articles
    .createArticleComment(props.articleSlug, {
      comment: { body: comment.value },
    })
    .then((res) => res.data.comment)
  emit('addComment', newComment)
  comment.value = ''
}
</script>
