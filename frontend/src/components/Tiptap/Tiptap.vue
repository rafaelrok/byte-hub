<template>
  <div
    v-if="editor"
    class="container mx-auto my-4 p-4 bg-white rounded-lg shadow-lg border-b-2 border-e-md border-l-2 border-r-2 border-2"
  >
    <div
      class="flex items-center overflow-x-auto space-x-2 py-2 border-b-2 border-e-md border-gray-600"
    >
      <!-- btn-Bold -->
      <v-btn
        @click="editor.chain().focus().toggleBold().run()"
        :disabled="!editor.can().chain().focus().toggleBold().run()"
        class="ml-2"
        v-bind="
          buttonClass(
            editor.isActive('bold'),
            !editor.can().chain().focus().toggleBold().run(),
          )
        "
        :active="editor.isActive('bold')"
        icon="fas fa-bold"
        size="x-small"
      >
      </v-btn>
      <!-- btn-Italic -->
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
        size="x-small"
      >
      </v-btn>
      <!-- btn-Underline -->
      <v-btn
        @click="editor.chain().focus().toggleUnderline().run()"
        :disabled="!editor.can().chain().focus().toggleUnderline().run()"
        v-bind="
          buttonClass(
            editor.isActive('underline'),
            !editor.can().chain().focus().toggleItalic().run(),
          )
        "
        :active="editor.isActive('underline')"
        icon="fas fa-underline"
        size="x-small"
      >
      </v-btn>
      <!-- btn-strike -->
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
        size="x-small"
      >
      </v-btn>
      <!-- btn-code -->
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
        size="x-small"
      >
      </v-btn>
      <!-- btn-link -->
      <v-btn
        @click="toggleLink"
        :disabled="!editor.can().chain().focus().toggleLink().run()"
        v-bind="buttonClass(editor.isActive('link'))"
        :icon="editor.isActive('link') ? 'fas fa-unlink' : 'fas fa-link'"
        aria-hidden="true"
        size="x-small"
      >
      </v-btn>
      <!-- btn-image -->
      <v-btn
        @click="toggleImageInput"
        v-bind="buttonClass(editor.isActive('image'))"
        :icon="editor.isActive('image') ? 'fas fa-unImage' : 'fas fa-image'"
        aria-hidden="true"
        size="x-small"
      >
      </v-btn>
      <!-- btn-video -->
      <v-btn
        @click="toggleLink"
        :disabled="!editor.can().chain().focus().toggleLink().run()"
        v-bind="buttonClass(editor.isActive('link'))"
        :icon="editor.isActive('link') ? 'fas fa-youtube' : 'fas fa-youtube'"
        aria-hidden="true"
        size="x-small"
      >
      </v-btn>
      <!-- btn-eraser -->
      <v-btn
        @click="editor.chain().focus().unsetAllMarks().run()"
        v-bind="buttonClass(false)"
        icon="fas fa-eraser"
        size="x-small"
      >
      </v-btn>
      <!-- btn-trash -->
      <v-btn
        @click="editor.chain().focus().clearNodes().run()"
        v-bind="buttonClass(false)"
        :active="editor.isActive('trash')"
        icon="fas fa-trash-alt"
        size="x-small"
      >
      </v-btn>
      <!-- btn-paragraph -->
      <v-btn
        @click="editor.chain().focus().setParagraph().run()"
        v-bind="buttonClass(editor.isActive('paragraph'))"
        icon="fas fa-paragraph"
        size="x-small"
      >
      </v-btn>
      <!-- Menu de fontes atualizado -->
      <v-menu open-on-hover>
        <template v-slot:activator="{ props }">
          <v-btn
            v-bind="props"
            class="flex items-center justify-center py-2 px-2"
            size="x-small"
            icon="fas fa-font"
            width="auto"
            height="26px"
            rounded="rounded"
            :color="'#2d2d2d'"
            variant="plain"
            elevation="10"
            border
          >
            <span :style="{ fontFamily: currentFontFamily || 'Inter' }">
              {{ currentFontFamily || 'Aa' }}
            </span>
          </v-btn>
        </template>

        <v-list>
          <v-list-item v-for="font in fonts" :key="font.name">
            <v-list-item-title>
              <v-btn
                @click="setFontFamily(font.name)"
                v-bind="{
                  'font-active': editor.isActive('textStyle', {
                    fontFamily: font.name,
                  }),
                }"
                :style="{ fontFamily: font.name }"
              >
                {{ font.label }}
              </v-btn>
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <!-- Menu de cabecalhos Headings -->
      <v-menu open-on-hover>
        <template v-slot:activator="{ props }">
          <v-btn
            v-bind="props"
            class="mx-1 flex items-center justify-center"
            size="x-small"
            icon="fas fa-heading"
            width="24px"
            height="24px"
            rounded="circle"
            :color="'#2d2d2d'"
            variant="plain"
            elevation="10"
            border
          >
          </v-btn>
        </template>

        <v-list>
          <v-list-item v-for="heading in headings" :key="heading.level">
            <v-list-item-title>
              <v-btn
                @click="toggleHeading(heading.level)"
                v-bind="editor.isActive('heading', { level: heading.level })"
                style="
                  width: 100%;
                  justify-content: start;
                  text-transform: none;
                  padding: 8px 16px;
                "
              >
                <i class="fas fa-heading"></i>{{ heading.label }}
              </v-btn>
            </v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
      <!-- Menu de text align -->
      <v-menu open-on-hover>
        <template v-slot:activator="{ props }">
          <v-btn
            v-bind="props"
            class="mx-1 flex items-center justify-center"
            size="x-small"
            icon="fas fa-align-left"
            width="24px"
            height="24px"
            rounded="circle"
            :color="
              editor.isActive('textAlign', { textAlign: currentTextAlign })
                ? '#059669'
                : '#2d2d2d'
            "
            :icon="getCurrentAlignIcon"
            variant="plain"
            elevation="10"
            border
          >
          </v-btn>
        </template>

        <v-list>
          <v-list-item>
            <v-btn
              v-for="align in textAlignOptions"
              :key="align"
              @click="setTextAlign(align)"
              :class="{ 'is-active': editor.isActive({ textAlign: align }) }"
              class="mx-1 my-1"
              :icon="getAlignIcon(align)"
              size="x-small"
            >
            </v-btn>
          </v-list-item>
        </v-list>
      </v-menu>
      <!-- btn-bulletList -->
      <v-btn
        @click="editor.chain().focus().toggleBulletList().run()"
        v-bind="buttonClass(editor.isActive('bulletList'))"
        :active="editor.isActive('bulletList')"
        icon="fas fa-list-ul"
        size="x-small"
      >
      </v-btn>
      <!-- btn-orderedList -->
      <v-btn
        @click="editor.chain().focus().toggleOrderedList().run()"
        v-bind="buttonClass(editor.isActive('orderedList'))"
        icon="fas fa-list-ol"
        size="x-small"
      >
      </v-btn>
      <!-- btn-blockquote -->
      <v-btn
        @click="editor.chain().focus().toggleBlockquote().run()"
        v-bind="buttonClass(editor.isActive('blockquote'))"
        icon="fas fa-quote-right"
        size="x-small"
      >
      </v-btn>
      <!-- btn-HorizontalRul -->
      <v-btn
        @click="editor.chain().focus().setHorizontalRule().run()"
        v-bind="buttonClass(false)"
        icon="fas fa-minus"
        size="x-small"
      >
      </v-btn>
      <!-- btn-HardBreak -->
      <v-btn
        @click="editor.chain().focus().setHardBreak().run()"
        v-bind="buttonClass(false)"
        icon="fas fa-level-down-alt"
        size="x-small"
      >
      </v-btn>
      <!-- btn-undo -->
      <v-btn
        @click="editor.chain().focus().undo().run()"
        :disabled="!editor.can().chain().focus().undo().run()"
        v-bind="buttonClass(false, !editor.can().chain().focus().undo().run())"
        icon="fas fa-undo-alt"
        size="x-small"
      >
      </v-btn>
      <!-- btn-redo -->
      <v-btn
        @click="editor.chain().focus().redo().run()"
        :disabled="!editor.can().chain().focus().redo().run()"
        v-bind="buttonClass(false, !editor.can().chain().focus().redo().run())"
        icon="fas fa-redo-alt"
        size="x-small"
      >
      </v-btn>
      <!-- Menu text-color-picker -->
      <v-menu>
        <template v-slot:activator="{ props }">
          <v-btn
            v-bind="props"
            class="mx-1 flex items-center justify-center"
            size="x-small"
            :icon="
              editor.isActive('textStyle', { color: activeColor })
                ? 'fas fa-paint-brush'
                : 'fas fa-paint-brush'
            "
            width="24px"
            height="24px"
            rounded="circle"
            :color="
              editor.isActive('textStyle', { color: activeColor })
                ? activeColor
                : 'grey darken-2'
            "
            variant="plain"
            elevation="10"
            border
          >
          </v-btn>
        </template>

        <v-card>
          <v-color-picker
            v-model="activeColor"
            @update:model-value="updateColor"
            dot-size="20"
            swatches-max-height="100"
            hide-inputs
          ></v-color-picker>
        </v-card>
      </v-menu>
      <!-- Menu text-highlight -->
      <v-menu>
        <template v-slot:activator="{ props }">
          <v-btn
            v-bind="props"
            class="mx-1 flex items-center justify-center"
            size="x-small"
            :icon="
              editor.isActive('highlight')
                ? 'fas fa-highlighter'
                : 'fas fa-highlighter'
            "
            width="24px"
            height="24px"
            rounded="circle"
            :color="
              editor.isActive('highlight') ? activeColor : 'grey darken-2'
            "
            variant="plain"
            elevation="10"
            border
          >
          </v-btn>
        </template>

        <v-card>
          <v-color-picker
            v-model="activeColor"
            @update:model-value="updateHighlight"
            dot-size="20"
            swatches-max-height="100"
            hide-inputs
          ></v-color-picker>
        </v-card>
      </v-menu>
      <!-- btn-undo-highlight-and-textColor -->
      <v-btn
        @click="
          editor.chain().focus().unsetHighlight().run() &&
            editor.chain().focus().unsetColor().run()
        "
        :disabled="!editor.can().chain().focus().unsetHighlight().run()"
        v-bind="
          buttonClass(
            editor.isActive('highlight'),
            !editor.can().chain().focus().unsetHighlight().run(),
          )
        "
        :active="editor.isActive('highlight')"
        icon="fas fa-droplet-slash"
        size="x-small"
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
    <!-- Input para o link -->
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
    <!-- Input para o youtube -->
    <div
      v-if="showLinkInput"
      :style="linkInputStyle"
      class="flex mt-2 p-2 bg-white shadow-lg border rounded-lg"
    >
      <input
        v-model="linkUrl"
        type="text"
        placeholder="Enter Video URL"
        class="p-2 mr-2 text-sm border rounded-lg focus:outline-none focus:ring-emerald-500 focus:border-emerald-500"
        style="width: 800px; height: 30px"
      />
      <v-btn
        @click="addVideo"
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
  </div>

  <template>
    <div v-if="editor">
      <bubble-menu
        class="container mx-auto my-4 p-4 bg-white rounded-lg shadow-lg border-b-2 border-e-md border-l-2 border-r-2 border-2"
        :tippy-options="{ duration: 100 }"
        :editor="editor"
        style="width: 600px"
      >
        <v-btn
          @click="editor.chain().focus().toggleBold().run()"
          :disabled="!editor.can().chain().focus().toggleBold().run()"
          class="ml-2"
          v-bind="
            buttonClass(
              editor.isActive('bold'),
              !editor.can().chain().focus().toggleBold().run(),
            )
          "
          :active="editor.isActive('bold')"
          icon="fas fa-bold"
          size="x-small"
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
          size="x-small"
        >
        </v-btn>
        <!-- btn-Underline -->
        <v-btn
          @click="editor.chain().focus().toggleUnderline().run()"
          :disabled="!editor.can().chain().focus().toggleUnderline().run()"
          v-bind="
            buttonClass(
              editor.isActive('underline'),
              !editor.can().chain().focus().toggleItalic().run(),
            )
          "
          :active="editor.isActive('underline')"
          icon="fas fa-underline"
          size="x-small"
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
          size="x-small"
        >
        </v-btn>
        <v-btn
          @click="toggleLink"
          :disabled="!editor.can().chain().focus().toggleLink().run()"
          v-bind="buttonClass(editor.isActive('link'))"
          :icon="editor.isActive('link') ? 'fas fa-unlink' : 'fas fa-link'"
          aria-hidden="true"
          size="x-small"
        >
        </v-btn>
        <!-- Menu de fontes atualizado -->
        <v-menu open-on-hover>
          <template v-slot:activator="{ props }">
            <v-btn
              v-bind="props"
              class="mx-1 flex items-center justify-center py-2 px-2"
              size="x-small"
              icon="fas fa-font"
              width="auto"
              height="32px"
              rounded="rounded"
              :color="'#2d2d2d'"
              variant="plain"
              elevation="10"
              border
            >
              <span :style="{ fontFamily: currentFontFamily || 'Inter' }">
                {{ currentFontFamily || 'Aa' }}
              </span>
            </v-btn>
          </template>

          <v-list>
            <v-list-item v-for="font in fonts" :key="font.name">
              <v-list-item-title>
                <v-btn
                  @click="setFontFamily(font.name)"
                  v-bind="{
                    'font-active': editor.isActive('textStyle', {
                      fontFamily: font.name,
                    }),
                  }"
                  :style="{ fontFamily: font.name }"
                >
                  {{ font.label }}
                </v-btn>
              </v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>

        <!-- Menu de cabeçalhos Headings -->
        <v-menu open-on-hover>
          <template v-slot:activator="{ props }">
            <v-btn
              v-bind="props"
              class="mx-1 flex items-center justify-center"
              size="x-small"
              icon="fas fa-heading"
              width="32px"
              height="32px"
              rounded="circle"
              :color="'#2d2d2d'"
              variant="plain"
              elevation="10"
              border
            >
            </v-btn>
          </template>

          <v-list>
            <v-list-item v-for="heading in headings" :key="heading.level">
              <v-list-item-title>
                <v-btn
                  @click="toggleHeading(heading.level)"
                  v-bind="editor.isActive('heading', { level: heading.level })"
                  style="
                    width: 100%;
                    justify-content: start;
                    text-transform: none;
                    padding: 8px 16px;
                  "
                >
                  <i class="fas fa-heading"></i>{{ heading.label }}
                </v-btn>
              </v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>

        <!-- Menu de text align -->
        <v-menu open-on-hover>
          <template v-slot:activator="{ props }">
            <v-btn
              v-bind="props"
              class="mx-1 flex items-center justify-center"
              size="x-small"
              icon="fas fa-align-left"
              width="24px"
              height="24px"
              rounded="circle"
              :color="
                editor.isActive('textAlign', { textAlign: currentTextAlign })
                  ? '#059669'
                  : '#2d2d2d'
              "
              :icon="getCurrentAlignIcon"
              variant="plain"
              elevation="10"
              border
            >
            </v-btn>
          </template>

          <v-list>
            <v-list-item>
              <v-btn
                v-for="align in textAlignOptions"
                :key="align"
                @click="setTextAlign(align)"
                :class="{ 'is-active': editor.isActive({ textAlign: align }) }"
                class="mx-1 my-1"
                :icon="getAlignIcon(align)"
                size="x-small"
              >
              </v-btn>
            </v-list-item>
          </v-list>
        </v-menu>

        <v-btn
          @click="editor.chain().focus().toggleBulletList().run()"
          v-bind="buttonClass(editor.isActive('bulletList'))"
          :active="editor.isActive('bulletList')"
          icon="fas fa-list-ul"
          size="x-small"
        >
        </v-btn>
        <v-btn
          @click="editor.chain().focus().toggleOrderedList().run()"
          v-bind="buttonClass(editor.isActive('orderedList'))"
          icon="fas fa-list-ol"
          size="x-small"
        >
        </v-btn>
        <v-btn
          @click="editor.chain().focus().toggleBlockquote().run()"
          v-bind="buttonClass(editor.isActive('blockquote'))"
          icon="fas fa-quote-right"
          size="x-small"
        >
        </v-btn>
        <!-- btn-undo -->
        <v-btn
          @click="editor.chain().focus().undo().run()"
          :disabled="!editor.can().chain().focus().undo().run()"
          v-bind="
            buttonClass(false, !editor.can().chain().focus().undo().run())
          "
          icon="fas fa-undo-alt"
          size="x-small"
        >
        </v-btn>
        <!-- Menu text-color -->
        <v-menu>
          <template v-slot:activator="{ props }">
            <v-btn
              v-bind="props"
              class="mx-1 flex items-center justify-center"
              size="x-small"
              :icon="
                editor.isActive('textStyle', { color: activeColor })
                  ? 'fas fa-paint-brush'
                  : 'fas fa-paint-brush'
              "
              width="24px"
              height="24px"
              rounded="circle"
              :color="
                editor.isActive('textStyle', { color: activeColor })
                  ? activeColor
                  : 'grey darken-2'
              "
              variant="plain"
              elevation="10"
              border
            >
            </v-btn>
          </template>

          <v-card>
            <v-color-picker
              v-model="activeColor"
              @update:model-value="updateColor"
              dot-size="20"
              swatches-max-height="100"
              hide-inputs
            ></v-color-picker>
          </v-card>
        </v-menu>
        <!-- Menu text-highlight -->
        <v-menu>
          <template v-slot:activator="{ props }">
            <v-btn
              v-bind="props"
              class="mx-1 flex items-center justify-center"
              size="x-small"
              :icon="
                editor.isActive('highlight')
                  ? 'fas fa-highlighter'
                  : 'fas fa-highlighter'
              "
              width="24px"
              height="24px"
              rounded="circle"
              :color="
                editor.isActive('highlight') ? activeColor : 'grey darken-2'
              "
              variant="plain"
              elevation="10"
              border
            >
            </v-btn>
          </template>

          <v-card>
            <v-color-picker
              v-model="activeColor"
              @update:model-value="updateHighlight"
              dot-size="20"
              swatches-max-height="100"
              hide-inputs
            ></v-color-picker>
          </v-card>
        </v-menu>
        <!-- btn-undo-highlight-and-textColor -->
        <v-btn
          @click="
            editor.chain().focus().unsetHighlight().run() &&
              editor.chain().focus().unsetColor().run()
          "
          :disabled="!editor.can().chain().focus().unsetHighlight().run()"
          v-bind="
            buttonClass(
              editor.isActive('highlight'),
              !editor.can().chain().focus().unsetHighlight().run(),
            )
          "
          :active="editor.isActive('highlight')"
          icon="fas fa-droplet-slash"
          size="x-small"
        >
        </v-btn>
      </bubble-menu>

      <floating-menu
        class="floating-menu"
        :tippy-options="{ duration: 100 }"
        :editor="editor"
      ></floating-menu>
    </div>
  </template>

  <editor-content :editor="editor" />
