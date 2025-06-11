package applicazione;

import utenti.Fruitore;

/**
 * Classe che associa una richiesta a un'offerta.
 * Lo scambio viene posto in attesa alla creazione dell'oggetto.
 * Il fruitore sarà impostato solo quando necessario.
 * 
 * @author Irene Romano 736566      Erjona Maxhaku 735766
 * 
 */
public class PropostaScambio {
	
	private int id;
	private Proposta richiesta;
	private Proposta offerta; 
	private StatoProposta statoIniziale;
	private StatoProposta statoFinale;
	private Fruitore associato = null; //impostato solo quando la proposta viene confermata per essere salvata
	
	/**
	 * Costruttore dell'oggetto costituito dalla proposta del richiedente e l'offerta che gli viene proposta.
	 * Inizialmente lo stato è sempre impostato IN_ATTESA, finché il fruitore stesso la accetta o la rifiuta.
	 * @param richiesta
	 * @param offerta
	 */
	public PropostaScambio(Proposta richiesta, Proposta offerta, int id) {
		this.id = ++id;
		this.richiesta = richiesta;
		this.offerta = offerta;
		this.statoIniziale = StatoProposta.APERTA;
		this.statoFinale = null;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Proposta getRichiesta() {
		return richiesta;
	}
	
	public Proposta getOfferta() {
		return offerta;
	}

	public StatoProposta getStatoIniziale() {
		return statoIniziale;
	}

	public void setStatoIniziale(StatoProposta stato) {
		this.statoIniziale = stato;
	}
	
	public StatoProposta getStatoFinale() {
		return statoFinale;
	}
	
	public void setStatoFinale(StatoProposta stato) {
		this.statoFinale = stato;
	}

	public Fruitore getAssociato() {
		return associato;
	}
	
	/**
	 * Metodo che associa il fruitore allo scambio di proposta accettato
	 * @param associato
	 */
	public void setFruitoreAssociato(Fruitore associato) {
		this.associato = associato;	
	}
	
	private StatoProposta checkStato() {
		return (statoFinale == null) ? statoIniziale : statoFinale;
	}
	
	/*
	public String toString() {
		return "\nPropostaScambio \n> Richiesta: " + richiesta.toString() + "\n> Offerta: " + offerta.toString();
	}
	*/
	
	@Override
	public String toString() {
		return String.format("Richiesta: " + richiesta.toString() + " -> " + " Offerta: " + offerta.toString() + " | " + " Sato: " + checkStato());
	}

}