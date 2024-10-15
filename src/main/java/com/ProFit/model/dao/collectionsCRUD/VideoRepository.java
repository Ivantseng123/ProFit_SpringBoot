package com.ProFit.model.dao.collectionsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.collectionsBean.VideoBean;

public interface VideoRepository extends JpaRepository<VideoBean, Integer> {

}
