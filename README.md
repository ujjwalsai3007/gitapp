**GitApp**

GitApp is an Android application built with Kotlin that integrates the GitHub API to fetch and display repository details. It uses MVVM architecture and supports offline storage with Room Database.

**Features**
- Search Repositories: Fetch GitHub repositories by username.
- Repository Details: View repository info (stars, forks, issues, etc.).
- Offline Access: Save repositories locally using Room Database.


**Tech Stack**
- Kotlin 	
- MVVM Architecture 	
- **Retrofit:** For API integration. 	
- **Room Database:** For offline storage  
- **RecyclerView:** For list display. 	
- **Firebase Hosting:** Optional backend support.


**Deployment**
- **Netlify** : https://earnest-duckanoo-b1f9ac.netlify.app/
- **APK Download** : https://drive.google.com/file/d/1NxqMH9qJxtJCZOuybvM2XT_SKKkAf_8_/view?usp=drive_link


**Video Demo**

Watch the project explanation video [here](https://drive.google.com/file/d/1eKnKN87OLTOSG-h3k6BvzLiih0-KdObr/view?usp=sharing)


**ANSWERS**

**1.Explain the functionality**
GitApp allows users to search for GitHub repositories by entering a username. It fetches repository data using the GitHub API and displays details like stars, forks, and issues in a user-friendly interface. The app also supports offline storage, enabling users to view previously fetched repositories without an active internet connection.

**2.What Components are used in the project?**
Key components used include RecyclerView for displaying repository lists, ViewModel for managing UI-related data, and Room Database for offline data storage. Retrofit is used for API communication, and LiveData ensures the UI is updated with the latest data seamlessly.

**3.What architecture did you use and why do you think it’s best suitable?**
The project uses MVVM (Model-View-ViewModel) architecture because it separates UI logic from business logic, making the code modular and easy to maintain. MVVM also supports better testability and ensures that LiveData provides reactive updates between ViewModel and UI components.

**4.How do you ensure that code is testable?**
The project ensures testability by following the MVVM architecture, decoupling the View and Model layers. Dependency injection is used for testing components in isolation, and the use of ViewModel and Repository patterns ensures that business logic can be tested independently of the UI.







