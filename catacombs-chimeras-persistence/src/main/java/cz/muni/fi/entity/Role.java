/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Representation of hero role
 */
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "roles")
    private Set<Hero> heroes = new HashSet<>();

    public Role() {

    }

    public Role(final String name) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Set<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(final Set<Hero> heroes) {
        this.heroes = heroes;
    }

    public void addHero(final Hero hero) {
        this.heroes.add(hero);
    }

    public void removeHero(final Hero hero) {
        this.heroes.remove(hero);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Role)) {
            return false;
        }

        final Role role = (Role) o;

        if (getName() == null) {
            return role.getName() == null;
        }

        return getName().equals(role.getName());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        return result;
    }
}
