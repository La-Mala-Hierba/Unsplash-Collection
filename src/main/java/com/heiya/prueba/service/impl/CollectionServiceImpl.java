package com.heiya.prueba.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.heiya.prueba.config.TokenProperties;
import com.heiya.prueba.model.UnsplashCollection;
import com.heiya.prueba.repository.CollectionRepository;
import com.heiya.prueba.service.CollectionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


@Service
@EnableConfigurationProperties(TokenProperties.class)
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private CollectionRepository collectionRepository;

    public String getCollections(int page, int per_page) throws IOException, InterruptedException {
        // get collection
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = "https://api.unsplash.com/collections?page="+page+"&per_page="+per_page;
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
                .setHeader("Authorization", this.tokenProperties.getToken())
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    public void saveAllCollections() throws IOException, InterruptedException {
        int page = 1;
        int per_page = 400;
        int size = 0;
        do {
            String collections = this.getCollections(page, per_page);
            JSONArray jsonArray = JSON.parseArray(collections);

            for (int i = 0; i < jsonArray.size(); i++) {
                UnsplashCollection unsplashCollection = new UnsplashCollection();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String description = jsonObject.getString("description");
                String title = jsonObject.getString("title");
                String cover_photo_id = jsonObject.getJSONObject("cover_photo").getString("id");
                unsplashCollection.setId(id);
                unsplashCollection.setDescription(description);
                unsplashCollection.setTitle(title);
                unsplashCollection.setCover_photo_id(cover_photo_id);
                this.collectionRepository.save(unsplashCollection);
            }
            page ++;
            size = jsonArray.size();
            System.out.println("the size is: "+per_page);
        }while (size != 0);
    }

    public List<UnsplashCollection> getCollectionByFilter(String filter) {

        Specification<UnsplashCollection> spec = ((root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (StringUtils.isNotBlank(filter)){
                Predicate pId = criteriaBuilder.like(root.get("id").as(String.class), "%" + filter + "%");
                Predicate pDescription = criteriaBuilder.like(root.get("description").as(String.class), "%" + filter + "%");
                Predicate pTitle = criteriaBuilder.like(root.get("title").as(String.class), "%" + filter + "%");
                Predicate pPhotoId = criteriaBuilder.like(root.get("cover_photo_id").as(String.class), "%" + filter + "%");
                return criteriaBuilder.and(predicate, criteriaBuilder.or(pId, pDescription, pTitle, pPhotoId));
            }
            return predicate;
        });
        return this.collectionRepository.findAll(spec);
    }
}
