<template>
  <div class="header-container">
    <router-link to="/">
      <img class="header-container__logo img-fluid" :src="require('@/assets/logo.png')" 
        alt="Logo de IsFootball"/>
    </router-link>
    <ul class="header-container__menu-options">
      <router-link to="/competitions"><li>Competitions</li></router-link>
      <router-link to="/teams"><li>Teams</li></router-link>
      <router-link to="/players"><li>Players</li></router-link>
      <router-link to="/matches"><li>Matches</li></router-link>
    </ul>
  </div>
</template>
