/**
 * @author Tom Bartoň
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
public class TroopCreateDTO {

    @NotNull
    @Size(min = 3, max = 255)
    private String name;

    @NotNull
    private String mission;

    @Min(0L)
    @NotNull
    private Long amountOfMoney;

    public TroopCreateDTO() {
    }

    @JsonCreator
    public TroopCreateDTO(@JsonProperty("name") final String name,
                          @JsonProperty("mission") final String mission,
                          @JsonProperty("amountOfMoney") final Long amountOfMoney) {
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        final TroopCreateDTO that = (TroopCreateDTO) o;

        return name != null ? name.equals(that.name) : that.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
