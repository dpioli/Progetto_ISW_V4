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
	
	@Override
	public String toString() {
		ArrayList<String> mailFruitoriAssociati = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		sb.append("\n- id: ");
		sb.append(this.id + "\n\t");
		for(PropostaScambio p: proposte) {
			sb.append(p.toString() + "| Proposta da: " + p.getAssociato().getMail() + "\n\t");
			mailFruitoriAssociati.add(p.getAssociato().getMail());
		}
		
		sb.append("\n\tFruitori coinvolti: \n\t");
		for(String s: mailFruitoriAssociati) {
			sb.append("- " + s + "\n\t");
		}
		return sb.toString();
	}

}
