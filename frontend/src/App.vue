<template>
  <!--  <v-app>-->
  <AppNavigation />
  <RouterView />
  <AppFooter />
  <!--  </v-app>-->
</template>

<script setup lang="ts">
import AppFooter from './components/Footer/AppFooter.vue'
import AppNavigation from './components/NavBar/AppNavigation.vue'
</script>
