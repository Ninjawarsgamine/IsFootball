<template>
  <div class="match-card-container" :key="match.id" v-if="match?.competition?.logo">
    <div class="match-card-container__header d-flex justify-content-between align-items-start mb-3">
      <div class="match-card-container__competition d-flex align-items-center">
        <router-link :to="`/competitions/${match.competition.id}`">
          <img v-lazy="match.competition.logo" 
          class="match-card-container__competition__logo me-2">
          <span class="match-card-container__competition__name">
            {{ match.competition.name }}
          </span>
        </router-link>
      </div>

      <div class="match-card-container__meta text-end">
        <div class="match-card-container__meta__round fw-semibold">
          {{ match.competitionRound }}
        </div>
        <div class="match-card-container__meta__datetime small text-muted">
          {{ match.date }}
        </div>
        <div class="match-card-container__meta__status fw-bold small" v-if="match.matchLong">
          {{match.matchLong}}
        </div>
        <div class="match-card-container__meta__status text-success small" v-else-if="match.elapsed">
         {{ match.elapsed }}'
        </div>
      </div>
    </div>

    <div class="match-card-container__teams">
      <router-link :to="`/teams/${match.teamHome.name}/${match.teamHome.id}`" 
      class="match-card-container__teams__team me-4">
        <img v-lazy="match.teamHome.logo"
        class="match-card-container__teams__team__logo me-2">
        <span class="match-card-container__teams__team__name me-2">
          {{ match.teamHome.name }}
        </span>
      </router-link>

      <div class="match-card-container__teams__score-container text-center mx-2 d-flex flex-column align-items-center">
        <div class="match-card-container__teams__score-container__score d-flex align-items-center" 
        :class="{'match-card-container__teams__score-container__score__margin': match.penaltiesHome && match.penaltiesAway}">
          <span class="match-card-container__teams__score-container__score__value">
            {{ match.goalsHome }}
          </span>
          <span class="match-card-container__teams__score-container__score__sep mx-1">–</span>
          <span class="match-card-container__teams__score-container__score__value">
            {{ match.goalsAway }}
          </span>
        </div>
        <div class="match-card-container__teams__score-container__penalty text-muted small" 
        v-if="match.penaltiesHome && match.penaltiesAway">
          ({{match.penaltiesHome}} – {{ match.penaltiesAway }})
        </div>
      </div>

      <router-link :to="`/teams/${match.teamHome.name}/${match.teamAway.id}`" 
        class="match-card-container__teams__team match-card-container__teams__team__away d-flex
         align-items-center ms-4">
        <span class="match-card-container__teams__team__name me-2">
          {{ match.teamAway.name }}
        </span>
        <img v-lazy="match.teamAway.logo"
             class="match-card-container__teams__team__logo">
      </router-link>
    </div>
  </div>
</template>

<script setup>
  defineProps({
    match: {
      type: Object,
      required: true
    }
  });
</script>
