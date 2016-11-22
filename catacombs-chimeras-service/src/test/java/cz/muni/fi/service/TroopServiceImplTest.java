/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cz.muni.fi.dao.HeroDao;
import cz.muni.fi.dao.TroopDao;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Troop;
import cz.muni.fi.entity.Role;
import cz.muni.fi.exceptions.NotFoundException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;

public class TroopServiceImplTest {

    private static final Long ID = 2L;
    private static final String TROOP_NAME = "Mario";

    private static final Long HERO_ID = 333L;
    private static final String HERO_NAME = "Luigi";

    private TroopService troopService;

    @Mock
    private TroopDao troopDao;

    @Mock
    private HeroDao heroDao;

    private Troop troop;
    private Hero hero;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        troopService = new TroopServiceImpl(troopDao, heroDao);
        troop = new Troop(TROOP_NAME);
        troop.setId(2L);

        hero = new Hero(HERO_NAME);
        hero.setId(HERO_ID);
    }

    @Test
    public void testFindTroopById() throws Exception {
        when(troopDao.findById(ID)).thenReturn(troop);

        Troop troopById = troopService.findTroopById(ID);

        assertThat(troopById, is(equalTo(troop)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testFindTroopByNonExistentID() throws Exception {
        troopService.findTroopById(27L);
    }

    @Test
    public void testFindTroopByName() throws Exception {
        when(troopDao.findByName(TROOP_NAME)).thenReturn(troop);

        Troop troopByName = troopService.findTroopByName(TROOP_NAME);

        assertThat(troopByName, is(equalTo(troop)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testFindTroopByNonExistentName() throws Exception {
        troopService.findTroopByName("nonExistent");
    }

    @Test
    public void testFindAllTroops() throws Exception {
        List list = Collections.singletonList(troop);
        when(troopDao.findAll()).thenReturn(list);

        List<Troop> allTroops = troopService.findAllTroops();

        assertThat(allTroops, containsInAnyOrder(troop));
    }

    @Test
    public void testCreateTroop() throws Exception {
        troopService.createTroop(troop);

        verify(troopDao, times(1)).create(troop);
    }

    @Test
    public void testUpdateTroop() throws Exception {
        troopService.updateTroop(troop);

        verify(troopDao, times(1)).update(troop);
    }

    @Test
    public void testRemoveTroop() throws Exception {
        troop.addHero(hero);
        when(troopDao.findById(ID)).thenReturn(troop);

        troopService.removeTroop(ID);

        verify(troopDao, times(1)).delete(troop);
        assertThat(hero.getTroop(), is(nullValue()));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testRemoveNonExistentTroop() throws Exception {
        troopService.removeTroop(ID);
    }

    @Test
    public void testAddTroopHero() throws Exception {
        when(troopDao.findById(ID)).thenReturn(troop);
        when(heroDao.findById(HERO_ID)).thenReturn(hero);

        troopService.addTroopHero(ID, HERO_ID);

        verify(troopDao, times(1)).update(troop);
        assertThat(troop.getHeroes(), containsInAnyOrder(hero));
        assertEquals(hero.getTroop(), troop);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testAddTroopHeroToNonExistentTroop() throws Exception {
        when(heroDao.findById(HERO_ID)).thenReturn(hero);

        troopService.addTroopHero(27L, HERO_ID);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testAddTroopNonExistentHero() throws Exception {
        when(troopDao.findById(ID)).thenReturn(troop);

        troopService.addTroopHero(ID, 27L);
    }

    @Test
    public void testGetTroopHeroes() throws Exception {
        troop.addHero(hero);
        when(troopDao.findById(ID)).thenReturn(troop);

        List<Hero> troopHeroes = troopService.getTroopHeroes(ID);

        assertThat(troopHeroes, containsInAnyOrder(hero));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testGetNonExistentTroopHeroes() throws Exception {
        troopService.getTroopHeroes(37L);
    }
}
