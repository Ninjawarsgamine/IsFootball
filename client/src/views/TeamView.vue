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
                        <h4 v-if="matchesFields.length>0">Matches</h4>
                        <div class="table-responsive">
                            <table class="table table-bordered table-hover mb-0" 
                            v-if="matchesFields.length>0">
                                <thead class="table-light">
                                    <tr>
                                        <th scope="col"></th>
                                        <th scope="col">Home</th>
                                        <th scope="col">Away</th>
                                        <th scope="col">Total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                   <tr v-for="type in matchesFields" :key="type">
                                        <td>{{ type.label }}</td>
                                        <td v-for=" statistic in type.statistics" :key="statistic">
                                            {{ statistic }}
                                        </td>
                                   </tr>
                                </tbody>
                            </table>
                        </div>

                        <GoalsStatisticsComponent :title="'Goals scored'"
                        :goalsData="teamCompetitionStatistics.goalsFor"/>
                        <GoalsStatisticsComponent :title="'Goals against'"
                        :goals-data="teamCompetitionStatistics.goalsAgainst"/>

                        <h4 class="fw-bold mt-4" v-if="biggestFields.length>0">Biggest Results</h4>
                        <table class="table table-bordered text-center align-middle" 
                        v-if="biggestFields.length>0">
                            <thead class="table-light">
                                <tr>
                                    <th></th>
                                    <th>Home</th>
                                    <th>Away</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="type in biggestFields" :key="type">
                                    <td class="fw-bold bg-white">{{type.label}}</td>
                                    <td v-for="statistic in type.statistics" 
                                        :key="statistic">
                                            {{statistic !==null && statistic !=="null"?statistic:"-" }}
                                    </td>
                                </tr>
                            </tbody>
                        </table>

                        <h4 class="fw-bold mt-4" v-if="cleanSheetAnFailedToScoreFields.length>0">Clean Sheets & Failed to Score</h4>
                        <div class="table-responsive" v-if="cleanSheetAnFailedToScoreFields.length>0">
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
                                    <tr v-for="type in cleanSheetAnFailedToScoreFields" :key="type">
                                        <td class="fw-bold bg-white">{{type.label}}</td>
                                        <td v-for="statistic in type.statistics" 
                                        :key="statistic">
                                            {{statistic !==null && statistic !=="null"?statistic:"-" }}
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <h4 class="fw-bold mt-4"  v-if="penaltiesFields">Penalties</h4>
                        <div class="table-responsive" v-if="penaltiesFields">
                            <table class="table table-bordered text-center align-middle">
                                <thead class="table-light">
                                    <tr>
                                        <th></th>
                                        <th>Total</th>
                                        <th>Percentage</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr v-for="type in penaltiesFields" :key="type">
                                        <td class="fw-bold bg-white">{{ type.label }}</td>
                                        <td v-for="statistic in type.statistics" 
                                        :key="statistic">
                                        {{ statistic && statistic !=="null"?statistic:"-" }}
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>

                        <h4 v-if="teamCompetitionStatistics.lineups.length>0">Lineups Used</h4>
                        <div class="table-responsive" v-if="teamCompetitionStatistics.lineups.length>0">
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
                    v-if="teamSquad">
                        <h3>Coach</h3>
                        <div class="team-info-container__team-players__player-data list-group-item-action 
                        d-flex align-items-center p-3" v-if="teamSquad.coach">
                            <img class="rounded-circle flex-shrink-0 me-3" v-lazy="teamSquad.coach.photo"
                            />
                            <div class="team-info-container__team-players__player-data__player-info">
                                <h6>{{ teamSquad.coach.name }}</h6>
                            </div>
                        </div>
                        <div class="team-info-container__team-players__player-data list-group-item-action 
                        d-flex align-items-center p-3" v-else>
                            The information is not available.
                        </div>

                        <h3>Players</h3>
                        <div class="team-info-container__team-players__player-data list-group-item-action 
                        d-flex align-items-center p-3" v-if=" !teamSquad.players || teamSquad.players.length==0">
                            This information is not available.
                        </div>
                        <router-link v-for="player in teamSquad.players" :key="player" v-else
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
    import { computed, onMounted, ref, watch } from 'vue';
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
        if(newCompetitions && newCompetitions.length>0 && !competitionSelected.value){
            competitionSelected.value=newCompetitions[0].id;
            await getTeamCompetitionStatistics(competitionSelected.value);
        }
    });
    //Cuando se obtengan las competiciones del equipo, cambiamos el valor del "select".
    
   watch(competitionSelected,async(newCompetitionId)=>{
        await getTeamCompetitionStatistics(newCompetitionId);
    });
    //Esta función obtiene las estadísticas para la nueva competición seleccionada.

    const matchesFields=computed(()=>{
        const teamCompetitionAllStatistics=teamCompetitionStatistics.value;
        if(!teamCompetitionAllStatistics){
            return;
        }
        return [
            {
                label:"Played", 
                statistics:[
                    teamCompetitionAllStatistics.matchesPlayed.home,
                    teamCompetitionAllStatistics.matchesPlayed.away,
                    teamCompetitionAllStatistics.matchesPlayed.total
                ] 
            },
            {
                label:"Won", 
                statistics:[
                    teamCompetitionAllStatistics.matchesWon.home,
                    teamCompetitionAllStatistics.matchesWon.away,
                    teamCompetitionAllStatistics.matchesWon.total
                ]
            },
            {
                label:"Drawn", 
                statistics:[
                    teamCompetitionAllStatistics.matchesDrawn.home,
                    teamCompetitionAllStatistics.matchesDrawn.away,
                    teamCompetitionAllStatistics.matchesDrawn.total
                ]
            },
            {
                label:"Lost", 
                statistics:[
                    teamCompetitionAllStatistics.matchesLost.home,
                    teamCompetitionAllStatistics.matchesLost.away,
                    teamCompetitionAllStatistics.matchesLost.total
                ]
            }
        ];
    });

    const biggestFields=computed(()=>{
        const teamCompetitionAllStatistics=teamCompetitionStatistics.value;
        if(!teamCompetitionAllStatistics){
            return;
        }
        return [
            {
                label:"Biggest wins", 
                statistics:[
                    teamCompetitionAllStatistics.biggestWins?.home,
                    teamCompetitionAllStatistics.biggestWins?.away
                ] 
            },
            {
                label:"Biggest loses", 
                statistics:[
                    teamCompetitionAllStatistics.biggestLoses?.home,
                    teamCompetitionAllStatistics.biggestLoses?.away
                ] 
            },
            {
                label:"Goals for", 
                statistics:[
                    teamCompetitionAllStatistics.biggestGoalsFor?.home,
                    teamCompetitionAllStatistics.biggestGoalsFor?.away
                ] 
            },
            {
                label:"Goals against", 
                statistics:[
                    teamCompetitionAllStatistics.biggestGoalsAgainst?.home,
                    teamCompetitionAllStatistics.biggestGoalsAgainst?.away
                ] 
            },
        ];
    });

    const cleanSheetAnFailedToScoreFields=computed(()=>{
        const teamCompetitionAllStatistics=teamCompetitionStatistics.value;
        if(!teamCompetitionAllStatistics){
            return;
        }
        return [
            {
                label:"Clean Sheets", 
                statistics:[
                    teamCompetitionAllStatistics.cleanSheet?.home,
                    teamCompetitionAllStatistics.cleanSheet?.away,
                    teamCompetitionAllStatistics.cleanSheet?.total
                ] 
            },
            {
                label:"Failed to score", 
                statistics:[
                    teamCompetitionAllStatistics.failedToScore?.home,
                    teamCompetitionAllStatistics.failedToScore?.away,
                    teamCompetitionAllStatistics.failedToScore?.total
                ]
            },
        ];
    });

    const penaltiesFields=computed(()=>{
        const teamCompetitionAllStatistics=teamCompetitionStatistics.value;
        if(!teamCompetitionAllStatistics){
            return;
        }
        return [
            {
                label:"Scored", 
                statistics:[
                    teamCompetitionAllStatistics.penaltiesScored?.total,
                    teamCompetitionAllStatistics.penaltiesScored?.percentage
                ] 
            },
            {
                label:"Failed", 
                statistics:[
                    teamCompetitionAllStatistics.penaltiesFailed?.total,
                    teamCompetitionAllStatistics.penaltiesFailed?.percentage
                ]
            },
        ];
    });
    
    const teamSquad=ref();
    const getTeamSquad=async()=>{
        const {data,error}=await useFetch(`/api/teamSquad/${teamId}`);
        if(error){
            console.log("No se ha podido obtener la plantilla del equipo con ID: "+teamId)
        }
        teamSquad.value=data.value;
    }
    //Esta función obtiene la plantilla de un equipo con un ID especificado.
    
    onMounted(async()=>{
        await getTeamInfo();
    });
</script>
