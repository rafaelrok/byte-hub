<template>
  <div class="editor-page pt-24">
    <div class="container page">
      <div class="row">
        <div class="col-md-10 offset-md-1 col-xs-12">
          <form @submit.prevent="onSubmit">
            <fieldset class="form-group shadow-lg">
              <div>
                <span class="text-gray-500"
                  >Set the main image of your article here</span
                >
              </div>
              <div class="flex mt-2 items-center justify-center w-full">
                <label
                  for="dropzone-file"
                  class="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600"
                >
                  <div
                    class="flex flex-col items-center justify-center pt-5 pb-6"
                  >
                    <i
                      class="fas fa-cloud-arrow-down text-4xl text-emerald-600"
                    ></i>
                    <p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
                      <span class="font-semibold">Click to upload</span> or drag
                      and drop
                    </p>
                    <p class="text-xs text-gray-500 dark:text-gray-400">
                      SVG, PNG, JPG or GIF (MAX. 800x400px)
                    </p>
                  </div>
                  <input
                    @change=""
                    accept="image/*"
                    id="dropzone-file"
                    type="file"
                    class="hidden"
                  />
                </label>
              </div>
            </fieldset>
            <fieldset class="form-group shadow-lg">
              <v-text-field
                v-model="form.title"
                hide-details="auto"
                type="text"
                class="form-control form-control-lg rounded-lg border-emerald-900"
                placeholder="Article Title"
                label="Title"
              >
              </v-text-field>
            </fieldset>
            <fieldset class="form-group shadow-lg">
              <v-text-field
                v-model="form.description"
                hide-details="auto"
                type="text"
                class="form-control form-control-lg"
                placeholder="What's this article about?"
                label="Description"
              />
            </fieldset>
            <fieldset class="form-group shadow-lg">
              <Tiptap
                v-model="form.body"
                aria-label="Body"
                class="form-control"
                placeholder="Write your article (in markdown)"
              />
            </fieldset>
            <fieldset class="form-group shadow-lg">
              <v-text-field
                v-model="newTag"
                hide-details="auto"
                type="text"
                class="form-control rounded-lg border-emerald-900"
                placeholder="Enter new tag or exists tags"
                label="Tags"
                @change="addTag"
                @keypress.enter.prevent="addTag"
              />
              <div class="tag-list">
                <span
                  v-for="tag in form.tagList"
                  :key="tag"
                  class="tag-default tag-pill mt-2 bg-gray-500"
                >
                  <i
                    role="button"
                    tabindex="0"
                    :aria-label="`Delete tag: ${tag}`"
                    class="ion-close-round"
                    @click="removeTag(tag)"
                    @keypress.enter="removeTag(tag)"
                  />
                  {{ tag }}
                </span>
              </div>
            </fieldset>
            <!--            class="btn btn-lg pull-xs-right border-2 bg-emerald-600 border-emerald-900 text-white transition outline-active hover:bg-emerald-300"-->
            <v-btn
              class="btn btn-lg pull-xs-right"
              type="submit"
              color="#059669"
              elevation="5"
              width="180px"
              height="50px"
              variant="tonal"
              :disabled="!(form.title && form.description && form.body)"
            >
              Publish Article
            </v-btn>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { computed, onMounted, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { api } from '@/services'
import type { Article } from '@/services/api.ts'
import Tiptap from '@/components/Tiptap/Tiptap.vue'

interface FormState {
  title: string
  description: string
  body: string
  tagList: string[]
}

const route = useRoute()
const router = useRouter()
const slug = computed<string>(() => route.params.slug as string)

const form: FormState = reactive({
  title: '',
  description: '',
  body: '',
  tagList: [],
})

const newTag = ref<string>('')
function addTag() {
  form.tagList.push(newTag.value.trim())
  newTag.value = ''
}
function removeTag(tag: string) {
  form.tagList = form.tagList.filter((t) => t !== tag)
}

async function fetchArticle(slug: string) {
  const article = await api.articles
    .getArticle(slug)
    .then((res) => res.data.article)

  // FIXME: I always feel a little wordy here
  form.title = article.title
  form.description = article.description
  form.body = article.body
  form.tagList = article.tagList
}

onMounted(async () => {
  if (slug.value) await fetchArticle(slug.value)
})

async function onSubmit() {
  try {
    let article: Article
    const articleData = { ...form, body: form.body }

    if (slug.value) {
      article = await api.articles
        .updateArticle(slug.value, { article: articleData })
        .then((res) => res.data.article)
    } else {
      article = await api.articles
        .createArticle({ article: articleData })
        .then((res) => res.data.article)
    }

    await router.push({ name: 'article', params: { slug: article.slug } })
  } catch (error) {
    console.error('Error submitting article:', error)
    alert('Error submitting article')
  }
}
</script>
