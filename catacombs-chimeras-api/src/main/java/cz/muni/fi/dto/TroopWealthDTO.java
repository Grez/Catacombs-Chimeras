/**
 * @author David Osicka
 */
package cz.muni.fi.dto;

import java.util.List;
import static org.apache.commons.lang3.Validate.notNull;

public class TroopWealthDTO {
    
    private final List<TroopWealthItemDTO> troops;
    
    public TroopWealthDTO(final List<TroopWealthItemDTO> troopWealthList){
        notNull(troopWealthList);
        this.troops = troopWealthList;
    }
    
    /**
     * List of troops with its money amount per hero in the troop,
     * The list is ordered by the amount of money per hero.
     * @return ordered list of TroopWealthItemDTO
     */
    public List<TroopWealthItemDTO> getTroopWealthList() {
        return troops;
    }
    
    public int getNumberOfTroops() {
        return troops.size();
    }
    
    public Double getMaximumMoneyPerHero() {
        if(!troops.isEmpty()) {
            return troops.get(troops.size()-1).getMoneyPerHero();
        } else {
            return new Double(0.0);
        }
    }
    
    public Double getMinimumMoneyPerHero() {
        if(!troops.isEmpty()) {
            return troops.get(0).getMoneyPerHero();
        } else {
            return new Double(0.0);
        }
    }
}
