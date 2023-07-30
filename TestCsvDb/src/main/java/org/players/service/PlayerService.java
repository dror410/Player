package org.players.service;

import jakarta.annotation.PostConstruct;
import org.players.dal.PlayerRepository;
import org.players.exception.InvalidPlayerException;
import org.players.model.Player;
import org.players.utils.CsvUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final CsvUtil csvUtil;

    @PostConstruct
    private void init() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("player.csv");
        List<Player> players = csvUtil.csvToPlayers(is);
        playerRepository.saveAll(players);
    }

    @Autowired
    public PlayerService(PlayerRepository playerRepository, CsvUtil csvUtil) {
        this.playerRepository = playerRepository;
        this.csvUtil = csvUtil;
    }

    public List<Player> fetchAllPlayer() {
        return (List<Player>) playerRepository.findAll();
    }

    public Player getPlayerById(String playerId) throws InvalidPlayerException {
        Optional<Player> player = playerRepository.findById(playerId);
        if (!player.isPresent()) {
            throw new InvalidPlayerException("Player with ID: " + playerId + " does not exist.");
        }
        return player.get();
    }

}
