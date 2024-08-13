package com.crashcource.photoz.clone.web;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.crashcource.photoz.clone.model.Photo;
import com.crashcource.photoz.clone.service.PhotozService;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
// import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;



@RestController
public class PhotozController {

    private final PhotozService photozService;

    public PhotozController(PhotozService photozService) {
        this.photozService = photozService;
    }

    // private List<Photo> db = List.of(new Photo("1","hello.jpg"));

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/photoz")
    public Collection<Photo> getPhotoz() {
        return photozService.get();
    }
    
    @GetMapping("/photoz/{id}")
    public Photo getPhoto(@PathVariable String id) {
        Photo photo = photozService.get(id);
        if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return photo;
    }

    //delete with json
    @DeleteMapping("/photoz/{id}")
    public void deletePhoto(@PathVariable String id) {
        Photo photo = photozService.remove(id);
        if (photo == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    //post with json
    // @PostMapping("/photoz/")
    // public Photo createPhoto(@RequestBody @Valid Photo photo) {
    //     photo.setId(UUID.randomUUID().toString());
    //     photozService.put(photo.getId(), photo);
    //     return photo;
    // }

    //post with browser html
    @PostMapping("/photoz/")
    public Photo create(@RequestPart("data") MultipartFile file) throws IOException {
        return photozService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
    }
}
