package com.application.rest.Controllers;

import com.application.rest.Controllers.DTO.MakerDTO;
import com.application.rest.Entities.Maker;
import com.application.rest.Service.Impl.MakerServiceImpl;
import com.application.rest.Service.iMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/maker")
public class MakerController {
    @Autowired
    private iMakerService makerService;

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Optional<Maker> makerOptional = makerService.findById(id);
        if(makerOptional.isPresent()){
            Maker maker = makerOptional.get();
            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .products(maker.getProducts())
                    .build();
            return ResponseEntity.ok(makerDTO);
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/findAll")
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
    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO){ //RequestBody ---> viene en el cuerpo de la peticion
        if(makerDTO.getName().isBlank())
        {
            return ResponseEntity.badRequest().build();
        }
        makerService.save(Maker.builder().name(makerDTO.getName()).build());
        return ResponseEntity.created(URI.create("/api/maker/save")).build();

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody MakerDTO makerDTO){
        Optional<Maker> makerOptional = makerService.findById(id);
        if(makerOptional.isPresent())
        {
            Maker maker = makerOptional.get();
            maker.setName(makerDTO.getName());
            makerService.save(maker);
            return ResponseEntity.ok("Registro actualizado");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id)
    {
        if(id!=null)
        {
            makerService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }
        return ResponseEntity.badRequest().build();
    }
}
