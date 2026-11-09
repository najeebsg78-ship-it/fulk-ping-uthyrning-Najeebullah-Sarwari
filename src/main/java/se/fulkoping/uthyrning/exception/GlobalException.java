package se.fulkoping.uthyrning.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler (NotFoundException.class)
    public ResponseEntity<FelSvar> notFoundEx (NotFoundException ex) {
        FelSvar svar = new FelSvar (404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(svar);
    }

    @ExceptionHandler (ConflictException.class)
        public ResponseEntity<FelSvar> conflictEx (ConflictException ex) {
        FelSvar svar = new FelSvar(409, ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(svar);
    }

    @ExceptionHandler (MethodArgumentNotValidException.class)
    public ResponseEntity<FelSvar> notValidEx (MethodArgumentNotValidException ex) {
        FelSvar svar = new FelSvar(400, "fel i validering");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(svar);
    }

    @ExceptionHandler (HttpMessageNotReadableException.class)
    public ResponseEntity<FelSvar> wrongJson (HttpMessageNotReadableException ex) {
        FelSvar svar = new FelSvar(400, "fel i json");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(svar);
    }

    @ExceptionHandler (MethodArgumentTypeMismatchException.class)
    public ResponseEntity<FelSvar> wrongParameter (MethodArgumentTypeMismatchException ex){
        FelSvar svar = new FelSvar(400, "fel i parameter");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(svar);
    }


}
