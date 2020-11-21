# ACHOO!
Josefine Tijssen, Andrew Cervantes, Jonathan Vithoontien

# Engineering Notebook
## Week 8 11/20/2020
### Accomplishments:
   - Met with Ming to discuss project direction now that we have established that we might not be able to use the Google contact-tracing API.
   - Researched the availability of the Nearby Messages API. This API is a publish-subscribe API that enables the exchange of data between devices which are in close proximity of one another by passing small binary payloads between internet-connected Android and iOS devices.
      - In order to access the API, we need to register the project with the Google Developers Console.
      - The Nearby Messages API requires user consent. When either publish or subscribe is first invoked, the API will show an opt in dialog.
      - https://developers.google.com/nearby/developer-guidelines
   - Creation of detailed document outlining pro's and cons of the Nearby Messages API.
### Failures
   - Due to the ambiguity surrounding the API, we only recently discovered that the exposure API is not public and Exposure API is blocked to public. 
      - Usage of the API requires an extensive application affiliated with a health authority (as we established last week). However, getting this approval is more difficult than we initially anticipated.
### Goals for next week:
   - Develop a simple application that uses the Nearby Messages API.
### Key Decisions to be Made:
   - Hows will we faciliate the actual contact-tracing without the Exposure API?
### Challenges
   - Creating a new design document that does not use the Exposure API.
   - Facilitating the contact-tracing element of the application without using the Google contact-tracing API.

## Week 7 11/13/2020
### Accomplishments:
   - Reworked template app to push notifications correctly.
   - Investigated app distribution hurdles and regulations
   - Currently supports bluetooth activation/deactivation.
   - We have fully outlined all minimum device requirements to maximize device coverage but also support all of our required features
### Failures
   - Apple and Google have made strict regulations about contact tracing apps on their eco systems
      - The app can still be developed, but we will need a medical endorsement for it to go live
   - Because of challenges around creating our verification server, our early prototype will just deal with pushing notificiations based on proximity rather than a comprehensive medical check-in.
### Goals for next week:
   - Complete the rest of the app prototype to generate anonymous IDs for each device when near each other.
   - Secure android devices to test these features.
### Key Decisions to be Made:
   - Our final end game? Do we want this app to go live or just act a launchpad for others to build their products?
   - What is the most efficient way for us to get the mobile app to push proximity data to our server?
      - How often should we push? On wifi or cellular or both?
### Challenges
   - How we will do real world testing with the API?
   - Will sideloading the app be enough to use the google play service component?
   
## Week 6 11/6/2020
### Accomplishments:
   - Deployed first template app onto github
   - Completed design doc overviewing each piece we have to implement
   - Developed roadmap for what order we will tackle each piece of the app.
   - Determined testing strategies that we can do locally on devices.
      - No plans for automated testing yet.
### Failures
   - Found out that there now two major servers we will have to deploy ourselves: Key server and Verification Code Server
   - We now have to implement a two factor authentication system that is administered by a health authority
      - This is done to verify the legitimacy of a COVID-19 report.
   - Due to scope, we likely won't be able to implement every verification option that Google outlines.
   - Barebones app is not complete due to hang ups with the health authority component.
      - Have to also add a verification page for health authorities.
### Challenges:
   - How we will implement the verification code and long-term token verification
   - Best method of distributing the verification codes to users.
### Goals for next week:
   - Continue developing the app to meet all minimum user requirements.
   - Tinkering with servers to get our key and verification servers ready.
### Key Decisions to be Made:
   - What parts of the two factor (if any) will we want to have in the tech demo?
      - We want to have a usage loop shown for the demo at least.

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
