
package ut.edu.pickleball_booking.dto.request;

import jakarta.persistence.*;

public class TimeSlotDtoRequest {
    private String startTime;
    private String endTime;
    private Long courtId;

    // Getter and Setter for startTime
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    // Getter and Setter for endTime
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    // Getter and Setter for courtId
    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }
}
