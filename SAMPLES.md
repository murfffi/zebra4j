# Samples

The following samples demonstrate some of the puzzles you can generate with zebra4j.
Use the seed at the bottom of each sample to replicate with the a CLI command.
For example,

```
java -jar zebra4j-0.6-shaded generate --seed 9093154172883354085
```

Ten samples follow:

```
Facts:
Any person is either in blue clothes or in green clothes or in yellow clothes or in red clothes.
Any person is either Peter or Elena or Ivan or Theodora.
One person is a criminal.
They live in 4 adjacent houses.
The person who is in blue clothes is not the criminal.
Theodora isn't the criminal.
Elena isn't the criminal.
Ivan lives next door from Theodora.
Elena lives next door from Ivan.
Elena is in green clothes.
The person who is in blue clothes is also at house 1.
Theodora is in red clothes.

Question: Which is the house of the criminal?
Answer options: at house 1, at house 2, at house 3, at house 4
Answer: at house 3
Seed: 9093154172883354085
```

```
Facts:
Any person is either in green clothes or in blue clothes or in yellow clothes or in red clothes.
Any person is either Peter or Ivan or Theodora or Elena.
One person is a criminal.
They live in 4 adjacent houses.
Peter isn't in blue clothes.
Ivan is the criminal.
Theodora lives 2 doors away from Elena.
Elena isn't in green clothes.
The person who is in blue clothes is also at house 2.
The person who is in green clothes is also at house 4.
Theodora isn't in green clothes.

Question: What is the color of the clothes of the criminal?
Answer options: in green clothes, in blue clothes, in yellow clothes, in red clothes
Answer: in blue clothes
Seed: 5380064843200865713
```

```
Facts:
Any person is either in yellow clothes or in green clothes or in red clothes or in blue clothes.
Any person is either Ivan or Theodora or Elena or Peter.
One person is a criminal.
They live in 4 adjacent houses.
The person who is in blue clothes is not the criminal.
Elena isn't the criminal.
The person who is in green clothes lives 2 doors away from the one who is in blue clothes.
The person who is in green clothes is not the criminal.
The person who is in yellow clothes is not at house 4.
Theodora isn't in red clothes.
Theodora lives next door from Elena.
Theodora is at house 4.

Question: Which is the house of the criminal?
Answer options: at house 1, at house 4, at house 3, at house 2
Answer: at house 1
Seed: 9079221273189392311
```

```
Facts:
Any person is either in green clothes or in red clothes or in yellow clothes or in blue clothes.
Any person is either Theodora or Elena or Ivan or Peter.
One person is a criminal.
They live in 4 adjacent houses.
The person who is in yellow clothes is not the criminal.
Ivan lives 2 doors away from Peter.
The person who is in green clothes is not the criminal.
Peter isn't the criminal.
Theodora is at house 1.
Ivan isn't in red clothes.
Ivan isn't in blue clothes.
Theodora is in green clothes.

Question: Which is the house of the criminal?
Answer options: at house 1, at house 3, at house 2, at house 4
Answer: at house 3
Seed: 3053399025393576332
```

```
Facts:
Any person is either in green clothes or in red clothes or in blue clothes or in yellow clothes.
Any person is either Ivan or Peter or Theodora or Elena.
One person is a criminal.
They live in 4 adjacent houses.
Peter isn't in blue clothes.
The person who is in red clothes is also at house 3.
Ivan isn't in blue clothes.
The person who is in blue clothes is not at house 1.
The person who is in green clothes lives next door from the one who is in blue clothes.
Elena isn't in blue clothes.
Theodora is the criminal.

Question: Which is the house of the criminal?
Answer options: at house 1, at house 3, at house 2, at house 4
Answer: at house 2
Seed: 8297472352435153720
```

```
Facts:
Any person is either in blue clothes or in green clothes or in red clothes or in yellow clothes.
Any person is either Peter or Theodora or Elena or Ivan.
One person is a criminal.
They live in 4 adjacent houses.
The person who is in blue clothes is not the criminal.
The person who is in yellow clothes is not the criminal.
Elena isn't at house 1.
The person who is in blue clothes lives 2 doors away from the one who is in yellow clothes.
Ivan isn't at house 1.
Peter isn't in green clothes.
The person who is in red clothes is not at house 1.
The person who is in yellow clothes is also at house 4.
Theodora isn't the criminal.

Question: Which is the house of the criminal?
Answer options: at house 2, at house 1, at house 3, at house 4
Answer: at house 3
Seed: 3499706781685091191
```

```
Facts:
Any person is either in yellow clothes or in blue clothes or in green clothes or in red clothes.
Any person is either Elena or Theodora or Ivan or Peter.
One person is a criminal.
They live in 4 adjacent houses.
Ivan isn't in blue clothes.
The person who is in blue clothes is not at house 1.
Elena isn't at house 2.
Theodora lives 2 doors away from Ivan.
The person who is in blue clothes lives next door from the one who is in red clothes.
The person who is in yellow clothes is also the criminal.
The person who is the criminal is also at house 3.
Peter isn't in blue clothes.

Question: Who is at house 4?
Answer options: Elena, Theodora, Ivan, Peter
Answer: Ivan
Seed: 78085425728243198
```

```
Facts:
Any person is either in blue clothes or in yellow clothes or in red clothes or in green clothes.
Any person is either Peter or Elena or Theodora or Ivan.
One person is a criminal.
They live in 4 adjacent houses.
The person who is in blue clothes is also the criminal.
The person who is in yellow clothes is also at house 4.
Peter isn't in green clothes.
The person who is in blue clothes lives next door from the one who is in green clothes.
Ivan isn't the criminal.
Peter isn't in red clothes.
Peter lives next door from Ivan.
The person who is in blue clothes is not at house 2.
The person who is in red clothes is not at house 3.

Question: Who is at house 3?
Answer options: Peter, Elena, Theodora, Ivan
Answer: Peter
Seed: 2868273290441249526
```

```
Facts:
Any person is either in yellow clothes or in blue clothes or in red clothes or in green clothes.
Any person is either Peter or Ivan or Elena or Theodora.
One person is a criminal.
They live in 4 adjacent houses.
The person who is in blue clothes is also the criminal.
Peter lives 2 doors away from Theodora.
Peter isn't the criminal.
The person who is in green clothes is also at house 1.
Peter lives next door from Elena.
Elena is at house 4.
The person who is the criminal is not at house 4.

Question: What is the color of the clothes of Ivan?
Answer options: in yellow clothes, in blue clothes, in red clothes, in green clothes
Answer: in blue clothes
Seed: 6846105588568636878
```

```
Facts:
Any person is either in blue clothes or in green clothes or in red clothes or in yellow clothes.
Any person is either Peter or Ivan or Elena or Theodora.
They live in 4 adjacent houses.
Peter lives 2 doors away from Ivan.
Peter isn't in red clothes.
The person who is in blue clothes lives next door from the one who is in yellow clothes.
Peter isn't in yellow clothes.
Elena isn't in yellow clothes.
Ivan isn't in blue clothes.
Elena isn't at house 2.
Ivan isn't at house 4.
The person who is in red clothes lives 2 doors away from the one who is in yellow clothes.
Peter lives next door from Elena.

Question: What is the color of the clothes of at house 2?
Answer options: in blue clothes, in green clothes, in red clothes, in yellow clothes
Answer: in yellow clothes
Seed: 4238068536192740666
```
