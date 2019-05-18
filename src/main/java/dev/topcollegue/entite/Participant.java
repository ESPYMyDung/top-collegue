package dev.topcollegue.entite;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

@Entity
public class Participant
{
	// - attribut - 
	@Id
	@Column
	private String matricule;
	@Column
	private String nom;
	@Column
	private String prenom;
	@Column
	private String motDePasse;
	@Column
	private String photoUrl;
	@Column
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Role> authorites;
	@Column
	private int score;

	// - constructeur - 
	public Participant() {}

	public Participant(String matricule, String nom, String prenom, String motPass, String photoUrl, List<Role> authorites)
	{
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = motPass;
		this.photoUrl = photoUrl;
		this.setRoles(authorites);
		this.setScore(0);
	}
	//utiliser uniquement pour mettre des donnes dans la bdd
	public Participant(String matricule, String nom, String prenom, String motPass, String photoUrl,List<Role> authorites, int score)
	{
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = motPass;
		this.photoUrl = photoUrl;
		this.setRoles(authorites);
		this.score = score;
	}
	
	//methode
	public void votePlus()
	{ this.score += 1;}
	
	public void voteMoins()
	{ this.score -= 1;}
	
	// - getter - 
	public String getMatricule() {
		return matricule;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}
	
	public String getMotDePasse() {
		return motDePasse;
	}
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public List<Role> getRoles() {
		return authorites;
	}
	
	public int getScore() {
		return score;
	}

	// - setter - 
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	
	public void setRoles(List<Role> authorites) {
		this.authorites = authorites;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
}
