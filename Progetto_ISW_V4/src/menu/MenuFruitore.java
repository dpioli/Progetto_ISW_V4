package menu;

import java.util.*;

import applicazione.CampoCaratteristico;
import applicazione.Categoria;
import applicazione.CategoriaFoglia;
import applicazione.Gerarchia;
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
	
	private static String[] vociFruit = {NAVIGA, RICHIEDI_PRESTAZIONI, MSG_P_PRECEDENTE};
	
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
		int scelta = InputDati.leggiInteroConMINeMAX(MSG_SEL_PRESTAZIONE, 0, foglie.size()- 1);
		double ore = InputDati.leggiDoubleConMinimo(MSG_INS_ORE, 0);
		Proposta richiesta = new Proposta(foglie.get(scelta), TipoProposta.RICHIESTA, ore);

		//OFFERTA
		int incambio;
		do {
			incambio = InputDati.leggiInteroConMINeMAX(MSG_SEL_OFFERTA, 0, foglie.size()- 1);
			if(incambio == scelta) {
				System.out.println(MSG_PRESTAZIONE_UGUALE);
			}
		}while(incambio == scelta);
		ArrayList<Double> fattori = logica.getFatConversione().prendiRiga(scelta); 
		//prendendo tutti i fdc dalla tabella uscenti da id della prestazione richiesta
	    int valore = (int) (fattori.get(incambio) * ore);
		Proposta offerta = new Proposta(foglie.get(incambio), TipoProposta.OFFERTA, valore);
		
		//SCAMBIO
		PropostaScambio scambio = new PropostaScambio(richiesta, offerta);
		boolean sn = InputDati.yesOrNo(MSG_CONFERMA + scambio.toString() + MSG_Y_N);
		if(sn) { //aggiunto alle proposte aperte
			scambio.setFruitoreAssociato(fruit);
			logica.addScambio(scambio);
			verificaSoddisfacimento(scambio); //controllo che lo scambio possa essere completato
			GestorePersistenza.salvaScambi(logica.getScambi());
		} else {
			System.out.println(MSG_ANNULLATO_SCAMBIO +  MSG_MENU_PRINCIPALE );
			return;
		}
	}
	
	
	
	
	
	
	
	//PROVA VERIFICA SCAMBIO
	public void verificaSoddisfacimento(PropostaScambio nuova) {
		
		//SCAMBIO ESCLUSIVO TRA DUE PROPOSTE
	    for (PropostaScambio esistente : logica.getScambi()) {
	        if (esistente.getStato() != StatoProposta.APERTA || esistente.getAssociato().getUsername().equalsIgnoreCase(nuova.getAssociato().getUsername())) continue;

	        boolean categorieCompatibili =
	        	esistente.getOfferta().getPrestazione().getNome().equalsIgnoreCase(nuova.getRichiesta().getPrestazione().getNome()) &&
	       		esistente.getRichiesta().getPrestazione().getNome().equalsIgnoreCase(nuova.getOfferta().getPrestazione().getNome());

	        if (categorieCompatibili) {
	        	double qRichiestaNuova = nuova.getRichiesta().getQuantitaOre();
	        	double qOffertaEsistente = esistente.getOfferta().getQuantitaOre();
	            
	        	if (Math.abs(qRichiestaNuova - qOffertaEsistente) < 0.001) {
	        		nuova.setStato(StatoProposta.CHIUSA);
	        		esistente.setStato(StatoProposta.CHIUSA);
	        		System.out.println("Proposta soddisfatta automaticamente con una proposta esistente!");
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
	        
	        //stampa proposte soddisfatte
	        System.out.println("Le altre proposte soddisfatte sono: ");
	        for(int i = 0; i< catena.size(); i++) {
	        	System.out.println(catena.get(i).toString());
	        	System.out.println("Fruitore: " + catena.get(i).getAssociato().getUsername());
	        }
	        
	    } else {
	        System.out.println("Nessuna proposta compatibile trovata. La proposta resta in attesa.");
	    }
	  
	    System.out.println("");
	}
	
	
	
	
	
	
	
	
	
	//PROVA
	private boolean cercaCicloCompatibile(PropostaScambio nuova, ArrayList<PropostaScambio> catena, Proposta propostaCorrente, ArrayList<PropostaScambio> visitate) {
		for (PropostaScambio p : logica.getScambi()) {
			if (p.getStato() != StatoProposta.APERTA || visitate.contains(p) || p.getAssociato().getUsername().equalsIgnoreCase(nuova.getAssociato().getUsername())) continue;

			boolean compatibile = propostaCorrente.getPrestazione().getNome().equalsIgnoreCase(p.getRichiesta().getPrestazione().getNome());

			if (compatibile && Math.abs(propostaCorrente.getQuantitaOre() - p.getOfferta().getQuantitaOre()) < 0.001) {
				visitate.add(p);
				catena.add(p);

				boolean chiusura =
						p.getOfferta().getPrestazione().getNome().equalsIgnoreCase(nuova.getRichiesta().getPrestazione().getNome())
						&& Math.abs(p.getOfferta().getQuantitaOre() - nuova.getRichiesta().getQuantitaOre()) < 0.001;

				if (chiusura) return true;

				if (cercaCicloCompatibile(nuova, catena, p.getOfferta(), visitate)) return true;

				//backtrack
				catena.remove(catena.size() - 1);
				visitate.remove(p);
			}
		}
		return false;
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
		int i = 0; //contatore per legenda
		sb.append("Prestazioni a disposizione >>\n");		
		for(CategoriaFoglia f : foglie) {
			sb.append(i++);
			sb.append(": ");
			sb.append(f.getNome());
			sb.append("\n");
		}
		System.out.println(sb.toString());
	}

}
