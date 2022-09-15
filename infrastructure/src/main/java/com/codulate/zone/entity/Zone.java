package com.codulate.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "zone")
@Entity
public class Zone {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Size(min = 3, message = "Characters length does not match")
    private String name;

    @Valid
    @Size(min = 3, message = "Characters length does not match")
    @OneToMany(mappedBy = "zone", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Coordinate> coordinates = new HashSet<>();
}
