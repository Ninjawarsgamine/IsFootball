<template>
    <div class="search-widget">
       <div class="search-widget__form">
          <form method="GET" id="player-search-form" @submit.prevent="getPlayers()">
             <input type="text" name="name"  id="playerName" v-model="playerName"
             placeholder="Search for your favorite player here" autocomplete="off"/>
             <button type="submit">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" 
                fill="none"  viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2">
                   <path stroke-linecap="round" stroke-linejoin="round" 
                   d="M21 21l-4.35-4.35M17 11a6 6 0 11-12 0 6 6 0 0112 0z"/>
                </svg>
             </button>
          </form>
       </div>
       <div class="search-widget__results">
          <div class="search-widget__results__result table-responsive" 
          v-if="players.length > 0">
              <table class="table table-striped table-hover table-sm align-middle mb-0">
                <thead class="table-dark">
                   <tr>
                      <th scope="col" class="text-center">Nacionality</th>
                      <th scope="col" class="text-center">Player</th>
                      <th>Position</th>
                   </tr>
                </thead>
                <tbody>
                   <router-link v-for="player in players"
                   :to="`/players/${player.id}`"
                   :key="player.id" custom v-slot="{navigate}">
                      <tr :key="player.id" @click="navigate">
                        <td class="text-center">
                            <span>{{player.nationality.name }}</span>
                         </td>
                         <td><img v-lazy="player.photo" class="rounded-circle img-fluid"> {{ player.name }}</td>

                         <td>{{ player.position }}</td>
                      </tr>
                   </router-link>
                </tbody>
             </table>
          </div>
          <div class="search-widget__results__empty" v-else-if="hasSearched && !loading.isLoading">
            <p>No se han encontrado resultados</p>
          </div>
       </div>
    </div>
 </template>

<script setup>
    import { useFetch } from "@/composables/useFetch";
    import { useLoadingStore } from "@/stores/useLoadingStore";
import { removeAccents } from "@/utils/utils";
    import { ref } from "vue";
    
    const playerName = ref("");
    const loading=useLoadingStore();

    const hasSearched=ref(false);

    const players = ref([]);
    const getPlayers= async () => {
    loading.setLoading(true);
    if (!playerName.value.trim()) {
        loading.setLoading(false);
        return;
    }

    hasSearched.value=true;
    const playerNameEncoded=encodeURI(removeAccents(playerName.value.trim()));
    const {data,error}=await useFetch(`/api/playersSearch/${playerNameEncoded}`);
    if(error){
        console.log("Error al obtener jugadores con el nombre/apellido: '"+playerName.value+"'.")
        return;
    }
        players.value=data.value;
    };
</script>