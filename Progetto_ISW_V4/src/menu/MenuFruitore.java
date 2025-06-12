package menu;

import java.util.*;

import applicazione.CampoCaratteristico;
import applicazione.Categoria;
import applicazione.CategoriaFoglia;
import applicazione.Gerarchia;
import applicazione.InsiemeChiuso;
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
	
	private Fruitore fruit;
	private LogicaPersistenza logica;
	
	private static final String titolo = "\tMENU FRUITORE";
	
	private static final String NAVIGA = "Naviga tra le gerarchie";
	private static final String RICHIEDI_PRESTAZIONI = "Richiedi prestazioni al sistema";
	private static final String RITIRA_PROPOSTE = "Ritira proposte dal sistema";
	private static final String VISUALIZZA_PROPOSTE = "Visualizza le tue proposte";
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
	
	private static String[] vociFruit = {NAVIGA, RICHIEDI_PRESTAZIONI, RITIRA_PROPOSTE, VISUALIZZA_PROPOSTE, MSG_P_PRECEDENTE};
	
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
	 * 8. se confermato salvo lo scambio (aperta), salvando anche il fruitore
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
		int incambio = InputDati.leggiInteroConMINeMAX(MSG_SEL_OFFERTA, 0, foglie.size()- 1);
		ArrayList<Double> fattori = logica.getFatConversione().prendiRiga(scelta); 
		//prendendo tutti i fdc dalla tabella uscenti da id della prestazione richiesta
	    int valore = (int) (fattori.get(incambio) * ore);
		Proposta offerta = new Proposta(foglie.get(incambio), TipoProposta.OFFERTA, valore);
		
		int id = logica.recuperaId();
		
		//SCAMBIO
		PropostaScambio scambio = new PropostaScambio(richiesta, offerta, id);
		boolean sn = InputDati.yesOrNo(MSG_CONFERMA + scambio.toString() + MSG_Y_N);
		if(sn) { //aggiunto alle proposte aperte
			scambio.setFruitoreAssociato(fruit);
			logica.addScambio(scambio);
			GestorePersistenza.salvaScambi(logica.getScambi());
			verificaSoddisfacimento(scambio);// verifica che lo scambio venga soddisfatto da delle proposte preesistenti
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
	
	
	/**
	 * Metodo per verificare se una porposta inserita può essere soddisfatta da una singola proposta o da una catena
	 * 1. Vado a cercare le proposte che possono soddisfare la mia proposta (sono di un fruitore del mio stesso comprensorio ma non sono io)
	 * 2. Inizializzo la catena (perché anche se ho due proposte formo un insieme chiuso che è una lista di proposte)
	 * 3. Aggiungo la mia proposta alla catena (primo elemento della catena)
	 * 4. inizio il ciclo sulle propostValide
	 * 5. Primo controllo se richiesta dell'ultima proposta nella catena è = all'offerta della mia proposta attuale e se le ore combaciano => match
	 * 6. aggiungo questa proposta alla catena
	 * 7. secondo controllo se 
	 * @param proposta
	 */
	private void verificaSoddisfacimento(PropostaScambio proposta) {
		
		ArrayList<PropostaScambio> proposteValide = selezionaProposteValide(logica.getScambi(), proposta);
		if(proposteValide.isEmpty()) {
			System.out.println("\nAl momemnto non ci sono proposte che soddisfano la tua proposta.\n"
					+ "Sarai contattato appena verra soddisfatta !!");
			return;
		}
		
		for(PropostaScambio p: proposteValide) {
			if(coppiaPerfetta(proposta, p)) {
				InsiemeChiuso ins = new InsiemeChiuso(logica.recuperaIdInsiemeChiuso());
				aggiornaStatoAChiusa(proposta, logica.getScambi());
				aggiornaStatoAChiusa(p, logica.getScambi());
				ins.aggiungiProposteAInsiemeChiuso(proposta);
				ins.aggiungiProposteAInsiemeChiuso(p);
				logica.aggiungiInsieme(ins);
				GestorePersistenza.salvaInsiemiChiusi(logica.getInsiemi());
				GestorePersistenza.salvaScambi(logica.getScambi());
				System.out.println("\nLa tua proposta è stata soddisfatta verrai contatato a breve con tutte le informazioni!!\n");
				return;
			}
		}
		
		ArrayList<PropostaScambio> catena = new ArrayList<PropostaScambio>();
		catena.add(proposta);
		
		if(cercaCatena(catena, proposteValide)) {
			InsiemeChiuso insC = new InsiemeChiuso(logica.recuperaIdInsiemeChiuso());
			for(PropostaScambio p: catena) {
				aggiornaStatoAChiusa(p, logica.getScambi());
				insC.aggiungiProposteAInsiemeChiuso(p);
			}
			logica.aggiungiInsieme(insC);
			GestorePersistenza.salvaInsiemiChiusi(logica.getInsiemi());
			GestorePersistenza.salvaScambi(logica.getScambi());
			System.out.println("\nLa tua proposta è stata soddisfatta verrai contatato a breve con tutte le informazioni!!\n");
			return;
		} else {
			System.out.println("\nAl momemnto non ci sono proposte che soddisfano la tua proposta.\n"
					+ "Sarai contattato appena verra soddisfatta !!");
			return;
		}
		
		/* TENTATIVO 2
		ArrayList<PropostaScambio> proposteValide = selezionaProposteValide(logica.getScambi(), proposta);
		boolean trovatoCatena = false;
		
		ArrayList<PropostaScambio> catena = new ArrayList<PropostaScambio>();
		catena.add(proposta);
		
		for(PropostaScambio p: proposteValide) {
			if(catena.get(catena.size() - 1).getRichiesta().getPrestazione().getNome().equals(p.getOfferta().getPrestazione().getNome()) &&
					catena.get(catena.size() - 1).getRichiesta().getQuantitaOre() == p.getOfferta().getQuantitaOre()) {
				catena.add(p);
				if(catena.get(0).getOfferta().getPrestazione().getNome().equals(p.getRichiesta().getPrestazione().getNome()) && 
						catena.get(0).getOfferta().getQuantitaOre() == p.getRichiesta().getQuantitaOre()) {
					int id = logica.recuperaIdInsiemeChiuso();
					InsiemeChiuso insiemeC = new InsiemeChiuso(id);
					for(PropostaScambio pC: catena) {
						insiemeC.aggiungiProposteAInsiemeChiuso(pC);
						aggiornaStatoAChiusa(pC, logica.getScambi());
					}
					GestorePersistenza.salvaInsiemiChiusi(logica.getInsiemi());
					return true;
				} else {
					continue;
				}
			} else {
				continue;
			}
		}
		
		return trovatoCatena;
		*/
		
		/*TENTATIVO 1
		for(PropostaScambio p: logica.getScambi()) {
			if(verificaFruitore(proposta, p) && controlloStato(p) && verificaSoddisfacimentoNome(proposta, p) && verificaSoddisfacimentoOre(proposta, p)) {
				aggiornaStatoAChiusa(proposta, logica.getScambi());
				aggiornaStatoAChiusa(p, logica.getScambi());
				
				int id = logica.recuperaIdInsiemeChiuso();
				InsiemeChiuso ins = new InsiemeChiuso(id);
				
				ins.aggiungiProposteAInsiemeChiuso(proposta);
				ins.aggiungiProposteAInsiemeChiuso(p);
				
				logica.aggiungiInsieme(ins);
				
				GestorePersistenza.salvaInsiemiChiusi(logica.getInsiemi());
				
				System.out.println("\nLa tua proposta è stata accettata sarai contattato a breve!\n");
			}
		}
		*/
	}
	
	private boolean cercaCatena(ArrayList<PropostaScambio> catena, ArrayList<PropostaScambio> proposteValide) {
		ArrayList<PropostaScambio> propostePendenti = proposteValide;
		for(PropostaScambio p: propostePendenti) {
			if(verificaRichiestaOfferta(catena.get(catena.size() - 1), p)) {
				if(verificaOffertaRichiesta(catena.get(0), p)) {
					return true;
				}
				catena.add(p);
				propostePendenti.remove(p);
				cercaCatena(catena, propostePendenti);
			}
			continue;
		}
		return false;
	}
	
	/**
	 * Metodo che permette di controllare se una coppia di proposte si soddisfa avvicevolmente
	 * @param p1
	 * @param p2
	 * @return true se due proposte si soddisfano avvicevolmente, false altrimenti
	 */
	private boolean coppiaPerfetta(PropostaScambio p1, PropostaScambio p2) {
		if(verificaSoddisfacimentoNome(p1, p2) && verificaSoddisfacimentoOre(p1, p2))
			return true;
		return false;
	}
	
	/**
	 * Metodo per selezionare le possibili proposte valide per soddisfare al proposta appena creata
	 * @param proposte
	 * @param proposta
	 * @return ArrayList<PropostaScambio> proposte che possono soddisfare una proposta inserita
	 */
	private ArrayList<PropostaScambio> selezionaProposteValide(ArrayList<PropostaScambio> proposte, PropostaScambio proposta){
		ArrayList<PropostaScambio> proposteV = new ArrayList<PropostaScambio>();
		
		for(PropostaScambio p: proposte) {
			if(controlloStato(p) && verificaFruitore(proposta, p)) {
				proposteV.add(p);
			}
		}
		return proposteV;
	}
	
	/**
	 * Metodo per verificare che i due fruitori siano diversi
	 * @param p1
	 * @param p2
	 * @return true se i fruitori sono diversi e se il cmprensorio è lo stesso, false altrimenti
	 */
	private boolean verificaFruitore(PropostaScambio p1, PropostaScambio p2) {
		boolean f = p1.getAssociato().getUsername().equals(p2.getAssociato().getUsername());
		boolean c = p1.getAssociato().getNomeComprensorio().equals(p2.getAssociato().getNomeComprensorio());
		if(f) {
			return false;
		} else if (c) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Metodo per verificare che la prestazione richiesta combacia con la prestazione offerta e viceversa
	 * @param p1
	 * @param p2
	 * @return true veirfica soddisfatta / false verifica non soddisfatta
	 */
	private boolean verificaSoddisfacimentoNome(PropostaScambio p1, PropostaScambio p2) {
		boolean ro = p1.getRichiesta().getPrestazione().getNome().equals(p2.getOfferta().getPrestazione().getNome());
		boolean or = p1.getOfferta().getPrestazione().getNome().equals(p2.getRichiesta().getPrestazione().getNome());
		
		if(ro && or) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean verificaRichiestaOfferta(PropostaScambio p1, PropostaScambio p2) {
		boolean nomeRichiestaOfferta =  p1.getRichiesta().getPrestazione().getNome().equals(p2.getOfferta().getPrestazione().getNome());
		boolean oreRichiestaOfferta = p1.getRichiesta().getQuantitaOre() == p2.getOfferta().getQuantitaOre();
		
		if(nomeRichiestaOfferta && oreRichiestaOfferta) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean verificaOffertaRichiesta(PropostaScambio p1, PropostaScambio p2) {
		boolean nomeOffertaRichiesta = p1.getOfferta().getPrestazione().getNome().equals(p2.getRichiesta().getPrestazione().getNome());
		boolean oreOffertaRichiesta = p1.getOfferta().getQuantitaOre() == p2.getRichiesta().getQuantitaOre();
		
		if(nomeOffertaRichiesta && oreOffertaRichiesta) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Metodo per verificare il soddisfacimento delle ore tra richiesta e offerta e viceversa
	 * @param p1
	 * @param p2
	 * @return true veirfica soddisfatta / false verifica non soddisfatta
	 */
	private boolean verificaSoddisfacimentoOre(PropostaScambio p1, PropostaScambio p2) {
		boolean ro = p1.getRichiesta().getQuantitaOre() == p2.getOfferta().getQuantitaOre();
		boolean or = p1.getOfferta().getQuantitaOre() == p2.getRichiesta().getQuantitaOre();
		
		if(ro && or) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Metodo per verificare se lo stato di una proposta è APERTA
	 * @param proposta
	 * @return true se lo stato della proposta è aperto /false se lo stato della proposta è chiuso 
	 */
	private boolean controlloStato(PropostaScambio proposta) {
		boolean n = proposta.getStatoFinale() != null;
		boolean c = proposta.getStatoFinale() != StatoProposta.CHIUSA;
		boolean r = proposta.getStatoFinale() != StatoProposta.RITIRATA;
		
		if(n && c && r) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Metodo per poter ritirare le proposte pendenti
	 */
	public void ritiraProposte() {
		ArrayList<PropostaScambio> proposte = logica.getScambi();
		ArrayList<PropostaScambio> proposteFruit = new ArrayList<>();
		
		for(PropostaScambio p: proposte) {
			boolean corrisponde = this.fruit.getUsername().equals(p.getAssociato().getUsername());
			if(corrisponde && (p.getStatoFinale() == null)) {
				proposteFruit.add(p);
			}
		}
		
		if(proposteFruit.isEmpty()) {
			System.out.println("\nNon hai proposte da ritirare.\n");
			return;
		}
		
		int selezionata = selezionaPropostaRitirabile(proposteFruit);
		
		if(selezionata == proposteFruit.size()) {
			System.out.println("\nOperazione annullata....\n");
			return;
		}
		
		PropostaScambio p = proposteFruit.get(selezionata);
		boolean conferma = InputDati.yesOrNo("\nSei sicuro di ritirare questa proposta ");
		if(conferma) {
			aggiornaStatoARitirata(p, proposte);
			GestorePersistenza.salvaScambi(proposte);
			System.out.println("\nLa proposta è stata ritirata.\n");
		} else {
			System.out.println("\nOperazione annullata....\n");
		}
	}
	
	/**
	 * Metodo per aggiornare lo stato di una proposta che vuole essere ritirata
	 * @param proposta
	 * @param proposte
	 */
	private void aggiornaStatoARitirata(PropostaScambio proposta, ArrayList<PropostaScambio> proposte) {
		for(PropostaScambio p : proposte) {
			if(p.getId() == proposta.getId()) {
				p.setStatoFinale(StatoProposta.RITIRATA);
				System.out.println(p.toString());
			}
		}
	}
	
	/**
	 *Metodo per aggiornare lo stato di una proposta a chiusa dopo essere stata soddisfatta
	 * @param proposta
	 * @param proposte
	 */
	private void aggiornaStatoAChiusa(PropostaScambio proposta, ArrayList<PropostaScambio> proposte) {
		for(PropostaScambio p: proposte) {
			if(p.getId() == proposta.getId()) {
				p.setStatoFinale(StatoProposta.CHIUSA);
			}
		}
	}
	
	/**
	 * Metodo per selezionare la proposta da ritirare tra quelle disponibili
	 * @param proposte
	 * @return
	 */
	private int selezionaPropostaRitirabile(ArrayList<PropostaScambio> proposte) {
		System.out.println("Proposte ritirabili: ");
		for(int i = 0; i < proposte.size(); i++) {
			System.out.println(i + ": " + proposte.get(i).toString());
		}
		System.out.println(proposte.size() + ": Annulla selezione");
		
		int propostaSelezionata = InputDati.leggiInteroConMINeMAX("Seleziona la proposta che vuoi ritirare (annulla altrimenti) > ", 0, proposte.size());
		
		return propostaSelezionata;
	}
	
	/**
	 * Metodo per la visualizzazione delle proposte da parte del fruitore
	 * sia che siano aperte, chiuse, ritirate.
	 * Effettuato solo il controllo per vedere quelle di cui il fruitore è autore
	 */
	public void visualizzaProposte() {
		ArrayList<PropostaScambio> proposte = logica.getScambi();
		
		if(proposte.isEmpty()) {
			System.out.println("\nNon ci sono proposte presenti.\n");
			return;
		}
			
		for(PropostaScambio p: proposte) {
			boolean corrisponde = this.fruit.getUsername().equals(p.getAssociato().getUsername());
			if(corrisponde) {
				System.out.println("\n> " + p.toString());
			}
		}
	}

}
