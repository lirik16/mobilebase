#!/bin/sh

ENCRYPT_KEY=$1

openssl aes-256-cbc -md sha256 -d -in encrypted/fabric.properties.enc -out app/fabric.properties -pass $ENCRYPT_KEY
