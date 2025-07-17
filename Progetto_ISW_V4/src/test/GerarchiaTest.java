package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import applicazione.CampoCaratteristico;
import applicazione.CategoriaFoglia;
import applicazione.CategoriaNonFoglia;
import applicazione.Comprensorio;
import applicazione.Gerarchia;
import utenti.Configuratore;

class GerarchiaTest {

	@Test
	void CreazioneGerarchiaTest() {
		
		Configuratore propietario = new Configuratore("Diego", "Test");
		
		ArrayList<String> zoneTest = new ArrayList<>();
		zoneTest.add("zona1");
		zoneTest.add("zona2");
		Comprensorio comprensorio = new Comprensorio("ComprensorioTest", zoneTest);
		
		CampoCaratteristico campoCaratteristico = new CampoCaratteristico("tipo");
		ArrayList<String> valori = new ArrayList<>();
		valori.add("strumento");
		valori.add("teoria");
		
		campoCaratteristico.aggiungiValori(valori);
		campoCaratteristico.aggiungiDescrizioneSpecifica("strumento", "pianoforte");
		campoCaratteristico.aggiungiDescrizioneSpecifica("teoria", "lezione di teoria");
		
		CategoriaNonFoglia radice = new CategoriaNonFoglia("Lezioni di musica", campoCaratteristico, valori.size());
		CategoriaFoglia c1 = new CategoriaFoglia("Lezioni di pianoforte", 0);
		CategoriaFoglia c2 = new CategoriaFoglia("Lezioni di teoria musicale", 1);
		
		radice.getSottoCateg().add(c1);
		radice.getSottoCateg().add(c2);
		
		Gerarchia g = new Gerarchia(radice, propietario, comprensorio);
		
		assertEquals("Lezioni di musica", g.getCatRadice().getNome());
        assertEquals(radice, g.getCatRadice());
        assertEquals(2, radice.getSottoCateg().size());
        assertTrue(radice.getSottoCateg().contains(c1));
        assertTrue(radice.getSottoCateg().contains(c2));
		
	}

}
