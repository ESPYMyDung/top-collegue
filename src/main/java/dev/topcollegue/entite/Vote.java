package dev.topcollegue.entite;

public class Vote
{
	// attribut
	private String matriculeColl;
	private Boolean vote;
	
	//getter
	public String getMatriculeColl() {
		return matriculeColl;
	}
	
	public Boolean getVote() {
		return vote;
	}
	
	//setter
	public void setMatriculeColl(String matriculeColl) {
		this.matriculeColl = matriculeColl;
	}
	
	public void setVote(Boolean vote) {
		this.vote = vote;
	}


}
