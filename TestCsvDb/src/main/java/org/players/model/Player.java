package org.players.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @JsonProperty("playerID")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String playerId;
    private Integer birthYear;
    private Integer birthMonth;
    private Integer birthDay;
    private String birthCountry;
    private String birthState;
    private String birthCity;
    private Integer deathYear;
    private Integer deathMonth;
    private Integer deathDay;
    private String deathCountry;
    private String deathState;
    private String deathCity;
    private String nameFirst;
    private String nameLast;
    private String nameGiven;
    private Double weight;
    private Double height;
    private String bats;
    @JsonProperty("throws")
    private String throwss;
    private LocalDate debut;
    private LocalDate finalGame;
    private String retroID;
    private String bbrefID;
}
