<template>
  <div class="header-container">
    <router-link to="/">
      <img class="header-container__logo img-fluid" :src="require('@/assets/logo_transparente.png')" 
        alt="Logo de IsFootball"/>
    </router-link>
    <div class="header-container__menu-options">
      <router-link :to="option.route" v-for="option in options" :key="option.label"> 
       <span>{{ option.label }}</span>
      </router-link>
    </div>
  </div>
</template>

<script setup>
  const options=[
    {label: 'Competitions' , route: '/competitions'},
    {label: 'Teams', route:'/teams'},
    {label: 'Players' , route: '/players'}
  ];
</script>
