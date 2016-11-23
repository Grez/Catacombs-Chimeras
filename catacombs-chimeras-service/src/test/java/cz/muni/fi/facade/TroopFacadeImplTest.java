/**
 * @author Tom Bartoň
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

import cz.muni.fi.dto.TroopCreateDTO;
import cz.muni.fi.dto.TroopDTO;
import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.entity.Troop;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.exceptions.NotFoundException;
import cz.muni.fi.service.HeroService;
import cz.muni.fi.service.TroopService;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TroopFacadeImplTest {

    private static final Long TROOP_ID = 2L;
    private static final String TROOP_NAME = "Mario Brother's";
    private static final String TROOP_MISSION = "Saving princess";
    private static final Long TROOP_AMOUNT_OF_MONEY = 50L;

    private static final Long HERO1_ID = 1L;
    private static final String HERO1_NAME = "Mario";
    private static final Long HERO1_EXPERIENCE = 50L;

    private static final Long HERO2_ID = 2L;
    private static final String HERO2_NAME = "Green Mario";
    private static final Long HERO2_EXPERIENCE = 25L;

    private static final Long NON_EXISTENT_ID = 3L;
    private static final String NON_EXISTENT_NAME = "Batman";

    private TroopFacade troopFacade;

    @Mock
    private TroopService troopService;

    @Mock
    private HeroService heroService;

    private Troop troop;
    private TroopCreateDTO troopToCreate;
    private TroopDTO troopDTO;
    
    private Hero hero1;
    private Hero hero2;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        troopFacade = new TroopFacadeImpl(troopService);
        troop = new Troop(TROOP_NAME);
        troop.setId(TROOP_ID);
        troop.setMission(TROOP_MISSION);
        troop.setAmountOfMoney(TROOP_AMOUNT_OF_MONEY);

        troopToCreate = new TroopCreateDTO(TROOP_NAME, TROOP_MISSION, TROOP_AMOUNT_OF_MONEY);
        troopDTO = new TroopDTO(TROOP_ID, TROOP_NAME, TROOP_MISSION, TROOP_AMOUNT_OF_MONEY);

        hero1 = new Hero(HERO1_NAME);
        hero1.setId(HERO1_ID);
        hero1.setExperience(HERO1_EXPERIENCE);
        hero1.setTroop(troop);

        hero2 = new Hero(HERO2_NAME);
        hero2.setId(HERO2_ID);
        hero2.setExperience(HERO2_EXPERIENCE);
        hero2.setTroop(troop);
    }

    @Test
    public void testFindTroopById() throws Exception {
        when(troopService.findTroopById(TROOP_ID)).thenReturn(troop);

        TroopDTO troopById = troopFacade.findTroopById(TROOP_ID);

        assertThat(troopById.getId(), is(equalTo(TROOP_ID)));
        assertThat(troopById.getName(), is(equalTo(TROOP_NAME)));
        assertThat(troopById.getMission(), is(equalTo(TROOP_MISSION)));
        assertThat(troopById.getAmountOfMoney(), is(equalTo(TROOP_AMOUNT_OF_MONEY)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testFindTroopByNonExistentId() throws Exception {
        when(troopService.findTroopById(NON_EXISTENT_ID)).thenThrow(NotFoundException.class);

        troopFacade.findTroopById(NON_EXISTENT_ID);
    }

    @Test
    public void testFindTroopByName() throws Exception {
        when(troopService.findTroopByName(TROOP_NAME)).thenReturn(troop);

        TroopDTO troopById = troopFacade.findTroopByName(TROOP_NAME);

        assertThat(troopById.getId(), is(equalTo(TROOP_ID)));
        assertThat(troopById.getName(), is(equalTo(TROOP_NAME)));
        assertThat(troopById.getMission(), is(equalTo(TROOP_MISSION)));
        assertThat(troopById.getAmountOfMoney(), is(equalTo(TROOP_AMOUNT_OF_MONEY)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testFindTroopByNonExistentName() throws Exception {
        when(troopService.findTroopByName(NON_EXISTENT_NAME)).thenThrow(NotFoundException.class);

        troopFacade.findTroopByName(NON_EXISTENT_NAME);
    }

    @Test
    public void testFindAllTroops() throws Exception {
        List list = Collections.singletonList(troop);
        when(troopService.findAllTroops()).thenReturn(list);

        List<TroopDTO> allTroops = troopFacade.findAllTroops();

        assertThat(allTroops, hasSize(1));
        assertThat(allTroops.get(0).getId(), is(equalTo(TROOP_ID)));
        assertThat(allTroops.get(0).getName(), is(equalTo(TROOP_NAME)));
        assertThat(allTroops.get(0).getMission(), is(equalTo(TROOP_MISSION)));
        assertThat(allTroops.get(0).getAmountOfMoney(), is(equalTo(TROOP_AMOUNT_OF_MONEY)));
    }

    @Test
    public void testCreateTroop() throws Exception {
        when(troopService.createTroop(troop)).thenReturn(troop);

        TroopDTO troopDTO = troopFacade.createTroop(troopToCreate);

        verify(troopService, times(1)).createTroop(this.troop);
        assertThat(troopDTO.getId(), is(equalTo(TROOP_ID)));
        assertThat(troopDTO.getName(), is(equalTo(TROOP_NAME)));
        assertThat(troopDTO.getMission(), is(equalTo(TROOP_MISSION)));
        assertThat(troopDTO.getAmountOfMoney(), is(equalTo(TROOP_AMOUNT_OF_MONEY)));
    }

    @Test
    public void testUpdateTroop() throws Exception {
        troopFacade.updateTroop(troopDTO);

        verify(troopService, times(1)).updateTroop(troop);
    }

    @Test
    public void testRemoveTroop() throws Exception {
        troopFacade.removeTroop(TROOP_ID);

        verify(troopService, times(1)).removeTroop(TROOP_ID);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testRemoveNonExistentTroop() throws Exception {
        doThrow(NotFoundException.class).when(troopService).removeTroop(NON_EXISTENT_ID);

        troopFacade.removeTroop(NON_EXISTENT_ID);
    }

    @Test
    public void testAddHeroRole() throws Exception {
        troopFacade.addTroopHero(TROOP_ID, HERO1_ID);

        verify(troopService, times(1)).addTroopHero(TROOP_ID, HERO1_ID);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testAddHeroRoleNonExistent() throws Exception {
        doThrow(NotFoundException.class).when(troopService).addTroopHero(NON_EXISTENT_ID, HERO1_ID);

        troopFacade.addTroopHero(NON_EXISTENT_ID, HERO1_ID);
    }

    @Test
    public void testGetTroopHeroes() throws Exception {
        List list = new ArrayList();
        list.add(hero1);
        list.add(hero2);
        when(troopService.getTroopHeroes(TROOP_ID)).thenReturn(list);

        List<HeroDTO> troopHeroes = troopFacade.getTroopHeroes(TROOP_ID);

        assertThat(troopHeroes, hasSize(2));

        assertThat(troopHeroes.get(0).getId(), is(equalTo(HERO1_ID)));
        assertThat(troopHeroes.get(0).getName(), is(equalTo(HERO1_NAME)));
        assertThat(troopHeroes.get(0).getExperience(), is(equalTo(HERO1_EXPERIENCE)));

        assertThat(troopHeroes.get(1).getId(), is(equalTo(HERO2_ID)));
        assertThat(troopHeroes.get(1).getName(), is(equalTo(HERO2_NAME)));
        assertThat(troopHeroes.get(1).getExperience(), is(equalTo(HERO2_EXPERIENCE)));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void testGetTroopHeroesNonExistent() throws Exception {
        doThrow(NotFoundException.class).when(troopService).getTroopHeroes(NON_EXISTENT_ID);

        troopFacade.getTroopHeroes(NON_EXISTENT_ID);
    }

    @Test
    public void countTroopsAverageExperienceReport() throws Exception {
        troopFacade.countTroopsAverageExperienceReport();

        verify(troopService, times(1)).countTroopsAverageExperienceReport();
    }

}
