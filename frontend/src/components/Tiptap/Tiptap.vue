<template>
  <div
    v-if="editor"
    class="container mx-auto my-4 p-4 bg-white rounded-lg shadow-lg border-b-2 border-e-md border-l-2 border-r-2 border-2"
  >
    <div
      class="flex items-center overflow-x-auto space-x-2 py-2 border-b-2 border-e-md border-gray-600"
    >
      <v-btn
        @click="editor.chain().focus().toggleBold().run()"
        :disabled="!editor.can().chain().focus().toggleBold().run()"
        v-bind="
          buttonClass(
            editor.isActive('bold'),
            !editor.can().chain().focus().toggleBold().run(),
          )
        "
        :active="editor.isActive('bold')"
        icon="fas fa-bold"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().toggleItalic().run()"
        :disabled="!editor.can().chain().focus().toggleItalic().run()"
        v-bind="
          buttonClass(
            editor.isActive('italic'),
            !editor.can().chain().focus().toggleItalic().run(),
          )
        "
        :active="editor.isActive('italic')"
        icon="fas fa-italic"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().toggleStrike().run()"
        :disabled="!editor.can().chain().focus().toggleStrike().run()"
        v-bind="
          buttonClass(
            editor.isActive('strike'),
            !editor.can().chain().focus().toggleStrike().run(),
          )
        "
        icon="fas fa-strikethrough"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().toggleCodeBlock().run()"
        :disabled="!editor.can().chain().focus().toggleCodeBlock().run()"
        v-bind="
          buttonClass(
            editor.isActive('code'),
            !editor.can().chain().focus().toggleCodeBlock().run(),
          )
        "
        icon="fas fa-code"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="toggleLink"
        :disabled="!editor.can().chain().focus().toggleLink().run()"
        v-bind="buttonClass(editor.isActive('link'))"
        :icon="editor.isActive('link') ? 'fas fa-unlink' : 'fas fa-link'"
        aria-hidden="true"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="toggleImageInput"
        v-bind="buttonClass(editor.isActive('image'))"
        :icon="editor.isActive('image') ? 'fas fa-unImage' : 'fas fa-image'"
        aria-hidden="true"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().unsetAllMarks().run()"
        v-bind="buttonClass(false)"
        icon="fas fa-eraser"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().clearNodes().run()"
        v-bind="buttonClass(false)"
        :active="editor.isActive('trash')"
        icon="fas fa-trash-alt"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().setParagraph().run()"
        v-bind="buttonClass(editor.isActive('paragraph'))"
        icon="fas fa-paragraph"
        size="small"
      >
      </v-btn>

      <v-menu open-on-hover>
        <template v-slot:activator="{ props }">
          <v-btn
            v-bind="props"
            class="mx-1 flex items-center justify-center rounded-full transition-colors duration-300 md:mb-0 text-gray-600 bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium text-sm text-center"
            size="small"
            icon="fas fa-heading"
            width="32px"
            height="32px"
            rounded="circle"
            :color="'#2d2d2d'"
            variant="plain"
            border
          >
          </v-btn>
        </template>

        <v-list>
          <v-list-item>
            <v-list-item-title>
              <v-btn
                @click="
                  editor.chain().focus().toggleHeading({ level: 1 }).run()
                "
                v-bind="buttonClass(editor.isActive('heading', { level: 1 }))"
              >
                <i class="fas fa-heading"></i>1
              </v-btn>
            </v-list-item-title>
            <v-list-item-title>
              <v-btn
                @click="
                  editor.chain().focus().toggleHeading({ level: 2 }).run()
                "
                v-bind="buttonClass(editor.isActive('heading', { level: 2 }))"
              >
                <i class="fas fa-heading"></i>2
              </v-btn>
            </v-list-item-title>
            <v-list-item-title>
              <v-btn
                @click="
                  editor.chain().focus().toggleHeading({ level: 3 }).run()
                "
                v-bind="buttonClass(editor.isActive('heading', { level: 3 }))"
              >
                <i class="fas fa-heading"></i>3
              </v-btn>
            </v-list-item-title>
            <v-list-item-title>
              <v-btn
                @click="
                  editor.chain().focus().toggleHeading({ level: 5 }).run()
                "
                v-bind="buttonClass(editor.isActive('heading', { level: 5 }))"
              >
                <i class="fas fa-heading"></i>5
              </v-btn>
            </v-list-item-title>
            <v-list-item-title>
              <v-btn
                @click="
                  editor.chain().focus().toggleHeading({ level: 5 }).run()
                "
                v-bind="buttonClass(editor.isActive('heading', { level: 5 }))"
              >
                <i class="fas fa-heading"></i>5
              </v-btn>
            </v-list-item-title>
            <v-list-item-title>
              <v-btn
                @click="
                  editor.chain().focus().toggleHeading({ level: 6 }).run()
                "
                v-bind="buttonClass(editor.isActive('', { level: 6 }))"
              >
                <i class="fas fa-heading"></i>6
              </v-btn>
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>

      <v-btn
        @click="editor.chain().focus().toggleBulletList().run()"
        v-bind="buttonClass(editor.isActive('bulletList'))"
        :active="editor.isActive('bulletList')"
        icon="fas fa-list-ul"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().toggleOrderedList().run()"
        v-bind="buttonClass(editor.isActive('orderedList'))"
        icon="fas fa-list-ol"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().toggleBlockquote().run()"
        v-bind="buttonClass(editor.isActive('blockquote'))"
        icon="fas fa-quote-right"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().setHorizontalRule().run()"
        v-bind="buttonClass(false)"
        icon="fas fa-minus"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().setHardBreak().run()"
        v-bind="buttonClass(false)"
        icon="fas fa-level-down-alt"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().undo().run()"
        :disabled="!editor.can().chain().focus().undo().run()"
        v-bind="buttonClass(false, !editor.can().chain().focus().undo().run())"
        icon="fas fa-undo-alt"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().redo().run()"
        :disabled="!editor.can().chain().focus().redo().run()"
        v-bind="buttonClass(false, !editor.can().chain().focus().redo().run())"
        icon="fas fa-redo-alt"
        size="small"
      >
      </v-btn>
      <v-btn
        @click="editor.chain().focus().setColor('#059669').run()"
        v-bind="buttonClass(editor.isActive('textStyle', !editor))"
        icon="fas fa-paint-brush"
        size="small"
      >
      </v-btn>
    </div>
    <!-- Formulário personalizado para adicionar imagem -->
    <div v-if="showImageInput" class="mt-4 p-4 bg-gray-100 rounded-lg">
      <div class="flex flex-col space-y-4">
        <!-- Input para URL da imagem -->
        <input
          v-model="imageUrl"
          type="text"
          placeholder="Digit URL image"
          class="p-2 border text-sm rounded-lg focus:outline-none focus:ring-emerald-700 focus:border-emerald-500"
          style="height: 30px"
        />

        <!-- Botão para carregar imagem local -->
        <div class="flex mt-2 items-center justify-center w-full">
          <label
            for="dropzone-file"
            class="flex flex-col items-center justify-center w-full h-64 border-2 border-gray-300 border-dashed rounded-lg cursor-pointer bg-gray-50 dark:hover:bg-gray-800 dark:bg-gray-700 hover:bg-gray-100 dark:border-gray-600 dark:hover:border-gray-500 dark:hover:bg-gray-600"
          >
            <div class="flex flex-col items-center justify-center pt-5 pb-6">
              <i class="fas fa-cloud-arrow-down text-3xl text-emerald-600"></i>
              <p class="mb-2 text-sm text-gray-500 dark:text-gray-400">
                <span class="font-semibold">Click to upload</span> or drag and
                drop
              </p>
              <p class="text-xs text-gray-500 dark:text-gray-400">
                SVG, PNG, JPG or GIF (MAX. 800x400px)
              </p>
            </div>
            <input
              @change="handleFileUpload"
              accept="image/*"
              id="dropzone-file"
              type="file"
              class="hidden"
            />
          </label>
        </div>

        <!-- Botões de ação -->
        <div class="flex items-center space-x-2 mt-2">
          <v-btn
            @click="addImage"
            :disabled="isLoading || !imageUrl"
            class="px-4 py-1 bg-emerald-500 text-white font-semibold uppercase text-sm rounded-lg hover:bg-emerald-600 disabled:opacity-50 hover:shadow-md"
            elevation="5"
            color="#059669"
            style="height: 30px; font-weight: bolder"
          >
            To Add
          </v-btn>
          <v-btn
            @click="toggleImageInput"
            class="ml-2 px-4 py-1 bg-gray-500 text-gray-500 font-semibold uppercase text-sm rounded-lg border-e-md border hover:bg-emerald-800 hover:shadow-md hover:text-white"
            style="height: 30px"
          >
            Cancel
          </v-btn>
        </div>

        <!-- Indicador de carregamento -->
        <div v-if="isLoading" class="flex items-center space-x-2 mt-2">
          <span>Loading image...</span>
          <i class="fas fa-spinner fa-spin"></i>
        </div>
      </div>
    </div>

    <!-- Input flutuante para o link -->
    <div
      v-if="showLinkInput"
      :style="linkInputStyle"
      class="flex mt-2 p-2 bg-white shadow-lg border rounded-lg"
    >
      <input
        v-model="linkUrl"
        type="text"
        placeholder="Enter URL"
        class="p-2 mr-2 text-sm border rounded-lg focus:outline-none focus:ring-emerald-500 focus:border-emerald-500"
        style="width: 800px; height: 30px"
      />
      <v-btn
        @click="applyLink"
        :disabled="isLoading || !linkUrl"
        class="text-white px-4 py-1 rounded-lg text-sm uppercase font-semibold hover:bg-emerald-600"
        elevation="5"
        color="#059669"
        style="height: 30px; font-weight: bolder"
      >
        Apply
      </v-btn>
      <v-btn
        @click="cancelLink"
        class="ml-2 px-4 py-1 font-semibold uppercase text-sm rounded-lg border-e-md border hover:bg-emerald-800 hover:shadow-md hover:text-white"
        style="height: 30px; font-weight: bolder"
        levation="5"
        >Cancel
      </v-btn>
    </div>

    <editor-content :editor="editor" />
  </div>
