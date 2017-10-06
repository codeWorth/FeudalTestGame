package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import graphics.Camera;
import graphics.Drawable;
import physics.PhysicsObject;
import physics.Timeout;
import sprites.mobs.Player;
import sprites.statics.Tree;

public class World {

	public static Graphics2D ctx;
	
	public static Timer timer;
	public static int timerDelay = 100;
	
	public static Player player;
	private static ArrayList<Drawable> visuals = new ArrayList<Drawable>();
	private static ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>(); 
	private static ArrayList<Timeout> timeouts = new ArrayList<Timeout>();
	
	private static ArrayList<PhysicsObject> objectsToRemove = new ArrayList<PhysicsObject>();
	private static ArrayList<Drawable> visualsToRemove = new ArrayList<Drawable>();
	private static ArrayList<Timeout> timeoutsToRemove = new ArrayList<Timeout>();
	
	public static long score = 0;
	public static long enemies = 0;
	
	/**
	 * In milliseconds
	 */
	public static long time = 0;

	private static Timer spawnTimer;
	
	public static void initialize() {		
		player = new Player();
		add(new Tree(500, 500, 50));
		
		/* add(new AIShip(1200, 1200));
		add(new AIShip(0, 1200));
		add(new AIShip(1200, 2400));
		enemies += 3; */
		
		timer = new Timer(timerDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player.timeTick();
				time += timerDelay;
				
				int size = timeouts.size();
				for (int i = size - 1; i >= 0; i--) {
					Timeout out = timeouts.get(i);
					out.timeTick();
					
					if (out.timeLeft() <= 0) {
						remove(out);
					}
				}
				
				size = timeoutsToRemove.size();
				for (int i = size - 1; i >= 0; i--) {
					Object toRemoveObj = timeoutsToRemove.get(i);
					timeouts.remove(toRemoveObj);
					timeoutsToRemove.remove(i);
				}
			}
		});
		timer.setRepeats(true);
		timer.start();
		
		/* Random rand = new Random();
		spawnTimer = new Timer(6000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (enemies < 15) {
					for (int i = 0; i < 3; i++) {
						add(new AIShip(rand.nextInt(2400) - 1200, rand.nextInt(2400) - 1200));
						enemies++;
					}
				}
			}
		});
		spawnTimer.setRepeats(true);
		spawnTimer.start(); */
	}
	
	public static void graphicsUpdate() {
		int size = visuals.size();
		for (int i = 0; i < size; i++) {
			Drawable draw = visuals.get(i);
			if ( draw.inCameraWindow() ) {
				draw.draw(ctx);
			}
		}
		player.draw(ctx);
		
		drawPlayerStats();
				
		size = visualsToRemove.size();
		for (int i = size - 1; i >= 0; i--) {
			Object toRemoveObj = visualsToRemove.get(i);
			visuals.remove(toRemoveObj);
			visualsToRemove.remove(i);
		}
	}
	
	public static void drawPlayerStats() {
		
		//String stats = player.equipedWeapon.ammoInClip() + " / " + player.equipedWeapon.totalAmmo(); 
		String health = Double.toString(player.health());
		String scoreLength = "Score " + Long.toString(score);
		
		ctx.setColor(Color.WHITE);
		
		//ctx.drawString(stats, 10, Camera.CAM_HEIGHT - 35);
		ctx.drawString(health, Camera.CAM_WIDTH - 45, Camera.CAM_HEIGHT - 35);
		ctx.drawString(scoreLength, 10, 20);
		ctx.drawString(Long.toString(enemies), Camera.CAM_WIDTH - 45, 20);
		
	}
	
	public static void physicsUpdate(double dT) {			
		player.update(dT);	
		int size = objects.size();
		
		for (int i = 0; i < size; i++) {
			PhysicsObject obj1 = objects.get(i);
			obj1.update(dT);
			collide(player, obj1);
			
			for (int j = i + 1; j < size; j++) {
				collide(obj1, objects.get(j));
			}
		}
		
		size = objectsToRemove.size();
		for (int i = size - 1; i >= 0; i--) {
			Object toRemoveObj = objectsToRemove.get(i);
			objects.remove(toRemoveObj);
			objectsToRemove.remove(i);
		}
	}
	
	private static void collide(PhysicsObject obj1, PhysicsObject obj2) {
		if (obj1.shouldCollideWithObject(obj2)) {
			obj1.collideWithObject(obj2);
		}
		
		if (obj2.shouldCollideWithObject(obj1)) {
			obj2.collideWithObject(obj1);
		}
	}
	
	public static void remove(Object obj) {
		if (obj instanceof PhysicsObject) {
			objectsToRemove.add((PhysicsObject) obj);
		}
		if (obj instanceof Drawable) {
			visualsToRemove.add((Drawable) obj);
		}
		if (obj instanceof Timeout) {
			timeoutsToRemove.add((Timeout) obj);
		}
	}
	
	public static void add(Object obj) {
		if (obj instanceof PhysicsObject) {
			objects.add((PhysicsObject)obj);
		}
		if (obj instanceof Drawable) {
			visuals.add((Drawable)obj);
		}
		if (obj instanceof Timeout) {
			timeouts.add((Timeout)obj);
		}
	}
	
}
