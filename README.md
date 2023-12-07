# CS 245 (Fall 2023) - Assignment 3 - IRoadTrip

Can you plan the path for a road trip from one country to another?

Change the java source code, but do not change the data files. See Canvas for assignment details.

# Notes (*grader PLEASE READ*)

This project is built with Maven. There might be variations on
different systems and/or different versions of Maven. On my GNU/Linux
system (fedora), to run the program, you can use this command in the
project root directory:

```shell
java -cp \
target/classes:$HOME/.m2/repository/org/apache/commons/commons-csv/1.10.0/commons-csv-1.10.0.jar \
io.yiyuzhou.trip.IRoadTrip <path> <path> <path>
```

If this doesn't work, you might be running a different version of
Maven. However, in the `no-args` branch, I kept a copy of a version of
this program, where I use the Maven `resources` directory under
`src/main/resources`. If you clone the `no-args` branch and run it
with your IDE, it will automatically read the files from
`src/main/resources` as long as they match the file names provided in
the project spec.

If you have any issues running my program, please email me at
<yzhou155@dons.usfca.edu>.
