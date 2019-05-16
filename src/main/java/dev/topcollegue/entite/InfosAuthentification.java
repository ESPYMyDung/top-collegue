package dev.topcollegue.entite;

public class InfosAuthentification
{

	//attribut
	private String matriculeColl;
	private String motDePasse;
	private String PhotoUrl;

	//getter
	public String getMatriculeColl() {
		return matriculeColl;
	}
	
	public String getMotDePasse() {
		return motDePasse;
	}
	
	public String getPhotoUrl() {
		return PhotoUrl;
	}

	//setter
	public void setMatriculeColl(String matricule) {
		this.matriculeColl = matricule;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public void setPhotoUrl(String photoUrl) {
		PhotoUrl = photoUrl;
	}
}