package goeuro.exception;


public class CacheLoadException extends RuntimeException{
    public CacheLoadException(final Throwable e){
        super(e);
    }
}
