package org.players.controllers;

import org.players.exception.InvalidPlayerException;
import org.players.model.Player;
import org.players.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/allPlayers")
    public List<Player> saveDepartment() {
        return playerService.fetchAllPlayer();
    }

    @GetMapping("/{playerId}")
    public Player saveDepartment(@PathVariable String playerId) throws InvalidPlayerException {
        return playerService.getPlayerById(playerId);
    }

}