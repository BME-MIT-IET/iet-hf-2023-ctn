package Editor.Menu;

import Editor.SzituacioEpitoMenu;
import Editor.ViroMenu.*;
import Editor.ViroMenu.AnyagHozzaadasa;
import Editor.ViroMenu.Medve;
import Logika.Jatek;
import Logika.Virologus;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UjViro implements MainMenuItem {

    ArrayList<ViroMenuItem> menu = new ArrayList<ViroMenuItem>(){
        {
            add(new AgensHozzaadasa());
            add(new VethetoAgensHozzaadasa());
            add(new AnyagHozzaadasa());
            add(new Elkeszult());
            add(new Megse());
            add(new Medve());
            add(new VedofelszerelesHozzaadasa());
            add(new AktivFelszerelesHozzaadasa());
        }
    };


    @Override
    public String getName() {
        return "Uj Virologus";
    }

    @Override
    public void exec(String args_, SzituacioEpitoMenu.GenState state) throws Exception {
        System.out.println("Uj virologus ("+ args_+") letrehozasa, a lehetosegeid:");
        for (var it : menu){
            System.out.println(it.printDescription());
        }
        int res = 1;
        var scanner = new Scanner(System.in);
        var viro = new Virologus();
        viro.setNev(args_);
        while (res >= 0){
            try{
                System.out.print(">");
                String in;
                String args;
                String currentSuggestion = "";
                var line = scanner.nextLine().toLowerCase(Locale.ROOT);

                var potential_args = "";
                String finalIn = line;
                if(line.contains(" ")) {
                    potential_args = line.substring(line.lastIndexOf(" ") + 1);
                    finalIn = line.substring(0, line.lastIndexOf(" "));
                }

                String finalIn1 = finalIn;
                var itemsPossible = menu.stream().filter(p->p.getName().toLowerCase(Locale.ROOT).startsWith(finalIn1)).collect(Collectors.toList());
                System.out.print("\r");
                if(itemsPossible.size() > 0)
                {
                    if(itemsPossible.size() == 1){
                        if(itemsPossible.get(0).getName().contains(potential_args))
                            potential_args = "";
                        System.out.println("Menu item matched: " + itemsPossible.get(0).getName() + " with arguments: " + potential_args);
                        try{
                            res = itemsPossible.get(0).exec(potential_args, viro);
                        }catch (Exception e){
                            System.out.println(e.getMessage());
                        }
                    }
                    else
                    {
                        System.out.println("Nem sikerult egyetlen elemet azonositani a bemenet alapjan.");
                    }
                    System.out.println(itemsPossible);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(res == 1){
                state.getCurrent().belepes(viro);
                viro.setStartTerulet(state.getCurrent());
                Jatek.getInstance().getJatekosok().add(viro);
                if (Jatek.getInstance().getJatekosok().size() == 1) Jatek.getInstance().setAktiv(0);
                break;
            }
        }
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[]{"nev"};
    }
}