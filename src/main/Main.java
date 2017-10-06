package main;

import java.awt.EventQueue;

import javax.swing.JFrame;

import graphics.Camera;
import util.input.InputBinds;

public class Main extends JFrame {
	
	public static final double Chris = 6f;
		
	public Main() {
		initUI();
		
		InputBinds.bind(this);
	}

	private void initUI() {
		
		add(new Surface());
		
		setTitle("Space");
		setSize(Camera.CAM_WIDTH, Camera.CAM_HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		System.out.println("Chris is doubleing here: ");
		System.out.println(Chris);
		
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Main ex = new Main();
				ex.setVisible(true);
			}
		});
		
		/*Scanner sc = new Scanner(System.in);
		
		while (true) {
			System.out.println("Enter number");
			double num = (sc.nextDouble() - Math.PI) % (Math.PI*2);
			
			if (num < 0) {
				num += Math.PI;
			} else {
				num -= Math.PI;
			}
			
			System.out.println(num);
		}*/
		
	}
}
