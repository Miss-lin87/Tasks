package sv.linda.tasks.handelers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import sv.linda.tasks.Constants;

@RestControllerAdvice
class ErrorHandler implements Constants{

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handelIOException(HttpServletRequest request, IOException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BASIC_ERROR + ex.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<String> handelFileNotFound(HttpServletRequest request, FileNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("File not found: " + ex.getMessage());
    }

    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseEntity<String> handelNoSuchMethod(HttpServletRequest request, NoSuchMethodException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BASIC_ERROR + ex.getMessage());
    }

    @ExceptionHandler(InvocationTargetException.class)
    public ResponseEntity<String> handelInvocationTarget(HttpServletRequest request, InvocationTargetException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BASIC_ERROR + ex.getMessage());
    }

    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<String> handelIllegalAccess(HttpServletRequest request, IllegalAccessException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BASIC_ERROR + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handelGeneralException(HttpServletRequest request, Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BASIC_ERROR + ex.getMessage());
    }
}