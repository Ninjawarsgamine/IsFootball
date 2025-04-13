<template>
    <HeaderComponent/>

    <div class="main-competitions-container">
        <h3>Estas son las competiciones más populares</h3>
        <div class="" v-for="competition in mainCompetitions" :key="competition.id">
            <img :src="competition.logo">
            <span>{{ competition.name }}</span>
        </div>
    </div>
</template>

<script setup>
    import { ref, onMounted } from 'vue';
    import HeaderComponent from './HeaderComponent.vue';
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
