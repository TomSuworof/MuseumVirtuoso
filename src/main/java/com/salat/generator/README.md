## Event generator

In the object "Architectural and Museum Complex" the most frequent events are ticket purchase
and visitor registration. The generator of these events is the subject of this package.

## RmlStreamerFileAdapter

In the object "Architectural and Museum Complex" the most frequent events are ticket purchase
and visitor registration. The adapter allows to read events from file 
(see [data.csv](../../../../resources/data.csv)) and put them into socket. [RML-Streamer](https://github.com/RMLio/RMLStreamer) 
is connected to this socket and maps events from JSON to RDF triples and puts them into 
another socket.

To run RML-Streamer use:
```shell
java --add-opens=java.base/java.lang=ALL-UNNAMED --add-opens=java.base/java.util=ALL-UNNAMED -jar "<path-to-jar>\RMLStreamer-v2.5.0-standalone.jar" toTCPSocket -s localhost:9900 -m "<path-to-project>\MuseumVirtuoso\src\main\resources\mapping.ttl"
```

This command will print RDF triples to socket 9999. Use 
```shell
ncat -lk 9900
 ```
to open Netcat on Windows and see these triples.

In short, steps to run:
1. Run Netcat for client socket
2. Run [`RmlStreamerFileAdapter`](RmlStreamerFileAdapter.java). It will block until any client connected, so you
need to start RML-Streamer.
3. Run RML-Streamer
4. Type filename in console in IDE, that runs `RmlStreamerFileAdapter`
5. See in client socket result RDF triples.
