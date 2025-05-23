<template>
    <div class="welcome-container">
        <div class="welcome-container__app-description-container">
            <span>Welcome to IsFootball, the best place to discover statistics on teams, players, competitions, and matches.</span>
            <span>Our goal is to provide you with all the updated information so you can enjoy football in more detail.</span>
        </div>
        <div class="welcome-container__main-competitions-container">
            <h3>These are the most popular competitions</h3>
            <div class="welcome-container__main-competitions-container__competitions" >
                <div class="welcome-container__main-competitions-container__competitions__competition" 
                v-for="competition in mainCompetitions" :key="competition.id">
                <router-link :to="`/competitions/${competition.id}`">
                    <img class="img-fluid" v-lazy="competition.logo"/>
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
    };
    
    onMounted(async()=>{
        getCompetitions();
    })
</script>
