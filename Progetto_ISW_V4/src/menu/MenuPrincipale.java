package menu;

import persistenza.LogicaPersistenza;
import utenti.Configuratore;
import utenti.Fruitore;
import util.Menu;

/**
 * Classe per la visualizzazione della schermata principale del programma
 * @author Diego Pioli 736160
 *
 */

public class MenuPrincipale{
	
	private LogicaPersistenza logica;
	private Menu menu;
	
	/**
	 * Menu Iniziale
	 */
	private static final String MSG_BENVENUTO = "   BENVENUTO -- PAGINA INIZIALE";
	private static final String MSG_ACC_CONFIG = "Accedi come configuratore";
	private static final String MSG_ACC_FRUIT = "Accedi come fruitore";
	private static final int CASE_CONFIGURATORE = 1;
	private static final int CASE_FRUITORE = 2;
	private String[] vociIniziali = {MSG_ACC_CONFIG, MSG_ACC_FRUIT};
	
	/**
	 * Menu Autenticazione 
	 */
	private static final String MSG_AUTENT = "\tAUTENTICAZIONE";
	private static final String MSG_PRIMO_ACCESSO = "Registrati";
	private static final String MSG_CONFIG_REGISTRATO= "Accedi";
	private final static String MSG_P_PRECEDENTE = "Ritorna alla pagina iniziale";

	private static final int CASE_PRIMO_ACCESSO = 1;
	private static final int CASE_ACCESSO = 2;
	private static final int CASE_P_INIZIALE = 3;
	private String[] vociAutenticazione = {MSG_PRIMO_ACCESSO, MSG_CONFIG_REGISTRATO, MSG_P_PRECEDENTE};
	
	/**
	 * Voci Menu Configuratore
	 */
	private static final int CASE_N_COMPRENSORIO = 1;
	private static final int CASE_N_GERARCHIA = 2;
	private static final int CASE_V_COMPRENSORI = 3;
	private static final int CASE_V_GERARCHIE = 4;
	private static final int CASE_V_FAT_CONV = 5;
	private static final int CASE_V_PROPOSTE = 6;
	private static final int CASE_V_INSIEMI_CHIUSI = 7;
	private static final int CASE_SALVA = 8;
	private static final int CASE_P_AUTENTICAZIONE = 9;
	private static final int CASE_USCITA = 0;
	
	/**
	 * Voci Menu Fruitore
	 */
	private static final int CASE_NAVIGA = 1;
	private static final int CASE_RICHIEDI_PRESTAZIONI = 2;
	private static final int CASE_RITIRA_PROPOSTE = 3;
	private static final int CASE_V_FRUIT_PROPOSTE = 4;
	private static final int CASE_P_AUT = 5;

	/**
	 * Costruttore della pagina iniziale del programma
	 * @param logica
	 */
	public MenuPrincipale(LogicaPersistenza logica) {
		this.logica = logica;
		this.menu = new Menu(MSG_BENVENUTO, vociIniziali);
	}
	
	/**
	 * Metodo per mostrare le azioni che un utente puo' svolgere.
	 */
	public void azioniMenuPrincipale() {
		int scelta;
		do {
			scelta = menu.scegli();
			switch(scelta) {
			case CASE_CONFIGURATORE:
				autenticazioneConfig();
				break;	
			case CASE_FRUITORE:
				autenticazioneFruit();
				break;
			}
		} while (scelta != CASE_USCITA );
	}
	
	private void autenticazioneFruit() {
		Autenticazione autentic = new Autenticazione(logica);
		Menu menuAccessoFruit = new Menu(MSG_AUTENT, vociAutenticazione);
		int scelta;
		do {
			scelta = menuAccessoFruit.scegli();
			switch(scelta) {
			case CASE_PRIMO_ACCESSO:
				autentic.primoAccessoFruit();
				break;
			case CASE_ACCESSO:
				Fruitore fruit = autentic.accessoFruitore();
				if(fruit != null) {
					MenuFruitore menuFruit = new MenuFruitore(fruit, logica);
					avviaMenuFruitore(menuFruit);
				} else {
					scelta = CASE_P_INIZIALE;
				}
				break;
			case CASE_P_INIZIALE:
				break;
			default:
				System.exit(CASE_USCITA);
			}
		} while (scelta != CASE_P_INIZIALE);
		
	}

	/**
	 * Metodo per autenticare l'utente come configuratore o registrarsi come tale.
	 */
	private void autenticazioneConfig() {
		Autenticazione autentic = new Autenticazione(logica);
		Menu menuAccessoConfig = new Menu(MSG_AUTENT, vociAutenticazione);
		int scelta;
		do {
			scelta = menuAccessoConfig.scegli();
			switch(scelta) {
			case CASE_PRIMO_ACCESSO:
				autentic.primoAccessoConfig();
				break;
			case CASE_ACCESSO:
				Configuratore config = autentic.accessoConfiguratore();
				if(config != null) {
					MenuConfiguratore menuConfig = new MenuConfiguratore(config, logica);
					avviaMenuConfiguratore(menuConfig);
				} else {
					scelta = CASE_P_INIZIALE;
				}
				break;
			case  CASE_P_INIZIALE:
				break;
			default:
				System.exit(CASE_USCITA);
			}
		} while (scelta != CASE_P_INIZIALE);
	}
	
	
	/**
	 * Metodo per mostrare le azione che un configuratore puo' svolgere
	 * @param menuConfig
	 */
	private void avviaMenuConfiguratore(MenuConfiguratore menuConfig) {
		int scelta;
		do {
			scelta = menuConfig.scegli();
			switch(scelta) {
			case CASE_N_COMPRENSORIO:
				menuConfig.creaComprensorio();
				break;
			case CASE_N_GERARCHIA:
				menuConfig.creaGerarchia();
				break;
			case CASE_V_COMPRENSORI:
				menuConfig.visualizzaComprensori();
				break;
			case CASE_V_GERARCHIE:
				menuConfig.visualizzaGerarchie();
				break;
			case CASE_V_FAT_CONV:
				menuConfig.visualizzaFatConv();
				break;
			case CASE_V_PROPOSTE:
				menuConfig.visualizzaProposte();
				break;
			case CASE_V_INSIEMI_CHIUSI:
				menuConfig.visualizzaInsiemiChiusi();
				break;
			case CASE_SALVA:
				menuConfig.salva();
				break;
			case CASE_P_AUTENTICAZIONE:
				break;
			default:
				System.exit(CASE_USCITA);
			}
		} while (scelta != CASE_P_AUTENTICAZIONE);
	}
	
	private void avviaMenuFruitore(MenuFruitore menuFruit) {
		int scelta;
		do {
			scelta = menuFruit.scegli();
			switch(scelta) {
			case CASE_NAVIGA:
				menuFruit.naviga();
				break;
			case CASE_RICHIEDI_PRESTAZIONI:
				menuFruit.richiediPrestazioni();
				break;
			case CASE_RITIRA_PROPOSTE:
				menuFruit.ritiraProposte();
				break;
			case CASE_V_FRUIT_PROPOSTE:
				menuFruit.visualizzaProposte();
				break;
			case CASE_P_AUT:
				break;
			default:
				System.exit(CASE_USCITA);
			}
		} while (scelta != CASE_P_AUT);
		
	}

}
