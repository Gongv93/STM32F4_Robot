
public class CommandParser {
	// Given a command we use this class to find the command
	// and the how long you want to perform that command
	String cmd;
	int time;
	
	public CommandParser(String cmdLine) throws CommandException {
		int i, spaceIndex = 0, wordCount=0;
		
		// Check if acceptable format
		// The reason why less than 4
		// is because the shortest command can only have 3 chars
		// Example: char <space> int
		if( cmdLine.length() < 3 ) {
			throw new CommandException(cmdLine);
		}
		
		// Checks if it is 2 words by finding a space
		// This also deals with <space> <space> char and char <space> <space>
		for( i=0; i<cmdLine.length(); i++ ) {
			if( cmdLine.charAt(i) == ' ' ) {
				wordCount++;
				spaceIndex = i;
			}
		}
		
		if( wordCount != 1 ) {
			throw new CommandException(cmdLine);
		}
		
		cmd = getWord(cmdLine, 0, spaceIndex);
		
		try {
			time = Integer.parseInt( getWord(cmdLine, spaceIndex+1, cmdLine.length()) );
		} catch( NumberFormatException e ) {
			throw new CommandException(cmdLine);
		}
	}
	
	private String getWord(String s, int offset, int end) {
		// Returns a string from offset to end
		String retString;
		retString = new String();
		int i;
		
		for( i=offset; i < end; i++ ) {
			retString += s.charAt(i);
		}
		
		return retString;
	}
	
	public String getCmd() {
		return cmd;
	}
	
	public int getTime() {
		return time;
	}
	
}
