package applicazione;

import java.util.ArrayList;

public class InsiemeChiuso {
	
	private int id;
	private ArrayList<PropostaScambio> proposte;
	
	public InsiemeChiuso(int id) {
		this.id = ++id;
		this.proposte = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<PropostaScambio> getProposte() {
		return proposte;
	}

	public void setProposte(ArrayList<PropostaScambio> proposte) {
		this.proposte = proposte;
	}
	
	public void aggiungiProposteAInsiemeChiuso(PropostaScambio proposta) {
		proposte.add(proposta);
	}

}
