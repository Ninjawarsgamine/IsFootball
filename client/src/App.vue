<template>
  <div class="d-flex flex-column min-vh-100">
    <HeaderComponent/>
      <LaodingComponent v-if="loadingStore.isLoading"/>
      <router-view class="flex-fill" />
    <FooterComponent />
  </div>
</template>

<script setup>
  import HeaderComponent from './components/HeaderComponent.vue';
  import FooterComponent from './components/FooterComponent.vue';
  import LaodingComponent from './components/LaodingComponent.vue';
  import { useLoadingStore } from '@/stores/useLoadingStore';

  const loadingStore = useLoadingStore();
</script>
