package com.application.rest.Controllers;

import com.application.rest.Controllers.DTO.MakerDTO;
import com.application.rest.Entities.Maker;
import com.application.rest.Exceptions.BadRequestException;
import com.application.rest.Exceptions.ResourceNotFoundException;

import com.application.rest.Mapper.MakerMapper;
import com.application.rest.Service.iMakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("api/maker")
@RequiredArgsConstructor
public class MakerController {

    private static final int maxPageSize = 50;
    private final iMakerService makerService;
    private final MakerMapper makerMapper;


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        Maker maker = makerService.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Maker con id "+ id +"no encontrado"));
        return ResponseEntity.ok(makerMapper.toDTO(maker));
    }
    @GetMapping("/")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size){
        if(size>maxPageSize)
        {
            size=maxPageSize;
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<MakerDTO> makerList= makerService.findAll(pageable)
                .map(makerMapper::toDTO);
        return ResponseEntity.ok(makerList);
    }
    @PostMapping("/")
    public ResponseEntity<Void> save(@Valid @RequestBody MakerDTO makerDTO){ //RequestBody ---> viene en el cuerpo de la peticion
        makerService.save(makerMapper.toEntity(makerDTO));
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
