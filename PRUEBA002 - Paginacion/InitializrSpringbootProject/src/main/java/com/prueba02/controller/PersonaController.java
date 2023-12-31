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
    
    /* // RUTA HACIA LA PAGINA "index.html" EN LA QUE SE VA LISTAR TODOS LOS REGISTROS
    @GetMapping("/")
    public String listarRegistros(Model modelo){
        modelo.addAttribute("personas", personaService.getAllRegistros());
        return "index.html";
    } */
    
    // RUTA HACIA LA PAGINA "index.html" EN LA QUE SE VA LISTAR TODOS LOS REGISTROS PAGINADOS
    @GetMapping("/")
    public String listarRegistrosPaginados(@RequestParam(name = "page", defaultValue = "0") 
            int page, Model modelo){
        // VA A MOSTRAR 10 REGISTROS
        // Pageable pageRequest = PageRequest.of(page, 10);
        
        // VA A MOSTRAR 10 REGISTROS ORDENADOS DE FORMA DESCENDENTE (DE MAYOR A MENOR POR SU ID)
        Pageable pageRequest = PageRequest.of(page, 10);

        Page<PersonaDto> personaDto = personaService.getAllRegistrosPaginados(pageRequest);
        // RUTA HACIA EL CONTROLADOR
        PageRender<PersonaDto> pageRender = new PageRender<>("/", personaDto);
        modelo.addAttribute("personas", personaDto);
        modelo.addAttribute("page", pageRender);
        return "index.html";
    }
    
    // RUTA HACIA LA PAGINA "custom.html" EN LA QUE SE VA LISTAR TODOS LOS REGISTROS QUE TENGAN UN ESTADO TRUE
    @GetMapping("/true")
    public String listarRegistrosHabilitados(Model modelo){
        modelo.addAttribute("personas_habilitadas", personaService.getAllRegistrosStateVerdadero(Boolean.TRUE));
        return "custom.html";

    }
    
    // RUTA HACIA LA PAGINA "plantilla.html" EN LA QUE SE VA A MOSTRAR LOS DETALLES DE UN REGISTRO POR SU ID
    @GetMapping("/{id}")
    public String mostrarUnaImagen(@PathVariable("id") Long id, Model modelo) {
        var varUnRegistro = personaService.getRegistro(id);
        modelo.addAttribute("caracteristicas_persona", varUnRegistro);
        return "plantilla.html";
    }
    
    // RUTA HACIA LA PAGINA "form.html" EN LA QUE SE VA A PODER INGRESAR DATOS PARA UN NUEVO REGISTRO
    @GetMapping("/nuevo")
    public String mostrarFormularioDeAñadir(Model modelo) {
        PersonaEntity personaEntity = new PersonaEntity();
        modelo.addAttribute("datos_persona", personaEntity);
        return "form.html";
    }

    // RUTA HACIA LA PAGINA DE INICIO, LUEGO DE HABER INGRESADO CORRECTAMENTE LOS DATOS
    @PostMapping("/nuevo")
    public String guardarModelo(@ModelAttribute("datos_persona") PersonaDto objetopersona, Model modelo) {
        personaService.createRegistro(objetopersona);
        return "redirect:/";
    }
    
    // RUTA HACIA LA PAGINA "formeditar.html" EN LA QUE SE VA A PODER EDITAR UN REGISTRO
    @GetMapping("/{id}/editar")
    public String mostrarFormularioDeEditar(@PathVariable Long id, Model modelo) {
        var varUnRegistro = personaService.getRegistro(id);
        modelo.addAttribute("parametros_persona", varUnRegistro);
        return "formeditar.html";
    }
    
    // RUTA HACIA LA PAGINA DE INICIO, LUEGO DE HABER INGRESADO CORRECTAMENTE LOS DATOS
    @PostMapping("/{id}/editar")
    public String guardarDatosModelo(@PathVariable Long id, @ModelAttribute("parametros_persona")
            PersonaDto objetopersona, @RequestParam("imagen") MultipartFile imagen, Model modelo) {
        
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
    
    // RUTA HACIA LA PAGINA DE INICIO, LUEGO DE CAMBIAR EL ESTADO A FALSE
    @GetMapping("/{id}/cambioestadoafalse")
    public String cambiarEstadoTrueaFalse(@PathVariable Long id){
        personaService.changeStateFalseRegistro(id);
        return "redirect:/";
    }
    
    // RUTA HACIA LA PAGINA DE INICIO, LUEGO DE CAMBIAR EL ESTADO A TRUE
    @GetMapping("/{id}/cambioestadoatrue")
    public String cambiarEstadoFalseaTrue(@PathVariable Long id){
        personaService.changeStateVerdaderoRegistro(id);
        return "redirect:/";
    }
    
    // RUTA HACIA LA PAGINA DE INICIO, LUEGO HABER ELIMINADO EL REGISTRO, ADEMÁS TAMBIEN DE LA IMAGEN
    @GetMapping("/{id}/eliminardefinitivamente")
    public String eliminarDefinitivamente(@PathVariable Long id){
        PersonaDto personaEntity = personaService.getRegistro(id);
        personaService.deleteRegistro(id);
        personaService.eliminarImagen(personaEntity.getRutaImagen());
        return "redirect:/";
    }
    

    
    
}
