package dev.topcollegue.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.topcollegue.entite.ModelCollegue;
import dev.topcollegue.entite.Participant;
import dev.topcollegue.entite.ParticipantRepository;

@Service
public class ParticipantService
{
	//attribut
	private ParticipantRepository accesBDD;
	final String chemin = "http://localhost:8080/collegues";
	
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
	
	//recherche dans collegue si existe
	public Participant rechercherMatricule(String matricule)
	{
		ResponseEntity<ModelCollegue> response = restTemplate.getForEntity(chemin + "/"+matricule, ModelCollegue.class);
		ModelCollegue tmp = response.getBody();
		
		Participant pers = new Participant(tmp.getMatricule(), tmp.getNom(), tmp.getPrenoms(), tmp.getMotDePasse(), tmp.getPhotoUrl());
		return pers;
	}
	
	// ajoute dans bdd
	public void ajouterUnParticipant(Participant participantAAjouter)
	{
			accesBDD.save(participantAAjouter);
	}
	
	//modification du score
	public void modifierScore(String matricule, int nvScore)
	{
		Optional<Participant> tmp = rechercherParMatricule(matricule);
		if (tmp.isPresent())
		{ 
			Participant pers = tmp.get();
			pers.setScore(nvScore);
			accesBDD.save(pers);
		}
	}
	

}
