<template>
  <div class="header-container">
    <router-link to="/">
      <img class="header-container__logo img-fluid" :src="require('@/assets/logo.png')" 
        alt="Logo de IsFootball"/>
    </router-link>
    <ul class="header-container__menu-options">
      <router-link to="/competitions"><li>Competiciones</li></router-link>
      <router-link to="/teams"><li>Equipos</li></router-link>
      <router-link to="/players"><li>Jugadores</li></router-link>
      <router-link to="/matches"><li>Partidos</li></router-link>
    </ul>
  </div>
</template>
