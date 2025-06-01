<template>  
    <h2>{{ title }}</h2>
    <div></div>
    <div class="table-responsive competition-info-container__statistic-table" 
    v-if="players && players.length>0">
        <table class="table table-striped">
            <thead class="table-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col" class="text-start">Player</th>
                    <th scope="col" class="text-start">Team</th>
                    <th scope="col" v-if="players[0].totalGoals">Goals</th>
                    <th scope="col" v-if="players[0].assists">Assists</th>
                    <th scope="col" v-if="players[0].yellowCards || players[0].redCards">
                        Number of Cards
                    </th>
                    <th>Matches played</th>
                </tr>
            </thead>
            <tbody class="text-center">
                <tr v-for="(player,index) in players" :key="player.player.id">
                    <td><span>{{ index +1 }}</span></td>
                    <td>
                        <router-link :to="`/players/${player.player.id}`">
                            <img 
                            class="competition-info-container__statistic-table__player-photo 
                            img-fluid" v-lazy="player.player.photo">
                           {{player.player.name}}
                        </router-link>
                    </td>
                    <td>
                        <router-link :to="`/teams/${player.team.name}/${player.team.id}`">
                            <img class="img-fluid" v-lazy="player.team.logo"/>
                            {{ player.team.name }}
                        </router-link>
                    </td>
                    <td v-if="player.totalGoals"><span>{{ player.totalGoals }}</span></td>
                    <td v-else-if="player.assists"><span>{{ player.assists }}</span></td>
                    <td v-else-if="player.yellowCards"><span>{{ player.yellowCards }}</span></td>
                    <td v-else-if="player.redCards"><span>{{ player.redCards }}</span></td>
                    <td><span>{{ player.gamesAppearences }}</span></td>
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