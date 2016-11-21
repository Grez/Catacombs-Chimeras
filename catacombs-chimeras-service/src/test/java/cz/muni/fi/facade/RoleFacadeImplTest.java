/**
 * @author David Osicka
 */
package cz.muni.fi.facade;

import cz.muni.fi.dto.HeroDTO;
import cz.muni.fi.dto.RoleCreateDTO;
import cz.muni.fi.dto.RoleDTO;
import cz.muni.fi.entity.Role;
import cz.muni.fi.exceptions.NotFoundException;
import cz.muni.fi.service.RoleService;

import java.util.Collections;
import java.util.List;
import static org.hamcrest.CoreMatchers.equalTo;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.hamcrest.Matchers;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class RoleFacadeImplTest {
    
    private static final Long ID = 1L;
    private static final String NAME = "wizard";
    private static final String DESCRIPTION = "ugly";
    
    private static final Long NO_EXISTING_ID = 2L;
    private static final String NO_EXISTING_NAME = "knight";
    
    private RoleFacade roleFacade;
    
    @Mock
    private RoleService roleService;
    
    private Role role;
    private RoleCreateDTO roleCreateDTO;
    private RoleDTO roleDTO;
    
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        roleFacade = new RoleFacadeImpl(roleService);
        
        role = new Role();
        role.setId(ID);
        role.setName(NAME);
        role.setDescription(DESCRIPTION);
        
        roleCreateDTO = new RoleCreateDTO(NAME, DESCRIPTION);
        roleDTO = new RoleDTO(ID, NAME, DESCRIPTION);
    }
    
    @Test
    public void testFindRoleById() throws Exception {
        when(roleService.findRoleById(ID)).thenReturn(role);
        
        RoleDTO foundRole = roleFacade.findRoleById(ID);
        
        assertThat(foundRole.getId(), is(equalTo(ID)));
        assertThat(foundRole.getName(), is(equalTo(NAME)));
        assertThat(foundRole.getDescription(), is(equalTo(DESCRIPTION)));
    }
    
    @Test(expectedExceptions = NotFoundException.class)
    public void testFindRoleByNonExistentId() throws Exception {
        when(roleService.findRoleById(NO_EXISTING_ID)).thenThrow(NotFoundException.class);
        roleFacade.findRoleById(NO_EXISTING_ID);
    }
    
    @Test
    public void testFindRoleByName() throws Exception {
        when(roleService.findRoleByName(NAME)).thenReturn(role);
        
        RoleDTO foundRole = roleFacade.findRoleByName(NAME);
        
        assertThat(foundRole.getId(), is(equalTo(ID)));
        assertThat(foundRole.getName(), is(equalTo(NAME)));
        assertThat(foundRole.getDescription(), is(equalTo(DESCRIPTION)));
    }
    
    @Test(expectedExceptions = NotFoundException.class)
    public void testFindRoleByNonExistentName() throws Exception {
        when(roleService.findRoleByName(NO_EXISTING_NAME)).thenThrow(NotFoundException.class);
        roleFacade.findRoleByName(NO_EXISTING_NAME);
    }
    
    @Test
    public void testFindAllRoles() throws Exception {
        List list = Collections.singletonList(role);
        when(roleService.findAllRoles()).thenReturn(list);
        
        List<RoleDTO> allRoles = roleFacade.findAllRoles();
        
        assertThat(allRoles, Matchers.hasSize(1));
        assertThat(allRoles.get(0).getId(), is(equalTo(ID)));
        assertThat(allRoles.get(0).getName(), is(equalTo(NAME)));
        assertThat(allRoles.get(0).getDescription(), is(equalTo(DESCRIPTION)));
    }
    
    @Test
    public void testCreateRole() throws Exception {        
        when(roleService.createRole(role)).thenReturn(role);

        RoleDTO roleDTO = roleFacade.createRole(roleCreateDTO);

        verify(roleService, times(1)).createRole(role);
        assertThat(roleDTO.getId(), is(equalTo(ID)));
        assertThat(roleDTO.getName(), is(equalTo(NAME)));
        assertThat(roleDTO.getDescription(), is(equalTo(DESCRIPTION)));
    }
    
    @Test
    public void testUpdateRole() throws Exception {
        roleFacade.updateRole(roleDTO);
        verify(roleService, times(1)).updateRole(role);
    }
    
    @Test
    public void testRemoveRole() throws Exception {
        roleFacade.removeRole(ID);
        verify(roleService, times(1)).removeRole(ID);
    }
    
    @Test(expectedExceptions = NotFoundException.class)
    public void testRemoveNotExistingRole() throws Exception {
        doThrow(NotFoundException.class).when(roleService).removeRole(NO_EXISTING_ID);
        roleFacade.removeRole(NO_EXISTING_ID);
    }
}
