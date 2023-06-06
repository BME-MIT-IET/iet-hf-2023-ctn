import Logika.Virologus;
import Modell.Agensek.Agens;
import Modell.Agensek.Felejtes;
import Modell.Agensek.MedveVirus;
import Modell.Agensek.Sebezhetetlenseg;
import Modell.Anyagok.Aminosav;
import Modell.Anyagok.Anyag;
import Modell.Genetika.GenetikaiKod;
import Modell.Palya.Labor;
import Modell.Palya.Ovohely;
import Modell.Palya.Raktar;
import Modell.Palya.TeruletiElem;
import Modell.TulajdonsagModosito;
import Modell.Vedofelszereles.Balta;
import Modell.Vedofelszereles.Vedofelszereles;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

// Method name convention:
// MethodName_StateUnderTest_ExpectedBehavior
// https://medium.com/@stefanovskyi/unit-test-naming-conventions-dd9208eadbea

public class VirologusTest {
    static Virologus alice;
    static Virologus bob;

    @BeforeEach
    public void init(){
        alice = new Virologus();
        alice.setNev("Alice");

        bob = new Virologus();
        bob.setNev("Bob");
    }

    public Ovohely baltasOvohelyElokeszitese(){
        Ovohely ovohely = new Ovohely();
        Balta balta = new Balta();

        ovohely.vedofelszerelesElhelyezese(balta);

        return ovohely;
    }

    public Raktar aminosavasRaktarElokeszitese(int aminosavMennyiseg){
        Raktar raktar = new Raktar();
        Aminosav aminosav = new Aminosav(aminosavMennyiseg);
        raktar.anyagElhelyezese(aminosav);

        return raktar;
    }

    public Virologus agensselRendelkezoVirologusElokeszitese(){
        Virologus alex = new Virologus();

        int aminosavMennyiseg = 32;
        int agensAminosavKoltsege = 10; // => Lesz eleg anyaga az elkesziteshez

        // Teruletek elokeszitese
        Raktar raktar = aminosavasRaktarElokeszitese(aminosavMennyiseg);
        Labor labor = medvevirusosLaborElokeszitese(agensAminosavKoltsege);

        labor.addSzomszed(raktar);
        raktar.addSzomszed(labor);

        alex.setStartTerulet(raktar);

        // Anyag felvetele, kod letapogatasa
        alex.anyagFelvetele(new Aminosav(20));
        alex.mozgas(labor);
        alex.kodLetapogatasa();

        // Megszerzett eroforrasokbol agens elkeszitese
        GenetikaiKod medve = alex.getIsmertKodok().iterator().next();
        alex.agensKeszitese(medve);

        return alex;
    }

    public Labor medvevirusosLaborElokeszitese(int aminosavSzukseglet){
        Labor labor = new Labor();

        HashSet<Anyag> anyagok = new HashSet<Anyag>();
        anyagok.add(new Aminosav(aminosavSzukseglet));

        GenetikaiKod medveGenetikaiKod = new GenetikaiKod(anyagok, new MedveVirus());

        labor.setGenKod(medveGenetikaiKod);

        return labor;
    }

    // Vedofelszereles felvetele
    @Test
    @DisplayName("Felszereles sikeres felvetele")
    public void sikeresFelvetel_VanMitFelvennie_True(){
        Ovohely ovohely = baltasOvohelyElokeszitese();
        bob.setStartTerulet(ovohely);

        bob.vedofelszerelesFelvetele(new Balta());

        List<Vedofelszereles> bobFelszerelesei = bob.getAktivVedofelszerelesek();

        assertTrue(bobFelszerelesei.size() > 0);
    }

    @Test
    @DisplayName("Felszereles sikertelen felvetele")
    public void sikertelenFelvetel_NincsMitFelvennie_False(){
        Ovohely ovohely = baltasOvohelyElokeszitese();
        bob.setStartTerulet(ovohely);

        bob.vedofelszerelesFelvetele(new Balta());

        List<Vedofelszereles> bobFelszerelesei = bob.getAktivVedofelszerelesek();

        assertFalse(bobFelszerelesei.size() > 0);
    }

    @Test
    @DisplayName("Felszereles sikeres eldobasa")
    public void sikeresEldobas() {
        Ovohely ovohely = baltasOvohelyElokeszitese();
        bob.setStartTerulet(ovohely);

        bob.vedofelszerelesFelvetele(new Balta());

        List<Vedofelszereles> bobFelszerelesei = bob.getAktivVedofelszerelesek();

        assertTrue(bobFelszerelesei.size() > 0);

        bob.vedofelszerelesEldobasa(bobFelszerelesei.get(0));

        bobFelszerelesei = bob.getAktivVedofelszerelesek();

        assertFalse(bobFelszerelesei.size() > 0);
    }

    // Anyag felvetele
    @Test
    @DisplayName("Anyag sikeres felvetele")
    public void sikeresAnyagfelvetel_VanAnyagRaktarban_True(){
        int aminosavMennyiseg = 10;

        Raktar raktar = aminosavasRaktarElokeszitese(32);
        bob.setStartTerulet(raktar);

        bob.anyagFelvetele(new Aminosav(aminosavMennyiseg));
        Anyag aminosav = bob.getAnyagok().Hasznalat(new Aminosav(10));

        assertEquals(aminosavMennyiseg, aminosav.getMennyiseg());
    }

    @Test
    @DisplayName("Anyag sikertelen felvetele")
    public void sikertelenAnyagfelvetel_NincsElegAnyagRaktarban_False(){
        int aminosavMennyiseg = 40;

        Raktar raktar = aminosavasRaktarElokeszitese(32);
        bob.setStartTerulet(raktar);

        bob.anyagFelvetele(new Aminosav(aminosavMennyiseg));
        Anyag aminosav = bob.getAnyagok().Hasznalat(new Aminosav(10));

        assertNotEquals(aminosavMennyiseg, aminosav.getMennyiseg());
    }

