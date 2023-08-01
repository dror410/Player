package org.players.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.h2.util.StringUtils;
import org.players.dal.PlayerRepository;
import org.players.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CsvUtil {

    private final PlayerRepository playerRepository;

    @Autowired
    public CsvUtil(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void csvToPlayers(InputStream is) {
        NumberFormat nf = NumberFormat.getInstance();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

            List<Player> players = new ArrayList<>();
            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            int batchSize = 1000;
            int batchRow = 0;
            for (CSVRecord csvRecord : csvRecords) {
                if (StringUtils.isNullOrEmpty(csvRecord.get(0))) {
                    continue;
                }
                String playerId = csvRecord.get(0);
                try {
                    Integer birthYear = StringUtils.isNullOrEmpty(csvRecord.get(1)) ? null : Integer.parseInt(csvRecord.get(1));
                    Integer birthMonth = StringUtils.isNullOrEmpty(csvRecord.get(2)) ? null : Integer.parseInt(csvRecord.get(2));
                    Integer birthDay = StringUtils.isNullOrEmpty(csvRecord.get(3)) ? null : Integer.parseInt(csvRecord.get(3));
                    Integer deathYear = StringUtils.isNullOrEmpty(csvRecord.get(7)) ? null : Integer.parseInt(csvRecord.get(7));
                    Integer deathMonth = StringUtils.isNullOrEmpty(csvRecord.get(8)) ? null : Integer.parseInt(csvRecord.get(8));
                    Integer deathDay = StringUtils.isNullOrEmpty(csvRecord.get(9)) ? null : Integer.parseInt(csvRecord.get(9));
                    Double weight = StringUtils.isNullOrEmpty(csvRecord.get(16)) ? null : nf.parse(csvRecord.get(16)).doubleValue();
                    Double height = StringUtils.isNullOrEmpty(csvRecord.get(17)) ? null : nf.parse(csvRecord.get(17)).doubleValue();
                    LocalDate debut = StringUtils.isNullOrEmpty(csvRecord.get(20)) ? null : LocalDate.parse(csvRecord.get(20), DateTimeFormatter.ISO_LOCAL_DATE);
                    LocalDate finalGame = StringUtils.isNullOrEmpty(csvRecord.get(21)) ? null : LocalDate.parse(csvRecord.get(21), DateTimeFormatter.ISO_LOCAL_DATE);

                    Player player = new Player(playerId, birthYear, birthMonth, birthDay, csvRecord.get(4),
                            csvRecord.get(5), csvRecord.get(6), deathYear, deathMonth,
                            deathDay, csvRecord.get(10), csvRecord.get(11), csvRecord.get(12),
                            csvRecord.get(13), csvRecord.get(14), csvRecord.get(15), weight,
                            height, csvRecord.get(18), csvRecord.get(19), debut,
                            finalGame, csvRecord.get(22), csvRecord.get(23));
                    players.add(player);
                    batchRow++;
                    if (batchRow == batchSize) {
                        playerRepository.saveAll(players);
                        players = new ArrayList<>();
                        batchRow = 0;
                    }
                } catch (Exception ex) {
                    log.warn("Failed to parse record with playerId: {}, file: {}, error : {}", playerId, ex.getMessage(), ex.getStackTrace());
                }
            }
        } catch (IOException e) {
            log.error("Failed to parse CSV file: {}, error : {}", e.getMessage(), e.getStackTrace());
        }
    }
}