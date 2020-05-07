package com.weimob.user.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author itsNine
 * @create 2020-03-17-14:26
 */
@Data
@Table(name = "m_user")
public class User implements Serializable {

    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    private String username;
    private String password;
    private Date created;
    private String salt;
}
