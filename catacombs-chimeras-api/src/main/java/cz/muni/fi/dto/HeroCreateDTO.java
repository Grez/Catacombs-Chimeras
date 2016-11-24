/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

public class HeroCreateDTO {

    private String name;
    private Long experience;
    private Long troopId;

    public HeroCreateDTO(final String name, final Long experience) {
        this(name, experience, null);
    }

    public HeroCreateDTO(final String name, final Long experience, final Long troopId) {
        this.name = notNull(name);
        this.experience = notNull(experience);
        this.troopId = troopId;
    }

    public String getName() {
        return name;
    }

    public Long getExperience() {
        return experience;
    }

    public Long getTroopId() {
        return troopId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final HeroCreateDTO that = (HeroCreateDTO) o;

        return name.equals(that.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
