package com.application.rest.Controllers;

import com.application.rest.Controllers.DTO.MakerDTO;
import com.application.rest.Entities.Maker;
import com.application.rest.Exceptions.BadRequestException;
import com.application.rest.Exceptions.ResourceNotFoundException;
import com.application.rest.Service.Impl.MakerServiceImpl;
import com.application.rest.Service.iMakerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/maker")
@RequiredArgsConstructor
public class MakerController {

    private final iMakerService makerService;

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Maker maker = makerService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Maker con id "+ id +"no encontrado"));
        MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .products(maker.getProducts())
                    .build();
        return ResponseEntity.ok(makerDTO);
    }
    @GetMapping("/")
    public ResponseEntity<?> findAll(){
        List<MakerDTO> makerList= makerService.findAll()
                .stream()
                .map(maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .name(maker.getName())
                        .products(maker.getProducts())
                        .build())
                .toList();
        return ResponseEntity.ok(makerList);
    }
    @PostMapping("/")
    public ResponseEntity<Void> save(@RequestBody MakerDTO makerDTO){ //RequestBody ---> viene en el cuerpo de la peticion
        if(makerDTO.getName().isBlank())
        {
            throw new BadRequestException("El nombre del fabricante no puede estar vacio");
        }
        makerService.save(Maker.builder().name(makerDTO.getName()).build());
        return ResponseEntity.created(URI.create("/api/maker/save")).build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody MakerDTO makerDTO){
        Maker maker = makerService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Maker con id" +id + "no encontrado"));
        maker.setName(maker.getName());
        makerService.save(maker);

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id)
    {
        if(id==null)
        {
            throw new BadRequestException("El id no puede ser nulo");
        }
        makerService.deleteById(id);
        return ResponseEntity.badRequest().build();
    }
}
