package com.my.cloud.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

/**
 * 日志实体类
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 10:26
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_logs")
public class Logs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "l_id", nullable = false)
    // 日志id
    private Long id;

    @Column(name = "l_code")
    // 日志状态码
    private Integer code;

    @Column(name = "l_message")
    // 日志信息
    private String message;

    @Column(name = "l_time")
    // 日志时间
    private Date date;
    @ManyToOne
    @JoinColumn(name = "u_id")
    // 日志用户
    private User user;

}
