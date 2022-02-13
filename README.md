## Getting Started

### Prerequisites

Before working with the project you should have some basic software installed.

The *required* software components are:

* **JDK 11+** - you will need to have the Java 11+ SDK installed on your system. You can download the SDK from here: https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html

The *recommended* software components to install are:

* **IntelliJ** - you may use whatever development environment you prefer, but for starting we recommend IntelliJ. You can download it here: https://www.jetbrains.com/idea/

### Configure your environment

Assuming you've *installed the JDK version 11+*, you'll need to make sure that it is configured properly.

From the command-line of your operating system, type `java -version`. The output should show version the JDK version. Any version of 11+ will do. So if you see something like the following, then you are in good shape:

    java version "11.0.2" 2019-01-15 LTS
    Java(TM) SE Runtime Environment 18.9 (build 11.0.2+9-LTS)
    Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.2+9-LTS, mixed mode)

Finally, it's possible the `java` application may not be configured in your **PATH**, or your **PATH** might be referencing an older or newer version. If you don't know how to setup your system properly, please **ask for help**.

### Running test project
you can test the project by running `./gradlew check`.

### Running the project

You can start the Project by running `./gradlew bootRun`.
The first time you run the core, it will download dependencies, build from source, and start-up the application.# onlinepajak
# onlinepajak
