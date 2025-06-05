package menu;

import java.util.*;

import applicazione.CampoCaratteristico;
import applicazione.Categoria;
import applicazione.CategoriaFoglia;
import applicazione.Gerarchia;
import applicazione.InsiemeProposteChiuse;
import applicazione.Proposta;
import applicazione.PropostaScambio;
import applicazione.StatoProposta;
import applicazione.TipoProposta;
import persistenza.GestorePersistenza;
import persistenza.LogicaPersistenza;
import utenti.Fruitore;
import util.InputDati;
import util.Menu;

/**
 * Classe per definite il menu delle funzionalità del fruitore
 * 
 * @author Irene Romano 736566
 *
 */
public class MenuFruitore extends Menu{
	
	private static final String MSG_PRESTAZIONE_UGUALE = "Attenzione, hai selezionato la tua richiesta. Seleziona una prestazione diversa.";
	private Fruitore fruit;
	private LogicaPersistenza logica;
	
	private static final String titolo = "\tMENU FRUITORE";
	
	private static final String NAVIGA = "Naviga tra le gerarchie";
	private static final String RICHIEDI_PRESTAZIONI = "Richiedi prestazioni al sistema";
	private final static String MSG_P_PRECEDENTE = "Ritorna alla pagina di autenticazione";
	private static final String X = "\n******************************************";
	private static final String MSG_INIZIALE = "Gerarchie presenti nel tuo comprensorio:";
	private static final String MSG_ASSENZA_GERARCH = "Non ci sono gerarchie presenti per il tuo comprensorio.";
	private static final String MSG_SELEZ_GERARCH = "Seleziona una gerarchia > ";
	private static final String CATEGORIA_CORRENTE = "\nCategoria corrente: ";
	private static final String VALORI_IMPOSTATI = "Valori impostati finora: ";
	private static final String MSG_CATEG_FOGLIA = "Sei arrivato a una categoria foglia: ";
	private static final String MSG_MENU_PRINCIPALE = "Ritorno al menu principale.\n";
	private static final String MSG_ASSENZA_SOTTOCATEG = "Non ci sono sottocategorie. Ritorno al menu principale.";
	private static final String MSG_CAMPO_CARATT = "Campo caratteristico: ";
	private static final String MSG_SOTTOCATEG_DISP = "Sottocategorie disponibili:";
	private static final String DOT = ". ";
	private static final String COLON = ": ";
	private static final String MSG_VOCE_TORNA_INDIETRO = "0. Torna al menu";
	//versione 3
	private static final String MSG_SEL_PRESTAZIONE = "Seleziona la prestazione di interesse: ";
	private static final String MSG_INS_ORE = "Inserisci il numero di ore di questa prestazione che ti interessano:";
	private static final String MSG_SEL_OFFERTA = "Quale prestazione offri in cambio?";
	private static final String MSG_CONFERMA = "Confermi la seguente proposta di scambio?\n";
	private static final String MSG_Y_N = "\nVuoi confermare ";
	private static final String MSG_CHECK_COMPRENSORIO = "\nNon ci sono Gerarchie appartenenti al tuo comprensorio geografico.\n";
	private static final String MSG_ANNULLATO_SCAMBIO = "Hai annullato la proposta di scambio...";
	
	private static String[] vociFruit = {NAVIGA, RICHIEDI_PRESTAZIONI, "Visualizza le tue proposte", "Ritira le tue proposte", MSG_P_PRECEDENTE};
	
	/**
	 * Construttore di MenuFruitore
	 * 
	 * @param fruit
	 * @param logica
	 */
	public MenuFruitore(Fruitore fruit, LogicaPersistenza logica) {
		super(titolo, vociFruit);
		this.fruit = fruit;
		this.logica = logica;
	}
	
	/**
	 * Metodo per navigare in profondita' tra le gerarchie
	 */
	public void naviga() {
		System.out.println(X);
		System.out.println(MSG_INIZIALE);
		ArrayList<Gerarchia> gerarch = new ArrayList<Gerarchia>();
		for(Gerarchia g: logica.getGerarchie()) {
			if(g.getNomeComprensorio().equals(fruit.getNomeComprensorio())) {
				gerarch.add(g);
			}
		}
		Gerarchia gScelta;
		if(gerarch.isEmpty()) {
			System.out.println(MSG_ASSENZA_GERARCH);
			return;
		} else {
			gScelta = selezionaGerarchia(gerarch);
            navigaCategoria(gScelta.getCatRadice(), new HashMap<>()); 
            // Inizializziamo la mappa dei valori di campo per la navigazione
		}	
	}
	
