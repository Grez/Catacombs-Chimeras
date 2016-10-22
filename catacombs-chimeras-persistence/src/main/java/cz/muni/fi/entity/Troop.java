package cz.muni.fi.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author David Osička
 */
@Entity
public class Troop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = true)
    private String mission;
    
    @Column(nullable = false)
    private Long amountOfMoney;
    
//    @OneToMany(mappedBy = att_name)
//    private Set<Hero> heroes;
    
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
    
//    public Set<Hero> getHeroes() {
//        return heroes;
//    }
//    
//    public void setHeroes(Set<Hero> heroes) {
//        this.heroes = heroes;
//    }
//    
//    public bool containsHero(Hero hero) {
//        return heroes.equals(hero);
//    }
//    
//    public void addHero(Hero hero) {
//        heroes.add(hero);
//    }
    
    @Override
    public boolean equals(final Object obj) {
        if(obj == null) {
            return false;
        }
        if(!(obj instanceof Troop)) {
            return false;
        }
        if(obj == this) {
            return true;
        }
        
        final Troop tmp = (Troop) obj;
        
        if(name == null && tmp.name == null) {
            return true;
        }
        
        return name.equals(tmp.name);
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }
}
