# Use a valid Maven image with Eclipse Temurin JDK 21
FROM maven:3.9.5-eclipse-temurin-21 AS build

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copy the source code and package the application
COPY src ./src
RUN mvn clean package -DskipTests

# Use a lightweight JRE image for runtime
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port and run the application
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]








## Étape 1: Construction avec une image slim pour minimiser la taille
#FROM eclipse-temurin:21.0.2_13-jdk-jammy AS builder
#
## Définit le répertoire de travail dans le conteneur où les commandes suivantes seront exécutées.
#WORKDIR /opt/app
#
## Copie les fichiers de configuration Maven et le fichier POM.
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#
## Donne les droits d'exécution à "mvnw" et télécharge les dépendances nécessaires pour le projet en mode hors ligne.
#RUN chmod +x mvnw && ./mvnw dependency:go-offline
#
## Copie le code source de l'application et compile en ignorant les tests pour accélérer le build.
#COPY ./src ./src
#RUN ./mvnw clean package -DskipTests
#
## Étape 2: Utiliser une image JRE slim pour exécuter l'application
#FROM eclipse-temurin:21.0.2_13-jre-jammy
#
## Créer un utilisateur non-root pour exécuter l'application
#RUN addgroup --system javauser && adduser --system --ingroup javauser javauser
#
## Répertoire de travail pour exécuter l'application
#WORKDIR /opt/app
#
## Copier uniquement le fichier JAR final à partir de l'étape de build
#COPY --from=builder /opt/app/target/*.jar app.jar
#
## Changer la propriété du fichier JAR à l'utilisateur non-root
#RUN chown javauser:javauser app.jar
#
## Basculer vers l'utilisateur non-root
#USER javauser
#
## Expose le port 8081
#EXPOSE 8081
#
## Exécuter l'application avec des options JVM optimisées
#ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]