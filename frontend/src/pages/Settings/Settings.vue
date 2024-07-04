<template>
  <div class="relative bg-emerald-600 md:pt-32 pb-32 pt-12">
    <div class="px-4 md:px-10 mx-auto w-full">
      <div>
        <div class="flex flex-wrap">
          <div class="w-full lg:w-8/12 px-4 pt-24">
            <CardSettings />
          </div>
          <div class="w-full lg:w-4/12 px-4 pt-8">
            <CardProfile />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import CardProfile from '@/components/Cards/CardProfile.vue'
import CardSettings from '@/components/Cards/CardSettings.vue'
</script>
