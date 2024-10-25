package com.ProFit.model.dao.transactionCRUD;

import com.ProFit.model.bean.transactionBean.InvoiceBean;
import com.ProFit.model.dto.transactionDTO.InvoiceDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InvoiceDAORepository extends JpaRepository<InvoiceBean, String> {

    // 查詢所有發票，按日期降序排列
    List<InvoiceBean> findAllByOrderByIssuedDateDesc();

    // 根據 Transaction ID 查詢發票
    List<InvoiceBean> findByUserTransactionBean_TransactionId(String transactionId);

    // 查詢發票號碼
    List<InvoiceBean> findByInvoiceNumber(String invoiceNumber);

    // 根據發票狀態查詢
    List<InvoiceBean> findByInvoiceStatus(String invoiceStatus);

    // 查詢所有有 Transaction ID 的發票
    List<InvoiceBean> findAllByUserTransactionBean_TransactionIdIsNotNull();

    @Query("SELECT new com.ProFit.model.dto.transactionDTO.InvoiceDTO(i.invoiceNumber, t.transactionId, i.invoiceAmount, i.issuedDate, i.invoiceStatus) " +
    	       "FROM InvoiceBean i JOIN i.userTransactionBean t WHERE t.userId = :userId")
    	List<InvoiceDTO> findInvoicesByUserId(@Param("userId") Integer userId);

}
