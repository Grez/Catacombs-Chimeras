/**
 * @author Tom Bartoň
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

public class TroopDTO {

    private Long id;

    private String name;

    private String mission;

    private Long amountOfMoney;

    public TroopDTO(final Long id, final String name, final String mission, final Long amountOfMoney) {
        this.id = notNull(id);
        this.name = notNull(name);
        this.mission = mission;
        this.amountOfMoney = amountOfMoney;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMission() {
        return mission;
    }

    public Long getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setMission(final String mission) {
        this.mission = mission;
    }

    public void setAmountOfMoney(final Long amountOfMoney) {
        this.amountOfMoney = amountOfMoney;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TroopDTO troopDTO = (TroopDTO) o;

        return name.equals(troopDTO.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
