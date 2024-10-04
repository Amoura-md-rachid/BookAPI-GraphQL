# BookAPI - Opérations CRUD avec GraphQL

## Description
BookAPI est une application Spring Boot qui fournit un ensemble d'API GraphQL pour gérer une collection de livres. L'application prend en charge les opérations CRUD basiques (Créer, Lire, Mettre à jour, Supprimer) sur les livres, permettant aux utilisateurs de créer de nouveaux livres, de récupérer des détails sur les livres, de mettre à jour des livres existants et de les supprimer. L'utilisation de GraphQL permet une requête de données efficace, réduisant le besoin de plusieurs endpoints REST en offrant des options flexibles de récupération et de mutation de données.

## Technologies Utilisées
- **Java 17** : Le langage de programmation principal utilisé pour ce projet.
- **Spring Boot** : Un framework qui simplifie le développement d'applications Java, offrant des fonctionnalités intégrées telles que l'injection de dépendances, le développement web et l'accès aux bases de données.
- **GraphQL** : Un langage de requête pour les API qui permet aux clients de spécifier exactement les données dont ils ont besoin, améliorant ainsi la flexibilité et les performances.
- **Spring GraphQL** : L'intégration de GraphQL dans l'écosystème Spring pour la création d'API évolutives.
- **Base de données H2** : Une base de données en mémoire utilisée pour le développement et les tests. Cette base de données légère est facile à configurer et peut être remplacée par une base de données de production (par exemple, MySQL, PostgreSQL).
- **Lombok** : Une bibliothèque Java utilisée pour réduire le code répétitif en générant des méthodes communes telles que les getters, setters, constructeurs, etc.
- **SLF4J + Logback** : Bibliothèques de journalisation pour suivre et surveiller l'application.

## Fonctionnalités
- **Requêtes GraphQL** : Récupérer tous les livres, obtenir un livre spécifique par son ID, ou filtrer les livres en fonction de leur titre, auteur ou année de publication.
- **Mutations GraphQL** : Créer, mettre à jour et supprimer des livres à l'aide de mutations.
- **Base de données** : La persistance est gérée à l'aide de H2 pour le développement, avec la possibilité de passer à une autre base de données en production.
- **Récupération flexible des données** : Utilisez les requêtes GraphQL pour spécifier exactement quelles informations sur les livres vous souhaitez récupérer.

## Installation et Exécution de l'Application

### Prérequis
- **JDK 17** ou plus récent
- **Maven 3.8+**
- (Facultatif) **GraalVM** si vous souhaitez compiler le projet en une image native

### Exécution de l'Application
1. Clonez le dépôt :
    ```bash
    git clone https://github.com/votreutilisateur/BookAPI-GraphQL.git
    cd BookAPI-GraphQL
    ```

2. Exécutez l'application avec Maven :
    ```bash
    ./mvnw spring-boot:run
    ```

3. Accédez à l'interface GraphiQL dans votre navigateur :
    ```
    http://localhost:8080/graphiql
    ```

### Endpoints API (via GraphQL)
Vous pouvez utiliser l'interface `GraphiQL` pour tester les requêtes et mutations suivantes :

#### Exemples de Requêtes :
- Récupérer tous les livres :
    ```graphql
    {
      books {
        id
        title
        author
      }
    }
    ```

- Récupérer un livre par ID :
    ```graphql
    {
      bookById(id: 1) {
        title
        author
        publicationYear
      }
    }
    ```

#### Exemples de Mutations :
- Créer un nouveau livre :
    ```graphql
    mutation {
      createBook(bookInput: {title: "Nouveau Livre", author: "John Doe", publicationYear: 2023}) {
        id
        title
        author
      }
    }
    ```

- Mettre à jour un livre existant :
    ```graphql
    mutation {
      updateBook(id: 1, bookInput: {title: "Livre Mis à Jour", author: "Jane Doe", publicationYear: 2022}) {
        id
        title
        author
      }
    }
    ```

- Supprimer un livre :
    ```graphql
    mutation {
      deleteBook(id: 1)
    }
    ```

## Améliorations Futures
- Ajouter l'authentification et l'autorisation à l'API.
- Implémenter la pagination pour les ensembles de données volumineux.
- Remplacer la base de données en mémoire H2 par une base de données de production.
