import { createApp } from 'vue';
import App from './App.vue';
import router from './router'; 
import './assets/css/style.css';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import { createPinia } from 'pinia';
import VueLazyLoad from 'vue3-lazyload';

const pinia = createPinia();

createApp(App)
.use(pinia)
.use(VueLazyLoad)
.use(router)
.mount('#app');
