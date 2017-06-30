import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.qainfotech.tap.training.snl.api.Board;
import com.qainfotech.tap.training.snl.api.BoardModel;
import com.qainfotech.tap.training.snl.api.GameInProgressException;
import com.qainfotech.tap.training.snl.api.MaxPlayersReachedExeption;
import com.qainfotech.tap.training.snl.api.NoUserWithSuchUUIDException;
import com.qainfotech.tap.training.snl.api.PlayerExistsException;

public class TestBoard {
	Board b;
	BoardModel model;
	UUID id;
	Object obj;
	JSONObject player = (JSONObject)obj;

	@BeforeTest
	public void load_the_Board() throws FileNotFoundException, UnsupportedEncodingException, IOException {
		b = new Board();
		id=b.getUUID();
		b=new Board(id);
		}

	@Test
	public void adding_a_player()
			throws IOException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption {
		b.registerPlayer("Arpit");
		b.registerPlayer("Ruchir");

		b.getData();
	}

	@Test
	public void adding_new_player_should_throw_AlreadyPlayerExistsExcetion()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException {
		b.registerPlayer("Ruchir");
		b.getData();
	}

	@Test
	public void adding_new_player_should_throw_MaxPlayersReachedExeption()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException {
		b.registerPlayer("himanshu");
		b.registerPlayer("pandey");
		b.registerPlayer("vishnu");
	
		b.getData();
	}

	@Test
	public void deleting_the_player_removes_player() throws FileNotFoundException, 
	UnsupportedEncodingException, NoUserWithSuchUUIDException{
		
		UUID uuid1 = (UUID) b.getData().getJSONArray("players").getJSONObject(2).get("uuid");
		b.deletePlayer(uuid1);	
	}
	
	@Test
	public void deleting_the_player_throws_NoUserWithSuchUUIDException() throws FileNotFoundException,
	UnsupportedEncodingException, NoUserWithSuchUUIDException{
		b.deletePlayer(id);
		
	}
	
		
	
	
	
	
	
	
	
	
}
