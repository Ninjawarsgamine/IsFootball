<template>
    <div v-if="team">
        <ComponentHeader :componentLogo="team.logo" :component-name="team.name"/>
        <div class="team-info-container">
            <ul class="nav nav-underline mb-3" role="tablist">
                <li class="nav-item" role="presentation" v-if="team?.founded!==0 && team?.venue?.id!==0">
                    <button class="nav-link active" id="basic-data-tab" data-bs-toggle="tab" 
                    data-bs-target="#basicData" type="button" role="tab" aria-selected="true">
                        Basic data
                    </button>
                </li>

                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="matches-tab" data-bs-toggle="tab" 
                    data-bs-target="#matches" type="button" role="tab" aria-selected="true"
                    @click="getTeamMatches()">
                        All matches
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="statistics-tab" data-bs-toggle="tab" 
                    data-bs-target="#statistics" type="button" role="tab" aria-selected="true"
                    @click="getTeamCompetitions()">
                        Statistics
                    </button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="squad-tab" data-bs-toggle="tab" 
                    data-bs-target="#squad" type="button" role="tab"
                    aria-selected="true" @click="async()=>await getTeamSquad()">
                        Squad
                    </button>
                </li>
            </ul>

            <div class="tab-content" id="basicDataContent"  v-if="team?.founded!==0 && team?.venue?.id!==0">
                <div class="tab-pane fade show active" id="basicData" role="tabpanel" 
                aria-labelledby="table-tab" >
                    <div class="team-info-container__team-basic-data" v-if="team?.venue">
                        <h1>Team basic information</h1>
                        <div class="team-info-container__team-basic-data__team-info-content">
                            <div 
                            class="team-info-container__team-basic-data__team-info-content__team-info-general">
                                <h2>General information</h2>
                                <div 
                                class="team-info-container__team-basic-data__team-info-content__team-info-general__team-info-data">
                                    <div><strong>Country:</strong>
                                        <img class="img-fluid" v-lazy="team?.country?.flag" 
                                        alt="Bandera del país"/>
                                    </div>
                                    <div><strong>Founded:</strong> {{ team.founded }}</div>
                                </div>
                            </div>

                            <div class="team-info-container__team-info-venue">
                                <h2 class="">Venue</h2>
                                <div class="team-info-container__team-info-venue__team-info-venue-data">
                                    <img v-lazy="team.venue.image" alt="Imagen del estadio"/>
                                    <div>
                                        <div><strong>Name:</strong> {{ team.venue.name }}</div>
                                        <div><strong>Capacity:</strong>{{ team.venue.capacity }} personas</div>
                                        <div><strong>City:</strong> {{ team.venue.city }}</div>
                                        <div><strong>Address:</strong> {{ team.venue.address }}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="tab-content" id="teamMatches">
                <div class="tab-pane fade show" id="matches" role="tabpanel" aria-labelledby="table-tab">
                    <h1>Matches</h1>
                <div class="d-flex flex-wrap align-items-center">
                        <MatchCardComponent :match="match" v-for="match in teamMatches"  :key="match.id"/>
                </div>
                </div>
            </div>
            
            <div class="tab-content" id="teamStatistics">
                <div class="tab-pane fade show" id="statistics" role="tabpanel" aria-labelledby="table-tab">
                    <h1>Statistics</h1>
                    <select v-model="competitionSelected" name="team-competition" 
                    class="team-info-container__select form-select" 
                    @change="async()=>await getTeamCompetitionStatistics(competitionSelected)">
                        <option :value="competition.id" v-for="competition in teamCompetitions" 
                        :key="competition.id">
                        {{ competition.name }}
                        </option>
                    </select>

                    <div class="team-info-container__team-statistics" v-if="teamCompetitionStatistics">
                        <h4>Matches</h4>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover mb-0 ">
                                <thead class="table-light">
                                    <tr>
                                        <th scope="col"></th>
                                        <th scope="col">Home</th>
                                        <th scope="col">Away</th>
                                        <th scope="col">Total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Played</td>
                                        <td v-for="type in teamCompetitionStatistics.matchesPlayed" 
                                        :key="type">{{ type }}</td>
                                    </tr>
                                    <tr>
                                        <td>Won</td>
                                        <td v-for="type in teamCompetitionStatistics.matchesWon" 
                                        :key="type">{{ type }}</td>
                                    </tr>
                                    <tr>
                                        <td>Drawn</td>
                                        <td v-for="type in teamCompetitionStatistics.matchesDrawn" 
                                        :key="type">{{ type }}</td>
                                    </tr>
                                    <tr>
                                        <td>Lost</td>
                                        <td v-for="type in teamCompetitionStatistics.matchesLost" 
                                        :key="type">{{ type }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <GoalsStatisticsComponent :title="'Goals scored'"
                        :goalsData="teamCompetitionStatistics.goalsFor"/>
                        <GoalsStatisticsComponent :title="'Goals against'"
                        :goals-data="teamCompetitionStatistics.goalsAgainst"/>

                        <h4 class="fw-bold mt-4">Biggest Results</h4>
                        <table class="table table-bordered text-center align-middle">
                            <thead class="table-light">
                                <tr>
                                    <th></th>
                                    <th>Home</th>
                                    <th>Away</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="fw-bold bg-white">Biggest Win</td>
                                    <td v-for="type in teamCompetitionStatistics.biggestWins" 
                                    :key="type">{{ type!==null && type!=="null"?type:"-" }}</td>
                                </tr>
                                <tr>
                                    <td class="fw-bold bg-white">Biggest Loss</td>
                                    <td v-for="type in teamCompetitionStatistics.biggestLoses" 
                                    :key="type">{{ type!==null && type!=="null"?type:"-" }}</td>
                                </tr>
                                <tr>
                                    <td class="fw-bold bg-white">Most Goals Scored</td>
                                    <td v-for="type in teamCompetitionStatistics.biggestGoalsFor" 
                                    :key="type">{{ type!==null && type!=="null"?type:"-" }}</td>
                                </tr>
                                <tr>
                                    <td class="fw-bold bg-white">Most Goals Conceded</td>
                                    <td v-for="type in teamCompetitionStatistics.biggestGoalsAgainst" 
                                    :key="type">{{ type!==null && type!=="null"?type:"-" }}</td>
                                </tr>
                            </tbody>
                        </table>

                        <h4 class="fw-bold mt-4">Clean Sheets & Failed to Score</h4>
                        <div class="table-responsive">
                            <table class="table table-bordered text-center align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th></th>
                                        <th>Home</th>
                                        <th>Away</th>
                                        <th>Total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td class="fw-bold bg-white">Clean Sheets</td>
                                        <td v-for="type in teamCompetitionStatistics.cleanSheet" 
                                        :key="type">{{ type!==null && type!=="null"?type:"-" }}</td>
                                    </tr>
                                    <tr>
                                        <td class="fw-bold bg-white">Failed to Score</td>
                                        <td v-for="type in teamCompetitionStatistics.failedToScore" 
                                        :key="type">{{ type!==null && type!=="null"?type:"-" }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <h4 class="fw-bold mt-4">Penalties</h4>
                        <div class="table-responsive">
                            <table class="table table-bordered text-center align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th></th>
                                        <th>Total</th>
                                        <th>Percentage</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td class="fw-bold bg-white">Scored</td>
                                        <td v-for="data in teamCompetitionStatistics.penaltiesScored" 
                                        :key="data">{{ data!==null && data!=="null"?data:"-" }}</td>
                                    </tr>
                                    <tr>
                                        <td class="fw-bold bg-white">Missed</td>
                                        <td v-for="data in teamCompetitionStatistics.penaltiesMissed" 
                                        :key="data">{{ data!==null && data!=="null"?data:"-" }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <h4>Lineups Used</h4>
                        <div class="table-responsive">
                            <table class="table table-bordered text-center align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th>Formation</th>
                                        <th>Matches Played</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="lineup in teamCompetitionStatistics.lineups" 
                                    :key="lineup.formation">
                                        <td>{{ lineup.formation }}</td>
                                        <td>{{ lineup.matchesPlayed }}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

            <div class="tab-content" id="teamSquad">
                <div class="tab-pane fade show" id="squad" role="tabpanel" aria-labelledby="table-tab">
                    <h1>Squad</h1>  
                    <div class="team-info-container__team-players list-group" 
                    v-if="teamSquad && teamSquad.coach && teamSquad.players">
                        <h3>Coach</h3>
                        <div class="team-info-container__team-players__player-data list-group-item-action 
                        d-flex align-items-center p-3">
                            <img class="rounded-circle flex-shrink-0 me-3" v-lazy="teamSquad.coach.photo"
                            />
                            <div class="team-info-container__team-players__player-data__player-info">
                                <h6>{{ teamSquad.coach.name }}</h6>
                            </div>
                        </div>

                        <h3>Players</h3>
                        <router-link v-for="player in teamSquad.players" :key="player" 
                        class="team-info-container__team-players__player-data list-group-item-action 
                        d-flex align-items-center p-3" :to="`/players/${player.id}`">
                            <img class="rounded-circle flex-shrink-0 me-3" v-lazy="player.photo"/>
                            <div class="team-info-container__team-players__player-data__player-info">
                                <h6>{{player.name}}</h6>
                                <div>
                                    <span>{{player.position}}</span>
                                </div>
                            </div>
                        </router-link>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { useFetch } from '@/composables/useFetch';
    import { onMounted, ref, watch } from 'vue';
    import { useRoute } from 'vue-router';
    import ComponentHeader from '@/components/ComponentHeader.vue';
    import MatchCardComponent from '@/components/MatchCardComponent.vue';
    import GoalsStatisticsComponent from '@/components/GoalsStatisticsComponent.vue';

    const route=useRoute();
    const teamId=route.params.id;
    const teamName=route.params.name;
    
    const team=ref([]);
    const getTeamInfo=async()=>{
        const {data, error}=await useFetch(`/api/teamByNameAndId/${teamName}/${teamId}`);
        if(error){
            console.log("No se han encontrado información del equipo con ID: "+teamId);
        }
        team.value=data.value;
    }
    
    const teamMatches=ref([]);
    const getTeamMatches=async()=>{
        const {data, error}=await useFetch(`/api/teamMatches/${teamId}`);
        if(error){
            console.log("No se han los partidos del equipo con ID: "+teamId);
        }
        teamMatches.value=data.value;
    }
    //Esta función obtiene una lista de todos los partidos que ha jugado un equipo.

    const teamCompetitions=ref([]);

    const getTeamCompetitions=async()=>{
        const {data,error}=await useFetch(`/api/teamCompetitions/${teamId}`);
        console.log("Competiciones: "+data.value);
        
        if(error){
            console.log("No se han encontrado las competiciones disputadas por el equipo con ID: "
            +teamId);
        }
        teamCompetitions.value=data.value;
    }

    const teamCompetitionStatistics=ref(null);

    const getTeamCompetitionStatistics=async(competitionId)=>{
        const {data,error}=await useFetch(`/api/teamCompetitionStatistics/${teamId}/${competitionId}`);
        if(error){
            console.log("No se han encontrado las estadísticas de la competición");
        }
        teamCompetitionStatistics.value=data.value;
    }
    //Esta función obtiene las estadísticas de un equipo en una competición con un ID especificado.
    
    const competitionSelected=ref("");

    
    watch(teamCompetitions, async(newCompetitions)=>{
        if(newCompetitions.length>0 && !competitionSelected.value){
            competitionSelected.value=newCompetitions[0].id;
            await getTeamCompetitionStatistics(competitionSelected.value);
        }
    });
    //Cuando se obtengan las competiciones del equipo, cambiamos el valor del "select".
    
   watch(competitionSelected,async(newCompetitionId)=>{
        await getTeamCompetitionStatistics(newCompetitionId);
    });
    //Esta función obtiene las estadísticas para la nueva competición seleccionada.

    const teamSquad=ref();
    const getTeamSquad=async()=>{
        const {data,error}=await useFetch(`/api/teamSquad/${teamId}`);
        if(error){
            console.log("No se ha podido obtener la plantilla del equipo con ID: "+teamId)
        }
        teamSquad.value=data.value;
    }
    //Esta función ordena los jugadores de un equipo. por posición (desde "Goalkeeper" 
    //hasta "Attacker").
    
    onMounted(async()=>{
         await getTeamInfo();
    });
</script>
