package com.learn.core.exception;

import io.quarkus.logging.Log;
import io.quarkus.runtime.configuration.ProfileManager;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;


@Provider
public class CustomExceptionMapper implements ExceptionMapper<RuntimeException> {
    
    @Override
    public Response toResponse(RuntimeException exception) {
        Log.error(exception);
        var activeRecord = ProfileManager.getActiveProfile();
        if(activeRecord.equals("dev") || activeRecord.equals("test")) {
            throw exception;
        }
        
        if(exception instanceof DataNotFoundException) {
            return send404Response(exception);
        }else if(exception instanceof FormatException || exception instanceof NullPointerException){
            return send400Response(exception);
        }else if(exception instanceof UnauthorizedAccessException) {
            return send401Response(exception);
        }else if(exception instanceof DataAlreadyExistException) {
            return send409Response(exception);
        }
        else {
            return send500Response(exception);
        }
    }
    
    private Response send400Response(RuntimeException exception) {
        return buildExceptionMessage(exception.getMessage(), Status.BAD_REQUEST);
    }

    private Response send401Response(RuntimeException exception) {
        return buildExceptionMessage(exception.getMessage(), Status.UNAUTHORIZED);
    }

    private Response send404Response(RuntimeException exception) {
        return buildExceptionMessage(exception.getMessage(), Status.NOT_FOUND);
    }

    private Response send409Response(RuntimeException exception) {
        return buildExceptionMessage(exception.getMessage(), Status.CONFLICT);
    }

    private Response send500Response(RuntimeException exception) {
        return buildExceptionMessage(exception.getMessage(), Status.INTERNAL_SERVER_ERROR);
    }

    private Response buildExceptionMessage(String message, Status status) {
        return Response.status(status)
            .entity(new ExceptionMessage(status.toString(), message))
            .build();
    }

    
    
}
