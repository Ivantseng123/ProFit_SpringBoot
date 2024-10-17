package com.ProFit.model.dao.collectionsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.collectionsBean.ImageBean;

public interface ImageRepository extends JpaRepository<ImageBean, Integer> {

}
