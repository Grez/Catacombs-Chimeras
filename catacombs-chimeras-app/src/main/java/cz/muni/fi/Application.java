package cz.muni.fi;

import cz.muni.fi.dto.HeroCreateDTO;
import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.RoleCreateDTO;
import cz.muni.fi.dto.RoleDTO;
import cz.muni.fi.dto.TroopCreateDTO;
import cz.muni.fi.dto.TroopDTO;
import cz.muni.fi.facade.HeroFacade;
import cz.muni.fi.facade.RoleFacade;
import cz.muni.fi.facade.TroopFacade;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({cz.muni.fi.ServiceApplicationContext.class})
public class Application extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //init with some test data
    @Bean
    public CommandLineRunner commandLineRunner(final HeroFacade heroFacade, final TroopFacade troopFacade, final RoleFacade roleFacade) {
        return args -> {
            final HeroDTO superman = heroFacade.createHero(new HeroCreateDTO("Superman", 1000L));
            final HeroDTO batman = heroFacade.createHero(new HeroCreateDTO("Batman", 120L));
            final HeroDTO ironman = heroFacade.createHero(new HeroCreateDTO("Ironman", 95L));
            final HeroDTO captainAmerica = heroFacade.createHero(new HeroCreateDTO("Captain America", 99L));
            final HeroDTO joker = heroFacade.createHero(new HeroCreateDTO("Joker", 80L));
            final HeroDTO sinestro = heroFacade.createHero(new HeroCreateDTO("Sinestro", 700L));

            final TroopDTO justiceLeague = troopFacade.createTroop(new TroopCreateDTO("Justice League", "Protect world!", 1000000L));
            final TroopDTO injusticeLeague = troopFacade.createTroop(new TroopCreateDTO("Injustice League", "Rule the world!", 500000L));
            final TroopDTO avengers = troopFacade.createTroop(new TroopCreateDTO("Avengers", "Be cool!", 2000000L));

            final RoleDTO kryptonian = roleFacade.createRole(new RoleCreateDTO("Kryptonian alien", "powered by sun, weakened by Kryptonite"));
            final RoleDTO alien = roleFacade.createRole(new RoleCreateDTO("Alien", "being not born on Earth"));
            final RoleDTO billionaire = roleFacade.createRole(new RoleCreateDTO("Billionaire", "very much money"));
            final RoleDTO philanthropist = roleFacade.createRole(new RoleCreateDTO("Philanthropist", "caring, nourishing"));
            final RoleDTO playboy = roleFacade.createRole(new RoleCreateDTO("Playboy", "have a lot of women"));
            final RoleDTO pacifist = roleFacade.createRole(new RoleCreateDTO("Pacifist", "does not kill"));
            final RoleDTO human = roleFacade.createRole(new RoleCreateDTO("Human","only human"));
            final RoleDTO superhuman = roleFacade.createRole(new RoleCreateDTO("Superhuman","human with super powers"));

            heroFacade.addHeroRole(superman.getId(), kryptonian.getId());
            heroFacade.addHeroRole(superman.getId(), alien.getId());
            troopFacade.addTroopHero(justiceLeague.getId(), superman.getId());

            heroFacade.addHeroRole(batman.getId(), billionaire.getId());
            heroFacade.addHeroRole(batman.getId(), philanthropist.getId());
            heroFacade.addHeroRole(batman.getId(), playboy.getId());
            heroFacade.addHeroRole(batman.getId(), pacifist.getId());
            heroFacade.addHeroRole(batman.getId(), human.getId());
            troopFacade.addTroopHero(justiceLeague.getId(), batman.getId());

            heroFacade.addHeroRole(ironman.getId(), billionaire.getId());
            heroFacade.addHeroRole(ironman.getId(), philanthropist.getId());
            heroFacade.addHeroRole(ironman.getId(), playboy.getId());
            heroFacade.addHeroRole(ironman.getId(), human.getId());
            troopFacade.addTroopHero(avengers.getId(), ironman.getId());

            heroFacade.addHeroRole(captainAmerica.getId(), human.getId());
            heroFacade.addHeroRole(captainAmerica.getId(), superhuman.getId());
            troopFacade.addTroopHero(avengers.getId(), captainAmerica.getId());

            heroFacade.addHeroRole(joker.getId(), human.getId());
            troopFacade.addTroopHero(injusticeLeague.getId(), joker.getId());

            heroFacade.addHeroRole(sinestro.getId(), alien.getId());
            troopFacade.addTroopHero(injusticeLeague.getId(), sinestro.getId());
        };
    }
}
