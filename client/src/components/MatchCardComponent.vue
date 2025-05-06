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

      <div class="match-card-container__teams__score d-flex align-items-center mx-2">
        <span class="match-card-container__teams__score__value">
          {{ match.goalsHome }}
        </span>
        <span class="match-card-container__teams__score__sep mx-1">
          –
        </span>
        <span class="match-card-container__teams__score__value">
          {{ match.goalsAway }}
        </span>
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
