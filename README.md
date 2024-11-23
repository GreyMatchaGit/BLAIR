# Project BLAIR
![BLAIR Logo](BLAIR/src/main/resources/media/BLAIR.png)

**BLAIR** stands for **Better LMS And Instructional Resources**. This project is an enhanced version of the school's LAIR system, featuring a more visually appealing user interface and additional functionalities to improve the learning experience for both students and instructors.

## Table of Contents
- [Project Description](#project-description)
- [Technologies Used](#technologies-used)
- [Design Patterns](#design-patterns)
- [Setup Instructions](#setup-instructions)

## Project Description
BLAIR aims to provide a comprehensive Learning Management System (LMS) that simplifies the management of instructional resources. With a focus on user experience, BLAIR offers a modern interface and a suite of tools designed to facilitate learning and teaching. The system allows users to easily navigate through resources, manage courses, and access instructional materials.

## Technologies Used
- **JavaFX**: The primary framework used for building the graphical user interface (GUI).
- **SceneBuilder**: A visual layout tool for designing JavaFX user interfaces.
- **GSON**: A library for converting Java objects into their JSON representation and vice versa, used for database management.

## Design Patterns
In the development of BLAIR, we plan to implement the following design patterns to enhance code organization and maintainability:

_To be implemented..._
- **Singleton**: Yappity yap yap
- **Factory chu chu**: Lorem ipsum

## Setup Instructions
To set up the BLAIR project on your local machine, follow these steps:

1. **Download JavaFX**:
    - Visit the [JavaFX download page](https://gluonhq.com/products/javafx/) and download the latest version of the JavaFX SDK.
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

_This is an ongoing and unfinished project._