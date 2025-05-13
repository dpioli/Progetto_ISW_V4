package applicazione;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe per descrivere il campo caratteristico di una categoria non foglia
 * @author Erjona Maxhaku 735766
 *
 */
public class CampoCaratteristico {
	
	public String nomeCampo;
	public HashMap<String, String> valoriCampo;
	
	/**
	 * Costruttore della classe CampoCaratteristico
	 * @param nomeCampo
	 */
	public CampoCaratteristico (String nomeCampo) {
		this.nomeCampo = nomeCampo;
		this.valoriCampo = new HashMap<>();
	}
	
	public CampoCaratteristico (String nomeCampo, HashMap<String, String> valoriCampo) {
		this.nomeCampo = nomeCampo;
		this.valoriCampo = valoriCampo;
	}
	
	public String getNomeCampo() {
		return nomeCampo;
	}

	public HashMap<String, String> getValori(){
		return valoriCampo;
	}
	
	/**
	 * Metodo per aggiungere dei valori al campo
	 * @param valori
	 */
	public void aggiungiValori(ArrayList<String> valori) {
		for(String v: valori) {
			valoriCampo.put(v, "");
		}
	}
	
	/**
	 * Metodo per aggiungere una descrizione a un valore specifico del campo
	 * @param valore
	 * @param descrizione
	 */
	public void aggiungiDescrizioneSpecifica(String valore, String descrizione) {
		valoriCampo.put(valore, descrizione);
	}
	
	/**
	 * Metodo per andare ad aggiungere le descrizioni (se presenti) a tutti i valori del campo
	 * @param descrizione
	 */
	public void aggiungiDescrizioni(String descrizione) {
		for(String v: valoriCampo.keySet()) {
			valoriCampo.put(v, descrizione);
		}
	}
	
	public String toString() {
		return String.format("%s --> %s", this.getNomeCampo(), this.getValori().keySet());
	}

}
