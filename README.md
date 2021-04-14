# TripChargingProblem-LittlePay
Given the taps and travel routes information, create the trips and determine how much to charge for the trip based on whether it was complete, incomplete, or cancelled.

### Key Demonstrations
  - Graph implementation using adjacency list.
  - Reading and writting from and to .csv files
  - String manipulation using StringBuilder class
  - Clean and readable code under pressure.
  - Unit test cases for validating the correctness of solution
  
### Problem Description
  #### Sample Travel Routes 
  Prices apply for travel in either direction 
  (e.g. a passenger can tap on at stop 1 and tap off at stop 2, or they can tap on at stop 2 and can tap off at stop 1. 
  In either case, they would be charged $3.25).

```
1,2,3.50
2,3,5.50
1,3,7.30
```
        
### Trip Types

- Completed Trips

  If a passenger taps on at one stop and taps off at another stop, this is called a complete trip.
  The amount the passenger will be charged for the trip will be determined based on the table above.
  For example, if a passenger taps on at stop 3 and taps off at stop 1, they will be charged $7.30.

- Incomplete Trips

  If a passenger taps on at one stop but forgets to tap off at another stop, this is called an incomplete trip. 
  The passenger will be charged the maximum amount for a trip from that stop to any other stop they could have travelled to. 
  For example, if a passenger taps on at Stop 2, but does not tap off, they could potentially have travelled to either stop 1 ($3.25) or stop 3 ($5.50), 
  so they will be charged the higher value of $5.50.

- Cancelled Trips

  If a passenger taps on and then taps off again at the same bus stop, this is called a cancelled trip. The
  passenger will not be charged for the trip.
  
### The problem
  
#### taps.csv [input file]

```
ID, DateTimeUTC, TapType, StopId, CompanyId, BusID, PAN
1, 22-01-2018 13:00:00, ON, Stop1, Company1, Bus37, 5500005555555559
2, 22-01-2018 13:05:00, OFF, Stop2, Company1, Bus37, 5500005555555559
```

#### trips.csv [output file]

```
Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status
22-01-2018 13:00:00, 22-01-2018 13:05:00, 900, Stop1, Stop2, $3.25, Company1, B37, 5500005555555559, COMPLETED
```

