<template>  
    <h2>{{ title }}</h2>
    <div class="table-responsive competition-info-container__statistic-table" 
    v-if="players && players.length>0">
        <table class="table table-striped">
            <thead class="table-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Jugador</th>
                    <th scope="col">Equipo</th>
                    <th scope="col" v-if="players[0].totalGoals">Goles</th>
                    <th scope="col" v-if="players[0].assists">Asistencias</th>
                    <th scope="col" v-if="players[0].yellowCards || players[0].redCards">
                        Número de tarjetas
                    </th>
                </tr>
            </thead>
            <tbody class="text-center">
                <tr v-for="(player,index) in players" :key="player.player.id">
                    <td><span>{{ index +1 }}</span></td>
                    <td>
                        <router-link :to="`players/${player.player.id}`">
                            <img 
                            class="competition-info-container__statistic-table__player-photo 
                            img-fluid" :src="player.player.photo">
                           {{player.player.name}}
                        </router-link>
                    </td>
                    <td>
                        <router-link :to="`/teams/${player.team.id}`">
                            <img class="img-fluid" :src="player.team.logo"/>
                            {{ player.team.name }}
                        </router-link>
                    </td>
                    <td v-if="player.totalGoals"><span>{{ player.totalGoals }}</span></td>
                    <td v-else-if="player.assists"><span>{{ player.assists }}</span></td>
                    <td v-else-if="player.yellowCards">{{ player.yellowCards }}</td>
                    <td v-else-if="player.redCards">{{ player.redCards }}</td>
                </tr>

            </tbody>
        </table>
    </div>
</template>

<script setup>
    defineProps({
        title:{
            type:String,
            required:true
        },
        players: {
            type: Array,
            required: true
        }
    });
</script>