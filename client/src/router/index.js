import { createRouter, createWebHistory } from 'vue-router';
import WelcomeView from '@/views/WelcomeView.vue';
import CompetitionsSearchView from '@/views/CompetitionsSearchView.vue';  
import CompetitionView from '@/views/CompetitionView.vue';
import ErrorView from '@/views/ErrorView.vue';
import TeamsSearchView from '@/views/TeamsSearchView.vue';
import TeamView from '@/views/TeamView.vue';
import PlayersSearchView from '@/views/PlayersSearchView.vue';
import PlayerView from '@/views/PlayerView.vue';

const routes = [
  { path: '/', component: WelcomeView }, 
  { path:'/competitions', component: CompetitionsSearchView },
  { path: '/competitions/:id',component: CompetitionView },
  { path:'/teams', component:TeamsSearchView },
  { path: '/teams/:name/:id',component: TeamView },
  { path:'/players',component: PlayersSearchView },
  { path:'/players/:id',component: PlayerView },
  { path:'/:pathMatch(.*)*',component: ErrorView }
];
//Aquí están las rutas de la aplicación. 

const router = createRouter({
  history: createWebHistory(), 
  // Esto gestiona el historial del navegador
  routes
});
//Este objeto es el que va a gestionar las rutas.

export default router;
//Archivo de configuración para las rutas.