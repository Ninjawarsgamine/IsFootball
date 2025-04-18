<template>
  <div class="header-container">
    <router-link to="/">
      <img class="header-container__logo img-fluid" :src="require('@/assets/logo.png')" 
        alt="Logo de IsFootball"/>
    </router-link>
    <ul class="header-container__menu-options">
      <li><router-link to="/competitions">Competiciones</router-link></li>
      <li><router-link to="/teams">Equipos</router-link></li>
      <li><router-link to="/players">Jugadores</router-link></li>
      <li><router-link to="/matches">Partidos</router-link></li>
    </ul>
  </div>
</template>
