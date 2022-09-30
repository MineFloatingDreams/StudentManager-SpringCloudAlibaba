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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户实体类
 *
 * @Author: zhangjiachen
 * @Date: 2022/9/23 10:15
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id", nullable = false)
    // 用户id
    private Long id;
    @Column(name = "u_username", nullable = false)
    // 用户名
    private String username;
    @Column(name = "u_password", nullable = false)
    // 密码
    private String password;
    @Column(name = "u_email", nullable = false)
    // 邮箱
    private String email;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    // 用户日志
    private List<Logs> logs = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public User(Long id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
