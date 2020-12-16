package com.heiya.prueba.model;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Data
@ToString
@Entity
@Table(name = "collection_filter")
@Proxy(lazy = false)
public class UnsplashCollection {

    @Id
    private String id;
    private String description;
    private String title;
    @Column(name = "cover_photo_id")
    private String cover_photo_id;


}
