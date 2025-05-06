<template>
    <div class="search-widget">
       <div class="search-widget__form">
          <form method="GET" id="team-search-form" @submit.prevent="getTeams">
             <input type="text" name="name"  id="teamName" v-model="teamName"
             placeholder="Search for your team here" autocomplete="off"/>
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
          v-if="teams.length > 0">
              <table class="table table-striped table-hover table-sm align-middle mb-0">
                <thead class="table-dark">
                   <tr>
                      <th scope="col" class="text-center">🏳️</th>
                      <th scope="col" class="text-center">🏆</th>
                      <th scope="col">Team</th>
                   </tr>
                </thead>
                <tbody>
                   <router-link v-for="team in teams"
                   :to="`/teams/${removeAccents(team.name)}/${team.id}`"
                   :key="team.id" custom v-slot="{navigate}">
                      <tr :key="team.id" @click="navigate">
                         <td class="text-center" v-if="team.country.name!=='null'">
                            <span>{{ team.country.name }}</span>
                         </td>
                         <td v-else>-</td>
                         <td class="text-center">
                            <img v-lazy="team.logo" class="img-fluid">
                         </td>
                         <td>{{ team.name }}</td>
                      </tr>
                   </router-link>
                </tbody>
             </table>
          </div>
          <div class="search-widget__results__empty" v-else-if="hasSearched && !loading.isLoading">
            <p>No results found</p>
          </div>
       </div>
    </div>
 </template>
 
 <script setup>
   import { useFetch } from "@/composables/useFetch";
   import { useLoadingStore } from "@/stores/useLoadingStore";
   import { removeAccents } from "@/utils/utils";
   import { ref } from "vue";
    
   const teamName = ref("");
   const loading=useLoadingStore();
 
   const hasSearched=ref(false);
   
   const teams = ref([]);
   const getTeams= async () => {
      loading.setLoading(true);
      if (!teamName.value.trim()) {
         loading.setLoading(false);
         return;
      }
      
      hasSearched.value=true;
      const teamNameEncoded=encodeURI(removeAccents(teamName.value.trim()));
      const {data,error}=await useFetch(`/api/teams/${teamNameEncoded}`);
      if(error){
         console.log("Error al obtener competiciones con la cadena: '"+teamName.value+"'.")
         return;
      }
         teams.value=data.value;
   };
 </script>