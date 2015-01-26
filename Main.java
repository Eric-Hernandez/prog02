package prog02;

/**
 *
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
             to talk to the user.
      @param pd The PhoneDirectory object to use
             to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry",
				"Look Up Entry",
				"Remove Entry",
				"Save Directory",
		"Exit"};

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case 0:
				name = ui.getInfo("Enter the name");
				// !!! Check for null (cancel) or "" (blank)
				if (name == null || name.equals("")) {
					break;
				}
				number = ui.getInfo("Enter the number");
				// !!! Check for cancel.  Blank is o.k.
				if (number == null){
					break;
				}
				oldNumber = pd.addOrChangeEntry(name, number);
				if (oldNumber == null) {
					ui.sendMessage(name + " was added to the directory\nNew number: "+ number);
				} else {
					ui.sendMessage("Number for " + name + " has been changed"+
							"\nOld number: " + oldNumber +
							"\nNew number: " + number);
				}
				break;
			case 1: //lookup entry
				name = ui.getInfo("Enter name");
				if (name == null || name.equals("")) {
					break;
				}
				
				number = pd.lookupEntry(name);
				if (number == null) {
					ui.sendMessage(name + " is not listed in the directory");
					break;
				} else {
					ui.sendMessage("The number for " + name + " is " + number);
				}
				break;
			case 2: //remove
				name = ui.getInfo("Enter name");
				if (name == null || name.equals("")) {
					break;
				}
				number = pd.lookupEntry(name);
				if (number == null) {
					ui.sendMessage(name + " is not listed in the directory.");
					break;
				} else {
					oldNumber = pd.removeEntry(name);
					ui.sendMessage(name + " has been removed from the database");
				}
				break;
			case 3: //save
				pd.save();
				break;
			case 4: //exit
				pd.save();
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI();
		processCommands(fn, ui, pd);
	}
}
