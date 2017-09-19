#!/bin/sh -e

# create a temp dir in which to work
OLDDIR="$PWD"
mkdir /tmp/rds-ca && cd /tmp/rds-ca

# download the bundle
wget https://s3.amazonaws.com/rds-downloads/rds-combined-ca-bundle.pem
# split the bundle into individual certs (prefixed with xx)
csplit -sz rds-combined-ca-bundle.pem '/-BEGIN CERTIFICATE-/' '{*}'

# import each cert individually
for CERT in xx*; do
    # extract a human-readable alias from the cert
    ALIAS=$(openssl x509 -noout -text -in $CERT |
        perl -ne 'next unless /Subject:/; s/.*CN=//; print')
    echo "importing $ALIAS"
    # import the cert into the default java keystore
    keytool -import \
        -keystore /tmp/cacerts \
        -storepass changeit -noprompt \
        -alias "$ALIAS" -file $CERT
done

# back out of the temp dir and delete it
cd "$OLDDIR"
rm -r /tmp/rds-ca

# list the imported rds certs as a sanity check
keytool -list \
    -keystore /tmp/cacerts \
    -storepass changeit -noprompt |
    grep -i rds
