<template>

    <h4>{{ title }}</h4>

    <h6 class="font-bold text-sart">General</h6>
    <div class="table-responsive mb-3">
        <table class="table table-bordered text-center align-middle">
        <thead class="table-light">
            <tr>
            <th></th>
            <th v-for="(type) in  goalsDistribution" :key="type[0]">
                {{ type[0].charAt(0).toUpperCase() + type[0].slice(1) }}
            </th>
            </tr>
        </thead>
        <tbody>
            <tr>
            <td class="fw-bold bg-white">Goals</td>
            <td v-for="(type) in  goalsDistribution" :key="type[0]">
                {{ type[1] }}   
            </td>
            </tr>
            <tr>
            <td class="fw-bold bg-white">Goals's average</td>
            <td v-for="(type) in  goalsAverage" :key="type[0]">
                {{ type[1] }}
            </td>
            </tr>
        </tbody>
        </table>
    </div>

    <h6>Goals by minute</h6>
    <div class="table-responsive mb-3">
        <table class="table table-bordered text-center align-middle">
        <thead class="table-light">
            <tr>
                <th>Minute</th>
                <th>Total</th>
                <th>%</th>
            </tr>
        </thead>
        <tbody> 
            <tr v-for="(type,data) in goalsMinutes" :key="data">
                <td> {{ type[0] }}</td>
                <td>{{ type[1].total !== null ? type[1].total : '-' }}</td>
                <td>{{ type[1].percentage !== null ? type[1].percentage : '-' }}</td>
            </tr>
        </tbody>
        </table>
    </div>
    <h6>Over / Under Goal Stats</h6>
    <div class="table-responsive">
        <table class="table table-bordered text-center align-middle">
        <thead class="table-light">
            <tr>
                <th>Goal Line</th>
                <th>Over</th>
                <th>Under</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(type) in goalsUnderOver" :key="type[0]" >
                <td> {{ type[0] }}</td>
                <td>{{ type[1].over !== null ? type[1].over : '-' }}</td>
                <td>{{ type[1].under !== null ? type[1].under : '-' }}</td>
            </tr>
        </tbody>
        </table>
    </div>

</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  goalsData: {
    type: Object,
    required: true
  }
});

const goalsDistribution = computed(()=>{
    return Object.entries(props.goalsData.distribution);
});

const goalsAverage=computed(()=>{
    return Object.entries(props.goalsData.average)
});

const goalsMinutes=computed(()=>{
    return Object.entries(props.goalsData.minutes);
});

const goalsUnderOver=computed(()=>{
    return Object.entries(props.goalsData.underOver);
})
</script>


