This is the basic configuration and setup of MarkLogic and Gradle.
Project uses ml-gradle for most of the integration.
here are the list of tasks you can do with it:
1. Use predefined MLCP task to load xml data
2. Transform data on load.
3. Deploys REST endpoint.
4. Define custom transform on data extract.
5. Basic examples of java-client-api


Setup using following steps:
gradle mlDeploy
gradle importSampleData
REST API:
http://localhost:8035/v1/search?structuredQuery=<cts%3aelement-value-query+xmlns%3acts%3d%22http%3a//marklogic.com/cts%22>%0a++<cts%3aelement>CoverageCd</cts%3aelement>%0a++<cts%3atext+xml%3alang%3d%22en%22>PD</cts%3atext>%0a</cts%3aelement-value-query>
 
Java Search API
main.examples.StructuredSearch
