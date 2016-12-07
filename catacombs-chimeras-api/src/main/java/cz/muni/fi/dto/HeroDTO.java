/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeroDTO {

    private String name;
    private Long experience;
    private Long troopId;
    private Long id;

    @JsonCreator
    public HeroDTO(@JsonProperty("id") final Long id,
                   @JsonProperty("name") final String name,
                   @JsonProperty("experience") final Long experience,
                   @JsonProperty("troopId") final Long troopId) {
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
