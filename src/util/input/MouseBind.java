package util.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import util.actions.Action;

public class MouseBind implements MouseMotionListener {

	public double mouseX = 0;
	public double mouseY = 0;
	private ArrayList<Action> moveActions = new ArrayList<Action>();
	
	public double centerX = 0;
	public double centerY = 0;
	
	public double angle = -Math.PI/2;
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}

	public void addMoveAction(Action action) {
		this.moveActions.add(action);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX() - centerX;
		this.mouseY  = e.getY() - centerY;
				
		this.angle = Math.atan2(mouseY, mouseX);
		
		for (Action act : moveActions) {
			act.execute();
		}
	}
	
	/**
	 * Length squared, high performace
	 * @return The length as a double
	 */
	public double length2() {
		return mouseX*mouseX + mouseY*mouseY;
	}
	
}
