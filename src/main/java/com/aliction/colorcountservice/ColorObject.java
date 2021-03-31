package com.aliction.colorcountservice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.ColumnDefault;


import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name="squares")
public class ColorObject extends PanacheEntityBase{
    @Id
    @SequenceGenerator(
            name = "sq",
            sequenceName = "squares_id_seq",
            allocationSize = 1,
            initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq")
    @ColumnDefault("nextval('squares_id_seq'::regclass)")
    private Long id;
    private int squareId;
    private int boardId;
    private String color;

    public ColorObject() {
    };

    public Uni<Long> save(PgPool client) {
        return client.preparedQuery("INSERT INTO squares (squareId, boardId, color) VALUES ($1, $2, $3) RETURNING id")
                .execute(Tuple.of(squareId, boardId, color)).onItem()
                .transform(pgRowSet -> pgRowSet.iterator().next().getLong("id"));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSquareId() {
        return squareId;
    }

    public void setSquareId(int squareId) {
        this.squareId = squareId;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
