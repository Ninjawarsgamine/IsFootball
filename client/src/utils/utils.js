export function removeAccents(text) {
    return text.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
}
//Función para quitar los acentos y caracteres especiales de un texto.
  