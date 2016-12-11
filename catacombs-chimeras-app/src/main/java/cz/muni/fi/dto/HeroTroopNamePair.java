package cz.muni.fi.dto;

public class HeroTroopNamePair {
    private HeroDTO hero;
    private String troopName;

    public HeroTroopNamePair(final HeroDTO hero, final String troopName) {
        this.hero = hero;
        this.troopName = troopName;
    }

    public HeroDTO getHero() {
        return hero;
    }

    public String getTroopName() {
        return troopName;
    }
}
