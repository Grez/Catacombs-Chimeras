/**
 * @author Ondřej Benkovský
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;

import java.util.List;

public class TroopsAvgExpReportDTO {

    private final List<TroopsAvgExpReportItemDTO> troops;
    private final Double highestAverageExperience;
    private final Double lowestAverageExperience;

    public TroopsAvgExpReportDTO(final List<TroopsAvgExpReportItemDTO> troops, Double highestAverageExperience, Double lowestAverageExperience) {
        this.troops = notNull(troops);
        this.highestAverageExperience = notNull(highestAverageExperience);
        this.lowestAverageExperience = notNull(lowestAverageExperience);
    }

    /**
     * ordered list of report items
     *
     * @return ordered list of report items ordered based on average experience level of heroes in troop
     */
    public List<TroopsAvgExpReportItemDTO> getReportItems() {
        return troops;
    }

    /**
     * highest average experience based on heroes in troops
     *
     * @return highest average experience of troop
     */
    public Double getHighestAverageExperience() {
        return highestAverageExperience;
    }

    /**
     * lowest average experience based on heroes in troops
     *
     * @return lowest average experience of troop
     */
    public Double getLowestAverageExperience() {
        return lowestAverageExperience;
    }
}
