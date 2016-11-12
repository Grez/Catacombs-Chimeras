/**
 *
 * @author David Osička
 */
package cz.muni.fi.entity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;


@Entity
public class Troop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
    
    private String mission;
    
    private Long amountOfMoney;

    @OneToMany(mappedBy = "troop")
    private Set<Hero> heroes = new HashSet<>();
    
    public Troop() {
        
    }
    
    public Troop(final String name) {
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
    
    public String getMission() {
        return mission;
    }
    
    public void setMission(final String mission) {
        this.mission = mission;
    }
    
    public Long getAmountOfMoney() {
        return amountOfMoney;
    }
    
    public void setAmountOfMoney(final Long amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }
    
    public Set<Hero> getHeroes() {
        return Collections.unmodifiableSet(heroes);
    }
    
    public void setHeroes(final Set<Hero> heroes) {
        this.heroes = heroes;
    }

    public void addHero(final Hero hero) {
        heroes.add(hero);
        hero.setTroop(this);
    }

    public void removeHero(final Hero hero) {
        heroes.remove(hero);
        hero.setTroop(null);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Troop)) {
            return false;
        }

        final Troop tmp = (Troop) obj;

        if (getName() == null) {
            return tmp.getName() == null;
        }

        return getName().equals(tmp.getName());
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (getName() == null ? 0 : getName().hashCode());
        return result;
    }
}
