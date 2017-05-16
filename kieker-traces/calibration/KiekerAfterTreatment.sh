#!/bin/bash
for file in $(find -name "kieker*.dat"); do
  sed -i -E 's/ throws ([[:alnum:]]+\.)*[[:alnum:]]+\;/;/g' $file
done
