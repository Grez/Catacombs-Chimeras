/**
 * @author Tom Bartoň
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

public class TroopCreateDTO {

    private String name;

    private String mission;

    private Long amountOfMoney;

    public TroopCreateDTO(final String name, final String mission, final Long amountOfMoney) {
        this.name = notNull(name);
        this.mission = mission;
        this.amountOfMoney = notNull(amountOfMoney);
    }

    public String getName() {
        return name;
    }

    /**
     * @return String|null
     */
    public String getMission() {
        return mission;
    }

    public Long getAmountOfMoney() {
        return amountOfMoney;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TroopCreateDTO that = (TroopCreateDTO) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