	/**
	 * Metodo per selezionare una gerarchia presente all'interno del comprensorio selezionato
	 * @param gerarch
	 * @return
	 */
	private Gerarchia selezionaGerarchia(ArrayList<Gerarchia> gerarch) {
		for(int i = 0; i < gerarch.size(); i++) {
			System.out.println(i + COLON + gerarch.get(i).getCatRadice().getNome());
		}
		
		int scelta = InputDati.leggiIntero(MSG_SELEZ_GERARCH, 0, gerarch.size() - 1);
		return gerarch.get(scelta);
	}



	/**
	 * Metodo per andare a modificare l'output in base ai percorsi selezionati
	 * @param categoriaCorrente
	 * @param valoriImpostati
	 */
	private void navigaCategoria(Categoria categoriaCorrente, Map<String, String> valoriImpostati) {
	    System.out.println(CATEGORIA_CORRENTE + categoriaCorrente.getNome());

	    if (!valoriImpostati.isEmpty()) {
	        System.out.println(VALORI_IMPOSTATI + valoriImpostati);
	    }

	    if (categoriaCorrente.isFoglia()) {
	        System.out.println(MSG_CATEG_FOGLIA + categoriaCorrente.getNome());
	        System.out.println(MSG_MENU_PRINCIPALE);
	        return;
	    }

	    List<Categoria> sottocategorie = categoriaCorrente.getSottoCateg();
	    if (sottocategorie == null || sottocategorie.isEmpty()) {
	        System.out.println(MSG_ASSENZA_SOTTOCATEG);
	        return;
	    }

	    CampoCaratteristico campo = categoriaCorrente.getCampCaratt();
	    Categoria prossimaCategoria = null;

	    if (campo != null) {
	        System.out.println(MSG_CAMPO_CARATT + campo.getNomeCampo());
	    }

	    prossimaCategoria = selezionaSottoCategoria(sottocategorie);

	    if (prossimaCategoria != null) {
	        navigaCategoria(prossimaCategoria, new HashMap<>(valoriImpostati));
	    }
	}
	
	/**
	 * Metodo per selezionare una sottocategoria presente
	 * @param sottocategorie
	 * @return
	 */
	private Categoria selezionaSottoCategoria(List<Categoria> sottocategorie) {
	    System.out.println(MSG_SOTTOCATEG_DISP);
	    for (int i = 0; i < sottocategorie.size(); i++) {
	        System.out.println((i + 1) + DOT + sottocategorie.get(i).getNome());
	    }
	    
	    System.out.println(MSG_VOCE_TORNA_INDIETRO);
	    int scelta = InputDati.leggiIntero(MSG_SELEZ_GERARCH, 0, sottocategorie.size());
	    if (scelta == 0) return null;
	    return sottocategorie.get(scelta - 1);
	}
	
