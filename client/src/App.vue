<template>
  <div class="d-flex flex-column min-vh-100">
    <HeaderComponent/>
    <router-view class="flex-fill" />
    <FooterComponent />
  </div>
</template>

<script setup>
  import HeaderComponent from './components/HeaderComponent.vue';
  import FooterComponent from './components/FooterComponent.vue';
</script>
