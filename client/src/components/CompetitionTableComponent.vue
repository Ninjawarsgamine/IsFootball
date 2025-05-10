<template>
    <div class="table-responsive" v-if="competitionTeams">
        <table class="table table-hover align-middle text-center">
            <thead class="table-light">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col" class="text-start">Team</th>
                    <th scope="col">MP</th>
                    <th scope="col">W</th>
                    <th scope="col">D</th>
                    <th scope="col">L</th>
                    <th scope="col">GF</th>
                    <th scope="col">GA</th>
                    <th scope="col">GD</th>
                    <th scope="col">Pts</th>
                    <th scope="col">Form</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="team in competitionTeams" :key="team.id">
                    <td>{{ team.rank }}</td>
                    <td class="competition-info-container__team">
                        <router-link :to="`/teams/${team.team.name}/${team.team.id}`">
                            <img class="competition-info-container__team-logo img-fluid" 
                            v-lazy="team.team.logo" >
                            <span>{{ team.team.name }}</span>
                        </router-link>  
                    </td>
                    <td>{{ team.matchesPlayed?.total }}</td>
                    <td>{{ team.matchesWon?.total }}</td>
                    <td>{{ team.matchesDrawn?.total }}</td>
                    <td>{{ team.matchesLost?.total }}</td>
                    <td>{{ team.goalsFor?.distribution?.total }}</td>
                    <td>{{ team.goalsAgainst?.distribution?.total }}</td>
                    <td>{{ team.goalsDiff }}</td>
                    <td class="competition-info-container__team-points">{{ team.points }}</td>
                    <td>
                        <span v-for="(result,index) in team.form" :key="index" class="badge me-1" 
                        :class="{
                            'bg-success': result=='W',
                            'bg-secondary': result=='D',
                            'bg-danger': result=='L'
                        }">
                            {{ result }}
                        </span>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script setup>
    defineProps({
        competitionTeams: {
            type: Array,
            required: true
        }
    });
</script>
