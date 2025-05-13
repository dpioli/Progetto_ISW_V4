package applicazione;

/**
 * Classe che modellizza l'oggetto Proposta.
 * E' caratterizzato da un prestazione che può essere richiesta o offerta e per una certa quantità di ore.
 * 
 * @author Irene Romano 736566      Erjona Maxhaku 735766
 */
public class Proposta {
	
	private CategoriaFoglia prestazione;
	private TipoProposta tipo;
	private double quantitaOre;
	
	/**
	 * Costruttore dell'oggetto proposta
	 * @param prestazione
	 * @param tipo della proposta (richiesta / offerta)
	 * @param quantitaOre
	 */
	public Proposta(CategoriaFoglia prestazione, TipoProposta tipo, double quantitaOre) {
		this.prestazione = prestazione;
		this.tipo = tipo;
		this.quantitaOre = quantitaOre;
	}

	
	//GETTERS
	
	public double getQuantitaOre() {
		return quantitaOre;
	
	}
	
	public TipoProposta getTipo() {
		return tipo;	
	}

	public CategoriaFoglia getPrestazione() {
		return prestazione;
	}

	@Override
	public String toString() {
		return " [prestazione " + tipo.name().toLowerCase() + ": "+ prestazione.getNome() + ", ore: " + quantitaOre + "]";
	}

}
