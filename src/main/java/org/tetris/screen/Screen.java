package org.tetris.screen;

import org.tetris.Game;
import org.tetris.Tetris;
import org.tetris.event.KeyPressEvent;
import org.tetris.event.KeyReleaseEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Screen extends JFrame {
	DrawerPanel drawer;
	public KeyListener listener = new KeyListener() {
		
		final java.util.List<Integer> isPressed = new ArrayList<>();
		
		@Override
		public void keyTyped(KeyEvent e) {
			// this does nothing
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if (!isPressed.contains(e.getKeyCode())) {
				Tetris.getGame().callEvent(new KeyPressEvent(e));
				isPressed.add(e.getKeyCode());
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			isPressed.remove((Object) e.getKeyCode());
			Tetris.getGame().callEvent(new KeyReleaseEvent(e));
		}
	};
	public Screen() {
		setTitle("Tetris");
		setSize(675, 700);
		setVisible(true);
		setIconImage(Toolkit.getDefaultToolkit().createImage(Tetris.class.getResource("/assert/icon.png")));
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		drawer = new DrawerPanel();
		add(drawer);
		
		
		addKeyListener(listener);
	}
}
