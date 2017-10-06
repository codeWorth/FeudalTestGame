package util.input;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import graphics.Camera;

public class InputBinds {

	public static Keybind forward = new Keybind(KeyEvent.VK_W);
	public static Keybind back = new Keybind(KeyEvent.VK_S);
	public static Keybind left = new Keybind(KeyEvent.VK_A);
	public static Keybind right = new Keybind(KeyEvent.VK_D);
	public static Keybind shoot = new Keybind(KeyEvent.VK_1);
	public static Keybind enemyShoot = new Keybind(KeyEvent.VK_2);
	
	public static MouseBind direction = new MouseBind();
	
	public static void bind(JFrame bindTo) {
		bindTo.addKeyListener(forward);
		bindTo.addKeyListener(back);
		bindTo.addKeyListener(shoot);
		bindTo.addKeyListener(enemyShoot);
		bindTo.addKeyListener(left);
		bindTo.addKeyListener(right);
		
		bindTo.addMouseMotionListener(direction);
		direction.centerX = Camera.CAM_WIDTH/2;
		direction.centerY = Camera.CAM_HEIGHT/2;
	}
	
}
