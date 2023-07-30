package org.players.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    @Id
    @JsonProperty("playerID")
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private String playerId;
    private String birthYear;
    private String birthMonth;
    private String birthDay;
    private String birthCountry;
    private String birthState;
    private String birthCity;
    private String deathYear;
    private String deathMonth;
    private String deathDay;
    private String deathCountry;
    private String deathState;
    private String deathCity;
    private String nameFirst;
    private String nameLast;
    private String nameGiven;
    private String weight;
    private String height;
    private String bats;
    @JsonProperty("throws")
    private String throwss;
    private String debut;
    private String finalGame;
    private String retroID;
    private String bbrefID;
}
