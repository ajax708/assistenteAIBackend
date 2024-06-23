package com.soporte.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler{

    private static final Logger LOGGER=Logger.getLogger(GlobalExceptionHandler.class.getName());
    @ExceptionHandler(ModelNotFoundException.class)
    public ResponseEntity<GlobalError> ModelNotFoundExceptionHandler(ModelNotFoundException e, WebRequest wr){
        GlobalError error=new GlobalError(LocalDateTime.now(),e.getMessage(),wr.getDescription(false));
        LOGGER.log(Level.INFO,"ModelNotFoundExceptionHandler :{0} ",new Object[]{e.getMessage()});
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalError> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, WebRequest wr){
        GlobalError error=new GlobalError(LocalDateTime.now(),e.getMessage(),wr.getDescription(false));
        LOGGER.log(Level.INFO,"MethodArgumentNotValidExceptionHandler :{0} ",new Object[]{e.getMessage()});
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<GlobalError> SQLExceptionHandler(SQLException e, WebRequest wr){
        GlobalError error=new GlobalError(LocalDateTime.now(),e.getMessage(),wr.getDescription(false));
        LOGGER.log(Level.INFO,"SQLExceptionHandler :{0} ",new Object[]{e.getMessage()});
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalError> ExceptionHandler(Exception e, WebRequest wr){
        GlobalError error=new GlobalError(LocalDateTime.now(),e.getMessage(),wr.getDescription(false));
        LOGGER.log(Level.INFO,"ExceptionHandler :{0} ",new Object[]{e.getMessage()});
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
