import { useLoadingStore } from "@/stores/useLoadingStore";
import { ref } from "vue";

export async function useFetch(url, method='GET',body=null){
    const data=ref(null);
    const loadingStore=useLoadingStore();

    loadingStore.setLoading(true);
    loadingStore.setError(null);

    try{
        const options = {
            method,
            headers: {},
        };
      
        if (method!=='GET' && method!=='HEAD') {
            options.headers['Content-Type'] = 'application/json';
            if(body){
              options.body = JSON.stringify(body);
            }
        }
      
        const response=await fetch(url,options);

        if(response.ok){
            data.value=await response.json();
            console.log(data.value);
        }else{
            loadingStore.setError(`Error: ${response.statusText}`)
        }
        loadingStore.setLoading(false);
    }catch(err){
        loadingStore.setError(`Error: ${err.message}}`)
        console.log(err.message);
    }finally{
        loadingStore.setLoading(false);
    }
    return {data, error:loadingStore.error}
}