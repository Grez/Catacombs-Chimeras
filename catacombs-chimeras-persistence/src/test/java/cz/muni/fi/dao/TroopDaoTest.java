/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;

import cz.muni.fi.PersistenceApplicationTestContext;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Troop;
import org.hamcrest.CoreMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.Collections;
import java.util.List;

@ContextConfiguration(classes=PersistenceApplicationTestContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class TroopDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private TroopDao troopDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCreate() throws Exception {
        final String troopName = "alfa";
        final String mission = "kill JFK";
        final Long money = 20L;
        final Troop troop = new Troop(troopName);
        final Hero hero = new Hero("Batman");
        hero.setTroop(troop);
        entityManager.persist(hero);
        troop.setMission(mission);
        troop.setAmountOfMoney(money);
        troop.setHeroes(Collections.singleton(hero));
        troopDao.create(troop);
        final List<Troop> resultList = entityManager.createQuery("select t from Troop t", Troop.class).getResultList();

        assertThat(resultList, hasSize(1));
        assertThat(resultList.get(0).getName(), is(equalTo(troopName)));
        assertThat(resultList.get(0).getMission(), is(equalTo(mission)));
        assertThat(resultList.get(0).getAmountOfMoney(), is(equalTo(money)));
        assertThat(resultList.get(0).getHeroes(), contains(equalTo(hero)));
    }


    @Test(dependsOnMethods = "testCreate")
    public void testDelete() throws Exception {
        final Troop troop = new Troop("alfa");
        troopDao.create(troop);
        troopDao.delete(troop);
        final List<Troop> resultList = entityManager.createQuery("select t from Troop t", Troop.class).getResultList();
        assertThat(resultList, is(empty()));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindById() throws Exception {
        final Troop troop = new Troop("alfa");
        troopDao.create(troop);
        Troop foundTroop = troopDao.findById(troop.getId());
        assertThat(foundTroop, CoreMatchers.is(equalTo(foundTroop)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindAll() throws Exception {
        final Troop alfaTroop = new Troop("alfa");
        final Troop betaTroop = new Troop("beta");
        troopDao.create(alfaTroop);
        troopDao.create(betaTroop);
        assertThat(troopDao.findAll(), containsInAnyOrder(equalTo(alfaTroop), equalTo(betaTroop)));
    }

    @Test(dependsOnMethods = {"testCreate", "testFindById"})
    public void testUpdate() throws Exception {
        final String newName = "alfa";
        final Troop troop = new Troop("beta");
        troopDao.create(troop);
        troop.setName(newName);
        troopDao.update(troop);
        assertThat(troopDao.findById(troop.getId()).getName(), is(equalTo(newName)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindByName() {
        final String name = "alfa";
        final Troop troop = new Troop(name);
        troopDao.create(troop);
        assertThat(troop, is(equalTo(troopDao.findByName(name))));
    }

    @Test
    public void testFindNonExistentEntity() {
        assertThat(troopDao.findById(0L), is(nullValue()));
    }

    @Test
    public void testDeleteNonExistentEntity() {
        troopDao.delete(new Troop());
    }

    @Test
    public void testFindNonExistentEntityByName() {
        assertThat(troopDao.findByName("nonExistentName"), is(nullValue()));
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateInvalidEntity() {
        Troop troop = new Troop();
        troopDao.create(troop);
    }

    @Test(dependsOnMethods = "testCreate", expectedExceptions = DataAccessException.class)
    public void testCreateEntityUniqueName() {
        Troop alfaTroop = new Troop("alfa");
        troopDao.create(alfaTroop);
        Troop betaTroop = new Troop("alfa");
        troopDao.create(betaTroop);

    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testCreateNull() {
        troopDao.create(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testUpdateNull() {
        troopDao.update(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testFindByIdNull() {
        troopDao.findById(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testFindByNameNull() {
        troopDao.findByName(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testDeleteNull() {
        troopDao.delete(null);
    }
}
