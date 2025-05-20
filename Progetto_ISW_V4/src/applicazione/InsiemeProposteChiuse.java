package applicazione;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Classe di gestione delle proposte chiuse. 
 * Una richiesta che viene soddisfatta implica il salvataggio in un hashmap in cui:
 * - la chiave è la PropostaScambio di cui la richiesta viene soddisfatta;
 * - il valore è una lista delle ProposteScambio (anche una sola) che contengono l'insieme di offerte che la soddisfano.
 * 
 * @author Erjona Maxhaku 735766
 */
public class InsiemeProposteChiuse {

	private HashMap<PropostaScambio, ArrayList<PropostaScambio>> mappa;
	
	//COSTRUTTORI	
	public InsiemeProposteChiuse() {
		this.mappa = new HashMap<>();
	}
	public InsiemeProposteChiuse(HashMap<PropostaScambio, ArrayList<PropostaScambio>> mappa) {
		this.mappa = mappa;
	}
	
	//GETTERS E SETTERS
	public ArrayList<PropostaScambio> getInsieme(PropostaScambio chiave){
		if(!mappa.isEmpty() && mappa.containsKey(chiave))
			return mappa.get(chiave);
		else 
			return null;
	}
	
	public ArrayList<PropostaScambio> getChiavi(){
		return new ArrayList<>(mappa.keySet());
	}
	
	public HashMap<PropostaScambio, ArrayList<PropostaScambio>> getMappa() {
		return mappa;
	}

	public void setMappa(HashMap<PropostaScambio, ArrayList<PropostaScambio>> mappaCaricata) {
		this.mappa = mappaCaricata;		
	}
	
	//METODI AGGIUNTA PROPOSTE SODDISFATTE
	/**
	 * Se una proposta p1 viene soddisfatta totalmente da una proposta p2, e viceversa,
	 * salverò nella mappa sia un set che presenta p1 come chiave che un set che presenta p2 come chiave.
	 * @param p1
	 * @param p2
	 */
	public void aggiungiProposta(PropostaScambio p1, PropostaScambio p2) {
       // mappa.computeIfAbsent(chiave, k -> new ArrayList<>()).add(nuovaProposta);		 
		this.mappa.put(p1, new ArrayList<>(List.of(p2)));
		this.mappa.put(p2, new ArrayList<>(List.of(p1)));
	}
	public void aggiungiAChiave(PropostaScambio chiave, ArrayList<PropostaScambio> insieme) {
		for(PropostaScambio p : insieme)
			aggiungiProposta(chiave, p);
	}
	public void aggiungiAInsieme(PropostaScambio chiave, ArrayList<PropostaScambio> insieme) {
		mappa.put(chiave, insieme);
	}
	
	 /**
	  * Metodo di formattazione testo da presentare all'utente interessato a una particolare chiave.
	  * @param chiave = proposta soddisfatta
	  * @return testo 
	  */
	public String formattaScambi(PropostaScambio chiave){
		
		if(mappa.containsKey(chiave)) {
			
			StringBuffer sb = new StringBuffer();
			sb.append("\nA: " + chiave.getMailAssociato() + "\nLa tua proposta di scambio è stata soddisfatta da:\n");
			
			for(PropostaScambio p : mappa.get(chiave)) {
				sb.append(p.toString() + "   --fruitore associato: "+ p.getMailAssociato());
				sb.append("\n");			
			}
			
			return sb.toString();
			
		} else
			return "\nQuesta proposta di scambio non è ancora stata soddifatta o non è esistente.";
	}

}
