mvn package
java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 target/MorseMonkey-1.0.1-SNAPSHOT-swarm.jar
