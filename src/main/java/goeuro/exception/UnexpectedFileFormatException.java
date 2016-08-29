package goeuro.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnexpectedFileFormatException extends RuntimeException{
    public UnexpectedFileFormatException(final Throwable e){
        super(e);
    }
}
