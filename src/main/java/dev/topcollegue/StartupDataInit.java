package dev.topcollegue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import dev.topcollegue.entite.Participant;
import dev.topcollegue.entite.ParticipantRepository;

@Component
public class StartupDataInit
{
	@Autowired
    ParticipantRepository participantRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
 // La méthode init va être invoquée au démarrage de l'application.
    @EventListener(ContextRefreshedEvent.class)
    public void init()
    {
    	participantRepo.save(new Participant("azigueguagua", "Lovegood", "Luna", passwordEncoder.encode("nargles"), //passwordEncoder.encode(
				"https://www.hp-lexicon.org/wp-content/uploads/2016/07/nargle_by_verreaux.jpg"
    			, -200) );
    	participantRepo.save(new Participant("azi", "Lovegood", "Xenophilius", passwordEncoder.encode("Hallows"), //passwordEncoder.encode(
				"https://pngimage.net/wp-content/uploads/2018/05/deathly-hallows-symbol-png-5.png"
    			, -400) );
    	participantRepo.save(new Participant("guagua", "Weasley", "Ginevra", passwordEncoder.encode("boywholived"), //passwordEncoder.encode(
				"https://www.thesprucepets.com/thmb/0Y_9qW07-uYqkW9_kcasnwXqCi0=/450x0/filters:no_upscale():max_bytes(150000):strip_icc()/twenty20_d4afe7d2-ebe8-4288-a2ef-bcecbb99df88-5a8c4309c064710037e9965e.jpg"
    			, 100) );
    }

}