    // Kod letapogatasa
    @Test
    @DisplayName("Genetikai kod sikeres letapogatasa")
    public void sikeresKodLetapogatasa_VanKodLaborban_True(){

        Labor labor = medvevirusosLaborElokeszitese(32);
        bob.setStartTerulet(labor);

        bob.kodLetapogatasa();

        Set<GenetikaiKod> kodjai = bob.getIsmertKodok();

        assertEquals(1, kodjai.size());
    }

    // Mozgas
    @Test
    @DisplayName("Sikeres mozgas (szomszedos) mezore")
    public void sikeresMozgasMasikMezore_LetezikSzomszedosMezo_True(){
        TeruletiElem start = new TeruletiElem();
        TeruletiElem cel = new TeruletiElem();

        start.addSzomszed(cel);

        bob.setStartTerulet(start);
        bob.mozgas(cel);

        TeruletiElem jelenlegi = bob.getJelenlegiMezo();

        assertEquals(cel, jelenlegi);
    }

    @Test
    @DisplayName("Sikertelen mozgas (nem szomszedos) mezore")
    public void sikertelenMozgasMasikMezore_NemSzomszedosMezok_False(){
        TeruletiElem start = new TeruletiElem();
        TeruletiElem cel = new TeruletiElem();

        bob.setStartTerulet(start);
        bob.mozgas(cel);

        TeruletiElem jelenlegi = bob.getJelenlegiMezo();

        assertNotEquals(cel, jelenlegi);
    }

    @Test
    @DisplayName("Agens sikeres elkeszitese")
    public void agensKeszitese_SikeresAgensKeszites_True(){
        int aminosavMennyiseg = 32;
        int agensAminosavKoltsege = 10; // => Lesz eleg anyaga az elkesziteshez

        // Teruletek elokeszitese
        Raktar raktar = aminosavasRaktarElokeszitese(aminosavMennyiseg);
        Labor labor = medvevirusosLaborElokeszitese(agensAminosavKoltsege);

        labor.addSzomszed(raktar);
        raktar.addSzomszed(labor);

        bob.setStartTerulet(raktar);

        // Anyag felvetele, kod letapogatasa
        bob.anyagFelvetele(new Aminosav(20));
        bob.mozgas(labor);
        bob.kodLetapogatasa();

        // Megszerzett eroforrasokbol agens elkeszitese
        GenetikaiKod medve = bob.getIsmertKodok().iterator().next();
        bob.agensKeszitese(medve);

        List<Agens> agensei = bob.getFelhasznalhatoAgensek();

        //ROSSZ: assertTrue(agensei.get(0) instanceof Felejtes);
        assertTrue(agensei.get(0) instanceof MedveVirus);
        //Alternative: assertEquals(1, agensei.size());
    }

    @Test
    @DisplayName("Agens sikertelen elkeszitese (nincs eleg anyaga)")
    public void agensKeszitese_SikertelenAgensKeszitesNincsElegAnyag_False(){
        int aminosavMennyiseg = 8;
        int agensAminosavKoltsege = 10; // => Nem lesz eleg anyaga az elkesziteshez

        // Teruletek elokeszitese
        Raktar raktar = aminosavasRaktarElokeszitese(aminosavMennyiseg);
        Labor labor = medvevirusosLaborElokeszitese(agensAminosavKoltsege);

        labor.addSzomszed(raktar);
        raktar.addSzomszed(labor);

        bob.setStartTerulet(raktar);

        // Anyag felvetele, kod letapogatasa
        bob.anyagFelvetele(new Aminosav(20));
        bob.mozgas(labor);
        bob.kodLetapogatasa();

        // Megszerzett eroforrasokbol agens elkeszitese
        GenetikaiKod medve = bob.getIsmertKodok().iterator().next();
        bob.agensKeszitese(medve);

        List<Agens> agensei = bob.getFelhasznalhatoAgensek();

        assertEquals(0, agensei.size());
    }

    @Test
    @DisplayName("Agens sikeres vetese")
    public void agensVetese_BirtokoltAgensVetese_True(){

        TeruletiElem terulet = new TeruletiElem();

        bob = agensselRendelkezoVirologusElokeszitese();
        bob.setStartTerulet(terulet);
        alice.setStartTerulet(terulet);

        Agens medveAgens = bob.getFelhasznalhatoAgensek().get(0);

        bob.agensVetese(medveAgens, alice);

        List<TulajdonsagModosito> hatasai = alice.getAktivModositok();

        //ROSSZ: assertTrue(hatasai.get(0) instanceof Felejtes);
        assertTrue(hatasai.get(0) instanceof MedveVirus);
        //Alternative: assertEquals(1, hatasai.size());
    }

    @Test
    @DisplayName("Agens sikertelen vetese")
    public void agensVetese_BirtokoltAgensVetese_False(){

        TeruletiElem terulet = new TeruletiElem();

        bob = agensselRendelkezoVirologusElokeszitese();
        bob.setStartTerulet(terulet);
        alice.setStartTerulet(terulet);

        alice.agensKapasa(alice, new Sebezhetetlenseg());

        Agens medveAgens = bob.getFelhasznalhatoAgensek().get(0);

        bob.agensVetese(medveAgens, alice);

        List<TulajdonsagModosito> hatasai = alice.getAktivModositok();

        //ROSSZ: assertTrue(hatasai.get(0) instanceof Felejtes);
        assertFalse(hatasai.get(0) instanceof MedveVirus);
        //Alternative: assertEquals(1, hatasai.size());
    }
}
