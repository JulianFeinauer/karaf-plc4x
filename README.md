# Example for plc4x integration in karaf
... and hopefully some REST Proxy

## Modules

### org.pragmaticminds.plc4x.feature 

Contains the feature `plc4x-server-feature` which contains "all"

### org.pragmaticminds.plc4x.plc-feature

_Old_ feature, is used to generate the xml which is copied over to `org.pragmaticminds.plc4x.feature`.

### server

Contains the REST Server. And also a demo to use PLC4X.

## Installation

Install the feature repo `feature:repo-add mvn:org.pragmaticminds.plc4x/plc4x-server-feature/1.0.0-SNAPSHOT/xml/features`,
then install the feature `feature:install plc4x-server-rest` and then the bundle `proxy-server` should be starteable (currently does not work).
