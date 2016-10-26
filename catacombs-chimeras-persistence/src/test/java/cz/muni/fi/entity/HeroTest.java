/**
 * @author David Osička
 */
package cz.muni.fi.entities;

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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
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
import java.util.Set;


public class HeroTest extends TestCase {

    @Test
    public void testRole() throws Exception {
        final String heroName = "Mario Luigi Quattro Formaggi";
        Hero hero = new Hero();
        hero.setName(heroName);
        Assert.assertEquals(hero.getName(), heroName);

        final String roleName = "Italian Plumber";
        final Role role = new Role(roleName);
        hero.addRole(role);
        Assert.assertTrue(hero.getRoles().contains(role));

        final String roleName2 = "Luigi's brother";
        final Role role2 = new Role(roleName2);
        hero.addRole(role2);
        Assert.assertTrue(hero.getRoles().containsAll(new HashSet<Role>(Arrays.asList(role, role2))));
    }

    @Test
    public void testExperiencesAndLevels() {
        Hero hero = new Hero();
        Assert.assertEquals((long) hero.getExperience(), 0);

        hero.setExperience((long) 1);
        Assert.assertEquals((long) hero.getExperience(), 1);

        hero.setExperience((long) 58000);
        Assert.assertEquals((long) hero.getExperience(), 58000);
        
        boolean assertValue = false;
        try {
            hero.setExperience((long)-1);
        } catch(IllegalArgumentException e) {
            assertValue = true;
        }
        Assert.assertTrue(assertValue);
    }

}
