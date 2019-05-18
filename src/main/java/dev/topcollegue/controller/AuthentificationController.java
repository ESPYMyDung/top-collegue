package dev.topcollegue.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dev.topcollegue.entite.InfosAuthentification;
import dev.topcollegue.entite.ModelCollegue;
import dev.topcollegue.entite.Participant;
import dev.topcollegue.entite.ParticipantConnect;
import dev.topcollegue.service.ParticipantService;

@RestController
@CrossOrigin
public class AuthentificationController
{
	//cookies...
	@Value("${jwt.expires_in}")
	private Integer EXPIRES_IN;
	@Value("${jwt.cookie}")
	private String TOKEN_COOKIE;
	@Value("${jwt.secret}")
	private String SECRET;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired //le fameux restTemplate
	RestTemplate restTemplate;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ParticipantService servPart;

	@PostMapping(value = "/auth")
	public ResponseEntity authenticate(@RequestBody InfosAuthentification authenticationRequest, HttpServletResponse response) throws URISyntaxException {

		// encapsulation des informations de connexion
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getMatriculeColl(), authenticationRequest.getMotDePasse());

		final String chemin = "https://espy-collegues-api.herokuapp.com";

		ResponseEntity<?> resAuth= restTemplate.postForEntity(chemin + "/auth", authenticationRequest, InfosAuthentification.class);
		//on recupere le bazard
		String jetonJWT = resAuth.getHeaders().getFirst("Set-Cookie").split(";")[0].split("=")[1];
		
		Cookie authCookie = new Cookie(TOKEN_COOKIE, jetonJWT);
		// cree le cookie de l'appli top-collegue
		authCookie.setHttpOnly(true);
		authCookie.setMaxAge(EXPIRES_IN * 1000);
		authCookie.setPath("/");
		response.addCookie(authCookie);
		
		// header(blabla) pour recupere et stocker le cookie; et .build pour ne pas mettre de body
		RequestEntity<?> resColl = RequestEntity.get(new URI(chemin + "/me") ).header("Cookie", resAuth.getHeaders().getFirst("Set-Cookie")).build();
									
		//recupere le collegue, en theorie
		ResponseEntity<ModelCollegue> raiponce = restTemplate.exchange(resColl, ModelCollegue.class);

		ModelCollegue tmp = raiponce.getBody();

		Participant pers = new Participant(tmp.getMatricule(), tmp.getNom(), tmp.getPrenoms(), passwordEncoder.encode(authenticationRequest.getMotDePasse()), tmp.getPhotoUrl(), tmp.getRoles());
		
		if (authenticationRequest.getMotDePasse() !=null)
		{ pers.setPhotoUrl(authenticationRequest.getMotDePasse());}

		// si il n'est pas present dans la bdd, enregistre le
		if ( !servPart.rechercherParMatricule(tmp.getMatricule()).isPresent() )
		{ servPart.ajouterUnParticipant(pers); }
		
		return ResponseEntity.ok(raiponce.getBody());
	}
	
	//valeur du participant connecte
		@GetMapping(value="/me")
		@ResponseBody
		public ParticipantConnect renvoyerUtilisateur()
		{		
			String mat = SecurityContextHolder.getContext().getAuthentication().getName();
			Optional<Participant> tmp = servPart.rechercherParMatricule(mat);
			if (tmp.isPresent())
			{ 
				Participant pers = tmp.get();
				ParticipantConnect me = new ParticipantConnect(pers.getMatricule(), pers.getNom(), pers.getPrenom(), pers.getScore());
				return me;
			}
			return null;
		}


	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity mauvaiseInfosConnexion(BadCredentialsException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	}
	
	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity mauvaiseInfosConnexion(HttpClientErrorException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
}

}

