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
import static org.junit.Assert.assertThat;

import cz.muni.fi.PersistenceApplicationTestContext;
import cz.muni.fi.entity.Hero;
import cz.muni.fi.entity.Role;
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
        Role role = new Role("Italian Plumber");
        hero.setName(heroName);
        hero.setRole(role);
        heroDao.create(hero);
        final List<Hero> resultList = entityManager.createQuery("select h from Hero h", Hero.class).getResultList();
        assertThat(resultList, hasSize(1));
        assertThat(resultList.get(0).getName(), is(equalTo(heroName)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testDelete() throws Exception {
        final Hero hero = new Hero("Mario Luigi Quattro Formaggi");
        hero.setRole(new Role("Italian Plumber"));
        heroDao.create(hero);
        heroDao.delete(hero);
        final List<Hero> resultList = entityManager.createQuery("select h from Hero h", Hero.class).getResultList();
        assertThat(resultList, is(empty()));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindById() throws Exception {
        final Hero hero = new Hero("Mario Luigi Quattro Formaggi");
        hero.setRole(new Role("Italian Plumber"));
        heroDao.create(hero);
        Hero foundHero = heroDao.findById(hero.getId());
        assertThat(hero, is(equalTo(foundHero)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindAll() throws Exception {
        final Hero mario = new Hero("Mario Luigi Quattro Formaggi");
        mario.setRole(new Role("Italian Plumber"));

        final Hero hiro = new Hero("Hiro Nakamura");
        hiro.setRole(new Role("Japanese Kamikaze"));

        heroDao.create(mario);
        heroDao.create(hiro);
        assertThat(heroDao.findAll(), containsInAnyOrder(equalTo(mario), equalTo(hiro)));
    }

    @Test(dependsOnMethods = {"testCreate", "testFindById"})
    public void testUpdate() throws Exception {
        final String newName = "Mario";
        final Hero hero = new Hero("No name plumber");
        hero.setRole(new Role("Italian Plumber"));
        heroDao.create(hero);
        hero.setName(newName);
        heroDao.update(hero);
        assertThat(heroDao.findById(hero.getId()).getName(), is(equalTo(newName)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindByName() {
        final String name = "Mario";
        final Hero mario = new Hero(name);
        mario.setRole(new Role("Italian Plumber"));
        heroDao.create(mario);
        assertThat(mario, is(equalTo(heroDao.findByName(name))));
    }

    @Test
    public void testFindNonExistentEntity() {
        assertThat(heroDao.findById(0L), is(nullValue()));
    }

    @Test
    public void testDeleteNonExistentEntity() {
        heroDao.delete(new Hero());
    }

    @Test
    public void testFindNonExistentEntityByName() {
        assertThat(heroDao.findByName("nonExistentName"), is(nullValue()));
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateInvalidEntity() {
        heroDao.create(new Hero());
    }

    @Test(dependsOnMethods = "testCreate", expectedExceptions = DataAccessException.class)
    public void testCreateEntityUniqueName() {
        Hero hero = new Hero("Mario");
        hero.setRole(new Role("Italian Plumber"));
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
