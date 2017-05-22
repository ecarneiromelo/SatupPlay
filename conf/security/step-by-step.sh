#! /bin/bash

if [ "$JAVA_HOME" == "" -a "$1" == "" ]; then
  echo
  echo "You must define the environment variable JAVA_HOME or pass it as a param"
  echo "Ex.: $0 /Library/Java/JavaVirtualMachines/jdk1.7.0_45.jdk/Contents/Home"
  echo
  exit 1
fi

CACERTS_DIR="/jre/lib/security/cacerts"
if [ "$1" != "" ]; then 
	CACERTS="$1$CACERTS_DIR"
else
	CACERTS="$JAVA_HOME$CACERTS_DIR"
fi	

echo
echo "Using path: $CACERTS"
echo

#keytool -genkey -validity 180 -alias scif09host -keyalg RSA -keystore certificate.jks
#keytool -export -alias scif09host -keystore certificate.jks -file host.key
#keytool -import -trustcacerts -alias scif09host -file scif09.cer -keystore $CACERTS

openssl genrsa 1024 > host.key
openssl req -new -x509 -nodes -sha1 -days 365 -key host.key > host.cert

# For Linux/Windows import:
keytool -import -trustcacerts -alias scif09host -keystore $cacerts -file conf/host.cert

# For MacOs import:
#sudo /usr/bin/security add-trusted-cert -d -r trustRoot -k /Library/Keychains/System.keychain host.host
##sudo certtool i "host.host" k=/System/Library/Keychains/X509Anchors