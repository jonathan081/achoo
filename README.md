# ACHOO!
Josefine Tijssen, Andrew Cervantes, Jonathan Vithoontien

# Engineering Notebook

## Week 5 10/30/2020
### Accomplishments:
   - We have decided our use case for the app.
   - Coding is underway!
   - We have established how many app pages we need and what we want to do in terms of color scheme.
### Failures
   - We were unable to meet with Alva to talk about our new database requirements. This is one of the goals for this week.
   - We could not find any known test methods for this API.
### Challenges:
   - How is this API to work with? Is there a public app/code that utilizes the contact tracing API that we can use as an example?
### Goals for next week:
   - Start working on connecting the API to the app.
   - Create the barebones of an app in order to connect with the API.
   - Establish login techniques.
   - Working on the API's (designated out to 1 per person).
### Key Decisions to be Made:
   - How are we gonna test our API?
   - Database requirements. 
   
## Week 4 10/23/2020
### Accomplishments:
   - ACHOO! is the final project name.
   - Further narrowed the end user experience for contact tracing.
      - API handles all the tracking for us.
      - No need for QR/NFC transactions.
   - Determined privacy strategy: We don't deal with it, let Google handle it.
   - Finished our risk assessment document.
### Failures:
   - We underestimated how much the Google exposure API does for the developer already.
      - The contact-tracing is essentially fully implemented which defeated the purpose of our initial pitch.
      - In light of this, we are redirecting the project focus to documenting our development experience while also trying tackling the human factors challenge of getting users involved in this.
### Challenges:
   - Given that the contact tracing is done passively, what can we add to our app beyond what Google has already created?
   - How do we "gamify" meeting people in the context of social distancing?
   - This ties into the above point, but we essentially need to decide how we're going to get a user base.
      - Either need to socialize/gamify it if we want it in the United States in order for people to willingly share their information.
### Goals for Next Week:
   - Start implementing the exposure API into a mock Android app.
   - Discuss with sponsor and possibly reach out to TTS for option of working with Tufts (Thank you Ming!).
### Key Decisions to be Made:
   - What is the minimum we have to do to start interacting with the exposure API?
   - What will our testing methodology be to know that the contact-tracing is working correctly?
      
## Week 3 10/16/2020
### Accomplishments:
   - Decided on using Android studio
   - Android Studio Hello World application is underway
   - Finished most of and then discarded the iOS implementation of Hello World in Swift, as we will be creating an Android app.
   - Confirmation that SQL will work for the database structure
   - Potential storage method/real time query options discussed
   - Created document highlighting privacy concerns with Covid tracing and how to mitigate them. 
### Failures
   - Still don't know what database stores we will need/whether it will be fully supported by the Google.
       - We still can't determine what our overall architecture will be since we don't know our database solution
   - We weren't able to come up with a way to incentivize users to engage with our app that was meaningful.
       - We decided for scope that we would just strictly focus on the contact tracing component and come back to "gamifying" the user experience in the future.
### Challenges:
- Learning Java (Andrew is currrently the only one with some experience).
- Answering use case questions: 
    - Do we still want to do QR code scanning?
            - Opens possibility for real time capacity checking (confirmed this is an easy implementation with Prof. Couch).
    - How to implement the tap feature when people meet up.
- Determinining how much the APIs do.
            - This is in progress, we plan to have a better understanding by 10/23.
- Determining how to keep data privacy
    - Potential solution:
            - Reference all users by a randomized hash, and only deal with the hashes in the database. Then, when two hashes interact, and one is infected, the other will be notified.
### Goals for next week:
- Finalize what the API does.
- Finalize design diagram for the API.
- Begin a database design working with Prof. Couch
- Begin a front end structure / design (how should it look, what actions should it do, how many pages do we need.
- Figuring out access (Do we need location, or access to anything from the user?)
- Setting up local environments for everyone to work
### Key Decisions:
- Any use case besides tracing.
- How much backend is on us vs the APIs.
- A new name (we kinda like Achoo!).

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
