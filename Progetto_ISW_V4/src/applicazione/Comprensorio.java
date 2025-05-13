package applicazione;

import java.util.ArrayList;

/**
 * Classe per andare a identificare le proprieta0 si un comprensorio
 * @author Irene Romano 736566
 *
 */
public class Comprensorio {
	
	private String nome;
	private ArrayList<String> comuni = new ArrayList<>();
	
	/**
	 * Costruttore della classe comprensorio
	 * @param nome
	 * @param comuni
	 */
	public Comprensorio(String nome, ArrayList<String> comuni) {
		this.nome = nome;
		this.comuni = comuni;
	}
	
	public String getNome() {
		return nome;
	}
	
	public ArrayList<String> getComuni(){
		return comuni;
	}
	
	/**
	 * Metodo per andare ad aggiungere un comune/quartiere ad un comprensorio
	 * @param comune
	 */
	public void aggiungiComune(String comune) {
		comuni.add(comune);
	}
	
	/**
	 * Metodo per verficare se esiste gia' un comprensorio con lo stesso nome
	 * @param cerca
	 * @return
	 */
	public boolean ePresete(String cerca) {
		return nome.equalsIgnoreCase(cerca);
	}
	
	public String toString() {
		return String.format("Comprensorio: %s\nComuni: %s", this.nome, this.comuni);
	}
	

}
