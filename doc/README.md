# **University Student Study Schedule Planner**

## **Project Specifications and Sample Output**

---

## **Project Overview**

The University Student Study Schedule Planner is a Java-based application designed to assist university students in organizing their weekly study schedules efficiently. The application accommodates enrollment in up to four units, each comprising lectures, practical sessions (SGTA/tutorial/workshop), and self-study periods. The planner ensures that each unit receives exactly 10 hours of study time per week by appropriately allocating time across different activities.

---

## **Functional Requirements**

### **1. Unit Enrollment**

- **Enrollment Capacity:**
  - Students can enroll in **0 to 4 units** per semester.

- **Unit Details Input:**
  - For each enrolled unit, students must specify:
    - **Unit Name** (e.g., COMP1010, MATH1007).
    - **Lecture Hours per Week:** Either **2 or 3 hours**.
    - **Practical Hours per Week:** **1, 2, or 3 hours**.

### **2. Time Allocation**

- **Total Weekly Hours:**
  - Each unit must have a total of **10 study hours per week**.

- **Self-Study Calculation:**
  - The application calculates **self-study hours** by subtracting lecture and practical hours from the total 10 hours.
  - **Self-study hours** are allocated in **1-hour increments**.

### **3. Self-Study Scheduling**

- **Preferred Scheduling:**
  - Students can specify **preferred days and time slots** for self-study sessions.

- **Conflict Avoidance:**
  - The system ensures no **scheduling conflicts** between lectures, practicals, and self-study sessions.

- **Even Distribution:**
  - Self-study hours are distributed **evenly across the selected days**, considering student preferences.

### **4. Schedule Output**

- **Generation of Schedule:**
  - The application generates a **comprehensive weekly schedule** encompassing all activities.

- **Output Format:**
  - The schedule is outputted in **CSV (Comma-Separated Values)** format for easy readability and compatibility with spreadsheet applications.

- **CSV Structure:**
  - The CSV file includes the following columns:
    - **Unit Name**
    - **Activity Type** (Lecture, Practical, Self-Study)
    - **Day**
    - **Start Time**
    - **End Time**

### **5. Schedule Saving**

- **Persistence:**
  - Generated schedules can be **saved locally** for future reference and modification.

- **File Management:**
  - Users can **load, update, and overwrite** existing schedule files as needed.

---

## **Non-Functional Requirements**

### **1. Persistence and Compatibility**

- **CSV Format:**
  - Schedules are saved in **CSV format**, ensuring compatibility with various applications like Microsoft Excel, Google Sheets, and LibreOffice Calc.

- **File Accessibility:**
  - Saved files are **easily accessible and transferable**, facilitating sharing and backup.

### **2. Performance and Scalability**

- **Efficient Processing:**
  - The application efficiently handles schedule generation and modifications with minimal latency.

- **Resource Management:**
  - Optimized to run on systems with **limited resources**, ensuring a broad user base.

### **3. Flexibility and Maintainability**

- **Dynamic Adjustments:**
  - Users can **modify unit details and preferences** at any time, with the system automatically updating the schedule accordingly.

- **Extensibility:**
  - The application is designed with a **modular structure**, allowing for easy addition of new features and updates.

### **4. Error Handling and Validation**

- **Input Validation:**
  - The system checks for **valid input values** (e.g., correct hour ranges, valid time slots).

- **Conflict Resolution:**
  - Provides **suggestions and alerts** when scheduling conflicts or invalid entries are detected.

- **User Feedback:**
  - Informative **error messages** guide users to correct input mistakes seamlessly.

### **5. Documentation and Usability**

- **Comprehensive Documentation:**
  - Includes **user guides and developer documentation** detailing application usage and code structure.

- **User-Friendly Interface:**
  - Although no GUI is required, the application ensures **clear and intuitive command-line interactions**.

---

## **Technical Specifications**

### **1. Programming Language and Environment**

- **Language:**
  - Developed using **Java SE 11 or higher**.

- **Development Tools:**
  - Utilizes **Maven or Gradle** for project management and dependency handling.
  - **JUnit** framework employed for testing purposes.

### **2. Data Structures**

- **Unit Representation:**
  - Each unit is represented as an **object** containing its name and allocated hours.

- **Schedule Management:**
  - **Collections** (e.g., Lists, Maps) are used to manage and organize schedule entries efficiently.

### **3. Algorithms and Logic**

- **Time Allocation Algorithm:**
  - Calculates self-study hours by:
    - Ensuring total hours equal 10 per unit.
    - Allocating self-study sessions based on user preferences and availability.

- **Scheduling Algorithm:**
  - Assigns activities to time slots by:
    - Checking for **availability and conflicts**.
    - Distributing self-study hours **evenly and logically** throughout the week.

- **Time Formatting:**
  - Handles time inputs and outputs in **standard 12-hour or 24-hour formats**, based on user preference.

### **4. Testing and Quality Assurance**

- **Unit Testing:**
  - Extensive tests cover:
    - **Time calculations** and allocations.
    - **Schedule generation** and conflict detection.
    - **File saving and loading** functionalities.

- **Integration Testing:**
  - Ensures all components **work seamlessly together** and meet performance benchmarks.

- **CSV Validation:**
  - Verifies that generated CSV files adhere to correct formatting and are **readable by standard spreadsheet software**.

### **5. Documentation**

- **Code Documentation:**
  - All classes and methods are well-documented using **Javadoc**, facilitating easy understanding and maintenance.

- **User Guide:**
  - Provides **step-by-step instructions** on using the application, including examples and troubleshooting tips.

- **Developer Guide:**
  - Details on the application's **architecture, design decisions, and contribution guidelines** for future development.

---

## **Project Milestones**

