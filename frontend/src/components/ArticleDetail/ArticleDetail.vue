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
    <div>
      <h2 class="article-subtitle font-bold">{{ article.title }}</h2>
      <span class="article-subtitle font-light">{{ article.description }}</span>
    </div>
    <div class="row article-content">
      <div id="article-content" class="col-md-12" v-html="articleHandledBody" />
    </div>

    <hr />
  </div>
</template>

<script setup lang="ts">
import { computed, reactive } from 'vue'
import { useRoute } from 'vue-router'
// import marked from '@/plugins/marked.ts'
import { api } from '@/services'
import type { Article } from '@/services/api.ts'
import ArticleDetailMeta from '../ArticleDetailMeta/ArticleDetailMeta.vue'
import { Editor } from '@tiptap/vue-3'
import { StarterKit } from '@tiptap/starter-kit'
import Image from '@tiptap/extension-image'
import hljs from 'highlight.js'

const route = useRoute()
const slug = route.params.slug as string
const article: Article = reactive(
  await api.articles.getArticle(slug).then((res) => res.data.article),
)

hljs.initHighlighting()

const highlightCode = (html: any) => {
  const codeBlocks = html.match(/<code[^>]*>(.*?)<\/code>/gs)
  if (codeBlocks) {
    codeBlocks.forEach((codeBlock: any) => {
      const code = codeBlock.replace(/<code[^>]*>|<\/code>/g, '')
      const highlightedCode = hljs.highlight(code, {
        language: 'javascript',
      }).value
      html = html.replace(codeBlock, `<code>${highlightedCode}</code>`)
    })
  }
  return html
}

const articleHandledBody = computed(() => {
  const editor = new Editor({
    content: article.body,
    extensions: [
      StarterKit,
      Image.configure({
        allowBase64: true,
        inline: true,
        HTMLAttributes: {
          class: 'image-class',
        },
      }),
    ],
  })
  const html = editor.getHTML()
  return highlightCode(html)
})

function updateArticle(newArticle: Article) {
  Object.assign(article, newArticle)
}
</script>

<style scoped lang="scss">
:deep(.article-content h1) {
  font-size: 2em;
  font-weight: bold;
  margin-top: 1em;
}
:deep(.article-content h2) {
  font-size: 1.5em;
  font-weight: bold;
  margin-top: 0.83em;
}
:deep(.article-content h3) {
  font-size: 1.17em;
  font-weight: bold;
  margin-top: 1em;
}
:deep(.article-content p) {
  margin-top: 1em;
  margin-bottom: 1em;
}
:deep(.article-content strong) {
  font-weight: bold;
}
:deep(.article-content em) {
  font-style: italic;
}
:deep(.article-content ul, .article-content ol) {
  padding-left: 2em;
  margin-top: 1em;
  margin-bottom: 1em;
}
:deep(.article-content li) {
  margin-bottom: 0.5em;
}
:deep(.article-content a) {
  color: #0000ee;
  text-decoration: underline;
}
:deep(.article-content blockquote) {
  margin-left: 0;
  padding-left: 1em;
  border-left: 3px solid #ccc;
  color: #666;
}
:deep(.article-content pre) {
  position: relative;
  background: #2b2d30;
  border-radius: 0.5rem;
  font-family: 'JetBrainsMono', monospace;
  margin-left: 10rem;
  margin-right: 10rem;
  padding: 0.75rem 1rem;
  width: auto;
  overflow-x: auto;
}
:deep(.article-content code) {
  font-family: 'JetBrainsMono', monospace;
  background: none;
  color: #ffffff;
  font-size: 0.9rem;
  padding: 0;
}
:deep(.article-content table) {
  border-collapse: collapse;
  width: 100%;
}
:deep(.article-content th, .article-content td) {
  border: 1px solid #ddd;
  padding: 0.5em;
}
:deep(.article-content img) {
  max-width: 100%;
  height: auto;
  margin: 1em auto;
}
:deep(.article-content hr) {
  border: none;
  border-top: 1px solid #ddd;
  margin: 2em 0;
}
:deep(.article-content p img) {
  max-width: 800px;
  height: 400px;
  object-fit: cover;
  border-radius: 10px;
  border: 1px solid #ddd;
  margin: 2em auto;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
}
</style>
