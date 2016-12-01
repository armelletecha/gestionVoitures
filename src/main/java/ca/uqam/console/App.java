package ca.uqam.console;

import ca.uqam.config.Config;

import ca.uqam.model.Client;
import ca.uqam.model.Location;
import ca.uqam.model.Vehicule;
import ca.uqam.repositories.ClientRepository;
import ca.uqam.repositories.LocationRepository;
import ca.uqam.repositories.VehiculeRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.Date;

public class App {
	static ConfigurableApplicationContext context = SpringApplication.run(Config.class);
    static ClientRepository repository = context.getBean(ClientRepository.class);
    static VehiculeRepository repository1= context.getBean(VehiculeRepository.class);
    static LocationRepository repository2 = context.getBean(LocationRepository.class);

    
    //initialiser la base de donnees
    public static void initialisationBase() {
	    repository1.save(new Vehicule("789596","Toyota","Camry","Sedan","2016","120","Disponible"));
	    repository1.save(new Vehicule("20162009","Honda","Accord","SEDAN","2016","120", "Disponible"));
	    repository1.save(new Vehicule("20162010","gip","Accord","SEDAN","2016","120", "Disponible"));
	    
	    repository.save(new Client("AM002300","Armelle","Tenekeu","5147718969","1345 rue saint charles"));
	    repository.save(new Client("AM002310","Mama","Kouboura","5146740886","1345 chemin de Chambly"));
	    repository.save(new Client("AM002312","Moussa","Balla","5146212124","1345 rue de la barre"));
    }
    
    
    public static ArrayList<Client> allCustomers() {
    	ArrayList<Client> list_clients = new ArrayList<Client>();
	    Iterable<Client> customers = repository.findAll();
	    for (Client customer : customers){
	        list_clients.add(customer);
	    }
	    return list_clients;
    }
    
    public static ArrayList<Vehicule> allCars() {
    	ArrayList<Vehicule> list_voitures = new ArrayList<Vehicule>();
	    Iterable<Vehicule> voitures = repository1.findAll();
	    for (Vehicule voiture : voitures)
	        list_voitures.add(voiture);
	    return list_voitures;
    }
    
    public static ArrayList<Location> allLocations() {
    	ArrayList<Location> list_locations = new ArrayList<Location>();
	    Iterable<Location> locations = repository2.findAll();
	    for (Location location : locations)
	        list_locations.add(location);
	    return list_locations;
    }
    
    
	//determiner la liste des voitures disponibles
   public static  ArrayList<Vehicule> voituresDisponibles(){
    	ArrayList<Vehicule> listeVoituresDisponibles = new ArrayList<Vehicule>();
    	Iterable<Vehicule> voitures = repository1.findByState("Disponible");
	    for (Vehicule voiture : voitures){
	    	listeVoituresDisponibles.add(voiture);
	    }
    	return listeVoituresDisponibles;
    }
    
    
	//determiner la liste des voitures louees
    static ArrayList<Vehicule> voituresLouees(){
    	ArrayList<Vehicule> listeVoituresLouees = new ArrayList<Vehicule>();
    	Iterable<Vehicule> voitures = repository1.findByState("Louee");
	    for (Vehicule voiture : voitures){
	    	listeVoituresLouees.add(voiture);
	    }
    	return listeVoituresLouees;
    }
    
    
    public static  Vehicule carChoice(Long c){
    	 Vehicule voiture2 = repository1.findOne(c);
    	 return voiture2;
    }
    
    
    public static  Client verifierClient(String numPermis){
    	Client client = repository.findByPermitNumber(numPermis);
    	return client;
    }
    
     //methode pour louer une voiture
    public static  void louerVoiture(Client client3, Vehicule vehicule){
	    repository2.save(new Location(client3, vehicule));
        //changer l etat de la voiture
        vehicule.setState("Louee");
        repository1.save(vehicule);
    }
 
    public static Vehicule voitureARetourner(String p) {    	
    	Vehicule voitureARendre = new Vehicule();
    	//Date date = new Date(2010-10-10);
    	String b = "1969-12-31";
    	Client clientConcerne = repository.findByPermitNumber(p);
    	Iterable<Location> locationConcernes = repository2.findByClient(clientConcerne);
    	for(Location loc : locationConcernes) {
    		String a = loc.getDateOfReturn().toString();
    		if (a.equals(b)) {
    			voitureARendre = repository1.findOne(loc.getIdVehicule());
    		}
    	}
    	return voitureARendre;
}

    
    static void retournerVoiture(Vehicule voiture){
    		voiture.setState("Disponible");
    		repository1.save(voiture);
    		String b = "1969-12-31";
    		Iterable<Location> locationConcernes = repository2.findByVehicule(voiture);
    		for(Location loc : locationConcernes) {
        		String a = loc.getDateOfReturn().toString();
        		if (a.equals(b)) {
        			loc.setDateOfReturn(new Date());
        			repository2.save(loc);
        		}
    		}
        }
    
    public static void save(Client client) {
    	repository.save(client);
    }
    
    //afficher les voitures louees et leurs locataires
    public static ArrayList<String> locatairesVoitures(){
    	ArrayList<Vehicule> liste = new ArrayList<Vehicule>();
    	ArrayList<String> liste2 = new ArrayList<String>();
    	liste = voituresLouees();
    	String b = "1969-12-31";
    	for (Vehicule voiture : liste){
    		Iterable<Location> listeLoc = repository2.findByVehicule(voiture);
    		for (Location loc : listeLoc){
    			String a = loc.getDateOfReturn().toString();
        		if (a.equals(b)) {
		    		Client client = repository.findById(loc.getIdClient());
		    		liste2.add(voiture.toString()+" louee par"+" "+client.toString());		    		
        		}
    		}
    	}
    	return liste2;
    }
    
    
}