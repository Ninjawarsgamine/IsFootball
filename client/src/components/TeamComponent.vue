<template>
   <ComponentHeader :componentLogo="team.logo" :component-name="team.name"/>

    <div class="team-info-container">
        <ul class="nav nav-underline mb-3" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="basic-data-tab" data-bs-toggle="tab" 
                data-bs-target="#basicData" type="button" role="tab" aria-selected="true">
                    Datos básicos
                </button>
            </li>

            <li class="nav-item" role="presentation">
                <button class="nav-link" id="matches-tab" data-bs-toggle="tab" 
                data-bs-target="#matches" type="button" role="tab" aria-selected="true"
                @click="getTeamMatches()">
                    Todos los partidos
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="statistics-tab" data-bs-toggle="tab" 
                data-bs-target="#statistics" type="button" role="tab" aria-selected="true"
                @click="getTeamsCompetitionStatistics(teamCompetitions[0].id)">
                    Estadísticas
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="players-tab" data-bs-toggle="tab" 
                data-bs-target="#players" type="button" role="tab"
                aria-selected="true" @click="getTeamCoach(); getTeamPlayersOrderedByPosition()">
                    Plantilla
                </button>
            </li>
        </ul>

        <div class="tab-content" id="basicDataContent">
            <div class="tab-pane fade show active" id="basicData" role="tabpanel" 
            aria-labelledby="table-tab" >
                <div class="team-info-container__team-basic-data" v-if="team?.venue">
                    <h1>Datos básicos del equipo</h1>
                    <div class="team-info-container__team-basic-data__team-info-content">
                        <div 
                        class="team-info-container__team-basic-data__team-info-content__team-info-general">
                            <h2>Información general</h2>
                            <div 
                            class="team-info-container__team-basic-data__team-info-content__team-info-general__team-info-data">
                                <div><strong>País:</strong>
                                    <img class="img-fluid" :src="team?.country?.flag" 
                                    alt="Bandera del país"/>
                                </div>
                                <div><strong>Año de fundación:</strong> {{ team.founded }}</div>
                            </div>
                        </div>

                        <div class="team-info-container__team-info-venue">
                            <h2 class="">Estadio</h2>
                            <div class="team-info-container__team-info-venue__team-info-venue-data">
                                <img :src="team.venue.image" alt="Imagen del estadio"/>
                                <div>
                                    <div><strong>Nombre:</strong> {{ team.venue.name }}</div>
                                    <div><strong>Capacidad:</strong>{{ team.venue.capacity }} personas</div>
                                    <div><strong>Ciudad:</strong> {{ team.venue.city }}</div>
                                    <div><strong>Dirección:</strong> {{ team.venue.address }}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
        <div class="tab-content" id="teamMatches">
            <div class="tab-pane fade show" id="matches" role="tabpanel" aria-labelledby="table-tab">
                <h1>Partidos</h1>
               <div class="d-flex flex-wrap align-items-center">
                    <MatchCardComponent :match="match" v-for="match in teamMatches"  :key="match.id"/>
               </div>
            </div>
        </div>
        
        <div class="tab-content" id="teamStatistics">
            <div class="tab-pane fade show" id="statistics" role="tabpanel" aria-labelledby="table-tab">
                <h1>Estadísticas</h1>
                <select v-model="competitionSelected" name="team-competition" 
                class="team-info-container__select form-select">
                    <option :value="competition.id" v-for="competition in teamCompetitions" 
                    :key="competition.id">
                      {{ competition.name }}
                    </option>
                </select>
            </div>
        </div>

        <div class="tab-content" id="teamPlayers">
            <div class="tab-pane fade show" id="players" role="tabpanel" aria-labelledby="table-tab">
                <h1>Plantilla</h1>  

                <div class="team-info-container__team-players list-group">
                    <h3>Entrenador</h3>
                    <div class="team-info-container__team-players__player-data list-group-item-action 
                    d-flex align-items-center p-3">
                        <img class="rounded-circle flex-shrink-0 me-3" :src="teamCoach?.photo"
                        :alt="'Foto de ' + teamCoach.name"/>
                        <div class="team-info-container__team-players__player-data__player-info">
                            <h6>{{ teamCoach.name }}</h6>
                        </div>
                    </div>

                    <h3>Jugadores</h3>
                    <router-link v-for="player in teamPlayers" :key="player.id" 
                    class="team-info-container__team-players__player-data list-group-item-action 
                    d-flex align-items-center p-3" :to="`/players/${player.id}`">
                        <img class="rounded-circle flex-shrink-0 me-3" :src="player.photo"
                        :alt="'Foto de ' + player.name"/>
                        <div class="team-info-container__team-players__player-data__player-info">
                            <h6>{{ player.name }}</h6>
                            <div>
                                <span>{{ player.position }}</span>
                            </div>
                        </div>
                    </router-link>
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

    const getTeamsCompetitions=async()=>{
        const {data,error}=await useFetch(`/api/teamsCompetitions/${teamId}`);
        console.log("Competiciones: "+data.value);
        
        if(error){
            console.log("No se han encontrado las competiciones disputadas por el equipo con ID: "
            +teamId);
        }
        teamCompetitions.value=data.value;
    }

    const competitionSelected=ref("");
    watch(teamCompetitions, (newCompetitions)=>{
        if(newCompetitions.length>0 && !competitionSelected.value){
            competitionSelected.value=newCompetitions[0];
        }
    });

    const teamsCompetitionStatistics=ref(null);

    const getTeamsCompetitionStatistics=async(competitionId)=>{
        const {data,error}=await useFetch(`/api/teamCompetitionStatistics/${teamId}/${competitionId}`);
        if(error){
            console.log("No se han encontrado las estadísticas de la competición");
            teamsCompetitionStatistics.value=data.value;
        }
    }
       
    const teamCoach=ref([]);
    const getTeamCoach=async()=>{
        const{data,error}=await useFetch(`/api/teamCoach/${teamId}`);
        if(error){
            console.log("No se ha encontrado el entrenador del equipo con ID: "+teamId)
        }
        teamCoach.value=data.value;
    }
    //Esta función obtiene el entrenador de un equipo. 

    const teamPlayers=ref([]);

    const getTeamPlayers=async()=>{
        const {data,error}=await useFetch(`/api/teamPlayers/${teamId}`);
        if(error){
            console.log("No se han encontrado los jugadores para el equipo con ID: "+teamId)
        }
        teamPlayers.value=data.value;
    }
    //Esta función obtiene una lista de los jugadores de un equipo.

    const teamPositions=['Goalkeeper', 'Defender', 'Midfielder', 'Attacker'];

    const getTeamPlayersOrderedByPosition=async()=>{
        await getTeamPlayers();
        teamPlayers.value.sort((a, b) => {
            const indexA = teamPositions.indexOf(a.position);
            const indexB = teamPositions.indexOf(b.position);
            
            return (indexA === -1 ? 999 : indexA) - (indexB === -1 ? 999 : indexB);
            //Lo que hacemos es que restamos el "indexA"("goalkeeper") menos el "indexB"("Attacker")
            //para que se ordene primero por el "goalkeeper". Si hace que si alguno de los "index"
            //es "-1" (o sea, no está en la lista), se ponga al final de la lista.
        });
    }
    //Esta función ordena los jugadores de un equipo. por posición (desde "Goalkeeper" 
    //hasta "Attacker").
    
    onMounted(async()=>{
         await getTeamInfo();
         await getTeamsCompetitions();
    });
</script>