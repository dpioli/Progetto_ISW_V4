package test;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import applicazione.CategoriaFoglia;
import applicazione.FatConversione;
import java.util.ArrayList;

public class FatConversioneTest {

    @Test
    void testInizializzazioneMatrice() {
        FatConversione fc = new FatConversione();
        assertEquals(1, fc.getFdc().size());
        assertEquals(1, fc.getFdc().get(0).size());
        assertEquals(0.0, fc.getFdc().get(0).get(0));
    }
    
	@Test
	void testFDC() {
		FatConversione fdc = new FatConversione();
		CategoriaFoglia c1 = new CategoriaFoglia("CF1", 0);
		CategoriaFoglia c2 = new CategoriaFoglia("CF2", 1);
		
		fdc.agganciaTest(c1.getId(), 0.0);
		fdc.agganciaTest(c2.getId(), 1.5);
		
		assertEquals(fdc.prendiRiga(2).get(1), 1/fdc.prendiRiga(1).get(2));
		assertEquals(fdc.prendiRiga(1).get(2), 1/fdc.prendiRiga(2).get(1));
	}
    
}