</template>

<script>
import { Color } from '@tiptap/extension-color'
import ListItem from '@tiptap/extension-list-item'
import TextStyle from '@tiptap/extension-text-style'
import Document from '@tiptap/extension-document'
import Paragraph from '@tiptap/extension-paragraph'
import Text from '@tiptap/extension-text'
import Heading from '@tiptap/extension-heading'
import Link from '@tiptap/extension-link'
import Dropcursor from '@tiptap/extension-dropcursor'
import Image from '@tiptap/extension-image'
import StarterKit from '@tiptap/starter-kit'
import { Editor, EditorContent, VueNodeViewRenderer } from '@tiptap/vue-3'
import { FwbDropdown } from 'flowbite-vue'
import CodeBlockLowlight from '@tiptap/extension-code-block-lowlight'
import css from 'highlight.js/lib/languages/css'
import js from 'highlight.js/lib/languages/javascript'
import ts from 'highlight.js/lib/languages/typescript'
import html from 'highlight.js/lib/languages/xml'
import java from 'highlight.js/lib/languages/java'
// load all languages with "all" or common languages with "common"
import { all, createLowlight } from 'lowlight'
import CodeBlockComponent from './CodeBlock/CodeBlock.vue'

// create a lowlight instance
const lowlight = createLowlight(all)

