<template>
    <div class="welcome-container">
        <div class="welcome-container__app-description-container">
            <span>Bienvenido a IsFootball, el mejor lugar para descubrir estadísticas de equipos, jugadores, competiciones y partidos. </span>
            <span>Nuestro objetivo es proporcionarte toda la información actualizada para que puedas disfrutar del fútbol de manera más detallada.</span>
        </div>
        <div class="welcome-container__main-competitions-container">
            <h3>Estas son las competiciones más populares</h3>
            <div class="welcome-container__main-competitions-container__competitions" >
                <div class="welcome-container__main-competitions-container__competitions__competition" 
                v-for="competition in mainCompetitions" :key="competition.id">
                <router-link :to="`/competitions/${competition.id}`">
                    <img class="img-fluid" :src="competition.logo"/>
                </router-link>
                </div>
            </div>
        </div>
   </div>
</template>

<script setup>
    import { useFetch } from '@/composables/useFetch';
    import { ref, onMounted } from 'vue';
    const ids= [39,140,135,78,61,2];
    //Ids de las principales competiciones.
    const mainCompetitions=ref([]);

    const getCompetitions=async()=>{
        const {data,error}=await useFetch('/api/competitions/by-ids', 'POST', ids);

        if(error){
            console.log("Error al devolver las competiciones: "+error.value);
            return;
        }
        mainCompetitions.value=data.value;
        console.log(mainCompetitions.value)
    };

    onMounted(async()=>{
        getCompetitions();
    })
</script>
