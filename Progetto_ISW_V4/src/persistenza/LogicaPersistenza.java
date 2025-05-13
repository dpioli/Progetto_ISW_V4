package persistenza;

import java.util.ArrayList;

import applicazione.CategoriaFoglia;
import applicazione.Comprensorio;
import applicazione.FatConversione;
import applicazione.Gerarchia;
import applicazione.PropostaScambio;
import utenti.Configuratore;
import utenti.Fruitore;

/**
 * Classe che permette la gestione logistica dell'aggiornamento e modifica dei dati salvaati sui file
 * @author Diego Pioli 736160
 *
 */
public class LogicaPersistenza {
	
	private ArrayList<Gerarchia> gerarchie = new ArrayList<Gerarchia>();
	private ArrayList<Comprensorio> comprensori = new ArrayList<Comprensorio>();
	private ArrayList<Configuratore> configuratori = new ArrayList<Configuratore>();
	private ArrayList<CategoriaFoglia> categorieFoglia = new ArrayList<CategoriaFoglia>();
	private FatConversione fatConversione;
	private ArrayList<Fruitore> fruitori = new ArrayList<Fruitore>();
	private ArrayList<PropostaScambio> scambi = new ArrayList<PropostaScambio>();
	
	public LogicaPersistenza() {
		this.gerarchie = GestorePersistenza.caricaGerarchie();
		this.comprensori = GestorePersistenza.caricaComprensorio();
		this.configuratori = GestorePersistenza.caricaConfiguratori();
		this.fatConversione = GestorePersistenza.caricaFatConversione();
		this.categorieFoglia = GestorePersistenza.caricaCategorieFoglia();
		this.fruitori = GestorePersistenza.caricaFruitori();
		this.scambi = GestorePersistenza.caricaScambi();
	}
	
	/*
	 * 
	 * 
	 * GETTER E SETTER
	 * 
	 * 
	 */
	
	/**
	 * Metodo per ottenere l'insieme delle Gerarchie
	 * @return ArrayList<Gerarchia>
	 */
	public ArrayList<Gerarchia> getGerarchie() {
		return gerarchie;
	}
	
	/**
	 * Metodo per modificare l'insieme delle gerarchie
	 * @param gerarchie
	 */
	public void setGerarchie(ArrayList<Gerarchia> gerarchie) {
		this.gerarchie = gerarchie;
	}
	
	/**
	 * Metodo per ottenere l'insieme dei comprensori
	 * @return ArrayList<Comprensorio>
	 */
	public ArrayList<Comprensorio> getComprensori() {
		return comprensori;
	}
	
	/**
	 * Metodo per modificare l'insieme dei comprensori
	 * @param comprensori
	 */
	public void setComprensori(ArrayList<Comprensorio> comprensori) {
		this.comprensori = comprensori;
	}
	
	/**
	 * Metodo per ottenere l'insieme dei configuratori
	 * @return ArrayList<Configuratore>
	 */
	public ArrayList<Configuratore> getConfiguratori() {
		return configuratori;
	}
	
	/**
	 * Metodo per modificare l'insieme dei configuratori
	 * @param configuratori
	 */
	public void setConfiguratori(ArrayList<Configuratore> configuratori) {
		this.configuratori = configuratori;
	}
	
	/**
	 * Metodo per ottenere l'insieme dei fattori di conversione
	 * @return FatConversione
	 */
	public FatConversione getFatConversione() {
		return fatConversione;
	}
	
	/**
	 * Metodo per modificare l'insieme dei fattori di conversione
	 * @param fatConversione
	 */
	public void setFatConversione(FatConversione fatConversione) {
		this.fatConversione = fatConversione;
	}
	
	/**
	 * Metodo per ottenere l'insieme delle categorie foglia
	 * @return categorieFoglia
	 */
	public ArrayList<CategoriaFoglia> getCategorieFoglia() {
		return categorieFoglia;
	}

	/**
	 * Metodo per modificare l'insieme delle categorie foglia
	 * @param categorieFoglia
	 */
	public void setCategorieFoglia(ArrayList<CategoriaFoglia> categorieFoglia) {
		this.categorieFoglia = categorieFoglia;
	}
	
	/**
	 * Metodo per ottenere l'insieme dei fruitori
	 * @return fruitori
	 */
	public ArrayList<Fruitore> getFruitori() {
		return fruitori;
	}
	
	/**
	 * Medoto per modificare l'insieme dei fruitori
	 * @param fruitori
	 */
	public void setFruitori(ArrayList<Fruitore> fruitori) {
		this.fruitori = fruitori;
	}
	/**
	 * Metodo per ottenere l'insieme delle proposte aperte (scambi tra fruitore e server)
	 * @return scambi
	 */
	public ArrayList<PropostaScambio> getScambi() {
		return scambi;
	}
	
	/**
	 * Medoto per modificare l'insieme delle proposte aperte (scambi tra fruitore e server)
	 * @param scambi
	 */
	public void setScambi(ArrayList<PropostaScambio> scambi) {
		this.scambi = scambi;
	}

	/*
	 * 
	 * 
	 * FUNZIONALITA'
	 * 
	 * 
	 */
	
	/***
	 * METODI PER L'AGGIUNTA DI NUOVI OGGETTI AI RISPETTIVI INSIEMI
	 * @param oggetto da aggiungere
	 ***/
	
	public void addConfiguratore(Configuratore configuratore) {
		configuratori.add(configuratore);
	}

	public void addComprensorio(Comprensorio comprensorio) {
		comprensori.add(comprensorio);
	}

	public void addGerarchia(Gerarchia gerarchia) {
		gerarchie.add(gerarchia);
	}
	
	public void addFruitore(Fruitore fruitore) {
		fruitori.add(fruitore);
	}
	public void addScambio(PropostaScambio scambio) {
		scambi.add(scambio);
	}

	public void addCategoriaFoglia(CategoriaFoglia nuovaCategFoglia) {
		categorieFoglia.add(nuovaCategFoglia);
	}
	
	public void aggiungiFDC(Integer nuova) {
		fatConversione.aggancia(nuova);	
	}
	
	/**
	 * Metodo che recupera l'identificativo dell'ultima foglia salvata,
	 * permette di calcolare quello della successiva per mantenere la persistenza.
	 * @return id ultima foglia salvata
	 */
	public int recuperaUltimoID() {
		if(categorieFoglia.isEmpty())
			return 0;
		int ultimo = categorieFoglia.size() - 1;
		CategoriaFoglia f = categorieFoglia.get(ultimo);
		return f.getId();
	}
	

}
