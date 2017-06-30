import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.BoardModel;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.InvalidTurnException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.NoUserWithSuchUUIDException;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;

/**
 * 
 * @author arpit
 *
 */
public class TestBoard {
	Board b;
	BoardModel model;
	UUID id;
	Object obj;
	JSONObject player = (JSONObject) obj;

	@BeforeTest
	public void load_the_Board() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		b = new Board();
		id = b.getUUID();
		b = new Board(id);
	}

	@Test
	public void adding_new_player()
			throws IOException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption {
		b.registerPlayer("arpit");
		b.registerPlayer("ruchir");

		b.getData();
	}

	@Test(expectedExceptions = PlayerExistsException.class)
	public void adding_new_player_should_throw_Already_PlayerExistsException()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException {
		b.registerPlayer("ruchir");
		b.getData();
	}

	@Test(expectedExceptions = MaxPlayersReachedExeption.class)
	public void adding_new_player_should_throw_MaxPlayersReachedExeption()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException {
		b.registerPlayer("Vishnu");
		b.registerPlayer("himanshu");
		b.registerPlayer("Pandey");

		b.getData();
	}

	@Test
	public void deleting_the_player_removes_player()
			throws FileNotFoundException, UnsupportedEncodingException, NoUserWithSuchUUIDException {

		UUID uuid1 = (UUID) b.getData().getJSONArray("players").getJSONObject(2).get("uuid");
		b.deletePlayer(uuid1);
	}

	@Test(expectedExceptions = NoUserWithSuchUUIDException.class)
	public void deleting_the_player_throws_NoUserWithSuchUUIDException()
			throws FileNotFoundException, UnsupportedEncodingException, NoUserWithSuchUUIDException {
		b.deletePlayer(id);

	}

	@Test
	public void roll_the_dice()
			throws FileNotFoundException, UnsupportedEncodingException, JSONException, InvalidTurnException {

		b.rollDice((UUID) ((JSONObject) b.getData().getJSONArray("players").get(0)).get("uuid"));

	}

	@Test(expectedExceptions = InvalidTurnException.class)
	public void roll_the_dice_to_throw_InvalidTurnException()
			throws FileNotFoundException, UnsupportedEncodingException, JSONException, InvalidTurnException {
		b.rollDice(id);

	}

}
