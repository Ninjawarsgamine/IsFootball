import { createRouter, createWebHistory } from 'vue-router';
import EquiposComponents from '@/components/EquiposComponents.vue';  
import TestComponent from '@/components/TestComponent.vue';

const routes = [
  { path: '/', component: TestComponent }, 
  { path: '/equipos', component: EquiposComponents },
];
//Aquí van a estar las rutas de la aplicación. 

const router = createRouter({
  history: createWebHistory(), 
  // Esto gestiona el historial del navegador
  routes
});
//Este objeto es el que va a gestionar las rutas.

export default router;

//Archivo de configuración para las rutas.