package menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import applicazione.Comprensorio;
import persistenza.GestorePersistenza;
import persistenza.LogicaPersistenza;
import utenti.Configuratore;
import utenti.Fruitore;
import util.InputDati;
import util.Menu;


/**
 * Classe dedita ad autenticare gli utenti prima di poter agire all'interno del programma
 * @author Diego Pioli 736160
 *
 */
public class Autenticazione {

	private LogicaPersistenza logica;
	
	private static final String USERNAME_PREDEFINITO = "configuratore";
	private static final String PASSWORD_PREDEFINITA = "password";

	private static final String MSG_ASK_USERNAME = "Inserisci l'username (ESC per uscire) > ";
	private static final String MSG_ASK_PASSWORD = "Inserisci la password (ESC per uscire) > ";

	private static final String MSG_ASK_USER_PREDEF = "Inserisci l'username predefinito > ";
	private static final String MSG_ASK_PSW_PREDEF = "Inserisci la password predefinita > ";
	
	private static final String UTENTE_NON_PRESENTE = "Questo utente non è presente nel sistema. \nTornando indietro...";
	private static final String PSW_ERRATA = "Password errata. (ESC per uscire) ";

	private static final String MSG_NEW_CREDENZIALI = "Inserisci le nuove credenziali di seguito\n\n";
	private static final String MSG_NEW_USERNAME = "Inserisci un nuovo username > ";
	private static final String MSG_NEW_PASSWORD = "Inserisci una nuova password > ";
	
	private static final String MSG_RILEVA_PRIMO_ACCESSO = "\nSembra che tu non sia ancora registrato!\nREGISTRATI >";
	private static final String MSG_RICHIESTA_AUTENTICAZIONE = "Inserire le credenziali di autenticazione: ";

	private static final String MSG_ACCESSO_RIUSCITO = "\nAccesso effettuato con successo -- ";

	private static final String MSG_NON_VALIDO = "Username non valido, utente già registrato nel sistema. Riprova. ";
	
	///////////////////
	private static final String MSG_ASSENZA_COMPRENSORIO = "Non è presente nessun comprensorio, creane uno prima di continuare.";
	private static final String MSG_SELEZ_COMP = "\nScegli il comprensorio a cui appartieni tra quelli presenti";
	private static final String MSG_SUCC_REGIST = "\n\nRegistrazione avvenuta con successo.\n";
	private static final String MSG_INSERISCI_MAIL = "Inserisci la tua mail > ";
	private static final String FORMATO_MAIL_ERRATO = "\nPer piacere, inserire l'indirizzo email nel formato corretto.";
	private static final String FILTER = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+.[A-Za-z]";
	
	private static final String RILEVATA_RICHIESTA_DI_USCITA = "Rilevata richiesta di uscita.";
	private static final String ESC = "ESC";
	
	/**
	 * Costruttore della classe Autenticazione 
	 * Riceve come parametro al logica di persistenza per effettuare i controlli
	 * di validita' delle credenziali
	 * @param logica
	 */
	public Autenticazione(LogicaPersistenza logica) {
		this.logica = logica;
	}
	
	/**
	 * Metodo che permette di accedere alle funzionalita' di un configuratore.
	 * Se rileva a un primo accesso rimanda alla pagina di registrazione.
	 * @return Configuratore
	 */
	public Configuratore accessoConfiguratore() {
		String username = InputDati.leggiStringaNonVuota(MSG_ASK_USERNAME);
		String password;
		
		if(richiedeUscita(username)) {
			System.out.println(RILEVATA_RICHIESTA_DI_USCITA);
			return null;
		}
		
		if(username.equals(USERNAME_PREDEFINITO)) {
			System.out.println(MSG_RILEVA_PRIMO_ACCESSO);
			primoAccessoConfig();
		} 
		
		for(Configuratore c: logica.getConfiguratori()) {
			if(c.getUsername().equals(username)) {
				do{
					password = InputDati.leggiStringaNonVuota(MSG_ASK_PASSWORD);
					if(c.getPassword().equals(password)) {
						System.out.println(MSG_ACCESSO_RIUSCITO + username +"\n");
						return c;
					} else {
						System.out.println(PSW_ERRATA);
					}
				}while(!richiedeUscita(password));
			}
		}
		System.out.println(UTENTE_NON_PRESENTE);
		return null;
	}
	
	/**
	 * Metodo che verifica le credenziali del fruitore
	 * @return Fruitore
	 */
	public Fruitore accessoFruitore() {
		String username = InputDati.leggiStringaNonVuota(MSG_ASK_USERNAME);
		String password;
		if(richiedeUscita(username)) {
			System.out.println(RILEVATA_RICHIESTA_DI_USCITA);
			return null;
		}
		
		for(Fruitore f: logica.getFruitori()) {
			if(f.getUsername().equals(username)) {
				do {
					password = InputDati.leggiStringaNonVuota(MSG_ASK_PASSWORD);
					if(f.getPassword().equals(password)) {
						System.out.println(MSG_ACCESSO_RIUSCITO + username + "\n");
						return f;
					} else {
						System.out.println(PSW_ERRATA);
					}
				} while (!richiedeUscita(password));
			}
		}
		System.out.println(UTENTE_NON_PRESENTE);
		return null;
	}
	
	/***
	 * METODO PER REGISTRARSI PER LA PRIMA VOLTA COME CONFIGUTATORE
	 * 1. Controlla che abbia i permessi.
	 * 2. Inserimento nuove credenziali.
	 * 3. Salvaraggio.
	 */
	public void primoAccessoConfig() {
		
		controllaPredefinite();
			
		System.out.println(MSG_NEW_CREDENZIALI);
		String newUsername = inserisciUsername();
		String newPassword = InputDati.leggiStringaNonVuota(MSG_NEW_PASSWORD);
		
		logica.addConfiguratore(new Configuratore(newUsername, newPassword));
		GestorePersistenza.salvaConfiguratori(logica.getConfiguratori());
	}
	
