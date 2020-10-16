# Achoo (Soon to be changed)
Josefine Tijssen, Andrew Cervantes, Jonathan Vithoontien

# Engineering Notebook

## Week 3 10/16/2020
### Accomplishments:
    - Decided on using Android studio
    - Android Studio Hello World application is underway
    - Confirmation that SQL will work for the database structure
    - Potential storage method/real time query options discussed
    
### Failures

### Challenges
- Learning Java (Andrew the only one with some experience)
- Use cases 
    - Do we still want to do QR code scanning?
            - Opens possibility for real time capacity checking (confirmed this is an easy implementation with Prof. Couch
    - How to implement the tap feature
- Determinining how much the APIs do
        - This is in progress, will hopefully have a full understanding by 10/23
- Determining how to keep data privacy
    - 1 Potential solution:
            Reference all users by a randomized hash, and only deal with the hashes in the database. Then, when two hashes interact, and one is infected, the other will be notified.
### Goals for next week
- Finalize what the API does
- Begin a database design working with prof. Couch
- Begin a front end structure / design (how should it look, what actions should it do, how many pages do we need.
- Figuring out access (Do we need location, or access to anything from the user?)
### Key Decisions
- Any use case besides tracing
- How much backend is on us vs the APIs
- A new name (we kinda like Achoo!)

## Week 2 10/9/2020
### Accomplishments:
- Finished research on which databases to use. We feel SQL should be able to be used for all DBs as long as there is not a social media aspect.
- Learned about the intricacies and regulations with using personal data.
- Learned about iOS and Android APIs for mobile applications.
### Failures:
- Realized that making our project based on Tufts infrastructure is not viable due to time constraints and dealing with bureaucracy surrounding the university administration. This forced us to overhaul our design.
- Didn't set up AWS stack because of shifted project focus to a strictly mobile application.
    - We will have to determine if AWS will even be necessary.
### Challenges:
- Learning new primary language for apps (Swift for iOS/Java for Android).
- Figuring out other use cases for the application.
- Determining how much data we must store to use Android/Apple exposure APIs.
### Goals for Next Week:
- Redesign user experience of JumboScan to fit a wider scope.
    - This will include a change of the name.
- Research both iOS and Android exposure APIs and choose which to move foward with (iOS/android).
    - This decision will be made early next week.
    - Apple exposure notification framework: https://developer.apple.com/documentation/exposurenotification
    - Google exposure notifications API: https://developers.google.com/android/exposure-notifications/exposure-notifications-api
- Start experimenting with mobile app development.
    - Create a "hello world" starter app on each smartphone platform.
- Create an outline on how user privacy will be maintained while contact tracing. 
### Key Decisions to be Made:
- Android vs iOS.
- A new name for the project.
- Will there be an added purpose/use outside of contact tracing? Based on where/who our user base is, this might be a necessary incentive for people to accept the app tracing their location. 
    - Social media.
    - Game.
- What backend infrastructure (if any) will we need?
    
    
## Week 1 10/2/2020
### Accomplishments:
- Establish our high-level project architecture
- Decide technology choices for front end web application
  - HTML, CSS, JavaScript
- Decide to use AWS for our hosting and deployment
### Goals for Next Week:
- Configure our AWS stack
- Research edge cases
- Investigate whether MongoDB is viable on AWS
- Design our general backend infrastructure
  - Python back end with MongoDB/Dynamo database
### Key Decisions to be made:
- How our data will be formatted
- Web authentication strategy
- Handling cases where people forget to check out of a building.
