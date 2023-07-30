import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.StringUtils;
import org.players.dal.PlayerRepository;
import org.players.exception.InvalidPlayerException;
import org.players.model.Player;
import org.players.service.PlayerService;
import org.players.utils.CsvUtil;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private CsvUtil csvUtil;

    @Test
    void getPlayer_valid() throws InvalidPlayerException {
        String playerIdValid = "Player_valid";
        Player player = new Player();
        player.setPlayerId(playerIdValid);
        Mockito.when(playerRepository.findById(playerIdValid))
                .thenReturn(Optional.of(player));
        PlayerService playerService = new PlayerService(playerRepository, csvUtil);
        player = playerService.getPlayerById(playerIdValid);
        assertEquals(player.getPlayerId(), playerIdValid);
    }

    @Test
    void getPlayer_not_valid() {
        String playerIdInvalid = "Player_invalid";
        PlayerService playerService = new PlayerService(playerRepository, csvUtil);
        Throwable exception = assertThrows(InvalidPlayerException.class, () -> playerService.getPlayerById(playerIdInvalid));
        assertTrue(StringUtils.hasLength(exception.getMessage()));
    }

}
