/**
 * @author Tom Bartoň
 */
package cz.muni.fi.entity;

import org.apache.commons.lang3.Validate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private Role role;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