	/**
	 * Metodo per la formulazione di una richiesta di scambio
	 * 1. il fruitore visualizza le categorie foglia a disposizione
	 * 2. seleziona categoria della richiesta
	 * 3. inserice ore (durata della prestazione d'opera desiderata)
	 * 4. creo oggetto Proposta di tipo richiesta
	 * 5. seleziona quale prestazione offre in cambio
	 * 6. formulo l'offerta
	 * 7. chiedo conferma degli oggetti
	 * 8. se confermato salvo lo scambio (in attesa), salvando anche il fruitore
	 */
	public void richiediPrestazioni() {
		
		ArrayList<CategoriaFoglia> foglie = recuperaFoglieDisponibili();
		
		if(foglie.isEmpty()) {
			System.out.println(MSG_CHECK_COMPRENSORIO);
			return;
		}
	
		stampaPrestazioni(foglie); 
		
		//RICHIESTA
		int scelta = InputDati.leggiInteroConMINeMAX(MSG_SEL_PRESTAZIONE, 1, foglie.size()) - 1;
		double ore = InputDati.leggiDoubleConMinimo(MSG_INS_ORE, 0);
		Proposta richiesta = new Proposta(foglie.get(scelta), TipoProposta.RICHIESTA, ore);

		//OFFERTA
		int incambio;
		do {
			incambio = InputDati.leggiInteroConMINeMAX(MSG_SEL_OFFERTA, 1, foglie.size()) - 1;
			if(incambio == scelta) {
				System.out.println(MSG_PRESTAZIONE_UGUALE);
			}
		}while(incambio == scelta);
		ArrayList<Double> fattori = logica.getFatConversione().prendiRiga(scelta + 2); 
		//prendendo tutti i fdc dalla tabella uscenti da id della prestazione richiesta
	    int valore = (int) (fattori.get(incambio + 2) * ore);
		Proposta offerta = new Proposta(foglie.get(incambio), TipoProposta.OFFERTA, valore);
		
		//SCAMBIO
		PropostaScambio scambio = new PropostaScambio(richiesta, offerta);
		boolean sn = InputDati.yesOrNo(MSG_CONFERMA + scambio.toString() + MSG_Y_N);
		if(sn) { //aggiunto alle proposte aperte
			scambio.setFruitoreAssociato(fruit);
			logica.addPropostaAperta(scambio);
			GestorePersistenza.salvaProposteAperte(logica.getProposteAperte(), logica.getProposte());
			verificaSoddisfacimento(scambio); //controllo che lo scambio possa essere completato
		} else {
			System.out.println(MSG_ANNULLATO_SCAMBIO +  MSG_MENU_PRINCIPALE );
			return;
		}
	}
	
	/**
	 * Metodo che recupera le foglie disponibili nel comprensorio geografico del fruitore,
	 *  partendo dalle gerarchie salvate in LogicaPersistenza.
	 * @return array delle foglie a disposizione del fruitore
	 */
	private ArrayList<CategoriaFoglia> recuperaFoglieDisponibili() {
	    ArrayList<CategoriaFoglia> disponibili = new ArrayList<>();
	    
	    for (Gerarchia g : logica.getGerarchie()) {
	        if (g.getNomeComprensorio().equals(fruit.getNomeComprensorio())) {
	            disponibili.addAll(raccoltaFoglie(g.getCatRadice()));
	        }
	    }

	    return disponibili;
	}
	
	/**
	 * Metodo che contolla le sottocategorie di una categoria, inserendo quelle foglia in un array.
	 * @param categoria da cui ricavare le sottocategorie
	 * @return array di foglie
	 */
	private ArrayList<CategoriaFoglia>  raccoltaFoglie(Categoria cat) {
		ArrayList<CategoriaFoglia> cf = new ArrayList<>();

		if(cat.isFoglia()) {
			CategoriaFoglia foglia = getFogliaDaNome(cat);
			if (foglia != null) {
			    cf.add(foglia);
			}
	    } else if(cat.getSottoCateg() != null) {
	        for (Categoria sotto : cat.getSottoCateg()) {
	            cf.addAll(raccoltaFoglie(sotto));
	        }
	    }
		return cf;
	}
	
	/**
	 * Metodo che restituisce una foglia presente nel file categorieFoglia
	 * @param categoria che verifico sia di tipo foglia
	 * @return foglia 
	 */
	private CategoriaFoglia getFogliaDaNome(Categoria cat) {
	    String nomeCategoria = cat.getNome().trim().toLowerCase();

	    for (CategoriaFoglia f : logica.getCategorieFoglia()) {
	        if (f.getNome().trim().toLowerCase().equals(nomeCategoria)) {
	            return f;
	        }
	    }

	    return null;
	}
	
