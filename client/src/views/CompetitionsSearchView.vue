<template>
   <div class="search-widget">
      <div class="search-widget__form">
         <form method="GET" id="competition-search-form" @submit.prevent="getCompetitions">
            <input type="text" name="name"  id="competitionName" v-model="competitionName"
            placeholder="Search for your competition here" autocomplete="off"/>
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
         v-if="competitions.length > 0">
             <table class="table table-striped table-hover table-sm align-middle mb-0">
               <thead class="table-dark">
                  <tr>
                     <th scope="col" class="text-center">🏳️</th>
                     <th scope="col" class="text-center">🏆</th>
                     <th scope="col">Competition</th>
                  </tr>
               </thead>
               <tbody>
                  <router-link v-for="competition in competitions"
                  :to="`/competitions/${competition.id}`"
                  :key="competition.id" custom v-slot="{navigate}">
                     <tr :key="competition.id" @click="navigate">
                        <td class="text-center" v-if="competition.country.flag!=='null'">
                           <img v-lazy="competition.country.flag" class="img-fluid">
                        </td>
                        <td v-else>-</td>
                        <td class="text-center">
                           <img v-lazy="competition.logo" class="img-fluid">
                        </td>
                        <td>{{ competition.name }}</td>
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
   
   const competitionName = ref("");
   const loading=useLoadingStore();

   const hasSearched=ref(false);
   const competitions = ref([]);

   const getCompetitions = async () => {

      loading.setLoading(true);
      if (!competitionName.value.trim()) {
         loading.setLoading(false);
         return;
      }
      //Si no hay ningún nombre no se hace nada.

      hasSearched.value=true;
   
      const competitionNameEncoded=encodeURI(removeAccents(competitionName.value.trim()));
      const {data,error}=await useFetch(`/api/competitions/${competitionNameEncoded}`);
      if(error){
         console.log("Error al obtener competiciones con la cadena: '"+competitionName.value+"'.")
         return;
      }
      competitions.value=data.value;
   };
   //Función que hace una llamada al back y devuelve las competiciones que
   //coincidan con "competitionName".
</script>