package utenti;

import applicazione.Comprensorio;


/**
 * Classe per identificare le proprieta' di un configuratore
 * @author Irene Romano 736566
 *
 */

public class Fruitore {
	
	private String username;
	private String password;
	private String mail;
	private Comprensorio comprensorio;
	
	/**
	 * Costruttore della classe fuitore
	 * @param comprensorio
	 * @param password
	 * @param mail
	 * @param username
	 */
	public Fruitore (Comprensorio comprensorio, String username, String password, String mail) {
		this.username = username;
		this.password = password;
		this.mail = mail;
		this.comprensorio = comprensorio;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Comprensorio getComprensorio() {
		return comprensorio;
	}
	public String getNomeComprensorio() {
		return comprensorio.getNome();
	}

	public void setComprensorio(Comprensorio comprensorio) {
		this.comprensorio = comprensorio;
	}

	@Override
	public String toString() {
		return String.format("Username = %s", this.getUsername());
	}

}