	/**
	 * METODO PER REGISTRARSI PER LA PRIMA VOLTA COME FRUITORE
	 * 1. Controllo presenza di comprensori
	 * 2. Selezione comprensorio di appartenenza
	 * 3. Inserimento username
	 * 4. Inserimento password
	 * 5. Inserimento mail
	 */
	public void primoAccessoFruit() {
		
		if(logica.getComprensori().isEmpty()) {
			System.out.println(MSG_ASSENZA_COMPRENSORIO);
			return;
		}
		System.out.println(MSG_SELEZ_COMP);
		Comprensorio comp = Menu.selezionaComprensorio(logica.getComprensori());
		
		String newUsername = inserisciUsernameFruit();
		String newPassword = InputDati.leggiStringaNonVuota(MSG_NEW_PASSWORD);
		String mail = inserisciEmail();
		
		logica.addFruitore(new Fruitore(comp, newUsername, newPassword, mail));
		GestorePersistenza.salvaFruitori(logica.getFruitori());
		
		System.out.println(MSG_SUCC_REGIST);
	}

	/***
	 * Metodo che controlla se l'username inserito è già presente nel sistema.
	 * @param username da controllare
	 * @return true se trova corrispondenza nei configuratori salvati
	 */
	public boolean ePresenteConfiguratore(String username) {
		for(Configuratore c: logica.getConfiguratori()) {
			if(c.getUsername().equals(username)) {
				System.out.println(MSG_NON_VALIDO);
				return true;
			}
		}
		return false;
	}
	
	/***
	 * Metodo che si assicura che l'utente, che cerca di accedere come configuratore, inserisca l'username e la password predefinita 
	 * (controlla quindi che abbia l'autorizzazione per accedere).
	 */
	public void controllaPredefinite() {
		boolean predefinito;
		do {
			System.out.println(MSG_RICHIESTA_AUTENTICAZIONE);
			String username = InputDati.leggiStringaNonVuota(MSG_ASK_USER_PREDEF);
			String password = InputDati.leggiStringaNonVuota(MSG_ASK_PSW_PREDEF);
			predefinito = rilevaPrimoAccesso(username, password);
		}while(!predefinito);
	}
	
	/***
	 * Metodo che controlla se username e password inseriti corrispondono a quelli predefiniti.
	 * @param username
	 * @param password
	 * @return true se la corrispondenza è vera
	 */
	public boolean rilevaPrimoAccesso(String username, String password) {
		return (username.equals(USERNAME_PREDEFINITO) && password.equals(PASSWORD_PREDEFINITA)) ? true : false;
	}
	
	/***
	 * Metodo per ricezione stringa di input del nuovo username.
	 * Controlla che la stringa non sia vuota e che non coincida con l'username predefinito del configuratore.
	 * @return stringa username
	 */
	public String inserisciUsername() {
		boolean corretto = false;
		String newUsername = "";
		do {
			newUsername = InputDati.leggiStringaNonVuota(MSG_NEW_USERNAME);
			if(newUsername.equals(USERNAME_PREDEFINITO)) {
				System.out.println(MSG_NON_VALIDO);
			} if (ePresenteConfiguratore(newUsername)) {
				System.out.println(MSG_NON_VALIDO);
			}
			else break;
		} while(!corretto);
		return newUsername;
	}
	
	/**
	 * Metodo per controllare l'unicità del nome del fruitore
	 * @return username valido 
	 */
	public String inserisciUsernameFruit() {
		boolean corretto = false;
		String newUsername = "";
		do {
			newUsername = InputDati.leggiStringaNonVuota(MSG_NEW_USERNAME);
			if(ePresenteConfiguratore(newUsername)) {
				System.out.println(MSG_NON_VALIDO);
			} else if(ePresenteFruitore(newUsername)) {
				System.out.println(MSG_NON_VALIDO);
			} else {
				break;
			}
		} while(!corretto);
		return newUsername;
	}
	
	/**
	 * Metodo per inserire la mail del fruitore
	 * @return mail corretta
	 */
	public String inserisciEmail() {
		String email;
		do {
			email = InputDati.leggiStringaNonVuota(MSG_INSERISCI_MAIL);
			if(controllaEmail(email)) 
				return email;
			else
				System.out.println(FORMATO_MAIL_ERRATO);
		} while (true);
	}
	
	/**
	 * Metodo che effettua il controllo sulla formattazione della mail 
	 * @param daControllare
	 * @return true se formato corretto | false se formato incorretto
	 */
	private boolean controllaEmail(String daControllare) {
		if (daControllare == null || daControllare.isEmpty()) {
            return false;
        }
        String range = FILTER;
        Pattern pattern = Pattern.compile(range);
        Matcher matcher = pattern.matcher(daControllare);
        
        return matcher.matches();
	}
	
	/**
	 * Metodo per verificare la presenza di un fruitore nel sistema
	 * @param username
	 * @return true e' presente | false non e' presente
	 */
	public boolean ePresenteFruitore(String username) {
		for(Fruitore f: logica.getFruitori()) {
			if(f.getUsername().equals(username)) {
				System.out.println(MSG_NON_VALIDO);
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo che verifica se l'input corrisponde alla parola chiave di uscita.
	 * @param input
	 * @return true se corrispondono
	 */
	public boolean richiedeUscita(String in) {
		return in.equalsIgnoreCase(ESC) ? true : false;
	}

}
