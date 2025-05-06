<template>
    <div class="player-info-container" v-if="player && playerName && infoFields.length>0">
        <ComponentHeader :componentName="player.name" :componentLogo="player.photo"/>

        <ul class="nav nav-underline mb-3" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="table-tab" data-bs-toggle="tab" 
                data-bs-target="#basicData" type="button" role="tab" aria-selected="true">
                    Basic data
                </button>
            </li>

            <li class="nav-item" role="presentation">
                <button class="nav-link" id="statistics-tab" data-bs-toggle="tab" 
                data-bs-target="#statistics" type="button" role="tab" aria-selected="true">
                    Statistics
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="transfers-tab" data-bs-toggle="tab" 
                data-bs-target="#transfers" type="button" role="tab"
                aria-selected="true">
                   Transfer history
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
                                <div>
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
        
    </div>
</template>

<script setup>
    import { useFetch } from '@/composables/useFetch';
    import { ref, onMounted,computed } from 'vue';
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
        playerName.value=player.value.firstname+" "+player.value.lastname;
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
            { label: 'Team', teamName: player.value.playerTeam.name, 
            teamLogo:player.value.playerTeam.logo },

            { label: 'Nationality', teamName: player.value.nationality.name,
            teamLogo:player.value.nationality.flag},
        ];
    });

    onMounted(async()=>{
        await getPlayerById();
    });
</script>