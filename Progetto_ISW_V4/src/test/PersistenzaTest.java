package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.google.gson.reflect.TypeToken;

import persistenza.GestorePersistenza;


class PersistenzaTest {
	
	GestorePersistenza gp = new GestorePersistenza();

	@Test
	void SalvaCaricaTest() throws Exception {
		
		ArrayList<String> array = new ArrayList<>();
		array.add("Diego");
		array.add("Leonardo");
		array.add("Giacomo");
		
		File temp = File.createTempFile("array_test", ".json");
		
		GestorePersistenza.salvaTest(array, temp.getAbsolutePath());
		
		Type listType = new TypeToken<ArrayList<String>>() {}.getType();
		ArrayList<String> caricati = GestorePersistenza.caricaTest(listType, temp.getAbsolutePath());
		
		assertEquals(3, caricati.size());
		assertEquals("Diego", caricati.get(0));
		
		temp.deleteOnExit();
	}
	
}
