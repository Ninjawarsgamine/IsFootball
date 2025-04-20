<template>
    <div class="route-error-container">
      <h1>Vaya... parece que nos hemos quedado sin balón</h1>
      <img :src="require('@/assets/error404.png')" class="img-fluid">
      <span>La página que está buscando no existe o ya no se encuentra disponible.</span>
      <router-link to="/" class="btn btn-outline-primary mt-4">
        Volver a la página principal
      </router-link>
    </div>
  </template>