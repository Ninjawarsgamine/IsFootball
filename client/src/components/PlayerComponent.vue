<template>
    <div>
        <h1>Datos del jugador</h1>
    </div>
</template>

<script setup>
    import { useFetch } from '@/composables/useFetch';
    import { ref, onMounted } from 'vue';
    import { useRoute } from 'vue-router';

    const route=useRoute();
    const playerId=route.params.id;

    const player=ref(null);

    const getPlayerById=async()=>{
        const {data,error}=await useFetch(`/api/players/${playerId}`);
        if(error){
            console.log("No se ha encontrado ningún jugador con ID: "+playerId);
        }
        player.value=data.value;
    }

    onMounted(async()=>{
        await getPlayerById();
    });
</script>