/**
 * @author David Osicka
 */
package cz.muni.fi.service;

import cz.muni.fi.dao.RoleDao;
import cz.muni.fi.entity.Role;
import cz.muni.fi.exceptions.NotFoundException;

import java.util.Collections;
import java.util.List;

import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.mockito.Mock;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



public class RoleServiceImplTest {
    
    private static final Long ID = 1L;
    private static final String NAME = "wizard";
    private static final String DESCRIPTION = "ungly";
    
    private RoleService roleService;
    private Role role;
    
    @Mock
    private RoleDao roleDao;
    
    @BeforeTest
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        roleService = new RoleServiceImpl(roleDao);

        role = new Role();
        role.setId(ID);
        role.setName(NAME);
        role.setDescription(DESCRIPTION);
    }
    
    @Test
    public void testFindRoleById() throws Exception {
        when(roleDao.findById(ID)).thenReturn(role);
        Role foundRole = roleService.findRoleById(ID);
        assertThat(foundRole, is(equalTo(role)));
    }
    
    @Test(expectedExceptions = NotFoundException.class)
    public void testFindRoleByNonExistingID() throws Exception {
        roleService.findRoleById(3L);
    }

    @Test
    public void testFindRoleByName() throws Exception {
        when(roleDao.findByName(NAME)).thenReturn(role);
        Role foundRole = roleService.findRoleByName(NAME);
        assertThat(foundRole, is(equalTo(role)));
    }
    
    @Test(expectedExceptions = NotFoundException.class)
    public void testFindRoleByNonExistingName() throws Exception {
        roleService.findRoleByName("none");
    }

    @Test
    public void testFindAllRoles() throws Exception {
        List list = Collections.singletonList(role);
        when(roleDao.findAll()).thenReturn(list);
        List<Role> allRoles = roleService.findAllRoles();
        assertThat(allRoles, containsInAnyOrder(role));
    }

    @Test
    public void testCreateRole() throws Exception {
        roleService.createRole(role);
        verify(roleDao, times(1)).create(role);
    }

    @Test
    public void updateRole() throws Exception {
        roleService.updateRole(role);
        verify(roleDao, times(1)).update(role);
    }

    @Test
    public void removeRole() throws Exception {
        when(roleDao.findById(ID)).thenReturn(role);
        roleService.removeRole(ID);
        verify(roleDao, times(1)).delete(role);
    }
    
    @Test(expectedExceptions = NotFoundException.class)
    public void testRemoveNonExistingRole() throws Exception {
        roleService.removeRole(3L);
    }
}
