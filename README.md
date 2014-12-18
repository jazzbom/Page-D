# README #

# Page D #

[Click to visit the working app example](http://209.222.30.153:8080/MaliciousCodeDetector/)
*Note: The live app has been set to monitors changes at every 72 hours and hence cannot be used to detect real-time changes. This setting was required to limit server bandwidth data usage.*

Malicious code detector for web applications using CRC32 checksum algorithm. (Alerts against XSS and backdoor scripts attacks)
* Version
v.1.0

### Description ###
Monitors websites by comparing their generated checksum values.
To authorize a modification simply select the page to update and click the "Update" button. 

Note:
The application uses automated scheduled tasks which run at per-defined intervals.
To modify schedule run intervals look into the *src/QuartzInitialiser.java* class.
And *WebContent/js/data.js*

### How do I get set up? ###
Import into Eclipse Luna and run on any Java EE 6 compliant webserver.