package org.laotie.activiti.entity;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "T_USER")
@DynamicInsert(true)
@DynamicUpdate(true)
public class User implements Serializable {


    @Id
    @Column(name = "USER_ID")
    @GenericGenerator(name = "id",strategy = "assigned")
    @GeneratedValue(generator = "id")
    private Long id;


    @Column(name = "USER_NAME")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
