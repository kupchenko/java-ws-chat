#!/usr/bin/env bash
export url=jdbc:mysql://db:3306
export dbName=chat
export driver=com.mysql.jdbc.Driver
export username=root
export password=root
export tablesFile=migrations/changelog-master.groovy
export dataFile=migrations/changelog-apply-data.groovy

mvn liquibase:update -Durl=$url/$dbName -Ddriver=$driver -Dusername=$username -Dpassword=$password -DchangeLogFile=$tablesFile
mvn liquibase:update -Durl=$url/$dbName -Ddriver=$driver -Dusername=$username -Dpassword=$password -DchangeLogFile=$dataFile
