import { createRouter, createWebHistory } from 'vue-router';
import WelcomeComponent from '@/components/WelcomeComponent.vue';
import CompetitionsSearchComponent from '@/components/CompetitionsSearchComponent.vue';  
import CompetitionComponent from '@/components/CompetitionComponent.vue';
import RouteErrorComponent from '@/components/RouteErrorComponent.vue';
import TeamsSearchComponent from '@/components/TeamsSearchComponent.vue';
import TeamComponent from '@/components/TeamComponent.vue';
import PlayersSearchComponent from '@/components/PlayersSearchComponent.vue';
import PlayerComponent from '@/components/PlayerComponent.vue';

const routes = [
  { path: '/', component: WelcomeComponent }, 
  { path:'/competitions',component: CompetitionsSearchComponent },
  { path: '/competitions/:id',component: CompetitionComponent },
  { path:'/teams', component:TeamsSearchComponent },
  { path: '/teams/:name/:id',component: TeamComponent },
  { path:'/players',component: PlayersSearchComponent },
  { path:'/players/:id',component: PlayerComponent },
  { path:'/:pathMatch(.*)*',component: RouteErrorComponent }
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