package org.iplacex.proyectos.discografia.discos;

import org.iplacex.proyectos.discografia.artistas.IArtistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DiscoController {

    @Autowired
    private IDiscoRepository discoRepository;

    @Autowired
    private IArtistaRepository artistaRepository;

    // Método para crear un nuevo disco
    @PostMapping(
            value = "/disco",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandlePostDiscoRequest(@RequestBody Disco disco) {
        if (!artistaRepository.existsById(disco.idArtista)) {  
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
        }
    
        Disco temp = discoRepository.insert(disco);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    // Método para obtener todos los discos
    @GetMapping(
            value = "/discos",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosRequest() {
        List<Disco> discos = discoRepository.findAll();
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }

    // Método para obtener un disco por ID
    @GetMapping(
            value = "/disco/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Disco> HandleGetDiscoRequest(@PathVariable("id") String id) {
        Optional<Disco> temp = discoRepository.findById(id);
        if (!temp.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(temp.get(), HttpStatus.OK);
    }

    // Método para obtener discos por el ID del artista
    @GetMapping(
            value = "/artista/{id}/discos",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Disco>> HandleGetDiscosByArtistaRequest(@PathVariable("id") String idArtista) {
        List<Disco> discos = discoRepository.findDiscosByIdArtista(idArtista);
        if (discos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(discos, HttpStatus.OK);
    }
}
