package org.players.utils;

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
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvUtil {

    private final PlayerRepository playerRepository;

    @Autowired
    public CsvUtil(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public void csvToPlayers(InputStream is) {
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
                Player player = new Player(csvRecord.get(0),
                        csvRecord.get(1), csvRecord.get(2), csvRecord.get(3), csvRecord.get(4),
                        csvRecord.get(5), csvRecord.get(6), csvRecord.get(7), csvRecord.get(8),
                        csvRecord.get(9), csvRecord.get(10), csvRecord.get(11), csvRecord.get(12),
                        csvRecord.get(13), csvRecord.get(14), csvRecord.get(15), csvRecord.get(16),
                        csvRecord.get(17), csvRecord.get(18), csvRecord.get(19), csvRecord.get(20),
                        csvRecord.get(21), csvRecord.get(22), csvRecord.get(23));
                players.add(player);
                batchRow++;
                if (batchRow == batchSize) {
                    playerRepository.saveAll(players);
                    players = new ArrayList<>();
                    batchRow = 0;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage());
        }
    }
}