/**
 * @author Ondřej Benkovský
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
public class RoleDaoTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private RoleDao roleDao;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testCreate() throws Exception {
        final String roleName = "elf magician";
        final String roleDescription = "can cast fireballs";
        final Role role = new Role();
        role.setName(roleName);
        role.setDescription(roleDescription);
        roleDao.create(role);
        final List<Role> resultList = entityManager.createQuery("select r from Role r", Role.class).getResultList();
        assertThat(resultList, hasSize(1));
        assertThat(resultList.get(0).getName(), is(equalTo(roleName)));
        assertThat(resultList.get(0).getDescription(), is(equalTo(roleDescription)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testDelete() throws Exception {
        final Role role = new Role("elf magician");
        roleDao.create(role);
        roleDao.delete(role);
        final List<Role> resultList = entityManager.createQuery("select r from Role r", Role.class).getResultList();
        assertThat(resultList, is(empty()));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindById() throws Exception {
        final Role role = new Role("elf magician");
        roleDao.create(role);
        Role foundRole = roleDao.findById(role.getId());
        assertThat(role, is(equalTo(foundRole)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindAll() throws Exception {
        final Role elfMagician = new Role("elf magician");
        final Role trollWarlock = new Role("troll warlock");
        roleDao.create(elfMagician);
        roleDao.create(trollWarlock);
        assertThat(roleDao.findAll(), containsInAnyOrder(equalTo(elfMagician), equalTo(trollWarlock)));
    }

    @Test(dependsOnMethods = {"testCreate", "testFindById"})
    public void testUpdate() throws Exception {
        final String newName = "troll warlock";
        final Role role = new Role("elf magician");
        roleDao.create(role);
        role.setName(newName);
        roleDao.update(role);
        assertThat(roleDao.findById(role.getId()).getName(), is(equalTo(newName)));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testFindByName() {
        final String name = "elf magician";
        final Role elfMagician = new Role(name);
        roleDao.create(elfMagician);
        assertThat(elfMagician, is(equalTo(roleDao.findByName(name))));
    }

    @Test
    public void testFindNonExistentEntity() {
        assertThat(roleDao.findById(0L), is(nullValue()));
    }

    @Test
    public void testDeleteNonExistentEntity() {
        roleDao.delete(new Role());
    }

    @Test
    public void testFindNonExistentEntityByName() {
        assertThat(roleDao.findByName("nonExistentName"), is(nullValue()));
    }

    @Test(expectedExceptions = ConstraintViolationException.class)
    public void testCreateInvalidEntity() {
        roleDao.create(new Role());
    }

    @Test(dependsOnMethods = "testCreate", expectedExceptions = DataAccessException.class)
    public void testCreateEntityUniqueName() {
        Role role = new Role("elf magician");
        roleDao.create(role);
        role = new Role("elf magician");
        roleDao.create(role);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testCreateNull() {
        roleDao.create(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testUpdateNull() {
        roleDao.update(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testFindByIdNull() {
        roleDao.findById(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testFindByNameNull() {
        roleDao.findByName(null);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testDeleteNull() {
        roleDao.delete(null);
    }
}