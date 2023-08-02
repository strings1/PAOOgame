package DB_package;

public interface DB_Command {
    void execute() throws NoDatabaseFoundException;
}
