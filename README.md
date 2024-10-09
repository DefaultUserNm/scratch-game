# Home Assignment: Scratch Game

## Build
```shell
./gradlew clean build
```

## Run the game
```shell
java -jar build/libs/scratch_game-1.0.0.jar --config src/test/resources/config.json --betting-amount 100
```

## P.S.
The problem description does not clearly say how many bonus symbol should be generated or how applied bonus 
should be determined in case there is more than 1, so I've implemented it the same way as in 2 examples
(1 bonus per matrix)