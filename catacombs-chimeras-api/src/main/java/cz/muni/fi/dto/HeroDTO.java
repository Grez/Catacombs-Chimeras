/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

public class HeroDTO {

    private String name;
    private Long experience;
    private Long troopId;
    private Long id;

    public HeroDTO(final Long id, final String name, final Long experience, final Long troopId) {
        this.id = notNull(id);
        this.name = notNull(name);
        this.experience = notNull(experience);
        this.troopId = troopId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getExperience() {
        return experience;
    }

    /**
     * @return Long|null
     */
    public Long getTroopId() {
        return troopId;
    }

    public void setName(final String name) {
        this.name = notNull(name);
    }

    public void setExperience(final Long experience) {
        this.experience = notNull(experience);
    }

    public void setTroopId(final Long troopId) {
        this.troopId = troopId;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final HeroDTO heroDTO = (HeroDTO) o;

        return name.equals(heroDTO.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
