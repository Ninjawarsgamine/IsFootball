<template>
  <div class="header-container">
    <a href="/">
      <img class="header-container__logo img-fluid" :src="require('@/assets/logo.png')" 
        alt="Logo de IsFootball"/>
    </a>
    <ul class="header-container__menu-options">
        <a href="competitions"><li>Competiciones</li></a>
        <a href="teams"><li>Equipos</li></a>
        <a href="players"><li>Jugadores</li></a>
        <a href="matches"><li>Partidos</li></a>
    </ul>
  </div>
</template>
