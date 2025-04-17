package se.yrgo.services.diary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import se.yrgo.domain.Action;

public class DiaryManagementServiceMockImpl implements DiaryManagementService {
	
	private Set<Action>allActions= new HashSet<Action>();
	private List<Action> actions = new ArrayList<>();

	@Override
	public void recordAction(Action action) {
		allActions.add(action);
		System.out.println("Registered todo task: " + action);
	}

	//Hint: 
	//Create a list<Action>
	//In the for each loop going through the list use this condition: "if(action.getOwningUser().equals(requiredUser) && !action.isComplete())" to add a new action to the list. 
	@Override
	public List<Action> getAllIncompleteActions(String requiredUser) {
		for (Action a : allActions) {
			if (a.getOwningUser().equals(requiredUser) && !a.isComplete()) {
				actions.add(a);
			}
		}
		return actions;
	}

}
