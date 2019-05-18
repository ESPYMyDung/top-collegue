package dev.topcollegue.entite;

import java.util.List;

public class ModelCollegue
{
	//attribut
	private String matricule;
	private String nom;
	private String prenoms;
	private String photoUrl;
	private List<Role> authorites;

	//constructeur
	public ModelCollegue() {}


	//getter
	public String getMatricule() {
		return matricule;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenoms() {
		return prenoms;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public List<Role> getRoles() {
		return authorites;
	}

	//setter
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public void setRoles(List<Role> authorites) {
		this.authorites = authorites;
	}

}


