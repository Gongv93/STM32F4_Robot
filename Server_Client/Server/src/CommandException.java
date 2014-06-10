
public class CommandException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7740526329009089046L;

	public CommandException(String s) {
		System.out.println("\"" + s + "\" Is not a valid command." );
	}
}
