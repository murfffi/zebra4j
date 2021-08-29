# Samples

The following samples demonstrate some of the puzzles you can generate with zebra4j.
Use the seed at the bottom of each sample to replicate with the a CLI command.
For example,

```sh
docker run --rm murfffi/zebracli:0.9 generate --seed 9093154172883354085
```

or, if you downloaded the "uber" jar,

```sh
java -jar zebra4j-0.9-shaded.jar generate --seed 9093154172883354085
```

Ten samples follow:

```
Facts:
Any person either owns a zebra or owns a cat or owns a hamster or owns a snake.
People's names are Ivan, Theodora, Peter, Elena.
One person is a criminal.
Any person either is in yellow clothes or is in green clothes or is in red clothes or is in blue clothes.
They live in 4 adjacent houses.
Peter is at house 2.
Peter isn't the person who is in yellow clothes.
Theodora isn't the person who owns a hamster.
Ivan isn't the person who owns a cat.
Theodora is in green clothes.
The person who is at house 4 is not the person who owns a cat.
The person who is at house 2 is not the person who is the criminal.
The person who is at house 1 is not the person who owns a hamster.
Ivan isn't the person who owns a snake.
The person who is in blue clothes also is the criminal.
The person who is the criminal also owns a snake.
Elena is at house 3.

Question: Which is the house of the person who is in yellow clothes?
Answer options: is at house 4, is at house 1, is at house 2, is at house 3
Answer: is at house 4
Seed: 8574845971565421814
```

```
Facts:
Any person either owns a hamster or owns a snake or owns a cat or owns a zebra.
One person is a criminal.
People's names are Theodora, Peter, Elena, Ivan.
Any person either is in green clothes or is in red clothes or is in blue clothes or is in yellow clothes.
The person who is in red clothes also owns a snake.
The person who is the criminal also owns a cat.
Ivan isn't the person who is in blue clothes.
The person who is in blue clothes also owns a cat.
Theodora isn't the person who is in red clothes.
Theodora isn't the person who is the criminal.
Ivan isn't the person who is in red clothes.
Ivan isn't the person who owns a hamster.

Question: What pet does Theodora own?
Answer options: owns a hamster, owns a snake, owns a cat, owns a zebra
Answer: owns a hamster
Seed: 4574246929601235406
```

```
Facts:
Any person either owns a hamster or owns a cat or owns a zebra or owns a snake.
People's names are Theodora, Ivan, Peter, Elena.
Any person either is in red clothes or is in green clothes or is in blue clothes or is in yellow clothes.
They live in 4 adjacent houses.
The person who is in blue clothes is not the person who owns a snake.
The person who is is in red clothes lives next door from the one who is is in blue clothes.
The person who is at house 3 is not the person who is in green clothes.
The person who is at house 2 is not the person who is in blue clothes.
Elena is at house 3.
The person who is at house 2 also owns a hamster.
The person who is in blue clothes is not the person who owns a cat.
Elena owns a snake.
The person who is at house 1 is not the person who is in yellow clothes.

Question: What pet does the person who is in green clothes own?
Answer options: owns a hamster, owns a cat, owns a zebra, owns a snake
Answer: owns a cat
Seed: 5094510298122990809
```

```
Facts:
Any person either owns a cat or owns a zebra or owns a hamster or owns a snake.
People's names are Theodora, Elena, Ivan, Peter.
Any person either is in green clothes or is in blue clothes or is in yellow clothes or is in red clothes.
They live in 4 adjacent houses.
Elena owns a zebra.
Peter isn't the person who is in green clothes.
The person who is is in green clothes lives next door from the one who is is in yellow clothes.
The person who is is in green clothes lives next door from the one who is is in blue clothes.
The person who is at house 4 also owns a zebra.
Elena lives 2 doors away from Ivan.
Ivan isn't the person who is in green clothes.

Question: Which is the house of Peter?
Answer options: is at house 3, is at house 4, is at house 2, is at house 1
Answer: is at house 1
Seed: 6877092184460536910
```

```
Facts:
People's names are Theodora, Peter, Ivan, Elena.
One person is a criminal.
Any person either is in green clothes or is in blue clothes or is in yellow clothes or is in red clothes.
They live in 4 adjacent houses.
Ivan is at house 2.
The person who is at house 4 is not the person who is in yellow clothes.
The person who is in yellow clothes is not the person who is the criminal.
The person who is is in green clothes lives 2 doors away from the one who is is in blue clothes.
The person who is is in green clothes lives next door from the one who is is in red clothes.
The person who is at house 1 also is the criminal.
The person who is at house 2 is not the person who is in green clothes.

Question: Who is in yellow clothes?
Answer options: Theodora, Peter, Ivan, Elena
Answer: Ivan
Seed: 2603189112643104537
```

