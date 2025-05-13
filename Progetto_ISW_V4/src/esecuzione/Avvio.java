package esecuzione;

import persistenza.GestorePersistenza;
import persistenza.LogicaPersistenza;
import menu.MenuPrincipale;

/**
 * Classe Main del programma
 * @author Erjona Maxhaku 735766      Diego Pioli 736160      Irene Romano 736566 
 *
 */
public class Avvio {

	public static void main(String[] args) {
		GestorePersistenza gestore= new GestorePersistenza();
		LogicaPersistenza logica = new LogicaPersistenza();
		MenuPrincipale menu = new MenuPrincipale(logica);
		menu.azioniMenuPrincipale();
	}

}
