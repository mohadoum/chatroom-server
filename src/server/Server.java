package server;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;
import org.apache.xmlrpc.WebServer;

import entity.Message;
import entity.Room;
import entity.User;

/** <b>Point d'entree de l'application</b>
 * <b>Server d'application disposant de methodes utiles aux clients distants</b>
 * <i>Server RPC (base sur XML et HTTP)</i>
 * <i>Les methodes distantes aux clients retournent forcement un valeur </i>
 * <i>Les parametres et valeur de retour de methodes distantes ont des types valides pour RPC afin qu'ils puissent etre serialises</i> 
 * @author Alhamdoulilah!
 * @author thiandoummohammed@gmail.com
 * @since 17/10/2019
 *
 */
public class Server implements ServerInterface {    
	/** Room du server */ 
	private Room room;
	
	/** <b> Point d'entree de l'application </b> 
	 * Parametre le serveur et le lance
	 */
	public static void main(String [] args){  
		try { 
			System.out.println("Attempting to start XML-RPC Server For the ChatRoom...");  
			WebServer server = new WebServer(SERVER_PORT);  
			server.addHandler(SERVER_ALIAS, new Server(new Room(ROOM_NAME)));  
			server.start();  
			System.out.println("Started successfully.");  
			System.out.println("Accepting requests. (Halt program to stop.)");  
		}  
		catch (Exception exception){ 
			System.err.println("JavaServer: " + exception); 
		}  
		
	}
	
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Server(Room room) {
		this.setRoom(room);
	}
	
	/** Identifie le chatter et le connecte au room */
	@Override
	public boolean login(String pseudo, String password)
	{
		HashMap<String, User> users = this.getRoom().getUsers();
		if(users.containsKey(pseudo))
		{
			if(users.get(pseudo).getPassword().equals(password))
			{
				users.get(pseudo).setConnected(true);
				return true;
			}
		}
		return false;
	}
	
	/** Deconnecte le chatteur du room */
	@Override
	public boolean logout (String pseudo, String password)
	{
		HashMap<String, User> users = this.getRoom().getUsers();
		if(users.containsKey(pseudo))
		{
			if(users.get(pseudo).getPassword().equals(password))
			{
				users.get(pseudo).setConnected(false);
				return true;
			}
		}
		return false;
	}
	
	/** Inscrit un nouveau chatter au room */
	@Override
	public boolean subscribe(String nom, String prenom, String pseudo, String password)
	{
		User usr = new User(nom, prenom, pseudo, password);
		return this.getRoom().addUser(usr);
	}
	
	/** Desincrit un chatter du room */  
	@Override
	public boolean unsubscribe(String pseudo, String password)
	{
		HashMap<String, User> users = this.getRoom().getUsers();
		if(users.containsKey(pseudo))
		{
			if(password.equals(users.get(pseudo).getPassword()))
			{
				users.remove(pseudo);
				return true;
			}
		}
		return false;
	}
	
	/** Methode Privee du serveur
	 * Verifie la disponibilite d'un chatter
	 */
	private boolean connected(String pseudo)
	{
		HashMap<String, User> users = this.getRoom().getUsers();
		User usr = users.get(pseudo);
		if(usr != null && usr.getConnected()== true)
		{
			return true;
		}
		return false;
	}
	
	
	/** Retourne l'ensemble des pseudo des chatters du room à un de ces derniers
	 */
	@Override
	public String[] chatters(String pseudo)
	{
		if(this.connected(pseudo))
		{
			Collection<User> users =  this.getRoom().getUsers().values();
			String[] chatters = new String[users.size()];
			Iterator<User> it = users.iterator();
			int i=0;
			while(it.hasNext())
			{
				chatters[i] = it.next().getPseudo();
				i++;
			}
			return chatters;
		}else
		{
			return new String[0];
		}
		
	}
	
	
	/** Permet de diffuser un message d'un chatter dans le room*/
	@Override
	public boolean broadcasting(String message, String pseudo)
	{
		
		if(this.connected(pseudo))
		{
			this.getRoom().addMessage(new Message(pseudo, message));
			return true;
		}
		return false;
	}
	
	/** Permet de charger l'ensemble des anciens et nouveaux messages pour un chatter*/
	@Override
	public Object[] discover(String pseudo)
	{
		if(this.connected(pseudo))
		{
			
			ArrayList<Object[]> messagesAndViewers = this.getRoom().getMessagesAndViewers();
			if(messagesAndViewers.size()>0)
			{
				Object[] result = new Object[messagesAndViewers.size()];
				
				Iterator<Object[]> it1 = messagesAndViewers.iterator();
				int i=0;
				while(it1.hasNext())
				{
					Object[] obj = it1.next();
					Object[] res = new Object[2];
					Object[] mess = new Object[3];
					mess[0] = ((Message)obj[0]).getPseudo(); 
					mess[1] = ((Message)obj[0]).getContenu(); 
					mess[2] = ((Message)obj[0]).getDate(); 
					
					res[0] = mess;
					if(((HashSet<String>)obj[1]).contains(pseudo))
					{					
						res[1] = new Boolean(true) /* Message lu */;
					}else
					{				
						res[1] = new Boolean(false) /* Message non lu */;
						((HashSet<String>)obj[1]).add(pseudo);
					}
					result[i] = res;
					i++;
				}
				
				return result;
			}
		}
		
		return new Object[0];
	}
}
