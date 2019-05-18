package dev.topcollegue.controller;

import java.util.List;

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
import dev.topcollegue.entite.Vote;
import dev.topcollegue.service.ParticipantService;

@RestController
@RequestMapping("/topcollegue")
@CrossOrigin
public class ParticipantController
{
	@Autowired
	private ParticipantService servPart;

	@GetMapping(path = "/vote")
	public List<Participant> afficherParticipant()
	{
		return servPart.listerParticipant();
	}
	
	@GetMapping(path = "/classement")
	public List<Participant> afficherClassement()
	{
		List<Participant> tmp = servPart.listerParticipant();
		return servPart.classementParticipant(tmp);
	}
		
	//ajout participant
	@PostMapping()
	public void creerParticipant(@RequestBody Participant pers)
	{
		servPart.ajouterUnParticipant(pers);
	}

	//modification score
	@PostMapping(path = "/vote")
	public void changerScore(@RequestBody Vote bulletin)  
	{		
		servPart.modifierScore(bulletin);
	}



}
