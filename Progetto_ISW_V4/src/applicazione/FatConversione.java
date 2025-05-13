package applicazione;

import java.util.ArrayList;

import util.InputDati;


/**
 * Classe per l'impostazione della matrice e la gestione dei fattori di conversione
 * @author Erjona Maxhaku 735766
 *
 */

public class FatConversione {
	
	//MESSAGGI PER LA RICHIESTA
	private static final String INTERVALLO_DA_CONSIDERARE = "Considera l'intervallo che va da MIN = %f a MAX = %f (compresi)";
	private static final String RICHIESTA_FDC = "\nInserire valore fattore di conversione della nuova foglia associato alla prima foglia F1 > ";
	//MESSAGGI PER LA VISUALIZZAZIONE
	private static final String NESSUN_FDC = "Nessun fattore di conversione presente";
	//private static final String STAMPA_FDC = "STAMPA FAT CONVERSIONE > \n%s";
	//VALORI DI MASSIMO E MINIMO INDICATI DAL TEMA
	private static double MAX_FDC = 2;
	private static double MIN_FDC = 0.5;
	
	private ArrayList<ArrayList<Double>> fdc;
	
	public FatConversione(ArrayList<ArrayList<Double>> fdc) {
		this.fdc = fdc;
	}
	
	/**
	 * Costruisce la matrice contenente solo la prima casella 00.00 (casella vuota, non funzionale ai fdc).
	 */
	public FatConversione() {
		this.fdc = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> id = new ArrayList<>();
		id.add(00.00);
		fdc.add(id);
	}
	
	/**
	 * Metodo che aggancia una nuova foglia alle altre (partendo sempre da F1).
	 * 1.	Ricava il double dall'ID della foglia.
	 * 2.	Inserisce l'ID in colonna.
	 * 3.   Inserisce l'ID in riga con valori a zero.
	 * 4.   Azzera la colonna.
	 * 5.   Aggiorna i valori.
	 * @param ID nuova aggiunta
	 */
	public void aggancia(Integer nuova) {
		
		Double id = nuova.doubleValue();	
		fdc.get(0).add(id);

		aggiungiRiga(id);
		azzeraColonna();
		aggiornaValori();
	}
	
	/**
	 * Metodo che aggiunge alla matrice fdc una nuova riga costruita come:
	 *                                     NUOVO_ID 0.0  ... 0.0
	 * Quindi nome della foglia + tutti i valori settati riferiti alle altre presenti settati a zero (valore di default).
	 * I valori verranno impostati correttamente suceessovamente.
	 * @param identificativo foglia
	 */
	private void aggiungiRiga(Double id) {	
		//COSTRUISCO LA RIGA (array di double)
		ArrayList<Double> nuovaRiga = new ArrayList<>();		
		
		nuovaRiga.add(id); 
		for(int i = 1; i < fdc.size(); i++) { 
			nuovaRiga.add(0.0);
		}
		//AGGIUNGO LA RIGA ALL'ARRAY DI ARRAY
		fdc.add(nuovaRiga);
	}
	
	/**
	 * Metofo che completa la colonna con valori impostati a zero.
	 */
	private void azzeraColonna() {   
		//QUI fdc.size() è riferito alla matrice con la riga aggiunta
		for(int i = 1; i < fdc.size(); i++) { 
			fdc.get(i).add(0.0);
		}
	}
	
	/**
	 * Questo metodo permette di calcolare il massimo che può assumere il FDC.
	 * Se considero F1,F2 nodi di cui ho i fattori di conversione. OUTesistente 2->1
	 * Inserisco il nuovo nodo F3.
	 * OUTnuovo 2 -> 3, sarà un valore compreso tra 0,5 e 2
	 * Sapendo che la transizione 2 -> 3 = ( TR 2->1 ) * ( TR 1->3 )
	 * ( TR 1 -> 3 ) =   OUTnuovo/ OUTesistente
	 * Il discorso è analogo nel verso opposto.
	 * @param ramiUscenti dal primo nodo
	 * @return il valore minimo tra 2.0 e il massimo calcolato
	 */
	private double calcolaMAX(ArrayList<Double> ramiUscenti) {
		Double max = MAX_FDC;
		for(Double d : ramiUscenti) {
			if( d != 0.0) //0.0 solo se diagonale oppure appena riempito con valori 0.0 filler
				max = Math.min(max, MAX_FDC / d);
		}
		return max;		
	}
	
