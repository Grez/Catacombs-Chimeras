/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.muni.fi.dao.HeroDao;
import cz.muni.fi.dao.RoleDao;
import cz.muni.fi.dao.TroopDao;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Role;
import cz.muni.fi.entity.Troop;
import cz.muni.fi.exceptions.NotFoundException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class HeroServiceImplTest {

    private static final Long ID = 2L;
    private static final String NAME = "Superman";
    private static final Long ROLE_ID = 333L;
    private static final String ROLE_NAME = "Alien";
    private static final Long TROOP_ID = 1L;
    private static final String TROOP_NAME = "Mario";

    private HeroService heroService;

    @Mock
    private HeroDao heroDao;
    @Mock
    private RoleDao roleDao;
    @Mock
    private TroopDao troopDao;

    private Hero hero;
    private Role role;
    private Troop troop;


    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        heroService = new HeroServiceImpl(heroDao, roleDao, troopDao);
        hero = new Hero(NAME);
        hero.setId(2L);

        role = new Role(ROLE_NAME);
        role.setId(ROLE_ID);
        role.setDescription("");

        troop = new Troop(TROOP_NAME);
        troop.setId(TROOP_ID);
    }

    @Test
    public void testFindHeroById() throws Exception {
        when(heroDao.findById(ID)).thenReturn(hero);

        Hero heroById = heroService.findHeroById(ID);

        assertThat(heroById, is(equalTo(hero)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testFindHeroByNonExistentID() throws Exception {
        heroService.findHeroById(27L);
    }

    @Test
    public void testFindHeroByName() throws Exception {
        when(heroDao.findByName(NAME)).thenReturn(hero);

        Hero heroByName = heroService.findHeroByName(NAME);

        assertThat(heroByName, is(equalTo(hero)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testFindHeroByNonExistentName() throws Exception {
        heroService.findHeroByName("nonExistent");
    }

    @Test
    public void testFindAllHeroes() throws Exception {
        List list = Collections.singletonList(hero);
        when(heroDao.findAll()).thenReturn(list);

        List<Hero> allHeroes = heroService.findAllHeroes();

        assertThat(allHeroes, containsInAnyOrder(hero));
    }

    @Test
    public void testCreateHero() throws Exception {
        heroService.createHero(hero);

        verify(heroDao, times(1)).create(hero);
    }

    @Test
    public void testUpdateHero() throws Exception {
        heroService.updateHero(hero);

        verify(heroDao, times(1)).update(hero);
    }

    @Test
    public void testRemoveHero() throws Exception {
        hero.addRole(role);
        troop.addHero(hero);
        when(heroDao.findById(ID)).thenReturn(hero);

        heroService.removeHero(ID);

        verify(heroDao, times(1)).delete(hero);
        assertThat(role.getHeroes(), not(contains(hero)));
        assertThat(troop.getHeroes(), not(contains(hero)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testRemoveNonExistentHero() throws Exception {
        heroService.removeHero(ID);
    }

    @Test
    public void testAddHeroRole() throws Exception {
        when(heroDao.findById(ID)).thenReturn(hero);
        when(roleDao.findById(ROLE_ID)).thenReturn(role);

        heroService.addHeroRole(ID, ROLE_ID);

        verify(heroDao, times(1)).update(hero);
        verify(roleDao, times(1)).update(role);
        assertThat(hero.getRoles(), containsInAnyOrder(role));
        assertThat(role.getHeroes(), containsInAnyOrder(hero));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testAddHeroRoleToNonExistentHero() throws Exception {
        when(roleDao.findById(ROLE_ID)).thenReturn(role);

        heroService.addHeroRole(27L, ROLE_ID);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testAddHeroNonExistentRole() throws Exception {
        when(heroDao.findById(ID)).thenReturn(hero);

        heroService.addHeroRole(ID, 27L);
    }

    @Test(dependsOnMethods = "testAddHeroRole")
    public void testRemoveHeroRole() throws Exception {
        when(heroDao.findById(ID)).thenReturn(hero);
        when(roleDao.findById(ROLE_ID)).thenReturn(role);
        heroService.addHeroRole(hero.getId(), role.getId());

        heroService.removeHeroRole(hero.getId(), role.getId());

        assertThat(hero.getRoles(), is(empty()));
        assertThat(role.getHeroes(), is(empty()));
    }

    @Test
    public void testRemoveHeroRoleWhichHeDoesNotHave() throws Exception {
        when(heroDao.findById(ID)).thenReturn(hero);
        when(roleDao.findById(ROLE_ID)).thenReturn(role);

        heroService.removeHeroRole(hero.getId(), role.getId());

        assertThat(hero.getRoles(), is(empty()));
        assertThat(role.getHeroes(), is(empty()));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testRemoveHeroRoleToNonExistentHero() throws Exception {
        when(roleDao.findById(ROLE_ID)).thenReturn(role);

        heroService.removeHeroRole(27L, ROLE_ID);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testRemoveHeroNonExistentRole() throws Exception {
        when(heroDao.findById(ID)).thenReturn(hero);

        heroService.removeHeroRole(ID, 27L);
    }

    @Test
    public void testGetHeroRoles() throws Exception {
        hero.addRole(role);
        when(heroDao.findById(ID)).thenReturn(hero);

        List<Role> heroRoles = heroService.getHeroRoles(ID);

        assertThat(heroRoles, containsInAnyOrder(role));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testGetNonExistentHeroRoles() throws Exception {
        heroService.getHeroRoles(37L);
    }
}