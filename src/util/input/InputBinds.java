package util.input;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import graphics.Camera;

public class InputBinds {

	public static Keybind forward = new Keybind(KeyEvent.VK_W);
	public static Keybind back = new Keybind(KeyEvent.VK_S);
	public static Keybind shoot = new Keybind(KeyEvent.VK_1);
	public static Keybind enemyShoot = new Keybind(KeyEvent.VK_2);
	public static Keybind reload = new Keybind(KeyEvent.VK_R);
	
	public static MouseBind move = new MouseBind();
	
	public static void bind(JFrame bindTo) {
		bindTo.addKeyListener(InputBinds.forward);
		bindTo.addKeyListener(InputBinds.back);
		bindTo.addKeyListener(InputBinds.shoot);
		bindTo.addKeyListener(enemyShoot);
		bindTo.addKeyListener(InputBinds.reload);
		
		bindTo.addMouseMotionListener(move);
		move.centerX = Camera.CAM_WIDTH/2;
		move.centerY = Camera.CAM_HEIGHT/2;
	}
	
}
