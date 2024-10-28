package com.ProFit.model.dto.usersDTO;

import java.time.LocalDate;


import java.time.LocalDateTime;
import java.util.Date;
import java.time.LocalDate;

public class UserStatistics_registerTime {

    private Date registerDate; // 用于记录用户的注册日期
    private long count;

    // 添加接收 java.sql.Date 的构造函数
    public UserStatistics_registerTime(Date registerDate, long count) {
        this.registerDate = registerDate;
        this.count = count;
    }


    // Getter 和 Setter 方法
    public Date getRegisterDate() {
        return registerDate;
    }

    public long getCount() {
        return count;
    }
}





