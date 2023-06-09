import Logika.Virologus;
import Modell.Agensek.Agens;
import Modell.Agensek.Felejtes;
import Modell.Agensek.MedveVirus;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class CucumberStepDefinitions {
    private Virologus alice;
    private Virologus bob;
    private Ovohely ovohely;
    private Raktar raktar;
    private Labor labor;
    private TeruletiElem startingTerulet;
    private TeruletiElem adjacentTerulet;
    private TeruletiElem nonAdjacentTerulet;
    private Balta activeVedofelszereles;
    private Aminosav collectedAnyag;
    private GenetikaiKod scannedGenetikaiKod;
    private Agens createdAgens;

    @Given("There is a Virologus named Alice")
    public void there_is_a_virologus_named_alice() {
        alice = new Virologus();
        alice.setNev("Alice");
    }

    @Given("There is a Virologus named Bob")
    public void there_is_a_virologus_named_bob() {
        bob = new Virologus();
        bob.setNev("Bob");
    }

    @Given("there is an Ovohely with a Balta")
    public void there_is_an_ovohely_with_a_balta() {
        ovohely = new Ovohely();
        ovohely.vedofelszerelesElhelyezese(new Balta());
    }

    @Given("Bob starts on the Ovohely")
    public void bob_starts_on_the_ovohely() {
        bob.setStartTerulet(ovohely);
        startingTerulet = ovohely;
    }

    @When("Bob picks up a Vedofelszereles")
    public void bob_picks_up_a_vedofelszereles() {
        bob.vedofelszerelesFelvetele(new Vedofelszereles());
    }

    @Then("Bob should have at least one active Vedofelszereles")
    public void bob_should_have_at_least_one_active_vedofelszereles() {
        Assert.assertTrue(bob.getAktivVedofelszerelesek().size() >= 1);
    }

    @Given("there is an empty Ovohely")
    public void there_is_an_empty_ovohely() {
        ovohely = new Ovohely();
    }

    @When("Bob tries to pick up a Vedofelszereles")
    public void bob_tries_to_pick_up_a_vedofelszereles() {
        bob.vedofelszerelesFelvetele(new Vedofelszereles());
    }

    @Then("Bob should not have any active Vedofelszereles")
    public void bob_should_not_have_any_active_vedofelszereles() {
        Assert.assertNull(activeVedofelszereles);
    }

    @Given("there is a Raktar with Aminosav")
    public void there_is_a_raktar_with(int aminosavMennyiseg) {
        raktar = new Raktar();
        Aminosav aminosav = new Aminosav(aminosavMennyiseg);
        raktar.anyagElhelyezese(aminosav);;
    }

    @Given("Bob starts at the Raktar")
    public void bob_starts_at_the_raktar() {
        bob.setStartTerulet(raktar);
        startingTerulet = raktar;
    }

    @When("Bob picks up Aminosav")
    public void bob_picks_up_aminosav() {
        bob.anyagFelvetele(new Aminosav(10));
    }

    @Then("Bob should have the specified amount of Aminosav")
    public void bob_should_have_the_specified_amount_of_aminosav() {
        int specifiedAmount = 10; 
        Anyag aminosav = bob.getAnyagok().Hasznalat(new Aminosav(10));
        Assert.assertEquals(specifiedAmount, aminosav.getMennyiseg());
    }

    @Given("there is a Labor with a GenetikaiKod")
    public void there_is_a_labor_with_a_genetikai_kod() {
        labor = new Labor();
        HashSet<Anyag> anyagok = new HashSet<Anyag>();
        anyagok.add(new Aminosav(10));
        labor.setGenKod(new GenetikaiKod(anyagok, new MedveVirus()));
    }

    @Given("Bob starts at the Labor")
    public void bob_starts_at_the_labor() {
        bob.setStartTerulet(labor);
        startingTerulet = labor;
    }

    @When("Bob scans the code")
    public void bob_scans_the_code() {
        bob.kodLetapogatasa();
        Set<GenetikaiKod> scannedGenetikaiKod = bob.getIsmertKodok();
    }

    @Then("Bob should have the scanned GenetikaiKod")
    public void bob_should_have_the_scanned_genetikai_kod() {
        Assert.assertNotNull(scannedGenetikaiKod);
    }

    @Given("Bob starts at a TeruletiElem")
    public void bob_starts_at_a_teruleti_elem() {
        startingTerulet = new TeruletiElem();
        bob.setStartTerulet(startingTerulet);
    }

    @Given("there is another adjacent TeruletiElem")
    public void there_is_another_adjacent_teruleti_elem() {
        adjacentTerulet = new TeruletiElem();
        startingTerulet.addSzomszed(adjacentTerulet);
    }

    @When("Bob moves to the adjacent TeruletiElem")
    public void bob_moves_to_the_adjacent_teruleti_elem() {
        bob.mozgas(adjacentTerulet);
    }

    @Then("Bob should be on the adjacent TeruletiElem")
    public void bob_should_be_on_the_adjacent_teruleti_elem() {
        Assert.assertEquals(adjacentTerulet, bob.getJelenlegiMezo());
    }

    @Given("there is another non-adjacent TeruletiElem")
    public void there_is_another_non_adjacent_teruleti_elem() {
        nonAdjacentTerulet = new TeruletiElem();
    }

    @When("Bob tries to move to the non-adjacent TeruletiElem")
    public void bob_tries_to_move_to_the_non_adjacent_teruleti_elem() {
        bob.mozgas(nonAdjacentTerulet);
    }

    @Then("Bob should remain on the starting TeruletiElem")
    public void bob_should_remain_on_the_starting_teruleti_elem() {
        Assert.assertEquals(startingTerulet, bob.getJelenlegiMezo());
    }

    @Given("there is a Raktar")
    public void there_is_a_raktar() {
        Raktar raktar = new Raktar();
    }

    @Given("there is a Laborfor the creation")
    public void there_is_a_labor_for_the_creation() {
        labor = new Labor();
    }

    @Given("Bob starts at the Raktar Again")
    public void bob_starts_at_the_raktar_again() {
        bob.setStartTerulet(raktar);
        startingTerulet = raktar;
    }

    @When("Bob picks up Aminosav and moves to the Labor and scans the code and creates an Agens")
    public void bob_picks_up_aminosav_and_moves_to_the_labor_and_scans_the_code_and_creates_an_agens() {
        bob.anyagFelvetele(new Aminosav(20));
        bob.mozgas(labor);
        bob.kodLetapogatasa();
        bob.agensKeszitese(scannedGenetikaiKod);
    }

    @Then("Bob should have at least one Agens")
    public void bob_should_have_at_least_one_agens() {
        Assert.assertTrue(bob.getFelhasznalhatoAgensek().size() >= 1);
    }

    @Given("there is a Raktar with insufficient Aminosav")
    public void there_is_a_raktar_with_insufficient_aminosav() {
        raktar = new Raktar();
    }

    @When("Bob tries to pick up Aminosav and moves to the Labor and scans the code and tries to create an Agens")
    public void bob_tries_to_pick_up_aminosav_and_moves_to_the_labor_and_scans_the_code_and_tries_to_create_an_agens() {
        bob.anyagFelvetele(new Aminosav(2));
        bob.mozgas(labor);
        bob.kodLetapogatasa();
        bob.agensKeszitese(scannedGenetikaiKod);
    }

    @Then("Bob should not have any Agens")
    public void bob_should_not_have_any_agens() {
        Assert.assertNull(createdAgens);
    }

    @Given("there is a TeruletiElem")
    public void there_is_a_teruleti_elem() {
        startingTerulet = new TeruletiElem();
    }

    @Given("Bob has an Agens")
    public void bob_has_an_agens() {
        Agens medveAgens = bob.getFelhasznalhatoAgensek().iterator().next();
    }

    @Given("Alice is a Virologus")
    public void alice_is_a_virologus() {
        alice = new Virologus();
        alice.setNev("Alice");
    }

    @Given("Alice and Bob are on the same TeruletiElem")
    public void alice_and_bob_are_on_the_same_teruleti_elem() {
        alice.setStartTerulet(startingTerulet);
        bob.setStartTerulet(startingTerulet);
    }

    @When("Bob plants the Agens on Alice")
    public void bob_plants_the_agens_on_alice() {
        Agens medveAgens = bob.getFelhasznalhatoAgensek().iterator().next();
        bob.agensVetese(medveAgens, alice);
    }

    @Then("Alice should have the planted Agens as a modification")
    public void alice_should_have_the_planted_agens_as_a_modification() {
        Assert.assertEquals(1, alice.getAktivModositok().size());
    }
}
