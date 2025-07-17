package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import applicazione.Comprensorio;
import menu.Autenticazione;
import persistenza.GestorePersistenza;
import persistenza.LogicaPersistenza;
import utenti.Configuratore;
import utenti.Fruitore;

class AutenticazioneTest {
	private GestorePersistenza gestorePersistenza;
	private LogicaPersistenza logica;

	@BeforeEach
	void setUp() throws Exception {
		gestorePersistenza = new GestorePersistenza();
		logica = new LogicaPersistenza();
		
		ArrayList<String> zoneTest = new ArrayList<>();
		Comprensorio c = new Comprensorio("CTest", zoneTest);
		
		Configuratore configuratore = new Configuratore ("Admin", "Test");
		Fruitore fruitore = new Fruitore(c, "Fruitore", "Test", "leonardo@test.com");
		
		logica.addConfiguratore(configuratore);
		logica.addFruitore(fruitore);
	}

	@AfterEach
	void tearDown() throws Exception {
		logica.getConfiguratori().remove(logica.getConfiguratori().size() - 1);
		logica.getFruitori().remove(logica.getFruitori().size() - 1);
		GestorePersistenza.salvaConfiguratori(logica.getConfiguratori());
		GestorePersistenza.salvaFruitori(logica.getFruitori());
	}

    @Test
    void testAutenticazioneFruitoreCorretta() {
    	Autenticazione auth = new Autenticazione(logica);
        Fruitore autenticato = auth.autenticazioneFruitore("Fruitore", "Test");
        assertNotNull(autenticato);
        assertEquals("Fruitore", autenticato.getUsername());
    }

    @Test
    void testAutenticazioneFruitoreFallita() {
    	Autenticazione auth = new Autenticazione(logica);
        Fruitore autenticato = auth.autenticazioneFruitore("nonEsiste", "pass");
        assertNull(autenticato);
    }

    @Test
    void testAutenticazioneConfiguratoreCorretta() {
    	Autenticazione auth = new Autenticazione(logica);
        Configuratore autenticato = auth.autenticazioneConfiguratore("Admin", "Test");
        assertNotNull(autenticato);
        assertEquals("Admin", autenticato.getUsername());
    }

}
