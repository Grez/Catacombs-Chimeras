/**
 * @author Tom Bartoň
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TroopDTO {

    private Long id;

    private String name;

    private String mission;

    private Long amountOfMoney;

    @JsonCreator
    public TroopDTO(@JsonProperty("id") final Long id,
                    @JsonProperty("name") final String name,
                    @JsonProperty("mission") final String mission,
                    @JsonProperty("amountOfMoney") final Long amountOfMoney) {
        this.id = notNull(id);
        this.name = notNull(name);
        this.mission = mission;
        this.amountOfMoney = notNull(amountOfMoney);
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
    public String getMission() {
        return mission;
    }

    public Long getAmountOfMoney() {
        return amountOfMoney;
    }

    public void setName(final String name) {
        this.name = notNull(name);
    }

    public void setMission(final String mission) {
        this.mission = mission;
    }

    public void setAmountOfMoney(final Long amountOfMoney) {
        this.amountOfMoney = notNull(amountOfMoney);
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
