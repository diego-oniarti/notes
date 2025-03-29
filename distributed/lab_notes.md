# Classi utili
```java
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.Props;
import scala.concurrent.duration.Duration;
```

```java
// System
final ActorSystem system = ActorSystem.create("name");
ActorRef actor = system.actorOf(Props props, String name);
system.terminate();

// Actors
actor.tell(Object msg, ActorRef sender);

public class MioAttore extends AbstractActor {
    static public Props props(/* constructor arguments */) {
        return Props.create(MioAttore.class, () -> new MioAttore(/* constructor arguments */));
    }

    public static class MioMessaggio implements Serializable {}

    @Override
    public void preStart() {}

    @Override
    public Receive createReceive() {
        return receiveBuilder()
        .match(MioMessaggio.class,  this::handlerName)
        .match(MioMessaggio2.class, this::handlerName2)
        .build();
    }
}
```

# build.gradle
```gradle
apply plugin: 'java'
apply plugin: 'idea'
apply plugin: 'application'


repositories {
    mavenCentral()
    mavenLocal()
    maven {
        url "https://repo.akka.io/maven"
    }
}

def versions = [
            ScalaBinary: "2.13",
            AkkaVersion: "2.9.0",
            LogBackVersion: "1.2.3",
            JunitVersion: "4.13.1"
]

dependencies {
    implementation "com.typesafe.akka:akka-bom_${versions.ScalaBinary}:${versions.AkkaVersion}"
    implementation "com.typesafe.akka:akka-actor-typed_${versions.ScalaBinary}:${versions.AkkaVersion}"

    testImplementation "com.typesafe.akka:akka-actor-testkit-typed_${versions.ScalaBinary}:${versions.AkkaVersion}"
    testImplementation "junit:junit:${versions.JunitVersion}"
    implementation "ch.qos.logback:logback-classic:${versions.LogBackVersion}"
}
```
