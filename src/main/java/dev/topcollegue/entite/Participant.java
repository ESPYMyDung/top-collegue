package dev.topcollegue.entite;

import javax.persistence.Column;
import javax.persistence.Entity;
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
	private int score;

	// - constructeur - 
	public Participant() {}
	
	public Participant(String matricule, String nom, String prenom, String motPass, String photoUrl)
	{
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = motPass;
		this.photoUrl = photoUrl;
		this.setScore(0);
	}
	//utiliser uniquement pour mettre des donnes dans la bdd
	public Participant(String matricule, String nom, String prenom, String motPass, String photoUrl, int score)
	{
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.motDePasse = motPass;
		this.photoUrl = photoUrl;
		this.score = score;
	}
	
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
	
	public void setScore(int score) {
		this.score = score;
	}
	
}
