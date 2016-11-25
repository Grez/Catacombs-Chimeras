/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

public class RoleDTO {

    private String name;
    private String description;
    private Long id;

    public RoleDTO(final Long id, final String name, final String description) {
        this.id = notNull(id);
        this.name = notNull(name);
        this.description = description;
    }

    public Long getId() {
        return id;
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
        this.name = notNull(name);
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final RoleDTO roleDTO = (RoleDTO) o;

        return name.equals(roleDTO.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
