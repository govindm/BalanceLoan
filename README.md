# BalanceLoan

#### Steps run the application
      
      1. Install gradle. https://gradle.org/install/ for more details
      2. Install Need JDK 1.8+
      3. do git pull
      4. cd to  BalanceLoan and do ./gradlew 
      5. to run the app  ./gradw run
      6. The output files assignments.csv & yield.csv are created in the BalanceLoan folder 
      
### Follow-up questions

      1. How long did you spend working on the problem? What did you find to be the most
      difficult part?
     
          In total it took me about 4 hrs. The problem statement was clearly laid out except I had to make a few assumptions when analysing the input data. 
              
      
      2. How would you modify your data model or code to account for an eventual introduction
      of new, as-of-yet unknown types of covenants, beyond just maximum default likelihood
      and state restrictions?
      
         Assuming that it is going to be a complex requirements to satisfy both domestic and international bank facilities requirements, 
         we could build a rule engine that supports configurable rules. 
      
      3. How would you architect your solution as a production service wherein new facilities can
      be introduced at arbitrary points in time. Assume these facilities become available by the
      finance team emailing your team and describing the addition with a new set of CSVs.
      	
        a) Create a job that retrieves these files, validates and then copies them over to a predefined location/bucket. 
        b) Implement a polling job that can load these data and update the facility data and the cache accordingly and notify the finance team of the status.
        c) If the volume of the data is substantial then we can introduce a pub/sub system to process this data.
      
     
      4. Your solution most likely simulates the streaming process by directly calling a method in
      your code to process the loans inside of a for loop. What would a REST API look like for
      this same service? Stakeholders using the API will need, at a minimum, to be able to
      request a loan be assigned to a facility, and read the funding status of a loan, as well as
      query the capacities remaining in facilities.
      
                    Assigning loan:

                      POST /api/v1/loans  
                      {
                        "id": 123,
                        "interest_rate" : 0.09,
                        "amount": 3333444,
                        "default_likelihood": 0.12,
                        "state": "CA"
                      }

                      Response:
                      {
                         "status" : "assigned",
                         "loan_id": 123,
                         "facility_id" : 12,
                         "interest_rate" : 0.09		
                      }

                  Status a loan:

                        GET /api/v1/loans/<loan_id>

                  Response
                  {
                      "status" : “assigned”,
                      "loan_id": 123,
                      "facility_id" : 12,
                      "interest_rate" : 0.09,
                      "amount": 3333444,
                      "default_likelihood": 0.12,
                      "state": "CA"
                   }

                 Facilities APIs:

                  GET /api/v1/facilities/<facility_id>
                  GET /api/v1/facilities?bank_id=<bank_id> or
                  GET /api/v1/banks/<bank_id>/facilities	

      5. How might you improve your assignment algorithm if you were permitted to assign loans
      in batch rather than streaming? We are not looking for code here, but pseudo code or
      description of a revised algorithm appreciated.
	
	        To be honest, I haven’t spent much time on this but I would be implementing something similar to minimum cost flow algorithm.
      
      6. Discuss your solution’s runtime complexity.
    	
            Runtime complexity for assigning a loan to a facility.
              N - number of facilities

          Filter out all the facilities that fails to satisfy the covenants  - O(n)
          Sort the facilities based on the interested and amount  - O(nlogn)

           Hence   O(n + nlogn) -> O(nlogn)

          Time complexity to assign a loan to a facility : O(nlogn) 
