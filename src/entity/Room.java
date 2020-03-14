package entity;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;

/** <b> Caractéristiques du Room regroupant un ensemble de membres chatteurs </b>
 * 
 * @author Alhamdoulilah!
 * @author thiandoummohammed@gmail.com
 * @since 17/10/2019
 *
 */
public class Room {
	
	/** <b> Nom du Room </b>*/
	private String name;
	
	/** <b> L'ensemble des utilisateurs du Room, contenu dans une liste indexee par le nom des chatters</b>*/
	private HashMap<String, User> users;
	
	/** <b> L'ensemble de chaque message diffuse dans le room et de ses viewers correspondants</b>*/
	private ArrayList<Object[]> messagesAndViewers; 
	
	public Room(String name) {
		this.name = name;
		this.users = new HashMap<String, User>();
		this.messagesAndViewers = new ArrayList<Object[]>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String, User> getUsers() {
		return this.users;
	}

	public void setUsers(HashMap<String, User> users) {
		this.users = users;
	}
	
	public boolean addUser(User usr)
	{
		if(!this.users.containsKey(usr.getPseudo()))
		{
			this.users.put(usr.getPseudo(), usr);
			return true;
		}else
		{
			return false;
		}	
	}
	
	public boolean deleteUser(String pseudo)
	{
		if(this.users.containsKey(pseudo))
		{
			this.users.remove(pseudo);
			return true;
		}else
		{
			return false;
		}
	}
	
	public boolean modifyUser(User usr)
	{
		if(this.users.containsKey(usr.getPseudo()))
		{
			this.users.put(usr.getPseudo(), usr);
			return true;
		}else
		{
			return false;
		}
	}
	
	public ArrayList<Object[]> getMessagesAndViewers() {
		return messagesAndViewers;
	}

	public void setMessagesAndViewers(ArrayList<Object[]> messagesAndViewers) {
		this.messagesAndViewers = messagesAndViewers;
	}

	public void addMessage(Message mess)
	{
		Object[] arr = new Object[2];
		arr[0] = mess;
		arr[1] = new HashSet<String>();
		
		this.getMessagesAndViewers().add(arr);
	}

}
