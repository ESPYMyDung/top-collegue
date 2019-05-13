package dev.topcollegue.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.topcollegue.entite.Participant;
import dev.topcollegue.entite.ParticipantScore;
import dev.topcollegue.service.ParticipantService;

@RestController
@RequestMapping("/topcollegue")
@CrossOrigin
public class ParticipantController
{
	@Autowired
	private ParticipantService servPart;

	
	//recherche participant
	@GetMapping(path = "/{matricule}") //, produces = MediaType.APPLICATION_JSON_VALUE
	public Participant verifierParticipantMatricule (@PathVariable String matricule) // ,  Optional<Participant> part | throws CollegueNonTrouveException
	{
		Optional<Participant> tmp = servPart.rechercherParMatricule(matricule);
		if (tmp.isPresent())
			{ return tmp.get(); }
		else
		{
			Participant pers = servPart.rechercherMatricule(matricule);
			return pers;
		}

	}
		
	//ajout participant
	@PostMapping(path = "/{matricule}")
	public void creerParticipant(@RequestBody Participant pers)  //throws CollegueInvalideException
	{
		servPart.ajouterUnParticipant(pers);
	}
	
	
	//modification score
	@PatchMapping(path = "/{matricule}")
	public void changerScore (@PathVariable String matricule, @RequestBody ParticipantScore pers)  
	{		
		servPart.modifierScore(matricule, pers.getScore());
	}



}
