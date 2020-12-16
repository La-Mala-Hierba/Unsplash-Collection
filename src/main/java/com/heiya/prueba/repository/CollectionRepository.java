package com.heiya.prueba.repository;

import com.heiya.prueba.model.UnsplashCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<UnsplashCollection, String>, JpaSpecificationExecutor<UnsplashCollection> {
}
