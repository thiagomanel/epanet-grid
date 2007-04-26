#!/bin/bash

# $1 -> *.inp

java -Djava.library.path=lib/linux -cp classes org.ourgrid.epanetgrid.JEpanetToolkitMain $1 saida.txt log.txt
