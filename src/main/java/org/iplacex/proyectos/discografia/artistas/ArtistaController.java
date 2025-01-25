package org.iplacex.proyectos.discografia.artistas;

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
public class ArtistaController {

    @Autowired
    private final IArtistaRepository artistaRepository;

    // Constructor
    public ArtistaController(IArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    // Método para crear un nuevo artista
    @PostMapping(
            value = "/artista",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleInsertArtistaRequest(@RequestBody Artista artista) {
        Artista temp = artistaRepository.insert(artista);
        return new ResponseEntity<>(temp, HttpStatus.CREATED);
    }

    // Método para obtener todos los artistas
    @GetMapping(
            value = "/artistas", 
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<Artista>> HandleGetArtistasRequest() {
        List<Artista> artistas = artistaRepository.findAll();

        return new ResponseEntity<>(artistas, HttpStatus.OK);
    }

    // Método para obtener un artista por ID
    @GetMapping(
            value = "/artista/{id}", 
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleGetArtistaRequest(@PathVariable("id") String id) {
        Optional<Artista> temp = artistaRepository.findById(id);
        if (!temp.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra el artista
        }
        return new ResponseEntity<>(temp.get(), HttpStatus.OK);
    }

    // Método para actualizar un artista por ID
    @PutMapping(
            value = "/artista/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Artista> HandleUpdateArtistaRequest(
        @PathVariable("id") String id, 
        @RequestBody Artista artista
        ){
        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>(null, null, HttpStatus.NOT_FOUND); // Si no se encuentra el artista
        }
        artista._id =id; 
        Artista temp = artistaRepository.save(artista);

        return new ResponseEntity<>(temp, null, HttpStatus.OK);
    }

    // Método para eliminar un artista por ID
    @DeleteMapping(
            value = "/artista/{id}"
    )
    public ResponseEntity<Artista> HandleDeleteArtistaRequest(
        @PathVariable("id") String id) {
        if (!artistaRepository.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si no se encuentra el artista
        }
        Artista temp = artistaRepository.findById(id).get();
        artistaRepository.deleteById(id);
        
        return new ResponseEntity<>(temp, null, HttpStatus.OK); // HttpStatus.GONE
    }
}
