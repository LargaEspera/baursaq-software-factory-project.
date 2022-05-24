package com.sf.baursaq.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comm")
@Data
@NoArgsConstructor
public class Comm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commId;
    private String commAuthor;
    @Column(columnDefinition = "TEXT")
    private String commContent;
    private Date commTimestamp;
}
