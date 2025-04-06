<template>
  <div>
    <h1>Información del Equipo</h1>
    <div v-if="team">
      <h2>{{ team.name }}</h2>
      <p><strong>País:</strong> {{ team.country }}</p>
      <p><strong>Estadio:</strong> {{ team.stadium }}</p>
      <img :src="team.logo" alt="Logo del equipo" width="100" />
    </div>
    <p v-if="loading">Cargando información...</p>
    <p v-if="error" style="color: red;">Error al cargar la información: {{ error }}</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';

const team = ref(null);
const loading = ref(true);
const error = ref(null);

const apiKey = process.env.VUE_APP_API_KEY;
const apiHost = process.env.VUE_APP_API_HOST;

const teamId = 33;
const url = `${apiHost}/teams?id=${teamId}`;

onMounted(async () => {
  try {
    const response = await fetch(url, {
      method: 'GET',
      headers: {
        'X-RapidAPI-Host': 'v3.football.api-sports.io',
        'X-RapidAPI-Key': apiKey,
      },
    });

    if (!response.ok) {
      throw new Error('Error al obtener la información del equipo');
    }

    const data = await response.json();
    if (data.response && data.response.length > 0) {
      team.value = data.response[0].team;
    } else {
      throw new Error('No se encontró información para el equipo especificado');
    }
  } catch (err) {
    error.value = err.message;
  } finally {
    loading.value = false;
  }
});

</script>

<style scoped>
h1 {
  font-size: 2rem;
}

h2 {
  font-size: 1.5rem;
  margin-top: 10px;
}

img {
  margin-top: 10px;
  border-radius: 5px;
}

p {
  color: #333;
}
</style>
