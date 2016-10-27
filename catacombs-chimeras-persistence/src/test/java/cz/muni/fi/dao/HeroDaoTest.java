/**
 * @author David Osička
 */
package cz.muni.fi.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import cz.muni.fi.PersistenceApplicationTestContext;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Role;
import cz.muni.fi.entity.Troop;
import java.util.Arrays;
import java.util.HashSet;
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
import java.util.List;
import org.junit.Assert;


@ContextConfiguration(classes=PersistenceApplicationTestContext.class)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class HeroDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private HeroDao heroDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCreate() throws Exception {
        final String heroName = "Mario Luigi Quattro Formaggi";
        final Hero hero = new Hero();
        final Role role = new Role("Italian Plumber");
        entityManager.persist(role);
        final Troop troop = new Troop("Italian army");
        entityManager.persist(troop);
        
        hero.setName(heroName);
        hero.addRole(role);
        hero.setTroop(troop);
        heroDao.create(hero);
        
        final List<Hero> resultList = entityManager.createQuery("select h from Hero h", Hero.class).getResultList();
        Assert.assertThat(resultList, hasSize(1));
        Assert.assertThat(resultList.get(0).getName(), is(equalTo(heroName)));
        Assert.assertTrue(resultList.get(0).getRoles().contains(role));
        Assert.assertThat(resultList.get(0).getTroop(), is(equalTo(troop)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testDelete() throws Exception {
        final Hero hero = new Hero("Mario Luigi Quattro Formaggi");
        heroDao.create(hero);
        heroDao.delete(hero);
        final List<Hero> resultList = entityManager.createQuery("select h from Hero h", Hero.class).getResultList();
        Assert.assertThat(resultList, is(empty()));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindById() throws Exception {
        final Hero hero = new Hero("Mario Luigi Quattro Formaggi");
        heroDao.create(hero);
        Hero foundHero = heroDao.findById(hero.getId());
        Assert.assertThat(hero, is(equalTo(foundHero)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindAll() throws Exception {
        Hero mario = new Hero("Mario Luigi Quattro Formaggi");
        mario.addRole(new Role("Italian Plumber"));

        Hero hiro = new Hero("Hiro Nakamura");
        hiro.addRole(new Role("Japanese Kamikaze"));

        heroDao.create(mario);
        heroDao.create(hiro);
        Assert.assertThat(heroDao.findAll(), containsInAnyOrder(equalTo(mario), equalTo(hiro)));
    }

    @Test(dependsOnMethods = {"testCreate", "testFindById"})
    public void testUpdate() throws Exception {
        final String newName = "Mario";
        final Role role1 = new Role("Italian Plumber");
        entityManager.persist(role1);
        final Role role2 = new Role("German Plumber");
        entityManager.persist(role2);
        final Troop newTroop = new Troop("German army");
        entityManager.persist(newTroop);
        
        Hero hero = new Hero("No name plumber");
        hero.addRole(role1);
        hero.setTroop(new Troop("Italian army"));
        heroDao.create(hero);
        
        hero.setName(newName);
        hero.addRole(role2);
        hero.setTroop(newTroop);
        heroDao.update(hero);
        
        Hero foundHero = heroDao.findById(hero.getId());
        Assert.assertThat(foundHero.getName(), is(equalTo(newName)));
        Assert.assertTrue(foundHero.getRoles().containsAll(new HashSet<Role>(Arrays.asList(role1, role2))));
        Assert.assertEquals(hero.getTroop(), newTroop);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindByName() {
        final String name = "Mario";
        final Hero mario = new Hero(name);
        heroDao.create(mario);
        Assert.assertThat(heroDao.findByName(name), is(equalTo(mario)));
    }

    @Test
    public void testFindNonExistentEntity() {
        Assert.assertThat(heroDao.findById(0L), is(nullValue()));
    }

    @Test
    public void testDeleteNonExistentEntity() {
        heroDao.delete(new Hero());
    }

    @Test
    public void testFindNonExistentEntityByName() {
        Assert.assertThat(heroDao.findByName("nonExistentName"), is(nullValue()));
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateInvalidEntity() {
        heroDao.create(new Hero());
    }

    @Test(dependsOnMethods = "testCreate", expectedExceptions = DataAccessException.class)
    public void testCreateEntityUniqueName() {
        Hero hero = new Hero("Mario");
        heroDao.create(hero);
        hero = new Hero("Mario");
        heroDao.create(hero);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testCreateNull() {
        heroDao.create(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testUpdateNull() {
        heroDao.update(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testFindByIdNull() {
        heroDao.findById(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testFindByNameNull() {
        heroDao.findByName(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testDeleteNull() {
        heroDao.delete(null);
    }
}
