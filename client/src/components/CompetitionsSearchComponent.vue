<template>
   <div class="competitions-search">
      <div class="competitions-search__form">
         <form method="GET" id="competition-search-form" @submit.prevent="getCompetitions">
            <input type="text" name="name"  id="competitionName" v-model="competitionName"
            placeholder="Busca tu competición aquí" autocomplete="off"/>
            <button type="submit">
               <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" 
               fill="none"  viewBox="0 0 24 24"  stroke="currentColor" stroke-width="2">
                  <path stroke-linecap="round" stroke-linejoin="round" 
                  d="M21 21l-4.35-4.35M17 11a6 6 0 11-12 0 6 6 0 0112 0z"/>
               </svg>
            </button>
         </form>
      </div>
      <div class="competitions-search__competitions">
         <div class="competitions-search__competitions__result table-responsive" 
         v-if="competitions.length > 0">
             <table class="table table-striped table-hover table-sm align-middle mb-0">
               <thead class="table-dark">
                  <tr>
                     <th scope="col" class="text-center">🏳️</th>
                     <th scope="col" class="text-center">🏆</th>
                     <th scope="col">Competición</th>
                  </tr>
               </thead>
               <tbody>
                  <router-link v-for="competition in competitions" :to="`/competitions/${competition.id}`"
                  :key="competition.id" custom v-slot="{navigate}">
                     <tr :key="competition.id" @click="navigate">
                        <td class="text-center" v-if="competition.country.flag!=='null'">
                           <img :src="competition.country.flag" class="img-fluid">
                        </td>
                        <td v-else>-</td>
                        <td class="text-center">
                           <img :src="competition.logo" class="img-fluid">
                        </td>
                        <td>{{ competition.name }}</td>
                     </tr>
                  </router-link>
               </tbody>
            </table>
         </div>
         <div class="competitions-search__competitions__empty" v-else-if="hasSearched && !isLoading">
           <p>No se han encontrado resultados</p>
         </div>
      </div>
   </div>
</template>

<script setup>
   import { ref } from "vue";
   
   const competitionName = ref("");
   const isLoading=ref(false);
   const hasSearched=ref(false);
   const competitions = ref([]);
   const getCompetitions = async () => {
      try {
         if (!competitionName.value.trim()) {
            return;
         }
         //Si no hay ningún nombre no se hace nada.
         isLoading.value=true;
         hasSearched.value=true;
         const competitionNameEncoded=encodeURI(competitionName.value.trim());
         const response = await fetch( `/api/competitions/${competitionNameEncoded}`, {
            method: "GET",
         });
         if (response.ok) {
            const data = await response.json();
            competitions.value = data;
         }
         isLoading.value=false;
      } catch (error) {
         console.log("Se ha producido un error: ", error);
      }
   };
   //Función que hace una llamada al back y devuelve las competiciones que
   //coincidan con "competitionName".
</script>