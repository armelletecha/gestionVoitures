package ca.uqam.unittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import ca.uqam.console.App;
import ca.uqam.model.Client;
import ca.uqam.model.Location;
import ca.uqam.model.Vehicule;

public class AppTest {
	
	Vehicule voiture2 ;
	Client client2;
	Location location2;
	
	@Before
	public void setUp() throws Exception {
		voiture2 = new Vehicule("789596", "Toyota", "Camry", "Sedan", "2016", "120", "Louee");
		client2 = new Client("AM002300", "Armelle", "Tenekeu", "5147718969", "1345 rue saint charles");
		location2 = new Location(client2, voiture2);
	}

	@Test
	public void voituresDisponiblesTest() {
    	ArrayList<Vehicule> listeTest = new ArrayList<Vehicule>();
    	listeTest = App.voituresDisponibles();
    	for (Vehicule voiture : listeTest) {
    		assertEquals("Disponible", voiture.getState());
    	}

    }
	
	@Test
	public void carChoiceTest() {
		Vehicule voiture1 = App.carChoice(1L);
		assertEquals(voiture1.getMatricule(), voiture2.getMatricule());
	}
	
	@Test
	public void verifierClientTest() {
		Client client1 = App.verifierClient("AM002300");
		assertEquals(client1.getName(), client2.getName());
	}
	
	@Test
	//un pb ici
	public void loueVoitureTest() {
		ArrayList<Location> listeLouee = new ArrayList<Location>() ;
		App.louerVoiture(client2, voiture2);
		listeLouee = App.listeLocation();
		Location location1 = listeLouee.get(listeLouee.size() - 1);
		assertEquals(location1.getIdClient(), location2.getIdClient());
		
	}
	
	@Test
	public void voitureARetournerTest() {
		Vehicule voiture1 = App.voitureARetourner("AM002300");
		assertEquals(voiture1.getMatricule(), voiture2.getMatricule());
	}
	
	@Test
	public void retournerVoiture() {
		
	}

}
