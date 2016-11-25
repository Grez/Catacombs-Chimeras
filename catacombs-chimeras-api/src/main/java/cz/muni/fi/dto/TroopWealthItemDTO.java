/**
 * @author David Osicka
 */
package cz.muni.fi.dto;

import static org.apache.commons.lang3.Validate.notNull;


public class TroopWealthItemDTO {
    
    private final TroopDTO troopDTO;
    private final Double moneyPerHero;

    public TroopWealthItemDTO(final TroopDTO troopDTO, final Double moneyPerHero) {
        notNull(troopDTO);
        notNull(moneyPerHero);
        this.troopDTO = troopDTO;
        this.moneyPerHero = moneyPerHero;
    }

    public TroopDTO getTroopDTO() {
        return troopDTO;
    }

    /**
     *
     * @return returns how much money the troop has divided by number of heroes in troop {@link TroopsWealthItemDTO#troopDTO}
     */
    public Double getMoneyPerHero() {
        return moneyPerHero;
    }
}