```
Facts:
Any person either owns a hamster or owns a snake or owns a zebra or owns a cat.
One person is a criminal.
People's names are Elena, Peter, Theodora, Ivan.
They live in 4 adjacent houses.
The person who is the criminal is not the person who owns a hamster.
Peter is at house 4.
The person who is at house 2 also owns a hamster.
Peter isn't the person who owns a zebra.
Ivan isn't the person who owns a hamster.
Ivan isn't the person who is the criminal.
The person who is the criminal is not the person who owns a snake.
The person who is at house 3 also owns a cat.
Theodora is at house 1.

Question: Which is the house of the person who is the criminal?
Answer options: is at house 2, is at house 4, is at house 1, is at house 3
Answer: is at house 1
Seed: 6734106538942026681
```

```
Facts:
Any person either owns a snake or owns a cat or owns a zebra or owns a hamster.
People's names are Ivan, Theodora, Peter, Elena.
Any person either is in red clothes or is in yellow clothes or is in blue clothes or is in green clothes.
They live in 4 adjacent houses.
The person who is at house 4 is not the person who owns a snake.
The person who is at house 4 is not the person who owns a zebra.
The person who is in blue clothes is not the person who owns a hamster.
Ivan owns a snake.
The person who is in yellow clothes also owns a cat.
The person who is at house 3 also is in yellow clothes.
Theodora is at house 3.
Elena isn't the person who is in red clothes.
Elena isn't the person who owns a zebra.

Question: Which is the house of the person who is in green clothes?
Answer options: is at house 2, is at house 3, is at house 1, is at house 4
Answer: is at house 4
Seed: 433137855859917694
```

```
Facts:
Any person either owns a snake or owns a zebra or owns a hamster or owns a cat.
People's names are Theodora, Peter, Elena, Ivan.
Any person either is in green clothes or is in yellow clothes or is in blue clothes or is in red clothes.
They live in 4 adjacent houses.
The person who is at house 1 is not the person who is in yellow clothes.
Ivan is in red clothes.
The person who is in red clothes is not the person who owns a hamster.
Peter isn't the person who is in green clothes.
The person who is at house 4 also owns a snake.
Theodora is at house 4.
The person who is at house 2 is not the person who is in red clothes.
The person who is at house 3 also owns a cat.
Elena is in blue clothes.
The person who is owns a zebra lives next door from the one who is owns a cat.

Question: Which is the house of Peter?
Answer options: is at house 4, is at house 2, is at house 1, is at house 3
Answer: is at house 2
Seed: 6550313133364002776
```

```
Facts:
People's names are Elena, Ivan, Theodora, Peter.
Any person either is in yellow clothes or is in green clothes or is in red clothes or is in blue clothes.
They live in 4 adjacent houses.
Ivan isn't the person who is in red clothes.
Elena isn't the person who is in red clothes.
Ivan isn't the person who is at house 1.
Peter isn't the person who is at house 4.
Ivan isn't the person who is at house 2.
Peter isn't the person who is in red clothes.
Ivan lives 2 doors away from Theodora.
Elena lives next door from Theodora.

Question: Which is the house of the person who is in red clothes?
Answer options: is at house 3, is at house 4, is at house 2, is at house 1
Answer: is at house 2
Seed: 7987286072342432636
```

```
Facts:
Any person either owns a zebra or owns a hamster or owns a cat or owns a snake.
People's names are Ivan, Theodora, Elena, Peter.
One person is a criminal.
Any person either is in yellow clothes or is in green clothes or is in blue clothes or is in red clothes.
They live in 4 adjacent houses.
The person who is at house 3 is not the person who owns a snake.
The person who is at house 4 is not the person who is in green clothes.
Ivan isn't the person who owns a hamster.
The person who is in blue clothes also owns a cat.
The person who is in red clothes also is the criminal.
Ivan isn't the person who is in blue clothes.
The person who is is in green clothes lives next door from the one who is is in red clothes.
Ivan isn't the person who is the criminal.
The person who is at house 3 is not the person who owns a zebra.
The person who is at house 2 also is in blue clothes.

Question: Which is the house of Ivan?
Answer options: is at house 1, is at house 3, is at house 2, is at house 4
Answer: is at house 1
Seed: 1278895030451739164
```
