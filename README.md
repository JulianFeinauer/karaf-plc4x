# Example for plc4x integration in karaf
... and hopefully some REST Proxy

## Modules

### org.pragmaticminds.plc4x.feature 

Contains multiple features
 
* `plc4x-feature` which contains the dependencies of the plc4x modules (wrapped)
* `plc4x-consumer-example` which contains an example consumer (via DS)
* `plc4x-driver-s7` feature for s7 driver (with dependencies and stuff). Exposing s7 driver as DS.

### org.pragmaticminds.plc4x.plc-feature

_Old_ feature, is used to generate the xml which is copied over to `org.pragmaticminds.plc4x.feature`.

### server (deprecated)

Contains the REST Server. And also a demo to use PLC4X.

### s7-driver (deprecated)

### driver-s7-spi 

Wrapping of the s7 driver (with two bundles packaged to avoid split package).

### plc4x-user

Example of a consumer of the plc4x package via DS

### plc4x-api

Wrapped api bundle for plc4x.


## Installation

Install the feature repo `feature:repo-add mvn:org.pragmaticminds.plc4x/plc4x-server-feature/1.0.0-SNAPSHOT/xml/features`,
then install the features `feature:install plc4x-driver-s7` and the feature `plc4x-consumer-example`.
