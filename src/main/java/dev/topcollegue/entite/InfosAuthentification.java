package dev.topcollegue.entite;

public class InfosAuthentification
{

	//attribut
	private String matriculeColl;
	private String motDePasse;

	//getter
	public String getMatriculeColl() {
		return matriculeColl;
	}
	
	public String getMotDePasse() {
		return motDePasse;
	}

	//setter
	public void setMatriculeColl(String matricule) {
		this.matriculeColl = matricule;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
}