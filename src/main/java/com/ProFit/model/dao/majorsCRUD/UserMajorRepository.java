package com.ProFit.model.dao.majorsCRUD;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ProFit.model.bean.majorsBean.UserMajorBean;
import com.ProFit.model.bean.majorsBean.UserMajorPK;

public interface UserMajorRepository extends JpaRepository<UserMajorBean, UserMajorPK> {

    // 分頁查詢所有 UserMajor
    Page<UserMajorBean> findAll(Pageable pageable);

    // 根據用戶ID分頁查找所有關聯的專業
    Page<UserMajorBean> findByIdUserId(Integer userId, Pageable pageable);

    // 根據專業ID分頁查找所有關聯的用戶
    Page<UserMajorBean> findByIdMajorId(Integer majorId, Pageable pageable);

    // 檢查特定用戶和專業的關聯是否存在
    boolean existsByIdUserIdAndIdMajorId(Integer userId, Integer majorId);

    // 刪除特定用戶和專業的關聯
    void deleteByIdUserIdAndIdMajorId(Integer userId, Integer majorId);
}
