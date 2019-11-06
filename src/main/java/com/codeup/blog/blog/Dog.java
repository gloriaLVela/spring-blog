package com.codeup.blog.blog;

import javax.persistence.*;

@Entity
//@IdClass(MyKey.class)
@Table(name="dogs")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "int(11) UNSIGNED")
    private long id;

    @Column(nullable = false, columnDefinition = "tinyint(3) UNSIGNED")
    private long age;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "char(2) DEFAULT 'XX'")
    private String resideState;

//
//    CREATE TABLE dogs (
//            id int(11) unsigned NOT NULL AUTO_INCREMENT,
//    age tinyint(3) unsigned NOT NULL,
//    name varchar(200) NOT NULL,
//    reside_state char(2) DEFAULT 'XX',
//    PRIMARY KEY (id),
//    UNIQUE KEY UK_????????????????? (age)
//            ) ENGINE=MyISAM DEFAULT CHARSET=utf8

}
