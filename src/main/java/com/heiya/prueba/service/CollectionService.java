package com.heiya.prueba.service;

import com.heiya.prueba.model.UnsplashCollection;

import java.io.IOException;
import java.util.List;

public interface CollectionService {

    public String getCollections(int page, int per_page) throws IOException, InterruptedException;

    public void saveAllCollections() throws IOException, InterruptedException;

    public List<UnsplashCollection> getCollectionByFilter(String filter);
}
