# UML Overview

* The system has 3 major entities:
  * User
  * Assignment
  * Report

* The system allows every User to register, login and logout.
* A User can be either an Administrator, Professor or a TA.
* The User can upload an Assignment that can be either a File or a Directory.
* This Assignment is parsed by a Parser to be converted in to an Abstract Syntax Tree.
* The Comparator takes these Abstract Syntax Trees of the Assignments that need to be checked for plagiarism and generates a Report


###### User
Currently there are three kinds of users: Admin, TA and Professor.
* We assume users have collected all students’ homework, either as url or real files. So students wouldn't have access to the system and try to cheat against the system.
* Admin has authorization to manage other users
* TA can only send report to professor
* Professor can send report to OSCCR
* Users have common methods such as register, login, logout
* Users upload assignments for further analysis
* Users can get reports after analysis
 
###### Assignment
* Assignment will be checked by the program to detect plagiarism
* Assignment can be either files or directory
* We assume student will follow instructions when submitting the homework so they are in same format and naming standard.
* File will also have other information about the course and the students’ submission, such as whether a file is late submission or has already been checked

###### Report
* A report will be generated using the AST.
* The AST Node visitor will enable calculations like, similarity score, flagging files based on scores etc.
* Coloring patterns like Red for severe, Yellow for suspicious and green for safe will be used.
* Report will have an option to be exported as a document file and additionally allows to pass inform 
  a senior authority about file that require attention.
