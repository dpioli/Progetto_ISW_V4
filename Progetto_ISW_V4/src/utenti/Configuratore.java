package utenti;

/**
 * Classe per identificare le proprieta' di un configuratore
 * @author Erjona Maxhaku 735766
 *
 */
public class Configuratore {
	
	private String username;
	private String password;
	
	/**
	 * Costruttore della classe configuratore
	 * @param username
	 * @param password
	 */
	public Configuratore (String username, String password) {
		this.username = username;
		this.password = password;
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
	
	@Override
	public String toString() {
		return String.format("Username = %s", this.getUsername());
	}
}
