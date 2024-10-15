package com.ProFit.model.dao.collectionsCRUD;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.collectionsBean.CollectionBean;

public interface CollectionRepository extends JpaRepository<CollectionBean, Integer> {

}
