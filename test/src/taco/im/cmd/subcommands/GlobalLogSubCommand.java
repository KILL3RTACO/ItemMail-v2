package taco.im.cmd.subcommands;

public class GlobalLogSubCommand {

	private String[] aliases = new String[]{"global-log", "globallog", "log", "gl", "l"};
	
	public void execute(){
		
	}
	
	public boolean hasAlias(String name){
		for(String s : aliases){
			if(name.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
}
