/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.muni.fi.dao.HeroDao;
import cz.muni.fi.dao.RoleDao;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Role;
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

    private HeroService heroService;

    @Mock
    private HeroDao heroDao;
    @Mock
    private RoleDao roleDao;

    private Hero hero;
    private Role role;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        heroService = new HeroServiceImpl(heroDao, roleDao);
        hero = new Hero(NAME);
        hero.setId(2L);
        role = new Role("Alien");
        role.setId(ROLE_ID);
        role.setDescription("");
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
        when(heroDao.findById(ID)).thenReturn(hero);

        heroService.removeHero(ID);

        verify(heroDao, times(1)).delete(hero);
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