	/**
	 * Questo metodo permette di calcolare il minimo che può assumere il FDC.
	 * Se considero F1,F2 nodi di cui ho i fattori di conversione. OUTesistente 2->1
	 * Inserisco il nuovo nodo F3.
	 * OUTnuovo 2 -> 3, sarà un valore compreso tra 0,5 e 2
	 * Sapendo che la transizione 2 -> 3 = ( TR 2->1 ) * ( TR 1->3 )
	 * ( TR 1 -> 3 ) =   OUTnuovo/ OUTesistente
	 *  Il discorso è analogo nel verso opposto.
	 * @param ramiUscenti dal primo nodo
	 * @return il valore massimo tra 0.5 e il massimo calcolato
	 */
	private double calcolaMIN(ArrayList<Double> ramiUscenti) {
		Double min = MIN_FDC;
		for(Double d: ramiUscenti) {
			if( d != 0.0)  //ignoro i valori di default
				min = Math.max(min, MIN_FDC / d);
		}
		return min;
	}
	

	/**
	 * Funzione che aggiorna i valori della matrice.
	 * 1.	Se la matrice contiene una sola foglia non c'è bisogno di modifiche, quindi ritorna indietro;
	 *		altrimenti, ricava la rifa associata alla prima foglia, che sarà la lista dei rami uscenti da essa.
	 * 2.	Calcola massimo e minimo e richiede il valore del fattore di conversione della nuova foglia rispetto alla prima (che sarà sempre il riferimento).
	 * 3.	Inserisce il k inserito e anche il suo inverso.
	 * 4.	Completa le trasizioni dal nodo nuovo a quelli da i = 2 fino all'ultimo sfruttando la proprietà di transitività.
	 * 
	 * 			fdc[nuova][i] = fdc[nuova][riferimento]*fdc[riferimento][i];
	 * 
	 * 5.	Calcola gli inversi riempiendo le colonne.
	 *
	 * 			fdc[i][nuova] = 1/fdc[nuova][i];
	 */
	private void aggiornaValori() { //ATTENZIONE LA POSIZIONE 0 (riga e colonna) E' OCCUPATA DAGLI id
		
		int cont = fdc.size() - 1; //punto a prendere il nuovo arrivato**
		if(cont == 1) return; 
		
		ArrayList<Double> ramiUscenti = fdc.get(1); 
		
		double max = calcolaMAX(ramiUscenti);
		double min = calcolaMIN(ramiUscenti);
		System.out.printf(INTERVALLO_DA_CONSIDERARE, min, max);
		double k = InputDati.leggiDoubleConMINeMAX(RICHIESTA_FDC, min, max);
		
		//AGGANCIO ALLA PRIMA (quindi posizione 1)
		fdc.get(cont).set(1, k); 
		//RICAVO INVERSO
		fdc.get(1).set(cont, 1 / k);

		// COMPLETO SAPENDO CHE LE TRANSIZIONI SONO PRODOTTO DEL NUOVO INSERIMENTO (k) CON USCENTI DALLA PRIMA FOGLIA
		for(int i = 2; i < cont; i++) {
			
			//double valore = fdc.get(cont).get(0) * fdc.get(0).get(i);
			double valore = k * ramiUscenti.get(i);
			fdc.get(cont).set(i, valore);
			
			//COMPLETO LA COLONNA CON L'INVERSO
			double invertito = 1 / valore;
			fdc.get(i).set(cont, invertito);
		}
		//mi assicuro la diagonale sia nulla
		fdc.get(cont).set(cont, 0.0); 
	}
	
	/**
	 * Metodo che mi restituisce la riga riferita a un particolare ID
	 * @param i
	 * @return
	 */
	public ArrayList<Double> prendiRiga(int id){
		return fdc.get(id);
	}
	
	/**
	 * Metodo che stampa i fattori di conversione a terminale.
	 */
	public void stampaFDC() {
		if(fdc.size() == 1)
			System.out.println(NESSUN_FDC);
		else
			System.out.println(this.toString());
	}
	
	/**
	 * Metodo di formattazione stringa da stampare a video.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		//STAMPO LA PRIMA RIGA CON GLI IDENTIFICATIVI
		for (int i = 0; i < fdc.get(0).size(); i++) {
	        sb.append(String.format("%-15s", i == 0 ? "IDENTIFICATIVI" : "F" + i));
	    }
		sb.append("\n");

	    //STAMPO IL RESTO DELLA MATRICE
	    for (int i = 1; i < fdc.size(); i++) {	
	        for (int j = 0; j < fdc.get(i).size(); j++) {

	        	String value =  (j == 0 ? "F" + i : String.format("%.2f", fdc.get(i).get(j)));
	            sb.append(String.format("%-15s", value));
	        }
	        sb.append("\n");
	    }

	    return sb.toString();
	}
		
}
