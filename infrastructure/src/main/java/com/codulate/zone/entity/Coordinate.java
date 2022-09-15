package com.codulate.zone.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coordinate")
@Entity
public class Coordinate {
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    private float x;
    private float y;

    @NotNull
    @ManyToOne
    private Zone zone;
}