	/**
	 * Metodo di stampa delle prestazioni disponibili
	 * @param foglie
	 */
	private void stampaPrestazioni(ArrayList<CategoriaFoglia> foglie) {
		StringBuffer sb = new StringBuffer();
		int i = 1; //contatore per legenda
		sb.append("Prestazioni a disposizione >>\n");		
		for(CategoriaFoglia f : foglie) {
			sb.append(i++);
			sb.append(": ");
			sb.append(f.getNome());
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}
	
	/**
	 * Metodo che verifica se una nuova proposta scambio può essere soddisfatta.
	 * 1. verifica se esiste una proposta aperta che permetta uno SCAMBIO ESCLUSIVO,
	 * 2. altrimenti verifica se è possivile costruire un INSIEME CHIUSO => cerca se esiste un ciclo compatibile
	 *    RICORSIVITA' finché viene soddisfatta la condizione di chiusura [In teoria l'insieme dovrebbe sempre chiudersi perché verificata dalla condizione del blocco if]
	 *    
	 * @param nuova
	 */
	public void verificaSoddisfacimento(PropostaScambio nuova) {
		
		//SCAMBIO ESCLUSIVO TRA DUE PROPOSTE
	    for (PropostaScambio esistente : logica.getProposteAperte()) {
	        if (esistente.getStato() != StatoProposta.APERTA || esistente.getNomeAssociato().equalsIgnoreCase(nuova.getNomeAssociato())) continue;

	        if (eCompatibileTotal(esistente, nuova)) {
	     
	        	if (eSoddisfatto(esistente, nuova)) {
	        		
	        		nuova.setStato(StatoProposta.CHIUSA);
	        		esistente.setStato(StatoProposta.CHIUSA);
	        		
	        		System.out.println("Proposta soddisfatta automaticamente con una proposta esistente!");
	        		aggiornaPersistenzaProposte(esistente, nuova, StatoProposta.CHIUSA);
	        		return;  
	        	}
	        }
	    }
	    
	    //SCAMBIO TRA PIU PROPOSTE
	    ArrayList<PropostaScambio> catena = new ArrayList<>();
	    ArrayList<PropostaScambio> visitate = new ArrayList<>();

	    if (cercaCicloCompatibile(nuova, catena, nuova.getOfferta(), visitate)) {
	        nuova.setStato(StatoProposta.CHIUSA);
	        for (PropostaScambio p : catena) {
	            p.setStato(StatoProposta.CHIUSA);
	        }
	        System.out.println("Proposta soddisfatta tramite ciclo di " + (catena.size() + 1) + " proposte.\n");
	     
	        logica.addPropostaChiusa(nuova, catena);
	    	InsiemeProposteChiuse insieme = logica.getInsiemeProposteChiuse();
	    	GestorePersistenza.salvaAperteEChiuse(logica.getProposteAperte(), insieme, logica.getProposte());

	        //stampa proposte soddisfatte
	        System.out.println("Le altre proposte soddisfatte sono: ");
	        for(int i = 0; i< catena.size(); i++) {
	        	System.out.println(catena.get(i).toString());
	        	System.out.println("Fruitore: " + catena.get(i).getNomeAssociato());
	        }
	        
	    } else {
	        System.out.println("Nessuna proposta compatibile trovata. La proposta resta in attesa.");
	    }
	  
	    System.out.println("");
	}

	/**
	 * Metodo che cerca se è possibile costruire un insieme chiuso a partire da una nuova richiesta.
	 * Controlla se tra le proposte disponibili ce n'è una che viene soddisfatta dalla nuova proposta (che copre il ruolo di offerente).
	 * Il metodo prosegue poi ricorsivamente.
	 * Se alla fine viene soddisfatta la CONDIZIONE DI CHIUSURA ritorna true, altrimenti false.
	 * 
	 * CONDIZIONE DI CHIUSURA = la proposta p soddisfa la richiesta dell'offerente iniziale (testa)
	 * 
	 * @param nuova
	 * @param catena
	 * @param propostaCorrente
	 * @param visitate
	 * @return se esiste
	 */
	private boolean cercaCicloCompatibile(PropostaScambio nuova, ArrayList<PropostaScambio> catena, Proposta propostaCorrente, ArrayList<PropostaScambio> visitate) {
		for (PropostaScambio p : logica.getProposteAperte()) {
			if (p.getStato() != StatoProposta.APERTA || visitate.contains(p) || p.getNomeAssociato().equalsIgnoreCase(nuova.getNomeAssociato())) continue;

			boolean compatibile = propostaCorrente.getPrestazione().getNome().equalsIgnoreCase(p.getNomeRichiesta());

			if (compatibile && eSoddisfatto(p, nuova)) {
				visitate.add(p);
				catena.add(p);

				if (eCompatibile(p, nuova) && eSoddisfatto(p, nuova)) return true;

				if (cercaCicloCompatibile(nuova, catena, p.getOfferta(), visitate)) return true;

				//backtrack
				catena.remove(catena.size() - 1);
				visitate.remove(p);
			}
		}
		return false;
	}
	/**
	 * Metodo che verifica la prestazione richiesta da una proposta di scambio è la stessa della prestazione proposta da un'altra
	 * @param offerta di una proposta p1
	 * @param richiesta di una proposta p2
	 * @return vero se la prestazione è la stessa
	 */
	private boolean eCompatibile(PropostaScambio offerta, PropostaScambio richiesta) {
		if (offerta != null && richiesta != null)
			return richiesta.getNomeRichiesta().equalsIgnoreCase(offerta.getNomeOfferta());
		return false;
	}
	private boolean eCompatibileTotal(PropostaScambio p1, PropostaScambio p2) {
		return eCompatibile(p1, p2) && eCompatibile(p2,p1);	
	}
	
	/**
	 * Metodo che verifica se le ore richieste da una proposta coincidono con quelle offerte da una seconda proposta.
	 * @param offerta candidato compatibile con richiesta
	 * @param richiesta da soddisfare
	 * @return vero se la differenza è 0
	 */
	private boolean eSoddisfatto(PropostaScambio offerta, PropostaScambio richiesta) {
		if (richiesta != null && offerta != null) {
	    	return (Math.abs(richiesta.getOreRichiesta() -  offerta.getOreOfferta()) < 0.001 ? true : false);
		}
		return false;
	}

	//*********************************************************************************************************************
	//PROVA RIRITA PROPOSTE
	public void ritiraProposte() {
		
		ArrayList<PropostaScambio> associate = new ArrayList<>();
		ArrayList<PropostaScambio> aperte = logica.getProposteAperte();

	    for (PropostaScambio p : aperte) {
	        if (p.getNomeAssociato().equalsIgnoreCase(fruit.getUsername()) && p.getStato() == StatoProposta.APERTA) {
	            associate.add(p);
	        }
	    }

	    visualizzaProposte(associate, "\nProposte aperte disponibili per il ritiro:\n");
	    
	    System.out.println("\n0. Torna al menu.\n");

	    int scelta = InputDati.leggiInteroConMINeMAX("Seleziona la proposta da ritirare > ", 0, associate.size());
	    if (scelta == 0) {
	        System.out.println("Ritiro annullato.");
	        return;
	    }
	    
	    PropostaScambio selezionata = associate.get(scelta - 1);

	    boolean conferma = InputDati.yesOrNo("\nVuoi davvero ritirare questa proposta ?");
	    if (conferma) {
	    	
	        selezionata.setStato(StatoProposta.RITIRATA); 
	        		    
		    aggiornaPersistenzaProposte(selezionata, null, StatoProposta.RITIRATA);
	        
	        System.out.println("Proposta ritirata con successo.");
	        
	    } else {
	        System.out.println("Ritiro annullato.");
	    }
		
	}

	/**
	 * Metodo che aggiorna le liste nel caso i cui venga chiusa o ritirata una proposta. (bisogna aggiornare anche la lista di quelle chiuse)
	 * @param aggiunta
	 */
	private void aggiornaPersistenzaProposte(PropostaScambio esistente, PropostaScambio nuova, StatoProposta a) {
			
			switch (a) {
	            case CHIUSA -> 	{
	            	logica.addPropostaChiusa(esistente, nuova); //implicitamente elimina da aperte
	            					
	            	GestorePersistenza.salvaAperteEChiuse(logica.getProposteAperte(), logica.getInsiemeProposteChiuse(), logica.getProposte());
	            }
	
	            case RITIRATA -> {
	    		    logica.addPropostaRitirata(esistente); 

	            	GestorePersistenza.salvaAperteERitirate(logica.getProposteAperte(), logica.getProposteRitirate(), logica.getProposte());
	            }
	
	            default -> System.out.println("Errore");
	        }
				
	}
	/**
	 * Metodo per la visualizzazione delle proposte disponibili.
	 * @param s lista delle proposte  da formattare
	 * @param txt titolo
	 */
	public void visualizzaProposte(ArrayList<PropostaScambio> s, String txt) {
		StringBuffer sb = new StringBuffer();
		 sb.append(txt);
		 sb.append("\n");
		 
		    if(s.isEmpty()) {
		    	System.out.println("Non ci sono proposte disponibili.");
		    	return;
		    }
		    
		    for (int i = 1; i <= s.size(); i++) {
		    	sb.append( i + ". " + s.get(i - 1));
				 sb.append("\n");
		    }
		    System.out.println(sb.toString());
	  
	}
	
}
