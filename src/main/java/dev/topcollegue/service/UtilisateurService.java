package dev.topcollegue.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.topcollegue.entite.Participant;
import dev.topcollegue.entite.ParticipantRepository;

@Service
public class UtilisateurService implements UserDetailsService
{
	private ParticipantRepository partRepository;

	public UtilisateurService(ParticipantRepository partRepository) {
		this.partRepository = partRepository;
	}

	// cette méthode va permettre à Spring Security d'avoir accès
	// aux informations d'un utilisateur (mot de passe, roles) à partir
	// d'un nom utilisateur
	//
	// L'interface UserDetails détaille le contrat attendu par Spring Security.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Recherche d'utilisateur par nom utilisateur
		Participant utilisateurTrouve = this.partRepository.findBymatricule(username)
				.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));


		// Création d'un objet User (implémentant UserDetails)
		return new User(utilisateurTrouve.getMatricule(), utilisateurTrouve.getMotDePasse(), null);

	}

}
