package dev.topcollegue.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dev.topcollegue.entite.Participant;
import dev.topcollegue.entite.ParticipantRepository;

@Service
public class ParticipantService
{
	@Value("${jwt.expires_in}")
	private Integer EXPIRES_IN;
	@Value("${jwt.cookie}")
	private String TOKEN_COOKIE;
	
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
	
	//recherche dans collegue si existe
	/*public Participant rechercherMatricule(String matricule) // InfosAuthentification auth
	{
		final String chemin = "https://espy-collegues-api.herokuapp.com"; // local : http://localhost:8080/collegues
		
		/*ResponseEntity<CollegueConnecte> result = rt.postForEntity("https://remvia-collegues-api.herokuapp.com/auth", collegueInscription, CollegueConnecte.class);

		String jetonJWT = result.getHeaders().getFirst("Set-Cookie").split(";")[0].split("=")[1];
		Cookie authCookie = new Cookie(TOKEN_COOKIE, jetonJWT);
		// DEFINIE LE COOKIE ET PERMET DE LE TRANSMETTRE
		authCookie.setHttpOnly(true);
		authCookie.setMaxAge(EXPIRES_IN * 1000);
		authCookie.setPath("/");
		response.addCookie(authCookie);

		RequestEntity<?> requestEntity = RequestEntity.get(new URI("https://remvia-collegues-api.herokuapp.com/me"))
				.header("Cookie", result.getHeaders().getFirst("Set-Cookie")).build();

		ResponseEntity<CollegueConnecte> rep2 = rt.exchange(requestEntity, CollegueConnecte.class);
		return ResponseEntity.ok(rep2.getBody()); //
		
		
		// chat chez mois
		ResponseEntity<ModelCollegue> response = restTemplate.getForEntity(chemin + "/"+matricule, ModelCollegue.class);
		ModelCollegue tmp = response.getBody();
		
		Participant pers = new Participant(tmp.getMatricule(), tmp.getNom(), tmp.getPrenoms(), tmp.getMotDePasse(), tmp.getPhotoUrl(), tmp.getRoles());
		
		
		//nouvelle version avec cookies
		/*ResponseEntity<?> response = restTemplate.postForEntity(chemin + "/auth", auth, InfosAuthentification.class);
		//on recupere le bazard
		String jetonJWT = response.getHeaders().getFirst("Set-Cookie").split(";")[0].split("=")[1];
		Cookie authCookie = new Cookie(TOKEN_COOKIE, jetonJWT);
		// DEFINIE LE COOKIE ET PERMET DE LE TRANSMETTRE
		authCookie.setHttpOnly(true);
		authCookie.setMaxAge(EXPIRES_IN * 1000);
		authCookie.setPath("/");
		//response.addCookie(authCookie);
		
		ResponseEntity<?> resColl = RequestEntity.get(new URI(chemin + "/me") ).header("Cookie", response.getHeaders().getFirst("Set-Cookie")).build();
		
		ResponseEntity<ModelCollegue> reponse = restTemplate.exchange(resColl, ModelCollegue.class);
		ModelCollegue tmp = reponse.getBody();
		Participant pers = new Participant(tmp.getMatricule(), tmp.getNom(), tmp.getPrenoms(), tmp.getMotDePasse(), tmp.getPhotoUrl(), tmp.getRoles()); //
		
		return pers;
	}*/
	
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
