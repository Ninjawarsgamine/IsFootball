<template>
    <div class="route-error-container">
      <h1>Oops... looks like we've lost the ball</h1>
      <img :src="require('@/assets/error404.png')" class="img-fluid">
      <span>The page you're looking for does not exist or is no longer available.</span>
      <router-link to="/" class="btn btn-outline-primary mt-4">
        Return to the main page
      </router-link>
    </div>
  </template>