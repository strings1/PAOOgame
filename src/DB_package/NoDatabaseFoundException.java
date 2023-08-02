package DB_package;

public class NoDatabaseFoundException extends Exception{
    public NoDatabaseFoundException(String s)
    {
        super(s);
    }
}
