<template>
     <ComponentHeader :componentName="player.name" :componentLogo="player.photo"
     v-if="player && playerName"/>
    <div class="player-info-container" v-if="player && playerName && infoFields.length>0">
        <ul class="nav nav-underline mb-3" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="table-tab" data-bs-toggle="tab" 
                data-bs-target="#basicData" type="button" role="tab" aria-selected="true">
                    Basic data
                </button>
            </li>

            <li class="nav-item" role="presentation">
                <button class="nav-link" id="statistics-tab" data-bs-toggle="tab" 
                data-bs-target="#statistics" type="button" role="tab" aria-selected="true"
                @click="getPlayerCompetitions()">
                    Statistics
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="transfers-tab" data-bs-toggle="tab" 
                data-bs-target="#career" type="button" role="tab"
                aria-selected="true" @click="getPlayerTeamsCareer()">
                    Career
                </button>
            </li>
        </ul>
        <div class="tab-content" id="basicDataTabsContent">
            <div class="tab-pane fade show active" id="basicData" role="tabpanel" 
            aria-labelledby="table-tab">
                <div class="player-info-container__player-basic-info card shadow-sm">
                    <div class="card-body">
                        <div class="row gx-3 gy-3">
                            <div class="player-info-container__player-basic-info__item col-6 col-md-4"
                            v-for="(field, index) in infoFields" :key="index">
                                <span class="player-info-container__player-basic-info__item__label">
                                    {{ field.label }}:
                                </span>
                                <span class="player-info-container__player-basic-info__item__value" 
                                :class="field.valueClass">{{ field.value }}</span>
                            </div>

                            <div class="col-6 col-md-4 player-info-container__player-basic-info__item"
                            v-for="(field, index) in infoWithImage" :key="index">
                                <span class="player-info-container__player-basic-info__item__label">
                                    {{ field.label }}:
                                </span>
                                <router-link :to="`/teams/${field.teamName}/${field.teamId}`" 
                                v-if="index<infoWithImage.length-1">
                                    <img v-lazy="field.teamLogo">
                                    <span class="player-info-container__player-basic-info__item__value">
                                        {{field.teamName}}
                                    </span>
                                </router-link>
                                <div v-else>
                                    <img v-lazy="field.teamLogo">
                                    <span class="player-info-container__player-basic-info__item__value">
                                        {{field.teamName}}
                                    </span>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="tab-content p-1" id="StatisticsTabsContent">
            <div class="tab-pane fade show player-info-container__player-competition-statistics"
            id="statistics" role="tabpanel" aria-labelledby="table-tab">
                <h1>Statistics</h1>
                <select class="form-select" name="player-competition-name"
                v-model="competitionSelected" 
                @click="getPlayerCompetitionStatistics(competitionSelected)">
                    <option v-for="competition in playerCompetitions" :key="competition[0]"
                    :value="competition[0]">
                        {{ competition[1] }}
                    </option>
                </select>
                <div class="player-info-container__statistics mt-5">
                    <div class="player-info-container__statistics__team-info mb-4">
                        <router-link :to="`/teams/${playerCompetitionStatistics.team.name}/${playerCompetitionStatistics.team.id}`"
                         class="player-info-container__statistics__team-info__team-brand">
                            <img v-lazy="playerCompetitionStatistics.team.logo" 
                            class="player-info-container__statistics__team-info__team-logo" />
                            <h4 class="player-info-container__statistics__team-info__team-name mb-0">
                            {{ playerCompetitionStatistics.team.name }}
                            </h4>
                        </router-link>
                        <router-link :to="`/competitions/${playerCompetitionStatistics.competition.id}`" 
                        class="player-info-container__statistics__team-info__competition-brand">
                            <h5 class="player-info-container__statistics__team-info__competition-name mb-0">
                            {{ playerCompetitionStatistics.competition.name }}
                            </h5>
                            <img v-lazy="playerCompetitionStatistics.competition.logo" 
                            class="player-info-container__statistics__team-info__competition-logo" />
                        </router-link>
                    </div>
                    <section class="player-info-container__statistics__stats-section mb-4"
                    v-for="section in playerCompetitionStatisticsSections" :key="section">
                        <h5 class="player-info-container__statistics__stats-section__section-title">{{section.title}}</h5>
                        <div class="player-info-container__statistics__stats-section__row g-3">
                            <div class="col-md-3 player-info-container__stat-item"  
                            v-for="stats in section.fields" :key="stats">
                               {{stats.label}}: <span>{{stats.value}}</span>
                            </div>
                        </div>
                    </section>
                </div>

            </div>
        </div>

        <div class="tab-content p-1" id="CareerTabsContent">
            <div class="tab-pane fade show player-info-container__player-career"
            id="career" role="tabpanel" aria-labelledby="table-tab">
                <h1>Career</h1>
                <div class="player-info-container__player-career__row row row-cols-1 row-cols-md-2 
                row-cols-lg-3 g-4" v-if="playerTeamsCareer">
                    <div class="player-info-container__player-career__row__col col" 
                    v-for="team in playerTeamsCareer" :key="team">
                        <div class="player-info-container__player-career__row__col__career-item card h-100">
                            <router-link :to="`/teams/${team.team.name}/${team.team.id}`" 
                            class="card-body d-flex align-items-center">
                                <img v-lazy="team.team.logo" class="team-logo me-3">
                                <div>
                                    <h5 class="mb-1">{{team.team.name}}</h5>
                                    <p class="seasons mb-0" v-if="team.seasons && team.seasons.length>0">
                                        <span v-for="(season,index) in team.seasons" :key="season">
                                            {{ season }}
                                            <span v-if="index<team.seasons.length-1">, </span>
                                        </span>
                                    </p>
                                    <p class="seasons mb-0" v-else>No hay información disponible</p>
                                </div>
                            </router-link>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
    import { useFetch } from '@/composables/useFetch';
    import { ref, onMounted, computed, watch } from 'vue';
    import ComponentHeader from '@/components/ComponentHeader.vue';
    import { useRoute } from 'vue-router';

    const route=useRoute();
    const playerId=route.params.id;

    const player=ref(null);
    const playerName=ref();
    const getPlayerById=async()=>{
        const {data,error}=await useFetch(`/api/players/${playerId}`);
        if(error){
            console.log("No se ha encontrado ningún jugador con ID: "+playerId);
        }
        player.value=data.value;
        playerName.value=player.value?.firstname+" "+player.value?.lastname;
    }

    const infoFields = computed(() => {
        if (!player.value) {
            return [];
        }
        
        return [
            { label: 'Position', value: player.value.position },
            { label: 'Full name', value: playerName },
            { label: 'Birthday', value: player.value.birthday },
            { label: 'Age', value: player.value.age },
            { label: 'Height', value: player.value.height },
            { label: 'Weight', value: player.value.weight },
            { label: 'Injured', value: player.value.isInjured ? 'Yes' : 'No',
            valueClass: player.value.isInjured ? 'text-danger' : 'text-success' }
        ];
    });

    const infoWithImage = computed(() => {
        if (!player.value) {
            return [];
        }
        return [
            { label: 'Team', teamId: player.value.playerTeam.id, 
            teamName: player.value.playerTeam.name, teamLogo:player.value.playerTeam.logo },

            { label: 'Nationality', teamId: player.value.playerTeam.id, 
            teamName: player.value.nationality.name, teamLogo:player.value.nationality.flag},
        ];
    });

    const playerCompetitions=ref([]);
    const getPlayerCompetitions=()=>{
        const competitions=new Map();
        player.value.playerCompetitionStatistics.forEach(competition=>{
            const competitionId=competition.competition.id;
            const competitionName=competition.competition.name;

            if(!competitions.has(competitionId)){
                competitions.set(competitionId,competitionName)
            }
            
            playerCompetitions.value=Array.from(competitions.entries());
        });
    }
    //Función para sacar los nombres de todas las competiciones.

    const playerCompetitionStatistics=ref();
    const competitionSelected=ref("");
    
    const getPlayerCompetitionStatistics=(id)=>{
        const competitionStatistics=player.value.playerCompetitionStatistics
        .find((competition)=>competition.competition.id==id);

        if(competitionStatistics){
            playerCompetitionStatistics.value=competitionStatistics;
        }
    };
    //Esta función saca las estadísticas de un jugador de una sola de las competiciones que juega.

    watch(player, ()=>{
        if(player.value && !competitionSelected.value){

            competitionSelected.value=player.value.playerCompetitionStatistics[0].competition.id;
            getPlayerCompetitionStatistics(competitionSelected.value);

            console.log(playerCompetitionStatistics)
        }
    });
    //Cuando cambie el valor de "player", le asignará uno "por defecto" a "competitionSelected".

    
    const playerCompetitionStatisticsSections = computed(() => {
        const playerStats = playerCompetitionStatistics.value;
        if (!playerStats) return [];

        return [
            {
            title: "Games",
            fields: [
                { label: "Appearances", value: playerStats.gamesAppearences },
                { label: "Lineups", value: playerStats.gamesLineups },
                { label: "Minutes", value: playerStats.gamesMinutes },
                { label: "Rating", value: playerStats.gamesRating.toFixed(2) }
            ]
            },
            {
            title: "Substitutes",
            fields: [
                { label: "Substitutes In", value: playerStats.substitutesIn },
                { label: "Substitutes Out", value: playerStats.substitutesOut },
                { label: "Bench", value: playerStats.substitutesBench }
            ]
            },
            {
            title: "Shooting",
            fields: [
                { label: "Total Shots", value: playerStats.totalShots },
                { label: "Shots on Target", value: playerStats.shotsOn },
                { label: "Goals", value: playerStats.totalGoals },
                { label: "Assists", value: playerStats.assists }
            ]
            },
            {
            title: "Passing",
            fields: [
                { label: "Total Passes", value: playerStats.totalPasses },
                { label: "Key Passes", value: playerStats.keyPasses },
                { label: "Accuracy", value: `${playerStats.accuracyPasses}%` }
            ]
            },
            {
            title: "Defense & Duels",
            fields: [
                { label: "Tackles", value: playerStats.totalTackles },
                { label: "Blocks", value: playerStats.blocksTackles },
                { label: "Interceptions", value: playerStats.interceptionsTackles },
                { label: "Duels", value: playerStats.totalDuels },
                { label: "Duels Won", value: playerStats.duelsWon }
            ]
            },
            {
            title: "Goalkeeping",
            fields: [
                { label: "Goals Conceded", value: playerStats.concededGoals },
                { label: "Saves", value: playerStats.saves }
            ]
            },
            {
            title: "Dribbles",
            fields: [
                { label: "Attempts", value: playerStats.dribblesAttempts },
                { label: "Success", value: playerStats.dribblesSuccess }
            ]
            },
            {
            title: "Fouls",
            fields: [
                { label: "Fouls Drawn", value: playerStats.foulsDrawn },
                { label: "Fouls Committed", value: playerStats.foulsCommitted }
            ]
            },
            {
            title: "Cards",
            fields: [
                { label: "Yellow Cards", value: playerStats.yellowCards },
                { label: "Yellow-Red Cards", value: playerStats.yellowRedCards },
                { label: "Red Cards", value: playerStats.redCards }
            ]
            },
            {
            title: "Penalties",
            fields: [
                { label: "Penalties Won", value: playerStats.penaltiesWon },
                { label: "Penalties Committed", value: playerStats.penaltiesCommited },
                { label: "Penalties Scored", value: playerStats.penaltiesScored },
                { label: "Penalties Missed", value: playerStats.penaltiesMissed },
                { label: "Penalties Saved", value: playerStats.penaltiesSaved }
            ]
            },
            {
            title: "Captain",
            fields: [
                { label: "Is Captain", value: playerStats.captain ? 'Yes' : 'No' }
            ]
            }
        ];
    });

    const playerTeamsCareer=ref([]);
    const getPlayerTeamsCareer=async()=>{
        const {data, error}=await useFetch(`/api/playerCareer/${playerId}`);
        if(error){
            console.log("No se han encontrado los equipos del jugador con ID: "+playerId);
        }
        playerTeamsCareer.value=data.value;
        playerTeamsCareer.value.map((team)=>{
            if(!team.seasons){
                return;
            }
            return team.seasons.sort((a,b)=>a-b);
        });
        //Ordenamos los temporadas.
    }
    //Función que saca todos los equipos en los que ha estado un jugador con un ID especificado.

    onMounted(async()=>{
        await getPlayerById();
    });
</script>