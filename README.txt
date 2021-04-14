Assumptions

Assuming that in tripsData.txt (taps.csv in your case) there will be at max only 2 records with same customer ID.

Sample taps
ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN
1,22-01-2018 13:00:00,ON,1,Company1,Bus37,5500005555555559		<-59
2,22-01-2018 13:05:00,OFF,2,Company1,Bus37,5500005555555559		<-59	No more 59 after this
3,22-01-2018 13:00:00,ON,2,Company1,Bus37,5500005555555558		<-58
4,22-01-2018 13:05:00,OFF,1,Company1,Bus37,5500005555555558		<-58	No more 58 after this
5,22-01-2018 13:00:00,ON,2,Company1,Bus37,5500005555555560		<-60	Can be 1 more 60 after this
6,22-01-2018 13:05:00,ON,3,Company1,Bus37,5500005555555561		<-61	Can be 1 more 61 after this

The records in tap.csv occure as and when they occure. i.e. ON record will appear first followed by OFF record.

In case of incomplete trips
	I am assuming that taps.csv will only have not corressponding OFF record.
	So if customer forgets to tap off, there is only 1 ON record with that customerID, busID combination

In case of cancelled trips
	I am assuming that if tap ON and OFF occures at the same location, then it is cancelled. I am not taking account the duration of trip in this case.

There are no spaces between fields and , in the travelRoutes.txt and tripsData.txt files
tripsData.txt (taps.csv in your case) is complete and every line item is having min of 7 records.
tripsData.txt (taps.csv in your case) file does not have any stop id which is not in travelRoutes.txt file


Running the application
	
	1.  import project into IDE of your choice
	
	2.  Create or override the "path_to_application_folder/tripsData.txt" file with your file. (taps in your case)
		"," seperated file and no spaces between fields and "," and "\n" for new line and WITH HEADER
	
	3.  Create or override the "path_to_application_folder/travelRoutes.txt" file for stops and prices
		"," seperated file and no spaces between fields and "," and "\n" for new line and WIHTOUT HEADER
	
	4.  run Main.java
	
	5.  trips will be outputed to output.csv file
	
	
Testing
	1.  Requires junit 4
	2.  Run the BusinessHandlerTests.java file (Total 6 test cases)	
