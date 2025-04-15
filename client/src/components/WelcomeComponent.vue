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
                <a :href="'/competitions?id='+competition.id">
                    <img class="img-fluid" :src="competition.logo"/>
                </a>
                </div>
            </div>
     
        </div>
   </div>
</template>

<script setup>
    import { ref, onMounted } from 'vue';
    const ids= [39,140,135,78,61, 2, 4];
    //Ids de las principales competiciones.
    const mainCompetitions=ref([]);

    const getCompetitions=async()=>{
        try{
            const response=await fetch('/api/competitions/by-ids',{
                    method:'POST',
                    headers:{
                        'Content-Type':'application/json'
                    },
                    body: JSON.stringify(ids)
                }
            );
            if(response.ok){
                const data=await response.json();
                console.log(data)
                mainCompetitions.value=data;
            }else{
                console.log('No se han podido obtener las competiciones.');
                console.log(response)
            }
        }catch(error){
            console.log(error);
        }
    };
    onMounted(async()=>{
        getCompetitions();
    })
</script>
