<template>
    <div class="competition-info-container">
        
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
                console.log(data);
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