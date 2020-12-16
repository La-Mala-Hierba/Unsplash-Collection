package com.heiya.prueba.controller;

import com.heiya.prueba.model.UnsplashCollection;
import com.heiya.prueba.service.impl.CollectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("oauth")
public class CollectionController {


    @Autowired
    private CollectionServiceImpl collectionServiceImpl;

    @GetMapping("collections")
    public ResponseEntity<String> getCollections(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "per_page", defaultValue = "10") int per_page) throws IOException, InterruptedException {
        return Optional.ofNullable(this.collectionServiceImpl.getCollections(page, per_page))
                .map(collections -> ResponseEntity.ok().body(collections))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("collection/all")
    public ResponseEntity<List<UnsplashCollection>> getCollectionFilter(@RequestParam(value = "filter", required = false) String filter) throws IOException, InterruptedException {
        //this.collectionServiceImpl.saveAllCollections(); //only save once
        return Optional.ofNullable(this.collectionServiceImpl.getCollectionByFilter(filter))
                .map(UnsplashCollectiones -> ResponseEntity.ok().body(UnsplashCollectiones))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
