import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.testng.Assert;
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
	@Test(expectedExceptions = GameInProgressException.class)
	public void GameInProgressException_for_already_occuring_game()
			throws MaxPlayersReachedExeption, FileNotFoundException, UnsupportedEncodingException,
			PlayerExistsException, GameInProgressException, IOException, JSONException, InvalidTurnException {
	 b.rollDice((UUID) ((JSONObject) b.getData().getJSONArray("players").get(0)).get("uuid"));
		b.registerPlayer("Alfred");
	}
	@Test
	public void rr_Check_where_the_player_has_to_move_to_on_running_rollDice()
			throws JSONException, InvalidTurnException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, NoUserWithSuchUUIDException {
		b.deletePlayer((UUID) ((JSONObject) b.getData().getJSONArray("players").get(0)).get("uuid"));
		b.registerPlayer("Alfred");
		((JSONObject) b.getData().getJSONArray("players").get(0)).put("position", 0);
		UUID uuid = (UUID) ((JSONObject) b.getData().getJSONArray("players").get(0)).get("uuid");
		Object current_position = ((JSONObject) b.getData().getJSONArray("players").get(0)).get("position");
		JSONObject obj = b.rollDice(uuid);
		Object new_position = ((JSONObject) b.getData().getJSONArray("players").get(0)).get("position");
		Object dice = obj.get("dice");
		Object message = obj.get("message");
		int number = (int) current_position + (int) dice;
		JSONObject steps = null;
		// dice roll
		steps = (JSONObject) b.getData().getJSONArray("steps").get((int) number);
		int type = (Integer) steps.get("type");
		if (type == 2) {
			Assert.assertNotEquals(message, "Player climbed a ladder, moved to " + new_position);
		} else if (type == 0) {
			Assert.assertEquals(message, "Player moved to " + new_position);
		} else if (type == 1) {
			Assert.assertNotEquals(message, "Player was bit by a snake, moved back to " + new_position);
		}
	}

	/**
	 * 
	 */
	@Test
	public void re_Check_wether_toString_method_is_returning_accurate_value() {
		String value = "UUID:" + b.getUuid().toString() + "\n" + b.getData().toString();
		Assert.assertEquals(value, b.toString());
	}

}
