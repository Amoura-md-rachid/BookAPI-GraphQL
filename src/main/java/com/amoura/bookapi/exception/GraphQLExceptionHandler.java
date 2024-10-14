package com.amoura.bookapi.exception;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Component
@ControllerAdvice
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        // Si l'exception est une EntityNotFoundException, elle correspond à une ressource non trouvée.
        if (ex instanceof EntityNotFoundException) {
            return GraphqlErrorBuilder.newError()  // Construction d'un objet GraphQLError
                    .errorType(ErrorType.NOT_FOUND)  // Spécification du type d'erreur comme "NOT_FOUND"
                    .message(ex.getMessage())  // Message d'erreur spécifique de l'exception
                    .path(env.getExecutionStepInfo().getPath())  // Chemin dans la requête GraphQL où l'erreur s'est produite
                    .location(env.getField().getSourceLocation())  // Emplacement exact dans le champ de la requête où l'erreur a eu lieu
                    .build();  // Construction finale de l'objet GraphQLError
        }
        // Si l'exception est une EntityUpdateException, elle indique un problème lors de la mise à jour d'une entité.
        else if (ex instanceof EntityUpdateException) {
            return GraphqlErrorBuilder.newError()  // Construction d'un objet GraphQLError
                    .errorType(ErrorType.INTERNAL_ERROR)  // Spécification du type d'erreur comme "INTERNAL_ERROR"
                    .message(ex.getMessage())  // Message d'erreur spécifique de l'exception
                    .path(env.getExecutionStepInfo().getPath())  // Chemin dans la requête GraphQL où l'erreur s'est produite
                    .location(env.getField().getSourceLocation())  // Emplacement exact dans le champ de la requête où l'erreur a eu lieu
                    .build();  // Construction finale de l'objet GraphQLError
        }
        // Pour toute autre exception non prévue, une erreur générique est renvoyée.
        else {
            return GraphqlErrorBuilder.newError()  // Construction d'un objet GraphQLError
                    .errorType(ErrorType.INTERNAL_ERROR)  // Spécification du type d'erreur comme "INTERNAL_ERROR" pour les erreurs inattendues
                    .message("An unexpected error occurred")  // Message générique pour une erreur imprévue
                    .path(env.getExecutionStepInfo().getPath())  // Chemin dans la requête GraphQL où l'erreur s'est produite
                    .location(env.getField().getSourceLocation())  // Emplacement exact dans le champ de la requête où l'erreur a eu lieu
                    .build();  // Construction finale de l'objet GraphQLError
        }
    }
}
