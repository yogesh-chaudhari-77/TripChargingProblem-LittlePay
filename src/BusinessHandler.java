import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

public class BusinessHandler {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	// Stores all the travel routes and the respective pricing - Undirected/Bi-directed wighted graph
	Graph travelRoutes = new Graph();
	
	// Taps data from input file
	List<String> rawTrips = new ArrayList<String>();
	
	//Created Trips object will be stored here 
	HashMap<String, TripDetails> trips = new HashMap<String, TripDetails>();


	public void run() throws ParseException, IOException {

		readTravelRoutes();
		readTripsData();
		processTripsData();
		
		for(TripDetails trip : this.getTrips().values())
			calculateCharge(trip);
		
		generateCSVFile();

	}


	/**
	 * Reads the travel routes and respective pricing from the input file located under /path_to_application_folder
	 * Records must be seperated by "," chatacter, having no spaces between 2 fields and two records are seperated by "\n"
	 */
	public void readTravelRoutes() {
		try {
			File fileRef = new File(System.getProperty("user.dir")+"/travelRoutes.txt");

			if(fileRef.isFile()) {

				FileReader fr = new FileReader(System.getProperty("user.dir")+"/travelRoutes.txt");
				BufferedReader br = new BufferedReader( fr );

				String lineItem;
				while ( (lineItem = br.readLine() ) != null) {
					System.out.println(lineItem);
					String items[] = lineItem.split(",");

					// Undirected weighted graph
					travelRoutes.addEdge(String.valueOf(items[0]), String.valueOf(items[1]), Double.parseDouble(items[2]));
					travelRoutes.addEdge(String.valueOf(items[1]), String.valueOf(items[0]), Double.parseDouble(items[2]));
				}

				fr.close();
				br.close();
			}

			System.out.println(travelRoutes);

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Read the trips Data from CSV file
	 * Records must be seperated by "," chatacter, having no spaces between 2 fields and two records are seperated by "\n"
	 */
	public void readTripsData() {

		try {
			File fileRef = new File(System.getProperty("user.dir")+"/tripsData.txt");

			if(fileRef.isFile()) {

				FileReader fr = new FileReader(System.getProperty("user.dir")+"/tripsData.txt");
				BufferedReader br = new BufferedReader( fr );

				String lineItem;
				lineItem = br.readLine(); 		// Excluding the header
				while ( (lineItem = br.readLine() ) != null) {
					rawTrips.add(lineItem);
				}

				fr.close();
				br.close();
			}


		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Iterate over the read taps data to determine trips
	 * @throws ParseException
	 */
	public void processTripsData() throws ParseException {

		for(String lineItem : this.getRawTrips()) {

			System.out.println(lineItem);
			createTrip(lineItem);
		}
	}



	/**
	 * Creates a partial trips or set the end trip information based on tap type
	 * @param lineItem : String : Single tap information
	 * @throws ParseException
	 */
	public void createTrip(String lineItem) throws ParseException {

		String tripDetails[] = lineItem.split(",");
		String tapType = tripDetails[2].trim().strip();
		
		// TripDetails should have 7 fields at minimum
		if(tripDetails.length < 7 ) {
			return;
		}

		if((tapType).equalsIgnoreCase("ON")) {

			Date startDate;
			startDate = dateFormat.parse(tripDetails[1]);
			String companyId = tripDetails[4];
			String busId = tripDetails[5];
			String customerId = tripDetails[6];

			TripDetails trip = new TripDetails(Long.parseLong( tripDetails[0]), startDate, tripDetails[2],tripDetails[3], companyId, busId, customerId);
			this.getTrips().put(customerId, trip);

		}else if ( (tapType).equalsIgnoreCase("OFF") ) {

			this.setTripEndDetails(tripDetails);
		}

	}

	
	/**
	 * Finds the trip object with specified customer pan, and updates the end trip details
	 * @param tripDetails : Array : Tap information with tap_type as "OFF"
	 * @throws ParseException
	 */
	public void setTripEndDetails(String [] tripDetails) throws ParseException {

		Date endDate;
		endDate = dateFormat.parse(tripDetails[1]);

		String endCompanyId = tripDetails[4];
		String endBusId = tripDetails[5].strip().trim();
		String customerId = tripDetails[6];

		if ( this.getTrips().containsKey(customerId) ) {

			TripDetails trip = this.getTrips().get(customerId);

			if( trip.getStartBusId().equalsIgnoreCase(endBusId) ) {

				trip.setTripEndDetails(endDate, tripDetails[2], tripDetails[3], endCompanyId, endBusId);
			}
		}
	}


	/**
	 * Determines the end status of the trip
	 * Status could be COMPLETED, CANCELLED, INCOMPLETE
	 * @param trip
	 */
	public void calculateCharge( TripDetails trip ) {

		double charge = 0;
		long tripDuration = 0;

		if(trip.getEndDate() != null) {
			long mili = trip.getEndDate().getTime() - trip.getStartDate().getTime();
			tripDuration = TimeUnit.MILLISECONDS.toSeconds(mili);
		}


		String status = "";

		System.out.println(trip.getStartBusId() +" --- "+trip.getEndBusId());
		if( trip.getEndBusId() != null && trip.getStartBusId().equalsIgnoreCase(trip.getEndBusId()) ) {

			// Same bus stop, means cancelled
			if (trip.getStartStopId().equalsIgnoreCase( trip.getEndStopId() )) {

				charge = getCancelledTripDetails(trip.getStartStopId(), trip.getEndStopId() );
				status = "CANCELLED";

			}else {

				charge = getCompleteTripCharge(trip.getStartStopId(), trip.getEndStopId() );
				status = "COMPLETED";
			}

		}else {

			String [] incompleteTripDetails = getIncompleteTripCharge(trip.getStartStopId() );

			charge = Double.parseDouble(incompleteTripDetails[1]);
			trip.setEndStopId(incompleteTripDetails[0]);
			status = "INCOMPLETED";


		}

		// Setting trip other details
		trip.setChargeAmount(charge);
		trip.setTripDuration(tripDuration);
		trip.setStatus(status);

	}

	/**
	 * 
	 * @param startPoint : String : Stop number from the travelRoutes
	 * @param endPoint : String : End stop number from the travelRoutes
	 * @return
	 */
	public double getCompleteTripCharge(String startPoint, String endPoint) {

		double charge = 0;
		if ( this.getTravelRoutes().getNodes().containsKey(startPoint) ) {

			if(this.getTravelRoutes().getNodes().get(startPoint).containsKey(endPoint)) {

				charge = this.getTravelRoutes().getNodes().get(startPoint).get(endPoint);

			}
		}

		return charge;
	}

	/**
	 * For extensibility purpose only
	 * @param startPoint
	 * @param endPoint
	 * @return
	 */
	public double getCancelledTripDetails(String startPoint, String endPoint) {
		return 0;
	}

	/**
	 * In case of incomplete trips, user will charged highest fair from starting point
	 * Find the highly weighted connected end point
	 * @param startPoint
	 * @return
	 */
	public String[] getIncompleteTripCharge(String startPoint){

		HashMap<String,Double> possibleRoutes = this.getTravelRoutes().getNodes().get(startPoint);
		double max = -1; 
		String possiblyTravelledTo = "";

		for(String destination : possibleRoutes.keySet()) {

			double price = possibleRoutes.get(destination);
			if(price > max) {
				max = price;
				possiblyTravelledTo = destination;
			}

		}

		String ret[] = {possiblyTravelledTo, String.valueOf(max)};
		return ret;

	}

	
	/**
	 * Generates output.csv file
	 * Header : Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status
	 * @throws IOException
	 */
	public void generateCSVFile() throws IOException{
		
		FileWriter fw = new FileWriter(System.getProperty("user.dir")+"/output.csv");
		fw.append("Started,Finished,DurationSecs,FromStopId,ToStopId,ChargeAmount,CompanyId,BusID,PAN,Status\n");
		
		
		System.out.println(this.getTrips().size());
		
		for(TripDetails trip : this.getTrips().values() ) {
			
			fw.append(String.valueOf(trip.getStartDate())).append(",");
			fw.append(String.valueOf(trip.getEndDate())).append(",");
			fw.append(String.valueOf(trip.getTripDuration())).append(",");
			fw.append(trip.getStartStopId()).append(",");
			fw.append(trip.getEndStopId()).append(",");
			fw.append(String.valueOf(trip.getChargeAmount()) ).append(","); 
			fw.append(trip.getStartCompanyId()).append(",");
			fw.append(trip.getStartBusId()).append(",");
			fw.append(trip.getPan()).append(",");
			fw.append(trip.getStatus());
			fw.append("\n");
		}

		fw.flush();
		fw.close();
	}

	
	/**
	 * Getter Setters Starts Here
	 */
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}


	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}


	public HashMap<String, TripDetails> getTrips() {
		return trips;
	}


	public void setTrips(HashMap<String, TripDetails> trips) {
		this.trips = trips;
	}

	public List<String> getRawTrips() {
		return rawTrips;
	}


	public void setRawTrips(List<String> rawTrips) {
		this.rawTrips = rawTrips;
	}

	public Graph getTravelRoutes() {
		return travelRoutes;
	}


	public void setTravelRoutes(Graph travelRoutes) {
		this.travelRoutes = travelRoutes;
	}

	/**
	 * Getter Setter ends here
	 */
}
