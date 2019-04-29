package com.dt.open.test.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;

    private Integer age;

    private String password;

    @CreationTimestamp
    private Date createDate;

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public User(String name, Integer age) {
        this.age = age;
        this.name = name;
    }
}