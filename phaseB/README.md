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

