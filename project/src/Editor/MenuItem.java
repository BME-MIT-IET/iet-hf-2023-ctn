package Editor;

public interface MenuItem {
    public String getName();
    public String[] getPossibleArgs();

    public default String printDescription(){
        StringBuilder sb = new StringBuilder();
        sb.append(getName());
        if (getPossibleArgs().length == 0){
            return sb.toString();
        }
        sb.append(" <");
        for(var argname : getPossibleArgs())
            sb.append( (!argname.equals(getPossibleArgs()[0])? "|" : "" ) + argname);
        sb.append(">");

        return sb.toString();
    }
}
