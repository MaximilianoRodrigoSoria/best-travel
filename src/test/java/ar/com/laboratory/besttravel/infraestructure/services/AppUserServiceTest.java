package ar.com.laboratory.besttravel.infraestructure.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.com.laboratory.besttravel.domain.entities.documents.AppUserDocument;
import ar.com.laboratory.besttravel.domain.entities.documents.Role;
import ar.com.laboratory.besttravel.domain.repositories.mongo.AppUserRepository;
import ar.com.laboratory.besttravel.util.exceptions.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AppUserService.class})
@ExtendWith(SpringExtension.class)
class AppUserServiceTest {
    @MockBean
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserService appUserService;

    /**
     * Method under test: {@link AppUserService#enabled(String)}
     */
    @Test
    void testEnabled() {
        AppUserDocument appUserDocument = new AppUserDocument();
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);

        AppUserDocument appUserDocument2 = new AppUserDocument();
        appUserDocument2.setDni("Dni");
        appUserDocument2.setEnabled(true);
        appUserDocument2.setId("42");
        appUserDocument2.setPassword("iloveyou");
        Role.RoleBuilder builderResult2 = Role.builder();
        appUserDocument2.setRole(builderResult2.grantedAuthorities(new HashSet<>()).build());
        appUserDocument2.setUsername("janedoe");
        when(appUserRepository.save(Mockito.<AppUserDocument>any())).thenReturn(appUserDocument2);
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        Map<String, Boolean> actualEnabledResult = appUserService.enabled("janedoe");
        assertEquals(1, actualEnabledResult.size());
        Boolean expectedGetResult = new Boolean(true);
        assertEquals(expectedGetResult, actualEnabledResult.get("janedoe"));
        verify(appUserRepository).save(Mockito.<AppUserDocument>any());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#enabled(String)}
     */
    @Test
    void testEnabled2() {
        AppUserDocument appUserDocument = new AppUserDocument();
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);
        when(appUserRepository.save(Mockito.<AppUserDocument>any()))
                .thenThrow(new UsernameNotFoundException("Table Name"));
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class, () -> appUserService.enabled("janedoe"));
        verify(appUserRepository).save(Mockito.<AppUserDocument>any());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#enabled(String)}
     */
    @Test
    void testEnabled3() {
        AppUserDocument appUserDocument = mock(AppUserDocument.class);
        when(appUserDocument.isEnabled()).thenReturn(false);
        doNothing().when(appUserDocument).setDni(Mockito.<String>any());
        doNothing().when(appUserDocument).setEnabled(anyBoolean());
        doNothing().when(appUserDocument).setId(Mockito.<String>any());
        doNothing().when(appUserDocument).setPassword(Mockito.<String>any());
        doNothing().when(appUserDocument).setRole(Mockito.<Role>any());
        doNothing().when(appUserDocument).setUsername(Mockito.<String>any());
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);

        AppUserDocument appUserDocument2 = new AppUserDocument();
        appUserDocument2.setDni("Dni");
        appUserDocument2.setEnabled(true);
        appUserDocument2.setId("42");
        appUserDocument2.setPassword("iloveyou");
        Role.RoleBuilder builderResult2 = Role.builder();
        appUserDocument2.setRole(builderResult2.grantedAuthorities(new HashSet<>()).build());
        appUserDocument2.setUsername("janedoe");
        when(appUserRepository.save(Mockito.<AppUserDocument>any())).thenReturn(appUserDocument2);
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        Map<String, Boolean> actualEnabledResult = appUserService.enabled("janedoe");
        assertEquals(1, actualEnabledResult.size());
        Boolean expectedGetResult = new Boolean(true);
        assertEquals(expectedGetResult, actualEnabledResult.get("janedoe"));
        verify(appUserRepository).save(Mockito.<AppUserDocument>any());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
        verify(appUserDocument).isEnabled();
        verify(appUserDocument).setDni(Mockito.<String>any());
        verify(appUserDocument, atLeast(1)).setEnabled(anyBoolean());
        verify(appUserDocument).setId(Mockito.<String>any());
        verify(appUserDocument).setPassword(Mockito.<String>any());
        verify(appUserDocument).setRole(Mockito.<Role>any());
        verify(appUserDocument).setUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#enabled(String)}
     */
    @Test
    void testEnabled4() {
        Optional<AppUserDocument> emptyResult = Optional.empty();
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(UsernameNotFoundException.class, () -> appUserService.enabled("janedoe"));
        verify(appUserRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#addRole(String, String)}
     */
    @Test
    void testAddRole() {
        AppUserDocument appUserDocument = new AppUserDocument();
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);

        AppUserDocument appUserDocument2 = new AppUserDocument();
        appUserDocument2.setDni("Dni");
        appUserDocument2.setEnabled(true);
        appUserDocument2.setId("42");
        appUserDocument2.setPassword("iloveyou");
        Role.RoleBuilder builderResult2 = Role.builder();
        appUserDocument2.setRole(builderResult2.grantedAuthorities(new HashSet<>()).build());
        appUserDocument2.setUsername("janedoe");
        when(appUserRepository.save(Mockito.<AppUserDocument>any())).thenReturn(appUserDocument2);
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertEquals(1, appUserService.addRole("janedoe", "Role").size());
        verify(appUserRepository).save(Mockito.<AppUserDocument>any());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#addRole(String, String)}
     */
    @Test
    void testAddRole2() {
        AppUserDocument appUserDocument = new AppUserDocument();
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);
        when(appUserRepository.save(Mockito.<AppUserDocument>any()))
                .thenThrow(new UsernameNotFoundException("Table Name"));
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class, () -> appUserService.addRole("janedoe", "Role"));
        verify(appUserRepository).save(Mockito.<AppUserDocument>any());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#addRole(String, String)}
     */
    @Test
    void testAddRole3() {
        AppUserDocument appUserDocument = new AppUserDocument();
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);
        AppUserDocument appUserDocument2 = mock(AppUserDocument.class);
        when(appUserDocument2.getUsername()).thenThrow(new UsernameNotFoundException("Table Name"));
        Role.RoleBuilder builderResult2 = Role.builder();
        when(appUserDocument2.getRole()).thenReturn(builderResult2.grantedAuthorities(new HashSet<>()).build());
        doNothing().when(appUserDocument2).setDni(Mockito.<String>any());
        doNothing().when(appUserDocument2).setEnabled(anyBoolean());
        doNothing().when(appUserDocument2).setId(Mockito.<String>any());
        doNothing().when(appUserDocument2).setPassword(Mockito.<String>any());
        doNothing().when(appUserDocument2).setRole(Mockito.<Role>any());
        doNothing().when(appUserDocument2).setUsername(Mockito.<String>any());
        appUserDocument2.setDni("Dni");
        appUserDocument2.setEnabled(true);
        appUserDocument2.setId("42");
        appUserDocument2.setPassword("iloveyou");
        Role.RoleBuilder builderResult3 = Role.builder();
        appUserDocument2.setRole(builderResult3.grantedAuthorities(new HashSet<>()).build());
        appUserDocument2.setUsername("janedoe");
        when(appUserRepository.save(Mockito.<AppUserDocument>any())).thenReturn(appUserDocument2);
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class, () -> appUserService.addRole("janedoe", "Role"));
        verify(appUserRepository).save(Mockito.<AppUserDocument>any());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
        verify(appUserDocument2).getRole();
        verify(appUserDocument2).getUsername();
        verify(appUserDocument2).setDni(Mockito.<String>any());
        verify(appUserDocument2).setEnabled(anyBoolean());
        verify(appUserDocument2).setId(Mockito.<String>any());
        verify(appUserDocument2).setPassword(Mockito.<String>any());
        verify(appUserDocument2).setRole(Mockito.<Role>any());
        verify(appUserDocument2).setUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#addRole(String, String)}
     */
    @Test
    void testAddRole4() {
        Optional<AppUserDocument> emptyResult = Optional.empty();
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        AppUserDocument appUserDocument = mock(AppUserDocument.class);
        doNothing().when(appUserDocument).setDni(Mockito.<String>any());
        doNothing().when(appUserDocument).setEnabled(anyBoolean());
        doNothing().when(appUserDocument).setId(Mockito.<String>any());
        doNothing().when(appUserDocument).setPassword(Mockito.<String>any());
        doNothing().when(appUserDocument).setRole(Mockito.<Role>any());
        doNothing().when(appUserDocument).setUsername(Mockito.<String>any());
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        assertThrows(UsernameNotFoundException.class, () -> appUserService.addRole("janedoe", "Role"));
        verify(appUserRepository).findByUsername(Mockito.<String>any());
        verify(appUserDocument).setDni(Mockito.<String>any());
        verify(appUserDocument).setEnabled(anyBoolean());
        verify(appUserDocument).setId(Mockito.<String>any());
        verify(appUserDocument).setPassword(Mockito.<String>any());
        verify(appUserDocument).setRole(Mockito.<Role>any());
        verify(appUserDocument).setUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#removeRole(String, String)}
     */
    @Test
    void testRemoveRole() {
        AppUserDocument appUserDocument = new AppUserDocument();
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);

        AppUserDocument appUserDocument2 = new AppUserDocument();
        appUserDocument2.setDni("Dni");
        appUserDocument2.setEnabled(true);
        appUserDocument2.setId("42");
        appUserDocument2.setPassword("iloveyou");
        Role.RoleBuilder builderResult2 = Role.builder();
        appUserDocument2.setRole(builderResult2.grantedAuthorities(new HashSet<>()).build());
        appUserDocument2.setUsername("janedoe");
        when(appUserRepository.save(Mockito.<AppUserDocument>any())).thenReturn(appUserDocument2);
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertEquals(1, appUserService.removeRole("janedoe", "Role").size());
        verify(appUserRepository).save(Mockito.<AppUserDocument>any());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#removeRole(String, String)}
     */
    @Test
    void testRemoveRole2() {
        AppUserDocument appUserDocument = new AppUserDocument();
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);
        when(appUserRepository.save(Mockito.<AppUserDocument>any()))
                .thenThrow(new UsernameNotFoundException("Table Name"));
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class, () -> appUserService.removeRole("janedoe", "Role"));
        verify(appUserRepository).save(Mockito.<AppUserDocument>any());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#removeRole(String, String)}
     */
    @Test
    void testRemoveRole3() {
        AppUserDocument appUserDocument = new AppUserDocument();
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);
        AppUserDocument appUserDocument2 = mock(AppUserDocument.class);
        when(appUserDocument2.getUsername()).thenThrow(new UsernameNotFoundException("Table Name"));
        Role.RoleBuilder builderResult2 = Role.builder();
        when(appUserDocument2.getRole()).thenReturn(builderResult2.grantedAuthorities(new HashSet<>()).build());
        doNothing().when(appUserDocument2).setDni(Mockito.<String>any());
        doNothing().when(appUserDocument2).setEnabled(anyBoolean());
        doNothing().when(appUserDocument2).setId(Mockito.<String>any());
        doNothing().when(appUserDocument2).setPassword(Mockito.<String>any());
        doNothing().when(appUserDocument2).setRole(Mockito.<Role>any());
        doNothing().when(appUserDocument2).setUsername(Mockito.<String>any());
        appUserDocument2.setDni("Dni");
        appUserDocument2.setEnabled(true);
        appUserDocument2.setId("42");
        appUserDocument2.setPassword("iloveyou");
        Role.RoleBuilder builderResult3 = Role.builder();
        appUserDocument2.setRole(builderResult3.grantedAuthorities(new HashSet<>()).build());
        appUserDocument2.setUsername("janedoe");
        when(appUserRepository.save(Mockito.<AppUserDocument>any())).thenReturn(appUserDocument2);
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class, () -> appUserService.removeRole("janedoe", "Role"));
        verify(appUserRepository).save(Mockito.<AppUserDocument>any());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
        verify(appUserDocument2).getRole();
        verify(appUserDocument2).getUsername();
        verify(appUserDocument2).setDni(Mockito.<String>any());
        verify(appUserDocument2).setEnabled(anyBoolean());
        verify(appUserDocument2).setId(Mockito.<String>any());
        verify(appUserDocument2).setPassword(Mockito.<String>any());
        verify(appUserDocument2).setRole(Mockito.<Role>any());
        verify(appUserDocument2).setUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#removeRole(String, String)}
     */
    @Test
    void testRemoveRole4() {
        Optional<AppUserDocument> emptyResult = Optional.empty();
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        AppUserDocument appUserDocument = mock(AppUserDocument.class);
        doNothing().when(appUserDocument).setDni(Mockito.<String>any());
        doNothing().when(appUserDocument).setEnabled(anyBoolean());
        doNothing().when(appUserDocument).setId(Mockito.<String>any());
        doNothing().when(appUserDocument).setPassword(Mockito.<String>any());
        doNothing().when(appUserDocument).setRole(Mockito.<Role>any());
        doNothing().when(appUserDocument).setUsername(Mockito.<String>any());
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        appUserDocument.setRole(builderResult.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        assertThrows(UsernameNotFoundException.class, () -> appUserService.removeRole("janedoe", "Role"));
        verify(appUserRepository).findByUsername(Mockito.<String>any());
        verify(appUserDocument).setDni(Mockito.<String>any());
        verify(appUserDocument).setEnabled(anyBoolean());
        verify(appUserDocument).setId(Mockito.<String>any());
        verify(appUserDocument).setPassword(Mockito.<String>any());
        verify(appUserDocument).setRole(Mockito.<Role>any());
        verify(appUserDocument).setUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() {
        AppUserDocument appUserDocument = new AppUserDocument();
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        HashSet<String> grantedAuthorities = new HashSet<>();
        appUserDocument.setRole(builderResult.grantedAuthorities(grantedAuthorities).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = appUserService.loadUserByUsername("janedoe");
        assertEquals(grantedAuthorities, actualLoadUserByUsernameResult.getAuthorities());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() {
        AppUserDocument.AppUserDocumentBuilder passwordResult = AppUserDocument.builder()
                .dni("Dni")
                .enabled(true)
                .id("42")
                .password("iloveyou");
        Role.RoleBuilder builderResult = Role.builder();
        HashSet<String> grantedAuthorities = new HashSet<>();
        AppUserDocument buildResult = passwordResult.role(builderResult.grantedAuthorities(grantedAuthorities).build())
                .username("janedoe")
                .build();
        buildResult.setDni("Dni");
        buildResult.setEnabled(true);
        buildResult.setId("42");
        buildResult.setPassword("iloveyou");
        Role.RoleBuilder builderResult2 = Role.builder();
        buildResult.setRole(builderResult2.grantedAuthorities(new HashSet<>()).build());
        buildResult.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(buildResult);
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        UserDetails actualLoadUserByUsernameResult = appUserService.loadUserByUsername("janedoe");
        assertEquals(grantedAuthorities, actualLoadUserByUsernameResult.getAuthorities());
        assertTrue(actualLoadUserByUsernameResult.isEnabled());
        assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
        assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
        assertEquals("janedoe", actualLoadUserByUsernameResult.getUsername());
        assertEquals("iloveyou", actualLoadUserByUsernameResult.getPassword());
        verify(appUserRepository).findByUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername3() {
        AppUserDocument appUserDocument = mock(AppUserDocument.class);
        when(appUserDocument.getUsername()).thenThrow(new UsernameNotFoundException("Table Name"));
        Role.RoleBuilder builderResult = Role.builder();
        when(appUserDocument.getRole()).thenReturn(builderResult.grantedAuthorities(new HashSet<>()).build());
        doNothing().when(appUserDocument).setDni(Mockito.<String>any());
        doNothing().when(appUserDocument).setEnabled(anyBoolean());
        doNothing().when(appUserDocument).setId(Mockito.<String>any());
        doNothing().when(appUserDocument).setPassword(Mockito.<String>any());
        doNothing().when(appUserDocument).setRole(Mockito.<Role>any());
        doNothing().when(appUserDocument).setUsername(Mockito.<String>any());
        appUserDocument.setDni("Dni");
        appUserDocument.setEnabled(true);
        appUserDocument.setId("42");
        appUserDocument.setPassword("iloveyou");
        Role.RoleBuilder builderResult2 = Role.builder();
        appUserDocument.setRole(builderResult2.grantedAuthorities(new HashSet<>()).build());
        appUserDocument.setUsername("janedoe");
        Optional<AppUserDocument> ofResult = Optional.of(appUserDocument);
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(ofResult);
        assertThrows(UsernameNotFoundException.class, () -> appUserService.loadUserByUsername("janedoe"));
        verify(appUserRepository).findByUsername(Mockito.<String>any());
        verify(appUserDocument).getRole();
        verify(appUserDocument).getUsername();
        verify(appUserDocument).setDni(Mockito.<String>any());
        verify(appUserDocument).setEnabled(anyBoolean());
        verify(appUserDocument).setId(Mockito.<String>any());
        verify(appUserDocument).setPassword(Mockito.<String>any());
        verify(appUserDocument).setRole(Mockito.<Role>any());
        verify(appUserDocument).setUsername(Mockito.<String>any());
    }

    /**
     * Method under test: {@link AppUserService#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername4() {
        Optional<AppUserDocument> emptyResult = Optional.empty();
        when(appUserRepository.findByUsername(Mockito.<String>any())).thenReturn(emptyResult);
        assertThrows(UsernameNotFoundException.class, () -> appUserService.loadUserByUsername("janedoe"));
        verify(appUserRepository).findByUsername(Mockito.<String>any());
    }
}

