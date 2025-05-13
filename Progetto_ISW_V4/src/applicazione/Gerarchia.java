package applicazione;

import java.util.List;

import utenti.Configuratore;

/**
 * Classe per identificare le proprieta' di una gerarchia
 * @author Diego Pioli 736160
 *
 */
public class Gerarchia {
	
	private Categoria catRadice;
	private Configuratore proprietario;
	private Comprensorio comprensorio;
	
	/**
	 * Costruttore della classe gerarchia
	 * @param catRadice
	 * @param prorpietario
	 * @param compr 
	 */
	public Gerarchia (Categoria catRadice, Configuratore prorpietario, Comprensorio comprensorio) {
		this.catRadice = catRadice;
		this.proprietario = prorpietario;
		this.comprensorio = comprensorio;
	}

	public Categoria getCatRadice() {
		return catRadice;
	}

	public Configuratore getProprietario() {
		return proprietario;
	}
	
	public Comprensorio getComprensorio() {
		return comprensorio;
	}
	public String getNomeComprensorio() {
		return comprensorio.getNome();
	}
	
	/**
	 * Metodo per verificare se una gerarchia ha lo stesso nome di un'altra
	 * @param nomeGerarchia
	 * @return true se è già presente una gerarchia con quel nome
	 */
	public boolean eNomeUguale(String nomeGerarchia) {
		return catRadice.eUguale(nomeGerarchia);
	}
	
	/**
	 * Metodo per ottenere la stringa di visualizzazione della gerarchia
	 * @param gerarchia
	 * @return stringa dell'albero generato
	 */
	public static String generaAlberoStringa(Gerarchia gerarchia) {
        StringBuilder builder = new StringBuilder();
        costruisciStringa(gerarchia.getCatRadice(), "", false, builder);
        return builder.toString();
    }
	
	/**
	 * Metodo per generare il grafo della gerarchia 
	 * @param categoria
	 * @param prefisso
	 * @param èUltimo
	 * @param builder
	 */
    private static void costruisciStringa(Categoria categoria, String prefisso, boolean èUltimo, StringBuilder builder) {
        builder.append(prefisso);
        if (!prefisso.isEmpty()) {
            builder.append(èUltimo ? "└── " : "├── ");
        }
        if(!(categoria.isFoglia())) {
        	builder.append(categoria.getNome()).append(categoria.getValoriCampo().toString()).append("\n");
        } else {
        	builder.append(categoria.getNome()).append("\n");
        }
        
        List<Categoria> figli = categoria.getSottoCateg();
        for (int i = 0; i < figli.size(); i++) {
            boolean ultimo = (i == figli.size() - 1);
            String nuovoPrefisso = prefisso + (prefisso.isEmpty() ? " " : (èUltimo ? "    " : "│   "));
            costruisciStringa(figli.get(i), nuovoPrefisso, ultimo, builder);
        }
    }
	
}