<template>
    <div class="competition-info-container" 
    v-if="competition.country">

        <div class="competition-info-container__competition-header">
            <img :src="competition.logo" alt="Logo de la competición">
            <h1>{{ competition.name }}</h1>
            <img class="competition-info-container__competition-header__competition-country img-fluid"
            :src="competition.country.flag" v-if="competition.country.flag!=='null'" 
            alt="Bandera del país de la competición">
        </div>

        <ul class="nav nav-underline mb-3" role="tablist">
            <li class="nav-item" role="presentation" v-if="competition.type=='League' || competition.teamsCompetitionStatistics">
                <button class="nav-link active" id="table-tab" data-bs-toggle="tab" 
                data-bs-target="#classification" type="button" role="tab" aria-selected="true">
                    Clasificación
                </button>
            </li>
            <li class="nav-item" role="presentation" v-if="competition.type=='Cup' && !competition.teamsCompetitionStatistics">
                <button class="nav-link active" id="matches-tab" data-bs-toggle="tab" 
                data-bs-target="#matches" type="button" role="tab" aria-selected="true">
                    Todos los partidos
                </button>
            </li>
            <li class="nav-item" role="presentation" v-else>
                <button class="nav-link" id="matches-tab" data-bs-toggle="tab" 
                data-bs-target="#matches" type="button" role="tab" aria-selected="true">
                    Todos los partidos
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="statistics-tab" data-bs-toggle="tab" 
                data-bs-target="#statistics" type="button" role="tab" aria-selected="true" 
                @click="getCompetitionTopScorers(); getCompetitionTopAssistsProviders();
                getCompetitionTopYellowCards(); getCompetitionTopRedCards();">
                    Estadísticas
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="teams-tab" data-bs-toggle="tab" 
                data-bs-target="#teams" type="button" role="tab"
                aria-selected="true" @click="getTeamsOrdered();">
                    Equipos
                </button>
            </li>
        </ul>

        <div class="tab-content" id="competitionTabsContent">
            <div class="tab-pane fade show active" id="classification" role="tabpanel" aria-labelledby="table-tab"
            v-if="competition.teamsCompetitionStatistics">
                <div v-if="competition.type=='League'">
                    <h1>Clasificación</h1>
                    <CompetitionTableComponent 
                    :competitionTeams="competition.teamsCompetitionStatistics"/>
                </div>
                <div v-if="competition.type=='Cup'">
                    <h1>Fase de grupos</h1>
                    <div v-for="(teams,group) in groupedTeams" :key="group">
                        <h2>{{ group }}</h2>
                        <CompetitionTableComponent 
                        :competitionTeams="teams" :key="teams.id"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-content" id="competitionMatches">
            <div class="tab-pane fade show" id="matches" role="tabpanel" aria-labelledby="table-tab">
                <h1>Partidos</h1>
                <div class="competition-info-container__competition-matches-container">
                    <MatchCardComponent :match="match" v-for="match in competitionRoundMatches" 
                    :key="match.id"/>
                </div>
            </div>
        </div>

        <div class="tab-content" id="competitionStatistics">
            <div class="tab-pane fade show" id="statistics" role="tabpanel" aria-labelledby="table-tab">
                <h1>Estadísticas</h1>
                <CompetitionStatisticTableComponent title="Máximos goleadores"
                 :players="competitionTopScorers"/>
                <CompetitionStatisticTableComponent title="Máximos asistidores"
                :players="competitionTopAssistsProviders"/>
                <CompetitionStatisticTableComponent title="Más tarjetas amarillas"
                :players="competitionTopYellowCards"/>
                <CompetitionStatisticTableComponent title="Más tarjetas rojas"
                :players="competitionTopRedCards"/>
            </div>
        </div>
        
        <div class="tab-content" id="competitionTeams">
            <div class="tab-pane fade show" id="teams" role="tabpanel" aria-labelledby="table-tab">
                <h1>Equipos</h1>
                <div class="competition-info-container__teams-list-container table-responsive">
                        <table class="table table-hover align-middle text-center">
                            <tr v-for="team in teamsOrdered" :key="team.id">
                                <td class="d-flex align-items-center gap-3 pd-4">
                                    <router-link :to="`/teams/${team.id}`">
                                        <img :src="team.logo" class="img-fluid" />
                                        <span>{{ team.name }}</span>
                                    </router-link>
                                </td>
                            </tr>
                     </table>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { useRoute } from 'vue-router';
    import { computed, onMounted, ref } from "vue";
    import CompetitionTableComponent from '@/components/CompetitionTableComponent.vue';
    import CompetitionStatisticTableComponent from '@/components/CompetitionStatisticTableComponent.vue';
    import MatchCardComponent from '@/components/MatchCardComponent.vue';

    const route=useRoute();
    const competitionId=route.params.id;

    const competition=ref([]);

    const getCompetitionInfo= async() =>{
        try{
            const response=await fetch(`/api/competitionAllData/${competitionId}`,{
                method:"GET",
            });
            if(response.ok){
                const data=await response.json();
                competition.value=data;
                console.log(data);
            }else{
                console.log("No se ha encontrado ninguna competición con ID "+competitionId);
            }
        }catch(error){
            console.log(error);
        }
    }

    const groupedTeams=computed(()=>{
        return competition.value["teamsCompetitionStatistics"].reduce((acc,team)=>{
            const group=team.group;
            if(!acc[group]){
                acc[group]=[];
            }
            acc[group].push(team);
            return acc;
        },{});
    })
    //Esta función nos saca un array de grupos que dentro contiene los equipos de cada uno de
    //esos grupos.
    
    const teamsOrdered=ref([]);
    const getTeamsOrdered= async() =>{
        try{
            const response=await fetch(`/api/competitionTeams/${competitionId}`,{
                method:"GET",
            });
            if(response.ok){
                const data=await response.json();
    
                teamsOrdered.value=data;
                teamsOrdered.value.sort((a,b)=>(a.name.localeCompare(b.name)));
            }else{
                console.log("No se han encontrado equipos para la competición con ID "+competitionId);
            }
        }catch(error){
            console.log(error);
        }
    }
    //Esta función saca todos los equipos de una liga y los ordena alfabéticamente según su nombre.

    const competitionTopScorers=ref([]);
    const getCompetitionTopScorers=async()=>{
        try{
            const response=await fetch(`/api/competitions/${competitionId}/top-scorers`,{
                method:'GET'
            });
            if(response.ok){
                const data=await response.json();
                console.log("Máximos goleadores:", data);
                competitionTopScorers.value=data;
            }else{
                console.log("No se han encontrado los máximos goleadores de la competición con ID "+
                competitionId);
            }
        }catch(error){
            console.log(error);
        }
    }
    //Esta función saca los máximos goleadores de una competición.

    const competitionTopAssistsProviders=ref([]);
    const getCompetitionTopAssistsProviders=async()=>{
        try{
            const response=await fetch(`/api/competitions/${competitionId}/top-assists-providers`,{
                method:'GET'
            });
            if(response.ok){
                const data=await response.json();
                competitionTopAssistsProviders.value=data;
            }
        }catch(error){
            console.log(error);
        }
    }
    //Esta función saca los máximos asistentes de una competición.

    const competitionTopYellowCards=ref([]);
    const getCompetitionTopYellowCards=async()=>{
        try{
            const response=await fetch(`/api/competitions/${competitionId}/top-yellow-cards`,{
                method:'GET'
            });
            if(response.ok){
                const data=await response.json();
                competitionTopYellowCards.value=data;
            }
        }catch(error){
            console.log(error);
        }
    }
    //Esta función los jugadores que más tarjetas amarillas han recibido en una competición.

    const competitionTopRedCards=ref([]);
    const getCompetitionTopRedCards=async()=>{
        try{
            const response=await fetch(`/api/competitions/${competitionId}/top-red-cards`,{
                method:'GET'
            });
            if(response.ok){
                const data=await response.json();
                competitionTopRedCards.value=data;
            }
        }catch(error){
            console.log(error);
        }
    }
    //Esta función los jugadores que más tarjetas amarillas han recibido en una competición.

    const competitionRounds=ref([]);
    const getCompetitionRounds=async()=>{
        try{
            const response=await fetch(`/api/competitions/${competitionId}/rounds`,{
                method:'GET'
            });
            if(response.ok){
                const data=await response.json();
                competitionRounds.value=data;
                console.log("Rondas: ", data);
            }else{
                console.log("No se han encontrado las rondas.")
            }
        }catch(error){
            console.log(error);
        }
    }
    //Esta función obtiene todas las rondas de una competición.

    const competitionRoundMatches=ref([]);
    const getcompetitionRoundMatches=async(round)=>{
        try{
            const url=`/api/competitions/${competitionId}/round-matches-summary?round=${round}`;
            console.log(url);
            const response=await fetch(
            `/api/competitions/${competitionId}/round-matches-summary?round=${round}`,{
                method:'GET'
            });
            if(response.ok){
                const data=await response.json();
                competitionRoundMatches.value=data;
                console.log("Partidos de la ronda : "+round+": ", data);
            }else{
                console.log("No se han encontrado partidos para la ronda: "+round);
            }
        }catch(error){
            console.log(error);
        }
    }
    //Esta función obtiene una lista de partidos de una competición especificada en una ronda 
    //especificada.
    onMounted(async()=>{
        getCompetitionInfo();
        await getCompetitionRounds();
        
        await getcompetitionRoundMatches(competitionRounds.value[0]);
        
    });
</script>