</template>

<script>
import { Color } from '@tiptap/extension-color'
import ListItem from '@tiptap/extension-list-item'
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
import Highlight from '@tiptap/extension-highlight'
import Underline from '@tiptap/extension-underline'
import CodeBlockLowlight from '@tiptap/extension-code-block-lowlight'
import Youtube from '@tiptap/extension-youtube'

import {
  Editor,
  EditorContent,
  VueNodeViewRenderer,
  FloatingMenu,
  BubbleMenu,
} from '@tiptap/vue-3'
import { FwbDropdown } from 'flowbite-vue'

import css from 'highlight.js/lib/languages/css'
import js from 'highlight.js/lib/languages/javascript'
import ts from 'highlight.js/lib/languages/typescript'
import html from 'highlight.js/lib/languages/xml'
import java from 'highlight.js/lib/languages/java'

import { all, createLowlight } from 'lowlight'
import CodeBlockComponent from './CodeBlock/CodeBlock.vue'

const lowlight = createLowlight(all)

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
        width: '24px',
        height: '24px',
        rounded: 'circle',
        elevation: 10,
        disabled: isDisabled,
        color: isActive ? '#059669' : isDisabled ? '#2d2d2d' : '#2d2d2d',
        variant: isActive ? 'flat' : 'plain',
        border: `1px solid ${isActive ? '#10B981' : '#2d2d2d'}`,
        'aria-disabled': isDisabled,
      }
    },

    updateColor(color) {
      this.activeColor = color
      this.editor.chain().focus().setColor(color).run()
    },

    updateHighlight(color) {
      this.activeColor = color
      this.editor.chain().focus().toggleHighlight({ color }).run()
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

    setFontFamily(fontFamily) {
      this.editor.chain().focus().setFontFamily(fontFamily).run()
      this.currentFontFamily = fontFamily
    },

    toggleHeading(level) {
      this.editor.chain().focus().toggleHeading({ level }).run()
    },

    setTextAlign(align) {
      this.editor.chain().focus().setTextAlign(align).run()
      this.currentTextAlign = align
    },

    getCurrentAlignIcon() {
      const currentAlign = this.textAlignOptions.find((align) =>
        this.editor.isActive({ textAlign: align }),
      )
      return currentAlign
        ? this.getAlignIcon(currentAlign)
        : 'fas fa-align-left'
    },

    getAlignIcon(align) {
      switch (align) {
        case 'left':
          return 'fas fa-align-left'
        case 'center':
          return 'fas fa-align-center'
        case 'right':
          return 'fas fa-align-right'
        case 'justify':
          return 'fas fa-align-justify'
        default:
          return 'fas fa-align-left'
      }
    },

    addVideo() {
      const url = prompt('Enter YouTube URL')

      this.editor.commands.setYoutubeVideo({
        src: url,
        width: Math.max(320, parseInt(this.width, 10)) || 640,
        height: Math.max(180, parseInt(this.height, 10)) || 480,
      })
    },
  },

  components: {
    FwbDropdown,
    EditorContent,
    BubbleMenu,
    FloatingMenu,
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
      currentFontFamily: 'Inter',
      currentTextAlign: 'left',
      fonts: [
        { name: 'Inter', label: 'Inter' },
        { name: 'Comic Sans MS, Comic Sans', label: 'Comic Sans' },
        { name: 'serif', label: 'Serif' },
        { name: 'monospace', label: 'Monospace' },
        { name: 'cursive', label: 'Cursive' },
      ],
      headings: [
        { level: 1, label: '1' },
        { level: 2, label: '2' },
        { level: 3, label: '3' },
        { level: 4, label: '4' },
        { level: 5, label: '5' },
        { level: 6, label: '6' },
      ],
      textAlignOptions: ['left', 'center', 'right', 'justify'],
      textAligns: [
        { name: 'left', icon: 'fas fa-align-left' },
        { name: 'center', icon: 'fas fa-align-center' },
        { name: 'right', icon: 'fas fa-align-right' },
        { name: 'justify', icon: 'fas fa-align-justify' },
      ],
      activeColor: '#059669',
      colors: [
        { number: 1, color: '#ffffff' },
        { number: 2, color: '#37ff00' },
        { number: 3, color: '#ff0000' },
        { number: 4, color: '#22D3EE' },
        { number: 5, color: '#ff00f2' },
        { number: 6, color: '#ffd400' },
        { number: 7, color: '#4F46E5' },
        { number: 8, color: '#ff7500' },
        { number: 9, color: '#434343' },
        { number: 10, color: '#0700ff' },
      ],
      width: '640',
      height: '480',
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
        Color,
        FontFamily.configure({
          types: ['textStyle'],
        }),
        TextAlign.configure({
          types: ['heading', 'paragraph'],
          alignments: ['left', 'center', 'right', 'justify'],
          defaultAlignment: 'left',
        }),
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

    this.editor.on('selectionUpdate', () => {
      const fontFamily = this.editor.getAttributes('textStyle').fontFamily
      this.currentFontFamily = fontFamily || 'Inter'
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

  ul,
  ol {
    padding: 0 1rem;
    margin: 1.25rem 1rem 1.25rem 0.4rem;

    li p {
      margin-top: 0.25em;
      margin-bottom: 0.25em;
    }
  }

  /* Heading styles */
  h1,
  h2,
  h3,
  h4,
  h5,
  h6 {
    line-height: 1.1;
    margin-top: 2.5rem;
    text-wrap: pretty;
  }

  h1,
  h2 {
    margin-top: 3.5rem;
    margin-bottom: 1.5rem;
  }

  h1 {
    font-size: 1.4rem;
  }

  h2 {
    font-size: 1.2rem;
  }

  h3 {
    font-size: 1.1rem;
  }

  h4,
  h5,
  h6 {
    font-size: 1rem;
  }

  /* Code and preformatted text styles */
  code {
    background-color: var(--purple-light);
    border-radius: 0.4rem;
    color: var(--black);
    font-size: 0.85rem;
    padding: 0.25em 0.3em;
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
  blockquote {
    border-left: 3px solid var(--gray-3);
    margin: 1.5rem 0;
    padding-left: 1rem;
  }

  hr {
    border: none;
    border-top: 1px solid var(--gray-2);
    margin: 2rem 0;
  }

  .font-button {
    width: 100%;
    justify-content: start;
    text-transform: none;
    padding: 8px 16px;
  }

  .font-active {
    background-color: rgba(0, 0, 0, 0.1);
  }

  /* Bubble menu */
  .bubble-menu {
    background-color: var(--white);
    border: 1px solid var(--gray-1);
    border-radius: 0.7rem;
    box-shadow: var(--shadow);
    display: flex;
    padding: 0.2rem;

    button {
      background-color: unset;

      &:hover {
        background-color: var(--gray-3);
      }

      &.is-active {
        background-color: var(--purple);

        &:hover {
          background-color: var(--purple-contrast);
        }
      }
    }
  }

  /* Floating menu */
  .floating-menu {
    display: flex;
    background-color: var(--gray-3);
    padding: 0.1rem;
    border-radius: 0.5rem;

    button {
      background-color: unset;
      padding: 0.275rem 0.425rem;
      border-radius: 0.3rem;

      &:hover {
        background-color: var(--gray-3);
      }

      &.is-active {
        background-color: var(--white);
        color: var(--purple);

        &:hover {
          color: var(--purple-contrast);
        }
      }
    }
  }
  .mark {
    background-color: #3c763d;
    border-radius: 2rem;
    box-decoration-break: clone;
    padding: 0.1rem 0.3rem;
  }
  .highlight {
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
  div[data-youtube-video] {
    cursor: move;
    padding-right: 1.5rem;

    iframe {
      border: 0.5rem solid var(--black-contrast);
      display: block;
      min-height: 200px;
      min-width: 200px;
      outline: 0px solid transparent;
    }

    &.ProseMirror-selectednode iframe {
      outline: 3px solid var(--purple);
      transition: outline 0.15s;
    }
  }
}
</style>
