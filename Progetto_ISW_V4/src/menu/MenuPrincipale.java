package menu;

import java.util.ArrayList;

import applicazione.Categoria;
import applicazione.CategoriaFoglia;
import applicazione.PropostaScambio;
import persistenza.LogicaPersistenza;
import utenti.Configuratore;
import utenti.Fruitore;
import util.InputDati;
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
	private static final int CASE_SALVA = 7;
	private static final int CASE_P_AUTENTICAZIONE = 8;
	private static final int CASE_USCITA = 0;
	
	/**
	 * Voci Menu Fruitore
	 */
	private static final int CASE_NAVIGA = 1;
	private static final int CASE_RICHIEDI_PRESTAZIONI = 2;
	private static final int CASE_VISUALIZZA_PROPOSTE = 3;
	private static final int CASE_RITIRA_PROPOSTE = 4;
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
				visualizzaProposte();
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
			case CASE_VISUALIZZA_PROPOSTE:
				visualizzaProposte();
				break;
			case CASE_RITIRA_PROPOSTE:
				menuFruit.ritiraProposte();
				break;
			case CASE_P_AUT:
				break;
			default:
				System.exit(CASE_USCITA);
			}
		} while (scelta != CASE_P_AUT);
		
	}

	/**
	 * Metodo di visualizzazione delle proposte salvate nel sistema.
	 * 1. L'utente vusualizza le foglie (prestazioni proposte dal sistema) elencate tra cui scegliere.
	 * 2. Il sistema recupera le proposte associate a quella prestazione.
	 */
	public void visualizzaProposte() {
			
		ArrayList<CategoriaFoglia> foglie = logica.getCategorieFoglia();
	
	    if (foglie.isEmpty()) {
	        System.out.println("Nessuna categoria foglia disponibile.");
	        return;
	    }

	    System.out.println("\nSeleziona una categoria foglia:\n");
	    for (int i = 0; i < foglie.size(); i++) {
	        System.out.println((i + 1) + ". " + foglie.get(i).getNome());
	    }

	    int scelta = InputDati.leggiInteroConMINeMAX("Scegli una categoria > ", 1, foglie.size()) - 1;
	    CategoriaFoglia selezionata = foglie.get(scelta);

	    ArrayList<PropostaScambio> tutteProposte = new ArrayList<>();
	    tutteProposte.addAll(logica.getProposteAperte());
	    tutteProposte.addAll(logica.getProposteChiuse());
	    tutteProposte.addAll(logica.getProposteRitirate());
	    
	    ArrayList<PropostaScambio> aperte = new ArrayList<>();
	    ArrayList<PropostaScambio> chiuse = new ArrayList<>();
	    ArrayList<PropostaScambio> ritirate = new ArrayList<>();

	    for (PropostaScambio p : tutteProposte) {
	       
	        if (coinvolgeCategoria(p, selezionata)) {
	        	
	            switch (p.getStato()) {
	                case APERTA -> aperte.add(p);
	                case CHIUSA -> chiuse.add(p);
	                case RITIRATA -> ritirate.add(p);
	            }
	        }
	    }
	    
	    //caso non ci sono proposte
		 if (aperte.isEmpty() && chiuse.isEmpty() && ritirate.isEmpty()) {
		     System.out.println("Non hai proposte registrate.");
		     return;
		     
		 } else {

			// Stampa per ogni stato
			 stampaSezione("PROPOSTE APERTE", aperte);
			 stampaSezione("PROPOSTE CHIUSE", chiuse);
			 stampaSezione("PROPOSTE RITIRATE", ritirate);
			 
		 }
	
	}
	
	/**
	 * Metodo che controlla se una proposta di scambio coinvolge una categoria
	 * @param PropostaScambio
	 * @param categoria selezionata (scelta dal fruitore)
	 * @return vero se o la richiesta o l'offerta coincidono con la richiesta
	 */
	private boolean coinvolgeCategoria(PropostaScambio p, Categoria selezionata) {
	    return p.getNomeRichiesta().equalsIgnoreCase(selezionata.getNome()) ||
	           p.getNomeOfferta().equalsIgnoreCase(selezionata.getNome());
	}
	
	/**
	 * Metodo che stampa sezione di proposte di scambio con fruitori associati
	 * @param titolo
	 * @param proposte
	 */
	private void stampaSezione(String titolo, ArrayList<PropostaScambio> proposte) {
		StringBuffer sb = new StringBuffer();
		sb.append("\n--- " + titolo + " ---");
	    if (proposte.isEmpty()) {
	        System.out.println("\nNessuna proposta in questa sezione.");
	    } else {
	        for (PropostaScambio p : proposte) {
	            sb.append(p);
	            sb.append("Fruitore: " + p.getAssociato().getUsername());
	        }
	        System.out.println(sb.toString());
	    }
	}	
		
}
