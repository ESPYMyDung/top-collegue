package dev.topcollegue.entite;

public class ParticipantScore
{
	//attribut
	private String matricule;
	private int score;

	//getter
	public String getMatricule() {
		return matricule;
	}
	
	public int getScore() {
		return score;
	}
	
	//setter
	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
