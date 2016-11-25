/**
 * @author David Osička
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

public class RoleCreateDTO {

    private String name;
    private String description;

    public RoleCreateDTO(final String name, final String description) {
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

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final RoleCreateDTO that = (RoleCreateDTO) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
