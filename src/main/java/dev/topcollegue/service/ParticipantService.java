package dev.topcollegue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.topcollegue.entite.Participant;
import dev.topcollegue.entite.ParticipantRepository;
import dev.topcollegue.entite.Vote;

@Service
public class ParticipantService
{	
	//attribut
	private ParticipantRepository accesBDD;
	
	@Autowired //le fameux restTemplate
	RestTemplate restTemplate;

	//constructeur
	@Autowired
	public ParticipantService(ParticipantRepository accesBDD)
	{
		this.accesBDD = accesBDD;
	}
	
	//liste des participant
	public List<Participant> listerParticipant()
	{
		return accesBDD.findAll();
	}
	
	//recherche dans la base si participe deja
	public Optional<Participant> rechercherParMatricule(String matriculeRecherche)
	{

		 return accesBDD.findById(matriculeRecherche);
	}
	
	// ajoute dans bdd
	public void ajouterUnParticipant(Participant participantAAjouter)
	{
		
			accesBDD.save(participantAAjouter);
	}
	
	//modification du score
	public void modifierScore(Vote bulletin)
	{
		Optional<Participant> tmp = rechercherParMatricule(bulletin.getMatriculeColl());
		if (tmp.isPresent())
		{ 
			Participant pers = tmp.get();
			if (bulletin.getVote())
			{ pers.votePlus(); }
			else
			{  pers.voteMoins(); }

			accesBDD.save(pers);
		}
	}
	
	public List<Participant> classementParticipant(List<Participant> liste)
	{
		List<Participant> out = new ArrayList<>();
		while (!liste.isEmpty())
		{
			Participant tmp = scoreMax(liste);
			out.add(tmp);
			liste.remove(tmp);
		}

		return out;
	}
	
	public Participant scoreMax(List<Participant> liste)
	{
		int max = liste.get(0).getScore();
		Participant out = liste.get(0);
		for (Participant el:liste)
		{
			if (max<el.getScore())
				{ max = el.getScore(); 
				out = el;}
		}
		
		return out;
		
	}
	

}
