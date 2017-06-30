# Geo-Location extraction from text and URL
RESTful service for extracting possible geo-location from given text. Can be used to extract geo-location from given url e.g. a news article. It is running on-top of SparkJava micro-framework. In order to work it depends on CLAVIN (Cartographic Location And Vicinity INdexer). CLAVIN is an open source software package for document geotagging and geoparsing that employs context-based geographic entity resolution. http://clavin.bericotechnologies.com

How to build & use CLAVIN:
--------------------------

1. Check out a copy of the source code:
	> `git clone https://github.com/Berico-Technologies/CLAVIN.git`

2. Move into the newly-created CLAVIN directory:
	> `cd CLAVIN`

3. Download the latest version of allCountries.zip gazetteer file from GeoNames.org:
	> `curl -O http://download.geonames.org/export/dump/allCountries.zip`

4. Unzip the GeoNames gazetteer file:
	> `unzip allCountries.zip`

5. Compile the source code:
	> `mvn compile`

6. Create the Lucene Index (this one-time process will take several minutes):
	> `MAVEN_OPTS="-Xmx4g" mvn exec:java -Dexec.mainClass="com.bericotech.clavin.index.IndexDirectoryBuilder"`

7. Run the example program:
	> `MAVEN_OPTS="-Xmx2g" mvn exec:java -Dexec.mainClass="com.bericotech.clavin.WorkflowDemo"`
	
	If you encounter an error that looks like this:
	> `... InvocationTargetException: Java heap space ...`
	
	set the appropriate environmental variable controlling Maven's memory usage, and increase the size with `export MAVEN_OPTS=-Xmx4g` or similar.

Once that all runs successfully, feel free to modify the CLAVIN source code to suit your needs.

**N.B.**: Loading the worldwide gazetteer uses a non-trivial amount of memory. When using CLAVIN in your own programs, if you encounter `Java heap space` errors (like the one described in Step 7), bump up the maximum heap size for your JVM.

* Add a dependency on the CLAVIN project:

```xml
<dependency>
   <groupId>com.bericotech</groupId>
   <artifactId>clavin</artifactId>
   <version>2.0.0</version>
</dependency>
```

>  You will still need to build the GeoNames Lucene Index as described in steps 3, 4, and 6 in "How to build & use CLAVIN".

