levyhylly
=========

Levyhylly is a (poor) record collection application with a butt-ugly user interface.

To run from the command line, type "mvn jetty:run" in the webapp directory (levyhylly/levyhylly-parent/levyhylly-webapp). Set system property "populateTestDataToDb" to "true" ("-DpopulateTestDataToDb=true") to insert test data into the database. In order to generate a deployable (? - haven't tested yet) WAR file, run "mvn package".

Configuring the project for an IDE supporting Maven should be trivial: IDEA and Netbeans should pretty much work out-of-the-box, Eclipse requires a Maven plugin (m2eclipse recommended).

An instance of a somewhat recent version may or may not be running at:
http://levyhylly.herokuapp.com/
