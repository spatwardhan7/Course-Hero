Course Hero Readme: 

Please open this project in Android Studio and hit run. Nothing special is required to run this app.

Here are a few things which I had a great time working on: 
1) Figuring out the API Response : I spent a reasonable amount of time going through the sample query and response. I was trying to figure out the most efficient way to parse the response and with minimum calls. 

The sample query provided on the assignment is as follows : 
https://www.coursera.org/api/catalogResults.v2?q=search&query=machine%20learning&start=0&limit=5&fields=courseId,onDemandSpecializationId,courses.v1(name,photoUrl,partnerIds),onDemandSpecializations.v1(name,logo,courseIds,partnerIds),partners.v1(name)&includes=courseId,onDemandSpecializationId,courses.v1(partnerIds)

Upon running this query, I noticed that courses array in “linked” did not contain a description. 
I modified the query by adding description to the params to avoid another network call to https://api.coursera.org/api/courses.v1/[courseID]?fields=photoUrl,description just to get the description. 

2) Architecture for Catalog Element:  I observed that most of the parsing for courses and specialization is the same, besides course numbers for specializations and a difference in the key for the photo url. I decided to go with an abstract class since I wanted to apply common functionality to both of my subclasses. Also, dealing with an abstract class would make it much easier to pass to the list view adapter. 

Things I would have liked to do: 
1) Display Total number of results in list view header
2) Display empty set view for list
3) Fire off API calls onTextChanged on search box instead of a search button
4) Display more details on details page

Limitations: 
1) Currently, search activity is limited to portrait mode only. Handling config changes would have added some more time. 
2) No checks being made before making network calls. 
