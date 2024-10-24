package com.ProFit.model.dao.servicesCRUD;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ProFit.model.bean.majorsBean.MajorBean;
import com.ProFit.model.bean.servicesBean.ServiceOrderBean;

public interface ServiceOrderRepository extends JpaRepository<ServiceOrderBean, String> {

    // 去頭後查詢數字部分最大值
    @Query("SELECT MAX(CAST(SUBSTRING(s.serviceOrderId, 3) AS int)) FROM ServiceOrderBean s")
    Integer findMaxServiceOrderIdNumber();

    // 新增 serviceOrder 時自動生成 SR101 格式的serviceId
    @Transactional
    default ServiceOrderBean insertServiceOrder(ServiceOrderBean serviceOrder) {
        // 查詢當前最大值的數值部分
        Integer maxServiceOrderIdNumber = findMaxServiceOrderIdNumber();

        // 生成新的serviceOrderId
        int newServiceOrderIdNumber = (maxServiceOrderIdNumber == null) ? 100 : maxServiceOrderIdNumber + 1;
        String newServiceOrderId = String.format("SR%03d", newServiceOrderIdNumber);
        System.out.println("New Service Order ID: " + newServiceOrderId);

        // 設置生成的 serviceOrderId 到 ServiceOrderBean 中
        serviceOrder.setServiceOrderId(newServiceOrderId);

        // 保存服務訂單
        return save(serviceOrder);
    }

    // 搜尋 全部服務訂單


    // 搜尋 服務訂單 根據服務id

    // 修改 服務訂單 boolean

    // 刪除 服務訂單 boolean


   
}
