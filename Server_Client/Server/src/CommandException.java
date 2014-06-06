
public class CommandException extends Exception {
	public CommandException(String s) {
		System.out.println("\"" + s + "\" Is not a valid command." );
	}
}
