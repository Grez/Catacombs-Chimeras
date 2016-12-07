/**
 * @author David Osička
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleCreateDTO {

    @NotNull
    @Size(min = 3, max = 255)
    private String name;

    private String description;

    public RoleCreateDTO() {
    }

    @JsonCreator
    public RoleCreateDTO(@JsonProperty("name") final String name,
                         @JsonProperty("description") final String description) {
        this.name = notNull(name);
        this.description = description;
    }

    public String getName() {
        return name;
    }

    /**
     * @return String|null
     */
    public String getDescription() {
        return description;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final RoleCreateDTO that = (RoleCreateDTO) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
