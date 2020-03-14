package server;
import entity.Room;

public interface ServerInterface {

	/** Constante nom du Room du server*/
	String ROOM_NAME = "MT98SAPP";
	/** Constante port du server*/
	int SERVER_PORT = 80;
	/** Constante @IP ou DNS du server*/
	String SERVER_DNS = "localhost";
	/** Constante alias du server servant aux appelles de methodes distantes */
	String SERVER_ALIAS = "chatroom";

	Room getRoom();

	void setRoom(Room room);

	/** Identifie le chatter et le connecte au room */
	boolean login(String pseudo, String password);

	/** Deconnecte le chatteur du room */
	boolean logout(String pseudo, String password);

	/** Inscrit un nouveau chatter au room */
	boolean subscribe(String nom, String prenom, String pseudo, String password);

	/** Desincrit un chatter du room */
	boolean unsubscribe(String pseudo, String password);

	/** Retourne l'ensemble des pseudo des chatters du room à un de ces derniers
	 */
	String[] chatters(String pseudo);

	/** Permet de diffuser un message d'un chatter dans le room*/
	boolean broadcasting(String message, String pseudo);

	/** Permet de charger l'ensemble des anciens et nouveaux messages pour un chatter*/
	Object[] discover(String pseudo);

}