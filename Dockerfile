# Utiliser l'image de base Eclipse Temurin JRE 21
FROM eclipse-temurin:21-jre

# Définir le répertoire de travail
WORKDIR /app

# Copier le fichier JAR dans le répertoire de travail du conteneur
COPY code/Client/target/dai-lab-smtp-1.0.jar /app/dai-lab-smtp-1.0.jar

# Définir le point d'entrée
ENTRYPOINT ["java", "-jar", "dai-lab-smtp-1.0.jar"]

# Définir la commande par défaut
CMD ["--help"]
