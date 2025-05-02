<template>
    <div>
        Aquí va a ir la información de los equipos.
    </div>
</template>

<script setup>
import { useFetch } from '@/composables/useFetch';
import { onMounted, ref } from 'vue';
import { useRoute } from 'vue-router';

    const route=useRoute();
    const teamId=route.params.id;
    const teamName=route.params.name;
    const team=ref([]);
    const getTeamInfo=async()=>{
        const {data, error}=await useFetch(`/api/teamByNameAndId/${teamName}/${teamId}`);
        console.log(`/api/teamByNameAndId/${teamName}/${teamId}`);
        if(error){
            console.log("No se han encontrado información del equipo con ID: "+teamId);
        }
        console.log(data.value);
        team.value=data.value;
        console.log(team.value);
    }
    
    onMounted(async()=>{
         await getTeamInfo();
    });
</script>