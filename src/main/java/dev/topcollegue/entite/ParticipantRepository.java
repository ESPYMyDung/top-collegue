package dev.topcollegue.entite;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, String>
{
	// findBy + "nom colomne bdd"
	Optional<Participant> findBynom(String nom);
	Optional<Participant> findBymatricule(String matricule);
}

