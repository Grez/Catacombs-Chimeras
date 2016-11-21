/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.facade;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.muni.fi.dto.HeroCreateDTO;
import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.RoleDTO;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Role;
import cz.muni.fi.exceptions.NotFoundException;
import cz.muni.fi.service.HeroService;
import cz.muni.fi.service.TroopService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class HeroFacadeImplTest {

    private static final Long ID = 2L;
    private static final Long ROLE_ID = 331L;
    private static final String NAME = "Superman";
    private static final Long EXP = 33L;
    private static final String ROLE_NAME = "Alien";
    private static final String DESCRIPTION = "desc";

    private static final Long NON_EXISTENT_ID = 3L;
    private static final String NON_EXISTENT_NAME = "Batman";

    private HeroFacade heroFacade;

    @Mock
    private HeroService heroService;

    @Mock
    private TroopService troopService;

    private Hero hero;
    private HeroCreateDTO heroToCreate;
    private HeroDTO heroDTO;
    private Role role;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        heroFacade = new HeroFacadeImpl(heroService, troopService);
        hero = new Hero(NAME);
        hero.setId(ID);
        hero.setExperience(EXP);

        heroToCreate = new HeroCreateDTO(NAME, EXP, null);
        heroDTO = new HeroDTO(ID, NAME, EXP, null);

        role = new Role(ROLE_NAME);
        role.setId(ROLE_ID);
        role.setDescription(DESCRIPTION);
    }

    @Test
    public void testFindHeroById() throws Exception {
        when(heroService.findHeroById(ID)).thenReturn(hero);

        HeroDTO heroById = heroFacade.findHeroById(ID);

        assertThat(heroById.getId(), is(equalTo(ID)));
        assertThat(heroById.getName(), is(equalTo(NAME)));
        assertThat(heroById.getExperience(), is(equalTo(EXP)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testFindHeroByNonExistentId() throws Exception {
        when(heroService.findHeroById(NON_EXISTENT_ID)).thenThrow(NotFoundException.class);

        heroFacade.findHeroById(NON_EXISTENT_ID);
    }

    @Test
    public void testFindHeroByName() throws Exception {
        when(heroService.findHeroByName(NAME)).thenReturn(hero);

        HeroDTO heroById = heroFacade.findHeroByName(NAME);

        assertThat(heroById.getId(), is(equalTo(ID)));
        assertThat(heroById.getName(), is(equalTo(NAME)));
        assertThat(heroById.getExperience(), is(equalTo(EXP)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testFindHeroByNonExistentName() throws Exception {
        when(heroService.findHeroByName(NON_EXISTENT_NAME)).thenThrow(NotFoundException.class);

        heroFacade.findHeroByName(NON_EXISTENT_NAME);
    }

    @Test
    public void testFindAllHeroes() throws Exception {
        List list = Collections.singletonList(hero);
        when(heroService.findAllHeroes()).thenReturn(list);

        List<HeroDTO> allHeroes = heroFacade.findAllHeroes();

        assertThat(allHeroes, hasSize(1));
        assertThat(allHeroes.get(0).getId(), is(equalTo(ID)));
        assertThat(allHeroes.get(0).getName(), is(equalTo(NAME)));
        assertThat(allHeroes.get(0).getExperience(), is(equalTo(EXP)));
    }

    @Test
    public void testCreateHero() throws Exception {
        when(heroService.createHero(hero)).thenReturn(hero);

        HeroDTO heroDTO = heroFacade.createHero(heroToCreate);

        verify(heroService, times(1)).createHero(this.hero);
        assertThat(heroDTO.getId(), is(equalTo(ID)));
        assertThat(heroDTO.getExperience(), is(equalTo(EXP)));
        assertThat(heroDTO.getName(), is(equalTo(NAME)));
    }

    @Test
    public void testUpdateHero() throws Exception {
        heroFacade.updateHero(heroDTO);

        verify(heroService, times(1)).updateHero(hero);
    }

    @Test
    public void testRemoveHero() throws Exception {
        heroFacade.removeHero(ID);

        verify(heroService, times(1)).removeHero(ID);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testRemoveNonExistentHero() throws Exception {
        doThrow(NotFoundException.class).when(heroService).removeHero(NON_EXISTENT_ID);

        heroFacade.removeHero(NON_EXISTENT_ID);
    }

    @Test
    public void testAddHeroRole() throws Exception {
        heroFacade.addHeroRole(ID, ROLE_ID);

        verify(heroService, times(1)).addHeroRole(ID, ROLE_ID);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testAddHeroRoleNonExistent() throws Exception {
        doThrow(NotFoundException.class).when(heroService).addHeroRole(NON_EXISTENT_ID, ROLE_ID);

        heroFacade.addHeroRole(NON_EXISTENT_ID, ROLE_ID);
    }

    @Test
    public void testGetHeroRoles() throws Exception {
        List list = Collections.singletonList(role);
        when(heroService.getHeroRoles(ID)).thenReturn(list);

        List<RoleDTO> heroRoles = heroFacade.getHeroRoles(ID);

        assertThat(heroRoles, hasSize(1));
        assertThat(heroRoles.get(0).getId(), is(equalTo(ROLE_ID)));
        assertThat(heroRoles.get(0).getName(), is(equalTo(ROLE_NAME)));
        assertThat(heroRoles.get(0).getDescription(), is(equalTo(DESCRIPTION)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testGetHeroRolesNonExistent() throws Exception {
        doThrow(NotFoundException.class).when(heroService).getHeroRoles(NON_EXISTENT_ID);

        heroFacade.getHeroRoles(NON_EXISTENT_ID);
    }

}