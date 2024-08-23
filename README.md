# InPost Recruitment Task

This repo contains the solution for the InPost Recruitment Task.

## Architecture

This project is built using the MVI architecture pattern within the framework of Clean Architecture.

Clean Architecture is a software design philosophy that organizes software into distinct,
independent layers. The key objective is to create a flexible and maintainable codebase by clearly
defining the dependencies between these layers.

Each feature module in this project is divided into three layers:

Presentation: Handles the user interface, displaying data to the user and managing user input.
Domain: Contains the core business logic of the application.
Data: Manages data retrieval, whether from a network or a local database.
Given that the UI is built with Jetpack Compose, the MVI (Model-View-Intent) architecture pattern is
used to manage state and events effectively.

## Modularization

The modularization strategy is domain-based, with each domain divided into two modules:

- **api**
- **implementation**

This approach enhances separation of concerns and improves maintainability. By utilizing an `api`
module, the `implementation` module remains independent of other implementation modules. All public
APIs are defined within the `api` module, helping to prevent cyclic dependencies in the codebase.

```text
                                                                                    
                ┌────────────────┐                                                  
                │                │                                                  
                │  :feature:api  │◄──────────────────────────────────┐              
                │                │                                   │              
                └────────────────┘                                   │              
                        ▲                                            │              
                        │                                            │              
                        │                                            │              
                        │                                            │              
           ┌────────────┴─────────────┐                   ┌──────────┴───────┐      
           │                          │                   │                  │      
           │  :feature:implementation │                   │  :feature2:impl  │      
           │                          │                   │                  │      
           └──────────────────────────┘                   └──────────────────┘      
                                                                                                                        

```

# Remark

Manual archiving of a shipment is implemented by pressing "more button" on the shipment item.

# Task

## Intro

We travel back in time ⏱️. InPost Mobile app was just created and you join the team to improve its
feature set and make it ready for the future.
User base is growing fast and every day more people start to use it daily.

You, as an experienced developer, were assigned to the project to improve its quality. The initial
code is not perfect and is far from being.
Organize and refactor code the way you like to work (packages, modules, layers, data flow, names,
methods order etc.).

## Rules

- You can change and move any part you like (except JSON file), install any open source library you
  want
- A static JSON file is returned in response, **consider this is a real production environment**
  returning your data
- JSON file cannot be changed
- Git history is also important
- Feel free to comment your choices

## Tasks

1. Add grouping to the list of Shipments by flag **ShipmentNetwork.operations.highlight**
2. Style list items as in Figma (link: https://www.figma.com/file/E7vZMYESnKvmzn70FenrhP/InPost)
3. Sort list items in groups by (the closest date to current date should be at top of the list):
    * status - order is described in `ShipmentStatus.kt` file (the higher order, the higher it
      should be on the list)
    * pickupDate
    * expireDate
    * storedDate
    * number
4. Add pull to refresh and handle refresh progress
5. Add storing shipments locally (use Room)
6. Add local archiving of the shipment:
    * We consider archiving as hiding the shipment from the list of `Shipment`s
    * Design is not important here
    * `Shipment` must stay hidden after re-downloading data or relaunching the app
    * Use flag **ShipmentNetwork.operations.manualArchive**
7. Create unit tests

## Links and resources

- Fonts folder: [/app/src/main/res/font](./app/src/main/res/font)

If for some reason Figma link stops working, here you can see the requested design:
![Design from Figma](./images/Figma.png)

# Good luck! 💪
