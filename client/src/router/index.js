import { createRouter, createWebHistory } from 'vue-router';
import WelcomeComponent from '@/components/WelcomeComponent.vue';
import CompetitionsSearchComponent from '@/components/CompetitionsSearchComponent.vue';  

const routes = [
  { path: '/', component: WelcomeComponent }, 
  { path:'/competitions',component: CompetitionsSearchComponent },
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