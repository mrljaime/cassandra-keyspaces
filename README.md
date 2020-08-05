### Amazon Keyspaces Demo

This example allow Spring Boot + Apache Data Cassandra connect with AWS Keyspaces.
By default the `src/main/resources/application.properties` file is comment in the lines that define active profile to execute application.

`dev` profile is active by default in case that profile wasn't set in `src/main/resources/application.properties`


To run this little example we need the following dependencies:
* [Docker](https://www.docker.com/)
* [docker-compose](https://docs.docker.com/compose/)
* [AWS Keyspaces](https://aws.amazon.com/es/keyspaces/getting-started/?blog-items.sort-by=item.additionalFields.createdDate&blog-items.sort-order=desc)

This project was build to run on localhost dev environments but can be deployed with some modifications 
in java startup to define some properties about JMV like memory heap, what to do when some critical error ocurred 
or define parallel garbage collection, and other configurations in default config for Apache Cassandra. 


* ##### Run project on local environment 

To run the project in local environment we need just uncomment the line in `src/main/resources/application.properties` as show below: 
```properties
spring.profiles.active=dev
#spring.profiles.active=keyspaces
```

* ##### Run project with Amazon Keyspaces

To run the project with Amazon Keyspace we need uncomment the line in `src/main/resources/application.properties` as show below:
```properties
#spring.profiles.active=dev
spring.profiles.active=keyspaces
```

After uncomment line to define the keyspaces profile, we need to edit the `src/main/resources/keyspaces.conf` file to set credentials 
for AWS Keyspaces ([get aws keyspaces credentials](https://docs.aws.amazon.com/keyspaces/latest/devguide/using_java_driver.html))

```hocon
datastax-java-driver {
    basic {
        contact-points = [ "cassandra.us-east-2.amazonaws.com:9142" ]
        load-balancing-policy.local-datacenter = "us-east-2"
        session-keyspace = transactions
        consistency = LOCAL_QUORUM
    }

    advanced.auth-provider {
        class = PlainTextAuthProvider
        username = "<providedByCredentialsInAWSKeyspaces>"
        password = "<providedByCredentialsInAWSKeyspaces>"
    }

    advanced.ssl-engine-factory {
        class = DefaultSslEngineFactory
        truststore-path = "./src/main/resources/cers/cassandra_truststore.jks"
        truststore-password = "pepepepe"
    }

    advanced.connection {
        pool {
              local {
                    size = 1
              }
        }
    }
}
```

* ##### Start project
Now, we just need to start containers to initialize the application with:
```bash
docker-compose up
# docker-compose up --detach in case that we want get back terminal usage
```

After started point we will see a line as below:

`Started CassandraKeyspacesApplication in x.xx seconds (JVM running for x.xxx)`

This line show us that now we can make request to service on endpoints 

```http request
###> Create some user (wihout unique restriction in email) ###
POST http://127.0.0.1:8080/api/1.0/users/
Content-Type: application/json

{"email":"jaime@bade.osmos.mx"}
###< Create some user (wihout unique restriction in email) ###
```

```http request
###> Get users list ###
GET http://127.0.0.1:8080/api/1.0/users/
Accept: application/json

###< Get users list ###
```


