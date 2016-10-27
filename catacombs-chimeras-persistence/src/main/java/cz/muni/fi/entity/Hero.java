/**
 * @author Tom Bartoň
 */
package cz.muni.fi.entity;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.Validate.isTrue;

/**
 * Representation of Hero
 */
@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    @NotNull
    private Long experience = 0L;

    @ManyToMany
    private Set<Role> roles = new HashSet<Role>();
    
    @ManyToOne
    private Troop troop;

    public Hero() {

    }

    public Hero(final String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Long getExperience() {
        return experience;
    }

    public void setExperience(final Long newExperience) {
        isTrue(newExperience >= 0, "You can't set negative experience");
        this.experience = newExperience;
    }

    public void addRole(final Role role) {
        this.roles.add(role);
    }

    public void removeRole(final Role role) {
        this.roles.remove(role);
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void setTroop(Troop troop) {
        this.troop = troop;
    }

    public Troop getTroop() {
        return troop;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Hero)) {
            return false;
        }

        final Hero hero = (Hero) o;

        if (getName() == null) {
            return hero.getName() == null;
        }

        return getName().equals(hero.getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        return result;
    }
}
