# Project <span style="color: #A83332;">BLAIR</span>
![BLAIR Logo](BLAIR/src/main/resources/media/BLAIR.png)

**<span style="color: #A83332;">BLAIR</span>** stands for **Better LMS And Instructional Resources**. This project is an enhanced version of the school's LAIR system, featuring a more visually appealing user interface and additional functionalities to improve the learning experience for both students and instructors.

## <br> <span style="color: #B4B4B4;">Table of Contents</span>
- [Project Description](#project-description)
- [Technologies Used](#technologies-used)
- [Design Patterns](#design-patterns)
- [Setup Instructions](#setup-instructions)

## <br> <span style="color: #B4B4B4;">Project Description</span>
BLAIR aims to provide a comprehensive Learning Management System (LMS) that simplifies the management of instructional resources. With a focus on user experience, BLAIR offers a modern interface and a suite of tools designed to facilitate learning and teaching. The system allows users to easily navigate through resources, manage courses, and access instructional materials.

## <br> <span style="color: #FFC107;">Technologies Used</span>
- **JavaFX**: The primary framework used for building the graphical user interface (GUI).
- **SceneBuilder**: A visual layout tool for designing JavaFX user interfaces.
- **GSON**: A library for converting Java objects into their JSON representation and vice versa, used for database management.

## <br> <span style="color: #FFC107;">Design Patterns</span>
In the development of BLAIR, we plan to implement the following design patterns to enhance code organization and maintainability:

_To be implemented..._
- **Singleton**: This pattern can be used for managing shared resources, such as database connections, ensuring that only one instance of a resource is created and used throughout the application.
- **Factory chu chu**: Lorem ipsum

## <br> <span style="color: #A83332;">Setup Instructions</span>
To set up the BLAIR project on your local machine, follow these steps:

1. **Download JavaFX**:
    - Visit the [JavaFX download page](https://openjfx.io/) and download the latest version of the JavaFX SDK.
    - Extract the downloaded SDK to a directory of your choice (e.g., `C:\javafx-sdk`).

2. **Add JavaFX to Your Project**:
    - Open your project in your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
    - Add the JavaFX library to your project:
        - In IntelliJ IDEA: Go to `File` > `Project Structure` > `Libraries` > `Add` > `Java` and select the `lib` folder from the extracted JavaFX SDK.
        - In Eclipse: Right-click on your project > `Build Path` > `Configure Build Path` > `Libraries` > `Add External JARs` and select the JAR files from the `lib` folder.

3. **Set Up Run Configuration**:
    - Configure your run settings to include the JavaFX modules:
        - In IntelliJ IDEA: Go to `Run` > `Edit Configurations` > `VM options` and add the following:
          ```
          --module-path "${PATH-TO-JAVAFX};mods/production" --add-modules javafx.controls,javafx.fxml
          ```
        - In Eclipse: Right-click on your project > `Run As` > `Run Configurations` > `Arguments` tab > `VM arguments` and add the same options.

4. **Run the Project**:
    - After setting up the project, you can run it using your IDE's run configuration.

<br><br>_This is an ongoing and unfinished project._