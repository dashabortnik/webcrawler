#!/bin/bash
if command -v mvn &> /dev/null
then 
	mvn clean install
else 
	echo "Maven wasn't found."
fi

if command -v java &> /dev/null 
then
	java -Dfile.encoding=UTF-8 -jar target/webcrawler.jar $1
fi
read -r -p "Press any key to continue..." key