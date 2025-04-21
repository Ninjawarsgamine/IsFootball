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
                data-bs-target="#classification" type="button" role="tab"
                aria-selected="true">
                    Clasificación
                </button>
            </li>
            <li class="nav-item" role="presentation" v-if="competition.type=='Cup' && !competition.teamsCompetitionStatistics">
                <button class="nav-link active" id="matches-tab" data-bs-toggle="tab" 
                data-bs-target="#matches" type="button" role="tab"
                aria-selected="true">
                    Todos los partidos
                </button>
            </li>
            <li class="nav-item" role="presentation" v-else>
                <button class="nav-link" id="matches-tab" data-bs-toggle="tab" 
                data-bs-target="#matches" type="button" role="tab"
                aria-selected="true">
                    Todos los partidos
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="statistics-tab" data-bs-toggle="tab" 
                data-bs-target="#statistics" type="button" role="tab"
                aria-selected="true">
                    Estadísticas
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="teams-tab" data-bs-toggle="tab" 
                data-bs-target="#teams" type="button" role="tab"
                aria-selected="true">
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
    </div>
</template>

<script setup>
    import { useRoute } from 'vue-router';
    import { computed, onMounted, ref } from "vue";
    import CompetitionTableComponent from '@/components/CompetitionTableComponent.vue';
    
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

    onMounted(async()=>{
        getCompetitionInfo();
    })
</script>