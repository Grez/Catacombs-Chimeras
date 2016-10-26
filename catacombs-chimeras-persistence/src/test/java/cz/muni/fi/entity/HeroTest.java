/**
 * @author David Osička
 */
package cz.muni.fi.entity;

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
import junit.framework.TestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintViolationException;
import java.util.List;


public class HeroTest extends TestCase {

    @Test
    public void testRole() throws Exception {
        final String heroName = "Mario Luigi Quattro Formaggi";
        Hero hero = new Hero();
        hero.setName(heroName);

        final String roleName = "Italian Plumber";
        Role role = new Role(roleName);
        hero.addRole(role);
        Assert.assertEquals(hero.getRoles().getName(), roleName);

        final String roleName2 = "Luigi's brother";
        Role role2 = new Role(roleName2);
        hero.setRole(role2);
        Assert.assertEquals(hero.getRole().getName(), roleName2);
    }

    @Test
    public void testExperiencesAndLevels() {
        Hero hero = new Hero();
        Assert.assertEquals((long) hero.getLevel(), 1);
        Assert.assertEquals((long) hero.getExperience(), 0);

        hero.addExperience((long) 300);
        Assert.assertEquals((long) hero.getLevel(), 1);

        hero.addExperience((long) 1300);
        Assert.assertEquals(1600, (long) hero.getExperience());
        Assert.assertEquals(2, (long) hero.getLevel());

        hero.setExperience((long) 58000);
        Assert.assertEquals((long) hero.getLevel(), 11);

        hero.setExperience((long) 3374);
        Assert.assertEquals((long) hero.getLevel(), 3);

        hero.setExperience((long) 3375);
        Assert.assertEquals((long) hero.getLevel(), 4);
    }

}