// you can also register languages
lowlight.register('html', html)
lowlight.register('css', css)
lowlight.register('js', js)
lowlight.register('ts', ts)
lowlight.register('java', ts)

export default {
  methods: {
    buttonClass(isActive, isDisabled = false) {
      return {
        class: 'mr-2',
        width: '32px',
        height: '32px',
        rounded: 'circle',
        elevation: 10,
        disabled: isDisabled,
        color: isActive ? '#059669' : isDisabled ? '#2d2d2d' : '#2d2d2d',
        variant: isActive ? 'flat' : 'plain',
        border: `1px solid ${isActive ? '#10B981' : '#2d2d2d'}`,
        'aria-disabled': isDisabled,
      }
    },

    toggleImageInput() {
      this.showImageInput = !this.showImageInput
      this.imageUrl = ''
      this.isLoading = false
    },

    async addImage() {
      if (!this.imageUrl) return
      this.isLoading = true

      try {
        await new Promise((resolve) => setTimeout(resolve, 2000))

        this.editor.chain().focus().setImage({ src: this.imageUrl }).run()
        this.toggleImageInput()
      } catch (error) {
        console.error('Error adding image:', error)
      } finally {
        this.isLoading = false
      }
    },

    handleFileUpload(event) {
      const file = event.target.files[0]
      if (file) {
        const reader = new FileReader()
        reader.onload = (e) => {
          this.imageUrl = e.target.result
        }
        reader.readAsDataURL(file)
      }
    },

    toggleLinkInput() {
      this.showLinkInput = !this.showLinkInput
      this.linkUrl = ''
      this.isLoading = false
    },

    toggleLink() {
      if (this.editor.isActive('link')) {
        this.editor.chain().focus().unsetLink().run()
      } else {
        this.showLinkInput = true
        this.linkUrl = ''
      }
    },

    applyLink() {
      if (this.linkUrl) {
        this.editor
          .chain()
          .focus()
          .setLink({
            href: this.linkUrl,
            class: 'text-emerald-500 underline cursor-pointer',
          })
          .run()
      }
      this.showLinkInput = false
    },

    cancelLink() {
      this.showLinkInput = false
    },
  },

  components: {
    FwbDropdown,
    EditorContent,
  },

  props: {
    modelValue: {
      type: String,
      default: '',
    },
  },

  data() {
    return {
      editor: null,
      showImageInput: false,
      imageUrl: '',
      isLoading: false,
      showLinkInput: false,
      linkUrl: '',
      linkInputStyle: {
        top: '0px',
        left: '0px',
      },
    }
  },

  watch: {
    modelValue(value) {
      const isSame = this.editor.getHTML() === value
      if (isSame) return
      this.editor.commands.setContent(value, false)
    },
  },

  mounted() {
    this.editor = new Editor({
      extensions: [
        Document,
        Paragraph,
        Text,
        Link.configure({
          openOnClick: true,
          linkOnPaste: true,
          protocols: ['ftp', 'mailto'],
          defaultProtocol: 'https',
          validate: (href) => /^https?:\/\//.test(href),
        }),
        Heading.configure({
          levels: [1, 2, 3, 4, 5, 6],
        }),
        Dropcursor,
        Image.configure({
          class:
            'block h-auto my-6 max-w-full outline-none ProseMirror-selected node:outline-[3px] ProseMirror-selected node:outline-purple-500',
          inline: true,
          allowBase64: true,
        }),
        CodeBlockLowlight.extend({
          addNodeView() {
            return VueNodeViewRenderer(CodeBlockComponent)
          },
        }).configure({ lowlight }),
        Color.configure({ types: [TextStyle.name, ListItem.name] }),
        TextStyle.configure({
          types: [ListItem.name],
          styles: {
            bold: 'font-weight: bold;',
            italic: 'font-style: italic;',
            strike: 'text-decoration: line-through;',
            underline: 'text-decoration: underline;',
            ListItem: 'list-style-type: none;',
          },
        }),
        StarterKit,
      ],
      content: this.modelValue,
      editorProps: {
        attributes: {
          class:
            'prose prose-sm sm:prose-base lg:prose-lg xl:prose-2xl mt-2 p-4 border-2 border-b-2 border-e-md border-l-2 border-r-2 rounded focus:outline-none resize-none overflow-hidden',
          style:
            'min-height: 200px !important; height: auto; overflow-y: auto;',
          placeholder: 'Write something...',
        },
      },
      autofocus: true,
      editable: true,
      injectCSS: true,
      onUpdate: () => {
        this.$emit('update:modelValue', this.editor.getHTML())
      },
    })
  },

  beforeUnmount() {
    this.editor.destroy()
  },
}
</script>

<style lang="scss">
/* Basic editor styles */
.tiptap {
  :first-child {
    margin-top: 0;
  }

  pre {
    background: var(--black);
    border-radius: 0.5rem;
    color: var(--white);
    font-family: 'JetBrainsMono', monospace;
    margin: 1.5rem 0;
    padding: 0.75rem 1rem;

    code {
      background: none;
      color: inherit;
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
      color: #f98181;
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

    .hljs-title,
    .hljs-section {
      color: #faf594;
    }

    .hljs-keyword,
    .hljs-selector-tag {
      color: #70cff8;
    }

    .hljs-emphasis {
      font-style: italic;
    }

    .hljs-strong {
      font-weight: 700;
    }
  }
}
</style>
