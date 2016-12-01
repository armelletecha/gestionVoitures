package ca.uqam.console;

import ca.uqam.model.Client;
import ca.uqam.model.Location;
import ca.uqam.model.Vehicule;

import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.context.ConfigurableApplicationContext;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ConfigurableApplicationContext context = App.context;
    static ArrayList<Vehicule> listeVoituresDisponibles = new ArrayList<Vehicule>();
    static ArrayList<Location> listeLocations = new ArrayList<Location>();
    
    public static void main(String[] args) {    
    App.initialisationBase();
    	help();
    	
    }
     
	    static void help() { 	
	    	 ArrayList<String> listeVoituresLouees;
		    	int choiceMenu ;
		    	
	    	System.out.println("\n Menu principal : \n");
	        System.out.println(
	                        "1 : Afficher les voitures disponibles\n " +
	                        "2 : Afficher les voitures louees \n " +
	                        "3 : Louer une voiture \n " +
	                        "4 : Retourner une voiture \n " +
	                        "5 : Quitter\n " + 
	                        "Que voulez-vous faire \n"
	                        );
	        choiceMenu = sc.nextInt();
	    	switch (choiceMenu) {
	    	//******************choisir d'afficher les voitures disponibles***********************
	    		case 1:
	        		afficherVoituresDisponibles();
	        		retourMenuPrincipal();
	        		break;		
    		//******************choisir d'afficher les voitures louees****************************		
	        	case 2:
	        		listeVoituresLouees = App.locatairesVoitures();
	        		if (listeVoituresLouees.size() == 0) {
	                 	System.out.println("\n Aucune voiture louee!!");
	                 }
	            	 else {
	        	        System.out.println("\n Liste des voitures louees :\n");
	        	        for (String voiture : listeVoituresLouees){
	        	        	System.out.println(voiture);
	        	        }
	            	 }
	        		retourMenuPrincipal();
	        		break;	
    		//******************choisir de louer une voiture**************************************
	        	case 3:
	        		Vehicule vehiculeChoisie = new Vehicule();
	        		Client client2 = new Client();
	        		afficherVoituresDisponibles();
	        		Long choiceUser; 
	            	
	            	//recuperer la voiture choisie par l'utilisateur
	            	do{
	            		System.out.println("\n veuillez choisir un numero :\n");
	            		choiceUser = sc.nextLong();
	      
	            	} while ((choiceUser < 0) || (choiceUser > listeVoituresDisponibles.size()));
	            	vehiculeChoisie = App.carChoice(choiceUser);
	            	System.out.println("\n Vehicule choisi : " +vehiculeChoisie);
	         	    	System.out.println("Confirmer votre choix  : O ou N ?\n");
	         	    	sc.nextLine();
	         	    	String r = sc.nextLine();
	         	    	char r_c = r.charAt(0);
	         	    	if ((r_c == 'O') || (r_c == 'o')){
	            	//entrer le numero de permis de conduire
	         	    		System.out.println("\n Veuillez saisir votre numero de permis de conduire :\n");
	         	    		String numeroPermis = sc.nextLine();
	         	    		client2 = App.verifierClient(numeroPermis);
	         	    		if (client2 == null){
	         	    				client2 = identifierClient(numeroPermis);
	         	    				App.save(client2);
	         	    				}
	         	    		App.louerVoiture(client2,vehiculeChoisie);
	         	    		System.out.println("\n Votre location a ete bien enregistree!!");
	         	    	}
	         	    	else 
	         	    		System.out.println("\n Votre choix a ete annule. Veuillez reesayer \n");
	            	 
	            	 retourMenuPrincipal();
	        		break;
	        			        		
    		//******************choisir de retourner une voiture**********************************
	        	case 4:
	        		sc.nextLine();
	        		Vehicule voiture2 = new Vehicule();
	        		System.out.println("Entrer le numero de votre permis : \n");
	            	String permisRetour = sc.nextLine();
	            	voiture2 = App.voitureARetourner(permisRetour);
	            	if (voiture2.getMatricule() == null) {
	            		System.out.println("\n Erreur quelque part! Ce numero de permis n'a louee aucune voiture \n");
	            		help();
	            	}
	            	else {
		            	System.out.println("\n S'agit il de la " +voiture2.toString() +" ?  \nO ou N");
		            	String v = sc.nextLine();
		            	char v_c = v.charAt(0);
		            	if ((v_c == 'O') || (v_c == 'o')){
		            		App.retournerVoiture(voiture2);
		            		System.out.println("\n Merci pour votre confiance !");
		                	retourMenuPrincipal();
		                }
		            	else {
		            		System.out.println("\n Un probleme quelque part!! Verifier le numero de permis entre! \n");
		            		help();
		            	}
	            	}
	        		break;
    		//******************choisir de quitter l'application*********************************
	        	case 5:
	        		quitter();
	        		break;
	        		
	        	default:
	        		System.out.println("\n Mauvaise entree ! Veuillez reessayer \n");
	        		help();
	        		break;
	        }
	      //  sc.close();
	    }
	   
		private static void retourMenuPrincipal() {
			sc.nextLine();
			char reponse_c;
	    	do {
	    		System.out.println("\n******************************************");
				System.out.println("Souhaitez vous continuer? O ou N");
	            String reponse = sc.nextLine();
				//sc.nextLine();
	        	reponse_c = reponse.charAt(0);
	        	if ((reponse_c == 'O') || (reponse_c == 'o'))
	        		help();  
	        	else if ((reponse_c == 'N') || (reponse_c == 'n')) {
	        		System.out.println("Au plaisir de vous revoir!");
	        		//sc.close();
	        		context.close();
	        		System.exit(0);
	        	} 
	        	else 
	        		System.out.print("Mauvaise entree\n");
	    	} while (reponse_c != 'O' || reponse_c != 'o' || reponse_c != 'N' || reponse_c != 'n');
			
		}

		static void quitter () {
	    	sc.nextLine();
	    	char r_c;
	    	do {
		    	System.out.println("Etes vous sure de vouloir quitter ? O ou N");
		    	String r = sc.nextLine();
		    	r_c = r.charAt(0);
		    	if ((r_c == 'O') || (r_c == 'o')){
		    		System.out.println("Merci d'avoir utilise notre application. au plaisir de vous revoir!");
		    		context.close();
	        		sc.close();
		    		System.exit(0);
		    	}
		    	else if ((r_c == 'N') || (r_c == 'n'))
		    		help();
	    	} while(r_c != 'O' || r_c != 'o' || r_c != 'N' || r_c != 'n');
	    }
   
	    
	    static Client identifierClient(String p) {
	    	Client nouveauClient = new Client();
	    	nouveauClient.setPermitNumber(p);
	    	System.out.println("\n IDENTIFICATION : ");
        	System.out.println("Entrez votre prenom :");
        	nouveauClient.setFirstName(sc.nextLine());
        	
        	System.out.println("Entrez votre nom :");
        	nouveauClient.setLastName(sc.nextLine());
        	
        	do {
        		System.out.println("Entrez votre numero de telephone :");
        		nouveauClient.setPhone(sc.nextLine());
        	} while (nouveauClient.getPhone().length() != 10);
        	
        	System.out.println("Entrez votre addresse :\n");
        	nouveauClient.setAdresse(sc.nextLine());
        	
        	return nouveauClient;
	    }
	    
	    static void afficherVoituresDisponibles() {
	    	listeVoituresDisponibles = App.voituresDisponibles();
			if (listeVoituresDisponibles == null) {
	         	System.out.println("\n Aucune voiture n'est disponible!!\n");
	         }
	    	 else {
		        System.out.println("\n \n Liste des voitures disponibles :\n");
		        for (Vehicule voiture : listeVoituresDisponibles){
		        	System.out.println(voiture);
		        }
	    	 }
	    }
}	

