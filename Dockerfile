FROM        openjdk:slim

WORKDIR     /enki
COPY        . .

EXPOSE      8080

CMD         [ "./gradlew", "stage" ]

ENTRYPOINT  ["./build/install/com.hdeoliv.enki/bin/com.hdeoliv.enki"]
