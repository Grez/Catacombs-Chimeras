/**
 * @author David Osička
 */
package cz.muni.fi.entity;

import java.util.Arrays;
import java.util.HashSet;
import org.testng.Assert;
import org.testng.annotations.Test;


public class HeroTest {

    @Test
    public void testName() {
        final String heroName = "Mario Luigi Quattro Formaggi";
        Hero hero = new Hero();
        hero.setName(heroName);
        Assert.assertEquals(hero.getName(), heroName);
    }
    
    @Test
    public void testRole() throws Exception {
        final String heroName = "Mario Luigi Quattro Formaggi";
        Hero hero = new Hero();
        hero.setName(heroName);

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
    public void testExperiences() {
        Hero hero = new Hero();
        Assert.assertEquals((long) hero.getExperience(), 0);

        hero.setExperience((long) 1);
        Assert.assertEquals((long) hero.getExperience(), 1);

        hero.setExperience((long) 58000);
        Assert.assertEquals((long) hero.getExperience(), 58000);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testNegativeExperiences() {
        Hero hero = new Hero();
        hero.setExperience((long)-1);
    }
    
    @Test
    public void testTroop() {
        Hero hero = new Hero();
        Troop troop = new Troop();
        hero.setTroop(troop);
        Assert.assertEquals(hero.getTroop(), troop);
    }
}
