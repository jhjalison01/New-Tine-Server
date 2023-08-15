package com.umc.NewTine.domain;

<<<<<<< HEAD
import javax.persistence.*;

=======
>>>>>>> origin/feature/#17-set-habit
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
import javax.persistence.*;
=======
>>>>>>> origin/feature/#17-set-habit
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "press")
public class Press extends BaseTimeEntity{
<<<<<<< HEAD
=======

>>>>>>> origin/feature/#17-set-habit

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String image;

    private int subscriber;

<<<<<<< HEAD

=======
>>>>>>> origin/feature/#17-set-habit
}
