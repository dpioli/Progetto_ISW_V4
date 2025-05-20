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
	
	private Proposta richiesta;
	private Proposta offerta; 
	private StatoProposta stato;
	private Fruitore associato = null; //impostato solo quando la proposta viene confermata per essere salvata
	
	/**
	 * Costruttore dell'oggetto costituito dalla proposta del richiedente e l'offerta che gli viene proposta.
	 * Inizialmente lo stato è sempre impostato IN_ATTESA, finché il fruitore stesso la accetta o la rifiuta.
	 * @param richiesta
	 * @param offerta
	 */
	public PropostaScambio(Proposta richiesta, Proposta offerta) {
		this.richiesta = richiesta;
		this.offerta = offerta;
		this.stato = StatoProposta.APERTA;
	}

	public Proposta getRichiesta() {
		return richiesta;
	}

	public Proposta getOfferta() {
		return offerta;
	}

	public StatoProposta getStato() {
		return stato;
	}

	public void setStato(StatoProposta stato) {
		this.stato = stato;
	}

	public Fruitore getAssociato() {
		return associato;
	}
	public String getNomeOfferta() {
		//controlli su offerta?
		return offerta.getPrestazione().getNome();
	}
	public String getNomeRichiesta() {
		return richiesta.getPrestazione().getNome();
	}
	public String getNomeAssociato() {
		if(associato != null)
			return associato.getUsername();
		else 
			return "errore"; //whatever else
	}
	public String getMailAssociato() {
		if(associato != null)
			return associato.getMail();
		else 
			return "errore"; //whatever else
	}
	public double getOreRichiesta() {
		return richiesta.getQuantitaOre();
	}
	public double getOreOfferta() {
		return offerta.getQuantitaOre();
	}
	
	/**
	 * Metodo che associa il fruitore allo scambio di proposta accettato
	 * @param associato
	 */
	public void setFruitoreAssociato(Fruitore associato) {
		this.associato = associato;	
	}
	
	@Override
	public String toString() {
		return "\nPropostaScambio \n> Richiesta: " + richiesta.toString() + "\n> Offerta: " + offerta.toString();
	}

}