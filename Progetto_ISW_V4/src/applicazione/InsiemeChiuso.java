package applicazione;

import java.util.ArrayList;

import utenti.Fruitore;

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
	
	public String ottieniComprensorioInsieme() {
		Comprensorio c = this.proposte.get(0).getAssociato().getComprensorio();
		return c.getNome();
	}
	
	@Override
	public String toString() {
		ArrayList<Fruitore> fruitoriAssociati = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		sb.append("\n- id: ");
		sb.append(this.id + "\n\t");
		for(PropostaScambio p: proposte) {
			sb.append(p.toString() + "| Proposta da: " + p.getAssociato().getMail() + "\n\t");
			fruitoriAssociati.add(p.getAssociato());
		}
		
		sb.append("\n\tFruitori coinvolti: \n\t");
		for(Fruitore f: fruitoriAssociati) {
			sb.append("- " + f.getUsername() + " -> " + f.getMail() + " | " + f.getNomeComprensorio() + "\n\t");
		}
		return sb.toString();
	}

}
