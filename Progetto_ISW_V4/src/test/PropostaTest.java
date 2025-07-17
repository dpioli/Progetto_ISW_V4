package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import applicazione.*;
import applicazione.TipoProposta;

public class PropostaTest {

    @Test
    void testCostruttoreEGetter() {
        CategoriaFoglia categoria = new CategoriaFoglia("Ripetizioni di matematica", 0);
        Proposta proposta = new Proposta(categoria, TipoProposta.RICHIESTA, 10.0);

        assertEquals(10.0, proposta.getQuantitaOre());
        assertEquals(TipoProposta.RICHIESTA, proposta.getTipo());
        assertEquals(categoria, proposta.getPrestazione());
    }

    @Test
    void testToString() {
        CategoriaFoglia categoria = new CategoriaFoglia("Pianoforte", 1);
        Proposta proposta = new Proposta(categoria, TipoProposta.OFFERTA, 5.0);

        String expected = " [prestazione offerta: Pianoforte, ore: 5.0]";
        assertEquals(expected, proposta.toString());
    }
}

