package entity;
import java.util.Date;

/** <b>Description des caracteristiques d'un message entre les chatters</b>
 * @since 17/10/2019
 * @author Alhamdoulilah!
 * @author thiandoummohammed@gmail.com
 * @version 1.0
 */
public class Message {
	
	/** <b>Pseudo du chatter expediteur du message</b> */
	private String pseudo;
	
	/** <b> Contenu du message </b>*/
	private String contenu;
	
	/** <b> Date d'envoi du message </b>*/
	private Date date;
	
	public String getPseudo() {
		return pseudo;
	}
	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Message()
	{
		
	}
	
	public Message(String pseudo, String contenu) {
		this.pseudo = pseudo;
		this.contenu = contenu;
		this.date = new Date();
	}
	
	
}
