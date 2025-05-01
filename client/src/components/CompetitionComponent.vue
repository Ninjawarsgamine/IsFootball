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

            <li class="nav-item" role="presentation">
                <button class="nav-link" id="matches-tab" data-bs-toggle="tab" 
                data-bs-target="#matches" type="button" role="tab" aria-selected="true"
                @click="getcompetitionRoundMatches(competitionRounds[0]);">
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
                <select v-model="roundSelected" name="matches-round" 
                class="competition-info-container__match-round-select form-select"
                @change="getcompetitionRoundMatches(roundSelected)">
                    <option :value="round" v-for="round in competitionRounds" :key="round">
                        {{ round }}
                    </option>
                </select>
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
                :players="competitionTopAssistsProviders" />
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
    import { computed, onMounted, ref, watch } from "vue";
    import CompetitionTableComponent from '@/components/CompetitionTableComponent.vue';
    import CompetitionStatisticTableComponent from '@/components/CompetitionStatisticTableComponent.vue';
    import MatchCardComponent from '@/components/MatchCardComponent.vue';
    import { useFetch } from '@/composables/useFetch';

    const route=useRoute();
    const competitionId=route.params.id;

    const competition=ref([]);
    const getCompetitionInfo= async() =>{
        const {data, error}=await useFetch(`/api/competitionAllData/${competitionId}`);
        console.log(data);
        if(error){
            console.log("No se ha encontrado ninguna competición con ID "+competitionId);
            return;
        }
        competition.value=data.value;
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
        const {data, error}=await useFetch(`/api/competitionTeams/${competitionId}`);
        if(error){
            console.log("No se han encontrado equipos para la competición con ID "+competitionId);
            return;
        }
        teamsOrdered.value=data.value;
        teamsOrdered.value.sort((a,b)=>(a.name.localeCompare(b.name)));

    }
    //Esta función saca todos los equipos de una liga y los ordena alfabéticamente según su nombre.

    const competitionTopScorers=ref([]);
    const getCompetitionTopScorers=async()=>{
        
        const {data, error}=await useFetch(`/api/competitions/${competitionId}/top-scorers`);
            if(error){
                console.log("No se han encontrado los máximos goleadores de la competición con ID: "+competitionId);
                return;
            }
            competitionTopScorers.value=data.value;
            console.log(competitionTopScorers.value)
    }
    //Esta función saca los máximos goleadores de una competición.

    const competitionTopAssistsProviders=ref([]);
    const getCompetitionTopAssistsProviders=async()=>{
        const {data,error}=await useFetch(`/api/competitions/${competitionId}/top-assists-providers`);
        console.log(data.value);
        if(error){
            console.log("No se han encontrado los máximos asistentes de la competición con ID: "+competitionId);
            return;
        }
        competitionTopAssistsProviders.value=data.value;
    }
    //Esta función saca los máximos asistentes de una competición.

    const competitionTopYellowCards=ref([]);
    const getCompetitionTopYellowCards=async()=>{
        const {data,error}=await useFetch(`/api/competitions/${competitionId}/top-yellow-cards`);
        if(error){
            console.log("No se han encontrado los jugadores con más tarjetas amarillas de la competición con ID: "+competitionId);
            return;
        }
        competitionTopYellowCards.value=data.value;
    }
    //Esta función los jugadores que más tarjetas amarillas han recibido en una competición.

    const competitionTopRedCards=ref([]);
    const getCompetitionTopRedCards=async()=>{
        const {data,error}=await useFetch(`/api/competitions/${competitionId}/top-red-cards`);
        console.log(data.value);
        if(error){
            console.log("No se han encontrado los jugadores con más tarjetas rojas de la competición con ID: "+competitionId);
            return;
        }
        competitionTopRedCards.value=data.value;
    }
    //Esta función los jugadores que más tarjetas amarillas han recibido en una competición.
    
    const competitionRounds=ref([]);
    const getCompetitionRounds=async()=>{
        const {data, error}=await useFetch(`/api/competitions/${competitionId}/rounds`);
        if(error){
            console.log("No se han encontrado las rondas de la competición con ID: "+competitionId);
            return;
        }
        competitionRounds.value=data.value;
    }
    //Esta función obtiene todas las rondas de una competición.

    const competitionRoundMatches=ref([]);
    const getcompetitionRoundMatches=async(round)=>{
        const {data,error}=await useFetch(`/api/competitions/${competitionId}/round-matches-summary?round=${round}`);
        if(error){
            console.log("No se han encontrado partidos para la ronda: "+round);
            return;
        }
        competitionRoundMatches.value=data.value;
    }
    //Esta función obtiene una lista de partidos de una competición especificada en una ronda 
    //especificada.

    const roundSelected=ref("");
    watch(competitionRounds, (newRounds)=>{
        if(newRounds.length>0 && !roundSelected.value){
            roundSelected.value=newRounds[0];
        }
    });
    //Usamos un "watch" para que cuando cambie el valor de las rondas, si "roundSelected" no tiene
    //valor, va a ser el primero del array de las rondas actualizadas.

    onMounted(async()=>{
        getCompetitionInfo();
        await getCompetitionRounds();
    });
</script>