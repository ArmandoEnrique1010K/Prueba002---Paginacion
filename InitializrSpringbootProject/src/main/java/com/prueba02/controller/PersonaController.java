package com.prueba02.controller;

import com.prueba02.dto.PersonaDto;
import com.prueba02.entity.PersonaEntity;
import com.prueba02.service.PersonaService;
import com.prueba02.util.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PersonaController {
    
    @Autowired
    private PersonaService personaService;

   
    @GetMapping("/")
    public String listarRegistros(Model modelo) {
        modelo.addAttribute("personas", personaService.getAllRegistros());
        return "index.html";
    }

    @GetMapping("/lista")
    public String listarRegistrosPaginados(@RequestParam(name = "page", defaultValue = "0") 
            int page, Model modelo) {
        
        Pageable pageRequest = PageRequest.of(page, 10);
        Page<PersonaDto> personaDto = personaService.getAllRegistrosPage(pageRequest);
        PageRender<PersonaDto> pageRender = new PageRender<>("/lista", personaDto);
        
        modelo.addAttribute("personas", personaDto);
        modelo.addAttribute("page", pageRender);
        return "listapaginada.html";
    }

    @GetMapping("/true")
    public String listarRegistrosTrue(Model modelo) {
        modelo.addAttribute("personas_true", personaService.getAllRegistrosStateVerdadero(Boolean.TRUE));
        return "custom.html";
    }
    
    @GetMapping("/{id}")
    public String mostrarUnaImagen(@PathVariable("id") Long id, Model modelo) {
        var varUnRegistro = personaService.getRegistro(id);
        modelo.addAttribute("caracteristicas_persona", varUnRegistro);
        return "plantilla.html";
    }
    
    @GetMapping("/nuevo")
    public String mostrarFormularioDeAñadir(Model modelo) {
        PersonaEntity personaEntity = new PersonaEntity();
        modelo.addAttribute("datos_persona", personaEntity);
        return "form.html";
    }

    @PostMapping("/nuevo")
    public String guardarModelo(@ModelAttribute("datos_persona") PersonaDto objetopersona, Model modelo) {
        personaService.createRegistro(objetopersona);
        return "redirect:/";
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        var varUnRegistro = personaService.getRegistro(id);
        modelo.addAttribute("parametros_persona", varUnRegistro);
        return "formeditar.html";
    }

    @PostMapping("/{id}/editar")
    public String guardarDatosModelo(@PathVariable Long id, @ModelAttribute("parametros_persona")
            PersonaDto objetopersona,
            @RequestParam("imagen") MultipartFile imagen, Model modelo) {
        
        // Si hay un archivo que se subio y el archivo no es de tipo image, entonces no se va a guardar el registro.
        if(!imagen.getContentType().startsWith("image/") && !imagen.isEmpty()){
            modelo.addAttribute("parametros_persona", objetopersona);
            return "formeditar.html";
        }


        // Si no hay errores de validación y se seleccionó una nueva imagen, actualizamos el modelo
        if(!imagen.isEmpty()){
            objetopersona.setImagen(imagen);
        } else {
            // Si no se seleccionó una nueva imagen, establecemos la imagen actual del modelo
            // para evitar que se borre la ruta de la imagen anterior al actualizar el modelo
            PersonaDto objetoActual = personaService.getRegistro(id);
            objetopersona.setImagen(objetoActual.getImagen());
        }
        
        // Si no hay errores de validación, actualizamos el modelo
        PersonaDto objetoActualizado = personaService.updateRegistro(objetopersona);
        
        if (objetoActualizado == null){
            // Modelo no encontrado, puedes manejar el caso en consecuencia (por ejemplo, mostrar un mensaje de error)
            return "redirect:/";
        }
        
        return "redirect:/";

    }

    @GetMapping("/{id}/cambioestadoafalse")
    public String cambiarEstadoTrueaFalse(@PathVariable Long id){
        personaService.changeStateFalseRegistro(id);
        return "redirect:/";
    }
    
    @GetMapping("/{id}/cambioestadoatrue")
    public String cambiarEstadoFalseaTrue(@PathVariable Long id){
        personaService.changeStateVerdaderoRegistro(id);
        return "redirect:/";
    }
    
    @GetMapping("/{id}/eliminardefinitivamente")
    public String eliminarDefinitivamente(@PathVariable Long id){
        PersonaDto personaEntity = personaService.getRegistro(id);
        personaService.deleteRegistro(id);
        personaService.eliminarImagen(personaEntity.getRutaImagen());
        return "redirect:/";
    }
    
    // Rutas que se van a usar en la página custom (Muestra los registros con estado true)
    @GetMapping("/custom/{id}/cambioestadoafalse")
    public String cambiarEstadoTrueaFalseCustom(@PathVariable Long id){
        personaService.changeStateFalseRegistro(id);
        return "redirect:/true/";
    }
    
    @GetMapping("/custom/{id}/cambioestadoatrue")
    public String cambiarEstadoFalseaTrueCustom(@PathVariable Long id){
        personaService.changeStateVerdaderoRegistro(id);
        return "redirect:/true/";
    }
    
    @GetMapping("/custom/{id}/eliminardefinitivamente")
    public String eliminarDefinitivamenteCustom(@PathVariable Long id){
        PersonaDto personaEntity = personaService.getRegistro(id);
        personaService.deleteRegistro(id);
        personaService.eliminarImagen(personaEntity.getRutaImagen());
        return "redirect:/true/";
    }
}
