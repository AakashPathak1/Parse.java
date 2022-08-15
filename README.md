#Parse.java
Parsing Script to extract readable data from EarthSense app
by Aakash Pathak

Parse.java is a script I created to be able to extract data about certain app variable states and display the output in a CSV file so that the EarthSense app developers could access this data in a convenient and readable format.

I designed the script to:
  * To be modular to work with different versions of the EarthSense app 
    * The script allows the user to input the file locations where the script should search depending on the version of the app
  * To allow the developers to customize how to handle blank fields in `output.csv`, where a particular attribute was not found
  
