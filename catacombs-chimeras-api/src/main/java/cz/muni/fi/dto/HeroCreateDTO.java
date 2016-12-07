/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HeroCreateDTO {

    @NotNull
    @Size(min = 3, max = 255)
    private String name;

    @Min(0L)
    @NotNull
    private Long experience;
    private Long troopId;

    public HeroCreateDTO() {
    }

    public HeroCreateDTO(final String name, final Long experience) {
        this(name, experience, null);
    }

    @JsonCreator
    public HeroCreateDTO(@JsonProperty("name") final String name,
                         @JsonProperty("experience") final Long experience,
                         @JsonProperty("troopId") final Long troopId) {
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

    public void setName(final String name) {
        this.name = name;
    }

    public void setExperience(final Long experience) {
        this.experience = experience;
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

        final HeroCreateDTO that = (HeroCreateDTO) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
