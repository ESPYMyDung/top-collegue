package dev.topcollegue.controller;

import java.net.URI;
import java.net.URISyntaxException;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import dev.topcollegue.entite.InfosAuthentification;
import dev.topcollegue.entite.CollegueConnect;
import dev.topcollegue.entite.Participant;
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
	RestTemplate restTemplate; //= new RestTemplate()
	
	@Autowired
	private ParticipantService servPart;

	@PostMapping(value = "/auth")
	public ResponseEntity authenticate(@RequestBody InfosAuthentification authenticationRequest, HttpServletResponse response) throws URISyntaxException {

		// encapsulation des informations de connexion
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getMatriculeColl(), authenticationRequest.getMotDePasse());

		final String chemin = "http://localhost:8080"; // https://espy-collegues-api.herokuapp.com   http://localhost:8080/collegues
		
		System.out.println("debut catch");
		
		ResponseEntity<?> resAuth= restTemplate.postForEntity(chemin + "/auth", authenticationRequest, InfosAuthentification.class);
		//on recupere le bazard
		String jetonJWT = resAuth.getHeaders().getFirst("Set-Cookie").split(";")[0].split("=")[1];
		
		Cookie authCookie = new Cookie(TOKEN_COOKIE, jetonJWT);
		// cree le cookie de l'appli top-collegue
		authCookie.setHttpOnly(true);
		authCookie.setMaxAge(EXPIRES_IN * 1000);
		authCookie.setPath("/");
		response.addCookie(authCookie);
		
		
		System.out.println("get");
		// header(blabla) pour recupere et stocker le cookie; et .build pour ne pas mettre de body
		RequestEntity<?> resColl = RequestEntity.get(new URI(chemin + "/me") ).header("Cookie", resAuth.getHeaders().getFirst("Set-Cookie")).build();
									//RequestEntity.get(new URI("https://remvia-collegues-api.herokuapp.com/me")).header("Cookie", result.getHeaders().getFirst("Set-Cookie")).build();

		//recupere le collegue, en theorie
		System.out.println("coll");
		ResponseEntity<CollegueConnect> raiponce = restTemplate.exchange(resColl, CollegueConnect.class);

		System.out.println("get body");
		CollegueConnect tmp = raiponce.getBody();
		
		System.out.println("creation");
		Participant pers = new Participant(tmp.getMatricule(), tmp.getNom(), tmp.getPrenoms(), authenticationRequest.getMatriculeColl(), tmp.getPhotoUrl(), tmp.getRoles());
		System.out.println(tmp.getMatricule());
		System.out.println(tmp.getNom());
		System.out.println(tmp.getPrenoms());
		System.out.println(tmp.getPhotoUrl());
		
		if (authenticationRequest.getMotDePasse() !=null)
		{ pers.setPhotoUrl(authenticationRequest.getMotDePasse());}
		
		System.out.println("bdd");
		servPart.ajouterUnParticipant(pers);
		
		System.out.println("Body");
		System.out.println(ResponseEntity.ok(raiponce.getBody()));
		
		return ResponseEntity.ok(raiponce.getBody());
		
		// vérification de l'authentification
		// une exception de type `BadCredentialsException` en cas d'informations non valides
		/*try 
		{
			System.out.println("avant authenticate");
			Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
			System.out.println("apres authenticate");
			User user = (User) authentication.getPrincipal();

			String rolesList = user.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.joining(","));

			Map<String, Object> infosSupplementaireToken = new HashMap<>();
			infosSupplementaireToken.put("roles", rolesList);

			String jetonJWT = Jwts.builder()
					.setSubject(user.getUsername())
					.addClaims(infosSupplementaireToken)
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRES_IN * 1000))
					.signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, SECRET)
					.compact();

			//javax
			Cookie authCookie = new Cookie(TOKEN_COOKIE, jetonJWT);
			authCookie.setHttpOnly(true);
			authCookie.setMaxAge(EXPIRES_IN * 1000);
			authCookie.setPath("/");
			response.addCookie(authCookie);

			System.out.println("Build");
			System.out.println(ResponseEntity.ok().build());
			
			return ResponseEntity.ok().build();
		}
		catch (HttpClientErrorException | BadCredentialsException e)
		{
			final String chemin = "http://localhost:8080/collegues"; // https://espy-collegues-api.herokuapp.com
			
			System.out.println("debut catch");
			
			ResponseEntity<?> resAuth= restTemplate.postForEntity(chemin + "/auth", authenticationRequest, InfosAuthentification.class);
			//on recupere le bazard
			String jetonJWT = resAuth.getHeaders().getFirst("Set-Cookie").split(";")[0].split("=")[1];
			
			
			System.out.println("get");
			RequestEntity<?> resColl = RequestEntity.get(new URI(chemin + "/me") ).header("Cookie", resAuth.getHeaders().getFirst("Set-Cookie")).build();
			//RequestEntity<?> resColl = RequestEntity.getForEntity(new URI(chemin + "/me") ).header("Cookie", resAuth.getHeaders().getFirst("Set-Cookie")).build();

			//recupere le collegue, en theorie
			System.out.println("coll");
			ResponseEntity<ModelCollegue> raiponce = restTemplate.exchange(RequestEntity.get(new URI(chemin + "/me") ).header("Cookie", resAuth.getHeaders().getFirst("Set-Cookie")).build()
					, ModelCollegue.class); //restTemplate.exchange(resColl, ModelCollegue.class);
			
			Cookie authCookie = new Cookie(TOKEN_COOKIE, jetonJWT);
			// cree le cookie de l'appli top-collegue
			authCookie.setHttpOnly(true);
			authCookie.setMaxAge(EXPIRES_IN * 1000);
			authCookie.setPath("/");
			response.addCookie(authCookie);
			
			System.out.println("get body");
			ModelCollegue tmp = raiponce.getBody();
			
			System.out.println("creation");
			Participant pers = new Participant(tmp.getMatricule(), tmp.getNom(), tmp.getPrenoms(), authenticationRequest.getMatriculeColl(), tmp.getPhotoUrl(), tmp.getRoles());
			System.out.println(tmp.getMatricule());
			System.out.println(tmp.getNom());
			System.out.println(tmp.getPrenoms());
			System.out.println(tmp.getPhotoUrl());
			
			if (authenticationRequest.getMotDePasse() !=null)
			{ pers.setPhotoUrl(authenticationRequest.getMotDePasse());}
			
			System.out.println("bdd");
			servPart.ajouterUnParticipant(pers);
			
			System.out.println("Body");
			System.out.println(ResponseEntity.ok(raiponce.getBody()));
			
			return ResponseEntity.ok(raiponce.getBody());*/
		//}


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

