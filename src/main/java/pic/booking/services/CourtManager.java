package pic.booking.services;

import pic.booking.implementClass.Court;

import java.util.HashMap;
import java.util.Map;

public class CourtManager {
    private Map<Long, Court> courts = new HashMap<>();

    public void addCourt(Court court) {
        courts.put(court.getId(), court);
    }

    public Court getCourtById(Long id) {
        return courts.get(id);
    }

    public Map<Long, Court> getAllCourts() {
        return courts;
    }
}
