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
                data-bs-target="#statistics" type="button" role="tab" aria-selected="true">
                    Estadísticas
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="players-tab" data-bs-toggle="tab" 
                data-bs-target="#players" type="button" role="tab"
                aria-selected="true" @click="getTeamPlayers()">
                    Jugadores
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
        <div class="tab-content" id="competitionMatches">
            <div class="tab-pane fade show" id="matches" role="tabpanel" aria-labelledby="table-tab">
                <h1>Partidos</h1>
                <div class="competition-info-container__competition-matches-container">
                    <MatchCardComponent :match="match" v-for="match in teamMatches" 
                    :key="match.id"/>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { useFetch } from '@/composables/useFetch';
    import { onMounted, ref } from 'vue';
    import { useRoute } from 'vue-router';
    import ComponentHeader from '@/components/ComponentHeader.vue';

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

    onMounted(async()=>{
         await getTeamInfo();
    });
</script>