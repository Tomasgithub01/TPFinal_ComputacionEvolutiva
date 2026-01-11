package Components;

import java.util.ArrayList;

import Model.Path;

public interface SurvivorsSelectionMethod {
    ArrayList<Path> selectSurvivors(ArrayList<Path> generacionVieja, ArrayList<Path> hijos);
}