### **Milestone 1: Core Functionality Implementation**

- **Duration:** 2 Weeks
- **Deliverables:**
  - Basic unit enrollment and input validation.
  - Time allocation logic for lectures, practicals, and self-study.
  - Initial command-line interface for data input.

### **Milestone 2: Scheduling and Conflict Management**

- **Duration:** 3 Weeks
- **Deliverables:**
  - Development of scheduling algorithms to assign activities to appropriate time slots.
  - Conflict detection and resolution mechanisms.
  - Enhanced user interaction for specifying preferences.

### **Milestone 3: CSV Output and Persistence**

- **Duration:** 2 Weeks
- **Deliverables:**
  - Implementation of CSV generation for schedule output.
  - Functions to save and load schedules from CSV files.
  - Validation to ensure compatibility with spreadsheet applications.

### **Milestone 4: Testing and Optimization**

- **Duration:** 2 Weeks
- **Deliverables:**
  - Comprehensive unit and integration testing.
  - Performance optimization for quick schedule generation.
  - Refinement based on testing feedback and bug fixes.

### **Milestone 5: Documentation and Finalization**

- **Duration:** 1 Week
- **Deliverables:**
  - Completion of user and developer documentation.
  - Preparation of demonstration materials and presentation.
  - Final review and project submission.

---

## **Sample CSV Output**

Below is a sample study schedule generated by the application, demonstrating how the data is structured and presented in CSV format.

### **Sample Schedule for Enrolled Units**

#### **Enrolled Units:**
- **COMP1010**
- **MATH1007**
- **STAT1170**
- **ENGG1000**

```csv
Unit Name,Activity Type,Day,Start Time,End Time
COMP1010,Lecture,Monday,09:00 AM,11:00 AM
COMP1010,Practical,Wednesday,01:00 PM,02:00 PM
COMP1010,Self-Study,Thursday,10:00 AM,12:00 PM
COMP1010,Self-Study,Friday,03:00 PM,05:00 PM
MATH1007,Lecture,Tuesday,10:00 AM,12:00 PM
MATH1007,Practical,Thursday,02:00 PM,04:00 PM
MATH1007,Self-Study,Monday,02:00 PM,04:00 PM
MATH1007,Self-Study,Wednesday,03:00 PM,05:00 PM
STAT1170,Lecture,Wednesday,09:00 AM,11:00 AM
STAT1170,Practical,Friday,10:00 AM,12:00 PM
STAT1170,Self-Study,Tuesday,01:00 PM,03:00 PM
STAT1170,Self-Study,Thursday,04:00 PM,06:00 PM
ENGG1000,Lecture,Monday,11:00 AM,01:00 PM
ENGG1000,Practical,Tuesday,03:00 PM,04:00 PM
ENGG1000,Self-Study,Wednesday,11:00 AM,01:00 PM
ENGG1000,Self-Study,Friday,01:00 PM,03:00 PM
```

### **Explanation of Schedule:**

- **COMP1010:**
  - **Lecture:** 2 hours on Monday from 9:00 AM to 11:00 AM.
  - **Practical:** 1 hour on Wednesday from 1:00 PM to 2:00 PM.
  - **Self-Study:** 4 hours divided into two sessions:
    - Thursday from 10:00 AM to 12:00 PM.
    - Friday from 3:00 PM to 5:00 PM.

- **MATH1007:**
  - **Lecture:** 2 hours on Tuesday from 10:00 AM to 12:00 PM.
  - **Practical:** 2 hours on Thursday from 2:00 PM to 4:00 PM.
  - **Self-Study:** 6 hours divided into two sessions:
    - Monday from 2:00 PM to 4:00 PM.
    - Wednesday from 3:00 PM to 5:00 PM.

- **STAT1170:**
  - **Lecture:** 2 hours on Wednesday from 9:00 AM to 11:00 AM.
  - **Practical:** 2 hours on Friday from 10:00 AM to 12:00 PM.
  - **Self-Study:** 6 hours divided into two sessions:
    - Tuesday from 1:00 PM to 3:00 PM.
    - Thursday from 4:00 PM to 6:00 PM.

- **ENGG1000:**
  - **Lecture:** 3 hours on Monday from 11:00 AM to 1:00 PM.
  - **Practical:** 1 hour on Tuesday from 3:00 PM to 4:00 PM.
  - **Self-Study:** 6 hours divided into two sessions:
    - Wednesday from 11:00 AM to 1:00 PM.
    - Friday from 1:00 PM to 3:00 PM.

### **Usage of Sample Schedule:**

- **Importing into Spreadsheet Applications:**
  - The CSV file can be easily imported into applications like **Microsoft Excel** or **Google Sheets** for further customization or visualization.

- **Personalization:**
  - Students can modify the schedule by adjusting the **day and time slots** as per their convenience, and the application will ensure all constraints are maintained.

- **Time Management:**
  - Provides a **clear and organized overview** of weekly commitments, aiding in effective time management and study planning.

---

## **Conclusion**

The University Student Study Schedule Planner is a practical and efficient tool designed to streamline the process of organizing study schedules for university students. By automating time allocation and ensuring balanced distribution across various academic activities, the application empowers students to manage their time effectively, leading to improved academic performance and reduced scheduling stress.

---

## **Next Steps**

- **Development Kick-off:**
  - Assemble the development team and set up the project repository and development environment.

- **Requirement Review:**
  - Conduct meetings with stakeholders to review and finalize requirements before proceeding to implementation.

- **Progress Tracking:**
  - Utilize project management tools (e.g., Jira, Trello) to track progress across different milestones and tasks.

- **Feedback and Iteration:**
  - Incorporate user feedback throughout the development process to refine and enhance the application's functionality and usability.

---