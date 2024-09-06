<template>
  <div class="banner top-0" style="height: 250px">
    <div class="relative ml-16 pl-16">
      <h1 class="ml-32 uppercase">{{ article.title }}</h1>
    </div>
    <div class="container ml-16">
      <ArticleDetailMeta
        v-if="article"
        :article="article"
        @update="updateArticle"
      />
    </div>
  </div>

  <div class="container page mb-10">
    <div class="article-subtitle">
      <h1 class="font-bold uppercase text-md">{{ article.title }}</h1>
      <span>{{ article.description }}</span>
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
import { api } from '@/services'
import type { Article } from '@/services/api.ts'
import ArticleDetailMeta from '../ArticleDetailMeta/ArticleDetailMeta.vue'
import { Editor } from '@tiptap/vue-3'
import { Color } from '@tiptap/extension-color'
import FontFamily from '@tiptap/extension-font-family'
import TextStyle from '@tiptap/extension-text-style'
import Document from '@tiptap/extension-document'
import Paragraph from '@tiptap/extension-paragraph'
import Text from '@tiptap/extension-text'
import TextAlign from '@tiptap/extension-text-align'
import Heading from '@tiptap/extension-heading'
import Link from '@tiptap/extension-link'
import Dropcursor from '@tiptap/extension-dropcursor'
import Image from '@tiptap/extension-image'
import StarterKit from '@tiptap/starter-kit'
import Youtube from '@tiptap/extension-youtube'
import hljs from 'highlight.js'
import Highlight from '@tiptap/extension-highlight'
import Underline from '@tiptap/extension-underline'

hljs.initHighlighting()

const route = useRoute()
const slug = route.params.slug as string
const article: Article = reactive(
  await api.articles.getArticle(slug).then((res) => res.data.article),
)

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

const CustomHighlight = Highlight.extend({
  addAttributes() {
    return {
      ...this.parent?.(),
      color: {
        default: null,
        parseHTML: (element) => element.getAttribute('data-color'),
        renderHTML: (attributes) => {
          if (!attributes.color) {
            return {}
          }
          return {
            'data-color': attributes.color,
            style: `background-color: ${attributes.color}; color: white;`,
          }
        },
      },
    }
  },
})

const articleHandledBody = computed(() => {
  const editor = new Editor({
    content: article.body,
    extensions: [
      StarterKit,
      TextStyle,
      TextAlign.configure({
        types: ['heading', 'paragraph'],
      }),
      Color,
      Dropcursor,
      Heading.configure({
        levels: [1, 2, 3, 4, 5, 6],
      }),
      Link.configure({
        openOnClick: true,
        linkOnPaste: true,
        protocols: ['ftp', 'mailto'],
        defaultProtocol: 'https',
      }),
      Image.configure({
        allowBase64: true,
        inline: true,
        HTMLAttributes: {
          class: 'image-class',
        },
      }),
      FontFamily.configure({
        types: ['textStyle'],
      }),
      CustomHighlight.configure({
        multicolor: true,
        HTMLAttributes: {
          class: 'highlight',
        },
      }),
      Underline,
      Youtube.configure({
        controls: false,
        nocookie: true,
      }),
      Document,
      Paragraph,
      Text,
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
:deep(.article-content) {
  font-family: Roboto, sans-serif;
  font-size: 1.1rem;
}
:deep(.article-content span) {
  line-height: 1.6;
  font-weight: normal;
  font-style: normal;
}
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
  line-height: 1.6rem;
  font-size: 1.1rem;
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
  background: #282c34;
  border-radius: 0.5rem;
  font-family: 'JetBrainsMono', monospace;
  font-size: 0.9rem;
  margin-left: 10rem;
  margin-right: 10rem;
  padding: 0.75rem 1rem;
  width: auto;
  overflow-x: auto;

  code {
    background: none;
    font-size: 0.9rem;
    padding: 0;
  }

  /* Code styling */
  .hljs-comment,
  .hljs-quote {
    color: #616161;
  }

  .hljs-variable,
  .hljs-template-variable,
  .hljs-attribute,
  .hljs-tag,
  .hljs-name,
  .hljs-regexp,
  .hljs-link,
  .hljs-name,
  .hljs-selector-id,
  .hljs-selector-class {
    color: #4d26bc;
  }

  .hljs-number,
  .hljs-meta,
  .hljs-built_in,
  .hljs-builtin-name,
  .hljs-literal,
  .hljs-type,
  .hljs-params {
    color: #fbbc88;
  }

  .hljs-string,
  .hljs-symbol,
  .hljs-bullet {
    color: #b9f18d;
  }

  .hljs-title {
    color: #d8946f;
  }

  .hljs-section {
    color: #bd7743;
  }

  .hljs-keyword,
  .hljs-selector-tag {
    color: #388ef4;
  }

  .hljs-emphasis {
    font-style: italic;
  }

  .hljs-strong {
    font-weight: 700;
  }
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
:deep(.article-content .highlight) {
  background-color: var(--highlight-color, yellow);
  border-radius: 0.25em;
  box-decoration-break: clone;
  padding: 0.05em 0.2em 0.15em;
}
.highlight[data-color] {
  background-color: var(data-color);
  color: white;
  border-radius: 0.25em;
  box-decoration-break: clone;
  padding: 0.1em 0.3em;
}
/* Youtube embed */
:deep(.article-content div[data-youtube-video]) {
  justify-content: center;
  align-items: center;
  cursor: move;
  padding-right: 1.5rem;
  margin-left: 160px;
  border-radius: 0.5rem;
  width: 800px;
  height: 480px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);

  iframe {
    width: 800px;
    height: 480px;
    border: 0.5rem solid var(--black-contrast);
    display: block;
    outline: 0 solid transparent;
    justify-content: center;
    align-items: center;
    margin: 0 auto;
    padding: 1rem;
  }

  &.ProseMirror-selectednode iframe {
    outline: 3px solid var(--purple);
    transition: outline 0.15s;
  }
}
</style>
