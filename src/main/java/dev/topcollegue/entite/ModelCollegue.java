package dev.topcollegue.entite;

import java.time.LocalDate;
import java.util.List;

public class ModelCollegue
{
	//attribut
	private String matricule;
	private String nom;
	private String prenoms;
	private String email;
	private LocalDate dateDeNaissance;
	private String photoUrl;
	//private Set<Note> notes;
	private String motDePasse;
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

	public String getEmail() {
		return email;
	}

	public LocalDate getDateDeNaissance() {
		return dateDeNaissance;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	/*public Set<Note> getNotes() {
		return notes;
	}*/

	public String getMotDePasse() {
		return motDePasse;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDateDeNaissance(String dateDeNaissance) {
		this.dateDeNaissance = LocalDate.parse(dateDeNaissance);
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	/*public void setNotes(Set<Note> tmp) {
		this.notes = tmp;
	}*/

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public void setRoles(List<Role> authorites) {
		this.authorites = authorites;
	}

}


