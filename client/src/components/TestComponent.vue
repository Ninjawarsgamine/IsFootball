<template>
    <div>
      <HeaderComponent/>
      <h1>IsFootball</h1>

      <p v-if="mensaje">{{ mensaje }}</p>
    </div>
  </template>
  
  <script setup>
  import { onMounted, ref } from "vue";
  import HeaderComponent from '/home/roberto/Documentos/IsFootball/client/src/components/HeaderComponent.vue';
  const mensaje = ref("");
  
  const getTestMessage = async () => {
    try {
      const response = await fetch("http://localhost:8080/test");
      if (!response.ok) throw new Error("Error al obtener el mensaje");
      mensaje.value = await response.text(); // Como es texto plano, usamos .text()
    } catch (error) {
      console.error("Error al obtener el mensaje:", error);
    }
  };
  onMounted(() => {
        getTestMessage();
    });
  </script>
  