# A Contextual Model for Smartphone Applications

Project code for MSc by Research in Computer Science (awarded in September 2015).

[**Link to Thesis**](http://library.ucc.ie/record=b2144610)

Published journal paper: [Harshvardhan Jitendra Pandit , Adrian O'Riordan , (2016) "**A Model for Contextual Data Sharing in Smartphone Applications**", International Journal of Pervasive Computing and Communications, Vol. 12 Iss: 3, pp. -](http://www.emeraldinsight.com/doi/abs/10.1108/IJPCC-06-2016-0030)



## Abstract (thesis)

The advent of smartphones as a computing device has resulted in a shift in focus towardsthe design and development of smartphone applications or apps, that allow the user tocomplete a wide range of tasks on their devices. The users depend on apps installed ontheir smartphones to access services such as emails, photos, music, browsing, messagingand telephony. However, the overall user experience is disjointed as users are requiredto use multiple apps to complete a task where each app requires the user to enter thesame information as the apps cannot share the data contextually.

This (research) investigates how smartphone apps can perform contextual data sharingwith an emphasis on practical integration into the existing platforms and app models.The identification of information and its associated context is necessary to create con-text definitions that allow different apps to identify the context of the shared data. Anapproach to model the Context Definitions using computer science concepts such asobject-oriented data structures provides flexibility. A context datastore is defined tostore and share contextual information between apps, which creates an independencebetween apps for acquiring information and provides compatibility with the existingsecurity models on various platforms. The model allows apps to retrieve contextualdata in a simple and efficient manner without interacting directly with the other apps.

This (research) explains the author’s hypothesis of creating contextual services in appsbased on the availability of contextual information on a smartphone device. An implementation of the model proving the hypothesis is presented on Android using nativetools and technologies available on the platform. The demonstration aims to show theviability of the model through use cases, evaluations and performance metrics.

Finally, the author provides recommendation for developers in adoption of the model,and the efforts required to integrate the implementation into existing platforms andapps. Further research avenues are identified that define the future of research in this area.

## Conclusion (thesis)

The Contextual Data Sharing Model allows smartphone applications to utilize andshare contextual information. The Context Definitions provide a uniform structureto the contextual information, which can be stored and shared through the ContextDatabase. Apps query the Context Database to retrieve contextual information whichsaves the user the effort of entering or finding related information in multiple apps usedwithin the same context.

An implementation of the model on Android is used to demonstrate the ContextualData Sharing Model. It uses Java classes to represent Context Definitions, which arethen instantiated as Java objects and provide a uniform representation of contextualinformation across apps and devices. The Context Database uses Android’s ContentProvider interface with SQLite as the storage backend for context entries. The Con-text Manager acts as a middleware between the apps and the Context Database, andis implemented as a static Java class instantiated in the app’s process. The ContextDefinitions and the Context Manager classes are bundled together into a library whichthe developers can include in their project to use contexts and interact with the Con-text Database. Concerns and considerations related to the security permissions andperformance of the model are discussed for an Android implementation.

The time required to complete various database operations and its relation to thesize of the database is analyzed to identify its impact on performance and usability inthe implementation. Conclusions regarding optimization of performance of the queriesare also discussed. The impact of running operations on the device was analyzed andfound to present no hindrance to the running of other apps on the device.

The demonstration of the movie ticket booking use case shows how apps utilizethe Contextual Data Sharing Model to access information which otherwise would havebeen entered by the user. The resulting user experience reduces user effort and providesrelevant information and services through recommendations and suggestions in the app.This allows the user to complete their tasks faster and access relevant informationwithout performing additional steps. The availability of contextual information toapps offers an opportunity to design new features and services that were not previouslypossible.

The main goal of this research is to enable apps to use the information generatedand stored on a device and create contextual services using this information. Apps canpresent users with services they are most likely require, which saves the efforts relatedto entering information multiple times across several apps. This leads to better featuresand an improved user experience due to the availability of contextual information acrossapps.



## Source code

### Description

The project is an Android implementation of the **Contextual Model** as described in the thesis and paper. 

Each folder / app is an independent android build (included).

#### Context Provider

The main code and classes for the Context Provider are located in the `Context Provider` folder. The namespace used is `msc.prototype.context` for the context manager and classes.

#### Apps

Apps are used to demonstrate the usability and features of the context model. The following apps have been shared in their entirety:

-   Maps
-   Messaging
-   Movie Booking
-   Notification service

### Build

Android Studio was used to generate the builds (using gradle) and the project can be imported in to Eclipse (as any other existing Android Project).