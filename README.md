# Tests for `odinson`

This project provides tests regarding the usage of `odinson`.
We will provide a general example using the UMBC corpus.

## What you'll need...
  1. [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
  2. [sbt](http://www.scala-sbt.org/release/tutorial/Setup.html)
  
## Tests

|__Domain__ | __Description__|
|--------|----------------|
|[`general`](src/main/scala/ai/lum/odinson/tests) | A very simple introduction that uses a general purpose corpus |

## How to run:

1. Download the 100 documents data in:

`wget --load-cookies /tmp/cookies.txt "https://docs.google.com/uc?export=download&confirm=$(wget --quiet --save-cookies /tmp/cookies.txt --keep-session-cookies --no-check-certificate 'https://docs.google.com/uc?export=download&id=1I5LLkK3S6Pl1k8MbsYInBCgXK2F2GmZK' -O- | sed -rn 's/.*confirm=([0-9A-Za-z_]+).*/\1\n/p')&id=1I5LLkK3S6Pl1k8MbsYInBCgXK2F2GmZK" -O 100.tar.gz && rm -rf /tmp/cookies.txt`.

2. Move the file to `/data/nlp/corpora/odinson-tests/100`.

3. Unpack the file `tar -xf 100.tar.gz`.
