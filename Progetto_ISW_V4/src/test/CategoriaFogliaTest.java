package test;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import applicazione.CategoriaFoglia;

public class CategoriaFogliaTest {

    @Test
    void testCostruttore() {
        CategoriaFoglia c = new CategoriaFoglia("Lezioni di chitarra", 5);
        assertEquals("Lezioni di chitarra", c.getNome());
        assertEquals(6, c.getId());
    }

    @Test
    void testSetId() {
        CategoriaFoglia c = new CategoriaFoglia("Solfeggio", 1);
        c.setId(10);
        assertEquals(10, c.getId());
    }
}
