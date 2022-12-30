package com.LearningEnglish.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "words")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "to_English")
    private String toEnglish;
    @Column(name = "to_Bulgarian")
    private String toBulgarian;
    @Column(name = "mistake")
    private int mistake;
    @Column(name = "views")
    private int views;
    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    public Word(String toEnglish, String toBulgarian) {
        this.toEnglish = toEnglish;
        this.toBulgarian = toBulgarian;
    }

}
