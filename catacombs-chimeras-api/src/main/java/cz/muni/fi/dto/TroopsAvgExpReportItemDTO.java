/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

public class TroopsAvgExpReportItemDTO {

    private final TroopDTO troopDTO;
    private final Double average;

    public TroopsAvgExpReportItemDTO(final TroopDTO troopDTO, final Double average) {
        this.troopDTO = notNull(troopDTO);
        this.average = notNull(average);
    }

    public TroopDTO getTroopDTO() {
        return troopDTO;
    }

    /**
     *
     * @return average experience level of heroes in {@link TroopsAvgExpReportItemDTO#troopDTO}
     */
    public Double getAverage() {
        return average;
    }
}
