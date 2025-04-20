<template>
    <div class="competition-info-container" v-if="competition.country">
        <div class="competition-info-container__competition-header">
            <img class="img-fluid" :src="competition.logo" alt="Logo de la competición">
            <h1>{{ competition.name }}</h1>
            <div class="competition-info-container__competition-header__competition-country">
      
                <span>{{ competition.country.name }}</span>
            </div>
        </div>
        <div class="competition">

        </div>
    </div>
</template>

<script setup>
    import { useRoute } from 'vue-router';
    import { onMounted, ref } from "vue";
    
    const route=useRoute();
    const competitionId=route.params.id;

    const competition=ref([]);

    const getCompetitionInfo= async() =>{
        try{
            const response=await fetch(`/api/competitionAllData/${competitionId}`,{
                method:"GET",
            });
            console.log(response);
            if(response.ok){
                const data=await response.json();
                competition.value=data;
            }else{
                console.log("No se ha encontrado ninguna competición con ID "+competitionId);
            }
        }catch(error){
            console.log(error);
        }
    }

    onMounted(async()=>{
        getCompetitionInfo();
    })
</script>