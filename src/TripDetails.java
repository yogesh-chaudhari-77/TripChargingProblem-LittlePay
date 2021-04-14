import java.util.Date;

/**
 * Class representing the trip details
 * @author Yogeshwar Chaudhari
 *
 */
public class TripDetails {
	long id;
	
	Date startDate = null;
	String startTapType = "";
	String startStopId = "";
	String startCompanyId = "";
	String startBusId = "";
	
	Date endDate = null;
	String endTapType = "";
	String endStopId = null;
	String endCompanyId = "";
	String endBusId = "";
	
	// Common for trip
	String pan = "";
	
	long tripDuration = 0;
	double chargeAmount = 0;
	String status = "";
	
	
	public TripDetails(long id, Date startDate, String startTapType, String startStopId, String startCompanyId,
			String startBusId, String pan) {
		super();
		this.id = id;
		this.startDate = startDate;
		this.startTapType = startTapType;
		this.startStopId = startStopId;
		this.startCompanyId = startCompanyId;
		this.startBusId = startBusId;
		this.pan = pan;
	}
	
	// Used to setTripEndDetails when matching OFF tap entry is found
	public void setTripEndDetails(Date endDate, String endTapType, String endStopId, String endCompanyId, String endBusId) {
		
		this.setEndDate(endDate);
		this.setEndTapType(endTapType);
		this.setEndStopId(endStopId);
		this.setEndCompanyId(endCompanyId);
		this.setEndBusId(endBusId);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	// Getter Setters Starts Here
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public String getStartTapType() {
		return startTapType;
	}
	public void setStartTapType(String startTapType) {
		this.startTapType = startTapType;
	}
	public String getStartStopId() {
		return startStopId;
	}
	public void setStartStopId(String startStopId) {
		this.startStopId = startStopId;
	}
	public String getStartCompanyId() {
		return startCompanyId;
	}
	public void setStartCompanyId(String startCompanyId) {
		this.startCompanyId = startCompanyId;
	}
	public String getStartBusId() {
		return startBusId;
	}
	public void setStartBusId(String startBusId) {
		this.startBusId = startBusId;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getEndTapType() {
		return endTapType;
	}
	public void setEndTapType(String endTapType) {
		this.endTapType = endTapType;
	}
	public String getEndStopId() {
		return endStopId;
	}
	public void setEndStopId(String endStopId) {
		this.endStopId = endStopId;
	}
	public String getEndCompanyId() {
		return endCompanyId;
	}
	public void setEndCompanyId(String endCompanyId) {
		this.endCompanyId = endCompanyId;
	}
	public String getEndBusId() {
		return endBusId;
	}
	public void setEndBusId(String endBusId) {
		this.endBusId = endBusId;
	}
	public String getPan() {
		return pan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public long getTripDuration() {
		return tripDuration;
	}
	public void setTripDuration(long tripDuration) {
		this.tripDuration = tripDuration;
	}
	public double getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	// Getter Setter Ends Here
}
