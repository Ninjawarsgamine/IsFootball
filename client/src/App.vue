<template>
  <div class="d-flex flex-column min-vh-100">
    <HeaderComponent/>
      <LaodingComponent v-if="loadingStore.isLoading"/>
      <router-view class="flex-fill" />
    <FooterComponent />
  </div>
</template>

<script setup>
  import HeaderComponent from './components/layout/HeaderComponent.vue';
  import FooterComponent from './components/layout/FooterComponent.vue';
  import LaodingComponent from './components/common/LaodingComponent.vue';
  import { useLoadingStore } from '@/stores/useLoadingStore';

  const loadingStore = useLoadingStore();
</script>
