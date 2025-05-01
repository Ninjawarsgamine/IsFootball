import { defineStore } from "pinia";
import { ref } from 'vue';
export const useLoadingStore= defineStore('loadingStore', ()=>{
    const isLoading=ref(false);
    //Variable que va a manejar si está cargando algo o no.

    const error=ref(null);

    const setLoading=(status)=>{
        isLoading.value=status;
    }

    const setError=(message)=>{
        error.value=message;
    }
    return {isLoading, error, setLoading, setError}
});
//Gestionamos los estados de carga con "pinia".