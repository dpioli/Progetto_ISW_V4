package persistenza;

import java.util.ArrayList;
import java.util.List;

import applicazione.CategoriaFoglia;
import applicazione.Comprensorio;
import applicazione.FatConversione;
import applicazione.Gerarchia;
import applicazione.InsiemeProposteChiuse;
import applicazione.PropostaScambio;
import utenti.Configuratore;
import utenti.Fruitore;

/**
 * Classe che permette la gestione logistica dell'aggiornamento e modifica dei dati salvaati sui file
 * @author Diego Pioli 736160
 *
 */
public class LogicaPersistenza {
	
	private ArrayList<Comprensorio> comprensori = new ArrayList<Comprensorio>();
	private ArrayList<Gerarchia> gerarchie = new ArrayList<Gerarchia>();
	private ArrayList<CategoriaFoglia> categorieFoglia = new ArrayList<CategoriaFoglia>();
	private FatConversione fatConversione;
	
	private ArrayList<Configuratore> configuratori = new ArrayList<Configuratore>();
	private ArrayList<Fruitore> fruitori = new ArrayList<Fruitore>();
	
	private ArrayList<PropostaScambio> proposte = new ArrayList<PropostaScambio>(); //aggiornamenti
	private ArrayList<PropostaScambio> proposteAperte = new ArrayList<PropostaScambio>();
//	private ArrayList<PropostaScambio> proposteChiuse = new ArrayList<PropostaScambio>();
	private ArrayList<PropostaScambio> proposteRitirate = new ArrayList<PropostaScambio>();
	
	
	private InsiemeProposteChiuse insiemechiuso = new InsiemeProposteChiuse();
	
	public LogicaPersistenza() {
		this.gerarchie = GestorePersistenza.caricaGerarchie();
		this.comprensori = GestorePersistenza.caricaComprensorio();
		this.configuratori = GestorePersistenza.caricaConfiguratori();
		this.fatConversione = GestorePersistenza.caricaFatConversione();
		this.categorieFoglia = GestorePersistenza.caricaCategorieFoglia();
		this.fruitori = GestorePersistenza.caricaFruitori();
		this.proposte = GestorePersistenza.caricaProposte();
		this.proposteAperte = GestorePersistenza.caricaProposteAperte();
		//this.proposteChiuse = GestorePersistenza.caricaProposteChiuse();
		this.proposteRitirate = GestorePersistenza.caricaProposteRitirate();
		this.insiemechiuso = new InsiemeProposteChiuse(GestorePersistenza.caricaInsiemeChiuso());;
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
	 * Metodo per ottenere l'insieme di TUTTE le proposte registrate e dei loro aggiornamenti 
	 * @return scambi
	 */
	public ArrayList<PropostaScambio> getProposte() {
		return proposte;
	}
	/**
	 * Metodo per ottenere l'insieme delle proposte aperte (scambi tra fruitore e server)
	 * @return scambi
	 */
	public ArrayList<PropostaScambio> getProposteAperte() {
		return proposteAperte;
	}
	/**
	 * Metodi per ottenere 
	 * - la lista delle proposte chiuse (chiavi della mappa InsiemeProposteChiuse)
	 * - la mappa delle proposte chiuse con offerenti associati
	 * @return scambi
	 */
	public ArrayList<PropostaScambio> getProposteChiuse() {
		//return proposteChiuse;
		return insiemechiuso.getChiavi();
	}
	public InsiemeProposteChiuse getInsiemeProposteChiuse() {
		//return proposteChiuse;
		return insiemechiuso;
	}
	/**
	 * Metodo per ottenere l'insieme delle proposte ritirate 
	 * @return scambi
	 */
	public ArrayList<PropostaScambio> getProposteRitirate() {
		return proposteRitirate;
	}
	
	/**
	 * Medoto per modificare l'insieme delle proposte aperte (scambi tra fruitore e server)
	 * @param scambi
	 */
	public void setProposteAperte(ArrayList<PropostaScambio> scambi) {
		this.proposteAperte = scambi;
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
	
	public void addFruitore(Fruitore fruitore) {
		fruitori.add(fruitore);
	}
	

	public void addComprensorio(Comprensorio comprensorio) {
		comprensori.add(comprensorio);
	}

	public void addGerarchia(Gerarchia gerarchia) {
		gerarchie.add(gerarchia);
	}

	public void addCategoriaFoglia(CategoriaFoglia nuovaCategFoglia) {
		categorieFoglia.add(nuovaCategFoglia);
	}
	
	public void aggiungiFDC(Integer nuova) {
		fatConversione.aggancia(nuova);	
	}

	/**
	 * Metodi per l'aggiunta di una proposta di scambio alla relativa lista.
	 * Ogni volta che una delle lista acquisisce un oggetto esso viene ripostato anche nella lista proposte
	 * che tiene traccia di tutti gli aggiornamenti.
	 * L'aggiunta di una proposta chiusa o una proposta ritirata implica implicitamente la rimozione di una proposta aperta.
	 * @param scambio
	 */
	public void addProposta(PropostaScambio scambio) {
		proposte.add(scambio);
	}
	public void addProposte(ArrayList<PropostaScambio> scambi) {
		proposte.addAll(scambi);
	}
	private void addScambio(PropostaScambio scambio, ArrayList<PropostaScambio> s) {
		s.add(scambio);
		proposte.add(scambio);
	}
	public void addPropostaAperta(PropostaScambio scambio) {
		addScambio(scambio, proposteAperte);
	}
	public void addPropostaRitirata(PropostaScambio scambio) {
		addScambio(scambio, proposteRitirate);
		rimuoviPropostaAperta(scambio);
	}
	public void addPropostaChiusa(PropostaScambio richiesta, ArrayList<PropostaScambio> offerte) {
		//addScambio(scambio, proposteChiuse);
		insiemechiuso.aggiungiAInsieme(richiesta, offerte);
		proposte.add(richiesta);
		rimuoviPropostaAperta(richiesta);
	}
	public void addPropostaChiusa(PropostaScambio p1, PropostaScambio p2) {
		insiemechiuso.aggiungiProposta(p1, p2);
		addProposte(new ArrayList<>(List.of(p1, p2)));
		rimuoviPropostaAperta(p1);
		rimuoviPropostaAperta(p2);
	}
	
	/**
	 * Metodi per la rimozione di proposte di scambio dalle liste
	 * @param scambio da rimuovere
	 */
	public void rimuoviPropostaAperta(PropostaScambio scambio) {
		rimuoviProposta(scambio, proposteAperte);
	}
	private void rimuoviProposta(PropostaScambio scambio, ArrayList<PropostaScambio> s) {
		s.remove(scambio);
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
