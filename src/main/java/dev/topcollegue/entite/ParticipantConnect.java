package dev.topcollegue.entite;

public class ParticipantConnect
{
	//attribut
	private String matricule;
	private String nom;
	private String prenom;
	private int score;
	
	public ParticipantConnect() {}
	
	public ParticipantConnect(String matricule, String nom, String prenom, int score)
	{
		this.matricule = matricule;
		this.nom = nom;
		this.prenom = prenom;
		this.score = score;
	}
	
	//getter
	public String getMatricule() {
		return matricule;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getPrenom() {
		return prenom;
	}
	
	public int getScore() {
		return score;
	}
	
	//setter
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setPrenom(String prenoms) {
		this.prenom = prenoms;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
