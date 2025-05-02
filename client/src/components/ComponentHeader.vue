<template>
    <div class="component-header">
        <img :src="componentLogo" alt="Logo del componente">
        <h1>{{ componentName }}</h1>
        <img class="component-header__component-country img-fluid" v-if="componentCountry?.flag" 
        :src="componentCountry.flag" alt="Bandera del país del componente">
    </div>
</template>

<script setup>
    defineProps({
        componentLogo: {
            type: String,
            required: true
        },
        componentName: {
            type: String,
            required: true
        },
        componentCountry: {
            type: String
        }
  });
</script>