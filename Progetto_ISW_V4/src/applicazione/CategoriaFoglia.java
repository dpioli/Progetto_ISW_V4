package applicazione;

/**
 * Classe per andare a identificare una classe foglia di una gerarchia
 * @author Irene Romano 736566
 *
 */
public class CategoriaFoglia extends Categoria{
	
	private int id;
	
	/**
	 * Costruttore oggetto foglia, assegna un numero identificativo passato come parametro
	 * per mantenere consistenza nella persistenza.
	 * @param nome
	 * @param ultimoID, identificativo foglia precedete (salvata nel file .json)
	 */
	public CategoriaFoglia(String nome, Integer ultimoID) {
		super(nome, null, null, null, true);
		this.id = ++ultimoID;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
