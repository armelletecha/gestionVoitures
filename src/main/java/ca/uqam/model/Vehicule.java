package ca.uqam.model;

import org.dom4j.tree.AbstractEntity;

import javax.persistence.*;

/**
 * Created by Mo-is-Balla on 2016-11-14.
 */
@Table
@Entity(name = "Vehicule")
public class Vehicule extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String Matricule;
    @Column(name = "Marque")
    private String Marque;
    @Column(name = "Model")
    private String Model;
    @Column(name = "Type")
    private String Type;
    @Column(name = "Year")
    private String Year;
    @Column(name = "Price_per_day")
    private String price;
    @Column(name = "State")
    private String state;

    public Vehicule(){
    	
    }
    
    public Vehicule(String matricule, String marque, String model, String type, String year, String price, String state) {
        this.Matricule = matricule;
        this.Marque = marque;
        this.Model = model;
        this.Type=type;
        this.Year= year;
        this.price=price;
        this.state = state;

    }
    @Override
    public String toString() {
        return String.format("[%d - Matricule=%s, Marque='%s', Model='%s', Type ='%s' , Year ='%s' ,Price = %s, Etat ='%s']",id , getMatricule(), Marque, Model, Type, Year, price, state);
    }
	public String getMatricule() {
		return Matricule;
	}
	public void setMatricule(String matricule) {
		Matricule = matricule;
	}

	public Long getId() {
		return id;
	}

	public void setState(String state) {
		this.state = state;
		
	}

	public String getState() {
		return this.state;
	}
}