package test;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import applicazione.*;
import menu.MenuFruitore;
import persistenza.GestorePersistenza;
import persistenza.LogicaPersistenza;
import utenti.Fruitore;
import java.util.ArrayList;

public class InsiemeChiusoTest {

    @Test
    void testCostruttore() {
        InsiemeChiuso ic = new InsiemeChiuso(1);
        assertEquals(2, ic.getId());
        assertTrue(ic.getProposte().isEmpty());
    }

    @Test
    void testAggiuntaProposte() {
        InsiemeChiuso ic = new InsiemeChiuso(5);
        CategoriaFoglia catr = new CategoriaFoglia("Ripetizioni di matematica", 0);
        Proposta richiesta = new Proposta(catr, TipoProposta.RICHIESTA, 10.0);
        CategoriaFoglia cato = new CategoriaFoglia("Ripetizioni di matematica", 0);
        Proposta offerta = new Proposta(cato, TipoProposta.OFFERTA, 10.0);
        
        PropostaScambio ps = new PropostaScambio(richiesta, offerta, 1); 
        ic.aggiungiProposteAInsiemeChiuso(ps);
        assertEquals(1, ic.getProposte().size());
    }

    @Test
    void testSoddisfacimentoProposte() {
    	GestorePersistenza gp = new GestorePersistenza();
    	LogicaPersistenza logica = new LogicaPersistenza();
    	ArrayList<String> zoneC = new ArrayList<>();
    	zoneC.add("z1");
    	zoneC.add("z2");
    	Comprensorio comprensorio = new Comprensorio("TestComprensorio", zoneC);
    	CategoriaFoglia c1 = new CategoriaFoglia("c1", 0);
    	CategoriaFoglia c2 = new CategoriaFoglia ("c2", 1);
    	Fruitore f1 = new Fruitore(comprensorio, "F1", "Test", "f1@test.com");
    	Fruitore f2 = new Fruitore(comprensorio, "F2", "Test2", "f2@test.com");
    	
    	Proposta r1 = new Proposta(c1, TipoProposta.RICHIESTA, 10.0);
    	Proposta o1 = new Proposta(c2, TipoProposta.OFFERTA, 10.0);
    	
    	Proposta r2 = new Proposta(c2, TipoProposta.RICHIESTA, 10.0);
    	Proposta o2 = new Proposta(c1, TipoProposta.OFFERTA, 10.0);
    	
    	PropostaScambio ps1 = new PropostaScambio(r1, o1, 1);
    	ps1.setFruitoreAssociato(f1);
    	PropostaScambio ps2 = new PropostaScambio(r2, o2, 2);
    	ps2.setFruitoreAssociato(f2);
    	
    	ArrayList<PropostaScambio> proposte = new ArrayList<>();
    	proposte.add(ps1);
    	proposte.add(ps2);
    	
    	MenuFruitore mF = new MenuFruitore(f1, logica);
    	InsiemeChiuso ic = mF.verificaSoddisfacimentoTest(ps1, proposte);
    	
    	assertEquals(StatoProposta.CHIUSA, ic.getProposte().get(0).getStatoFinale());
    	
    	
    }
}
